package com.kuilei.zhuyi.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuilei.zhuyi.App;
import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.DragAdapter;
import com.kuilei.zhuyi.adapter.OtherAdapter;
import com.kuilei.zhuyi.bean.ChannelItem;
import com.kuilei.zhuyi.bean.ChannelManage;
import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.webget.DragGrid;
import com.kuilei.zhuyi.webget.OtherGridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by lenovog on 2016/10/30.
 */
@EActivity(R.layout.channel)
public class ChannelActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public final Class TAG = ChannelActivity.class;
    /** 用户栏目的GRIDVIEW */
    private DragGrid userGridView;
    /** 其它栏目的GRIDVIEW */
    private OtherGridView otherGridView;
    /** 用户栏目对应的适配器，可以拖动 */
    DragAdapter userAdapter;
    /** 其它栏目对应的适配器 */
    OtherAdapter otherAdapter;
    /** 其它栏目列表 */
    ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
    /** 用户栏目列表 */
    ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    /** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
    boolean isMove = false;
    /** 初始化布局 */
    @AfterViews
    void initView() {
        userGridView = (DragGrid) findViewById(R.id.userGridView);
        otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
        initData();
    }

    /** 初始化数据 */
    @Background
    void initData() {
        try {
            userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(
                    App.getApp().getSQLHelper()).getUserChannel());

            otherChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(
                    App.getApp().getSQLHelper()).getOtherChannel());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setData();
    }

    @UiThread
    void setData() {
        userAdapter = new DragAdapter(this, userChannelList);
        userGridView.setAdapter(userAdapter);
        otherAdapter = new OtherAdapter(this, otherChannelList);
        otherGridView.setAdapter(otherAdapter);
//         设置GRIDVIEW的ITEM的点击监听
        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (isMove) {
            return;
        }
        switch (parent.getId()) {
            case R.id.userGridView:
                Logger.w(TAG,"userGridView onItemClick");
                if (position != 0 && position != 1) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channelItem = (ChannelItem) ((DragAdapter) parent.getAdapter()).getItem(position);
                        otherAdapter.setVisible(false);
                        otherAdapter.addItem(channelItem);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    moveAnim(moveImageView, startLocation, endLocation,channelItem, userGridView);
                                    userAdapter.setRemove(position);
                                    ChannelManage.getManage(App.getApp().getSQLHelper()).updateChannel(channelItem,"0");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },50L);
                    }
                }
                break;
            case R.id.otherGridView:
                Logger.w(TAG,"otherGridView onItemClick");
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channelItem = (ChannelItem) ((OtherAdapter) parent.getAdapter()).getItem(position);
                        userAdapter.setVisible(false);
                        userAdapter.addItem(channelItem);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    // 获取终点的坐标
                                    userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    moveAnim(moveImageView, startLocation, endLocation,channelItem, otherGridView);
                                    otherAdapter.setRemove(position);
                                    ChannelManage.getManage(App.getApp().getSQLHelper()).updateChannel(channelItem,"1");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },50L);
                    }
                break;
            default:
                break;
        }
    }

    /**
     * 点击ITEM移动动画
     *
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void moveAnim(View moveView, int[] startLocation, int[] endLocation,
                          final ChannelItem moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
// 获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        // 得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);


        TranslateAnimation moveAnimation = new TranslateAnimation(startLocation[0],endLocation[0],startLocation[1],endLocation[1]);
        moveAnimation.setDuration(300L);

        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                } else {
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     *
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    @Override
    public void finish() {
        super.finish();
        Logger.w(ChannelActivity.class,"isListChanged = " + userAdapter.isListChanged());
        if (userAdapter.isListChanged()) {
            MainActivity_.isChange = true;
            Logger.w(TAG,"channelActivity finish data change.");
        }
    }
}
