package com.kuilei.zhuyi.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.NewsFragmentPagerAdapter;
import com.kuilei.zhuyi.bean.ChannelItem;
import com.kuilei.zhuyi.fragment.NewsFragment_;
import com.kuilei.zhuyi.utils.BaseTools;
import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.view.LeftView;
import com.kuilei.zhuyi.webget.ColumnHorizontalScrollView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.main)
public class MainActivity extends BaseActivity {
    private final Class TAG = MainActivity.class;
    /**
     * 自定义HorizontalScrollView
     */
    @ViewById(R.id.mColumnHorizontalScrollView)
    protected ColumnHorizontalScrollView mColumnHorizontalScrollView;
    @ViewById(R.id.mRadioGroup_content)
    protected LinearLayout mRadioGroup_content;
    @ViewById(R.id.ll_more_columns)
    protected LinearLayout ll_more_columns;
    @ViewById(R.id.rl_column)
    protected RelativeLayout rl_column;
    @ViewById(R.id.button_more_columns)
    protected ImageView button_more_columns;
    @ViewById(R.id.mViewPager)
    protected ViewPager mViewPager;
    @ViewById(R.id.shade_left)
    protected ImageView shade_left;
    @ViewById(R.id.shade_right)
    protected ImageView shade_right;
    protected LeftView mLeftView;
    // protected SlidingMenu side_drawer;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth = 0;
    /**
     * Item宽度
     */
    private int mItemWidth = 0;
    /**
     * head 头部 的左侧菜单 按钮
     */
//    (R.id.top_head)
    protected ImageView top_head;
    /**
     * head 头部 的右侧菜单 按钮
     */
    //   (R.id.top_more)
    protected ImageView top_more;
    /**
     * 用户选择的新闻分类列表
     */
    protected static ArrayList<ChannelItem> userChannelLists;
    /**
     * 请求CODE
     */
    public final static int CHANNELREQUEST = 1;
    /**
     * 调整返回的RESULTCODE
     */
    public final static int CHANNELRESULT = 10;
    /**
     * 当前选中的栏目
     */
    private int columnSelectIndex = 0;
    private ArrayList<Fragment> fragments;

    private Fragment newfragment;
    private double back_pressed;

    public static boolean isChange = false;

    private NewsFragmentPagerAdapter mAdapetr;

    @AfterInject
    void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
       // UmengUpdateAgent.update(this);
       //MobclickAgent.updateOnlineConfig(this);
        mScreenWidth = BaseTools.getWindowsWidth(this);
        mItemWidth = mScreenWidth / 7;
        userChannelLists = new ArrayList<ChannelItem>();
        fragments = new ArrayList<Fragment>();
    }


    @AfterViews
    void initView() {
        Logger.w(TAG,"initView");
        try {
          //  initSlidingMenu();
            initViewPager();
            setChangeView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setChangeView() {
        initColumnData();
    }

    private void initColumnData() {
        Logger.w(TAG,"initColumnData");
        //   userChannelLists = ((ArrayList<ChannelItem>) ChannelManage.getManage(App.getApp.getSQLHelper()).getUserChannel());
        userChannelLists = initTestData();
        initTabColumn();
        initFragment();

    }

    private ArrayList<ChannelItem> initTestData() {
        ArrayList<ChannelItem> list = new ArrayList<ChannelItem>();
        ChannelItem channelItem = new ChannelItem(1, "head", 1, 1);
        list.add(channelItem);
        return list;
    }


    private void initFragment() {
        Logger.w(TAG,"ChannelLists size = " + userChannelLists.size());
        fragments.clear();
        int count = userChannelLists.size();
        for (int i = 0; i < count; i++) {
            String nameString = userChannelLists.get(i).getName();
            //fragments.add(initFragment(nameString));
            fragments.add(initFragment(nameString));
        }
        mAdapetr.appendList(fragments);
    }

     private Fragment initFragment(String channelName) {
         return new NewsFragment_();
     }

    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        int count = userChannelLists.size();
        mColumnHorizontalScrollView.setParam(this, mScreenWidth, mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;

            TextView columnTextView = new TextView(this);
            columnTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
            columnTextView.setBackgroundResource(R.drawable.radio_button_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setText(userChannelLists.get(i).getName());
            columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mRadioGroup_content.addView(columnTextView, i, params);
        }
    }

    private void initViewPager() {
        Logger.w(TAG,"initViewPager");
        mAdapetr = new NewsFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.addOnPageChangeListener(pageListener);
    }

    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void initSlidingMenu() {
 //       mLeftView = LeftView_.build(this);
//        side_drawer = Sli
    }

}
