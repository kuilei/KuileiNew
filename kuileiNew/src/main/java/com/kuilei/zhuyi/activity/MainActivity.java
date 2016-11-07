package com.kuilei.zhuyi.activity;

import android.content.Intent;
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

import com.kuilei.zhuyi.App;
import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.NewsFragmentPagerAdapter;
import com.kuilei.zhuyi.bean.ChannelItem;
import com.kuilei.zhuyi.bean.ChannelManage;
import com.kuilei.zhuyi.fragment.NewsFragment_;
import com.kuilei.zhuyi.initview.SlidingMenuView;
import com.kuilei.zhuyi.utils.BaseTools;
import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.view.SlideMenuLeftView;
import com.kuilei.zhuyi.view.SlideMenuLeftView_;
import com.kuilei.zhuyi.webget.ColumnHorizontalScrollView;
import com.kuilei.zhuyi.webget.slidingMenu.SlidingMenu;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
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
    protected SlideMenuLeftView mSlideMenuLeftView;
     protected SlidingMenu side_drawer;
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
    @ViewById(R.id.top_more)
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
            initSlidingMenu();
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
        try {
            userChannelLists = ((ArrayList<ChannelItem>) ChannelManage.getManage(App.getApp().getSQLHelper()).getUserChannel());
        } catch (SQLException e) {
            e.printStackTrace();
        }

     //   userChannelLists = initTestData();
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
      //   if (channelName.equals("head title")) {
      //       return new NewsFragment_();
     //    } else if (channelName.equals("football")) {
//             return new EntertainFragment_();
     //    }
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
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }

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

    @Click(R.id.button_more_columns)
    protected void onMoreColumns(View view) {
        Logger.w(TAG,"onMoreColumns");
        openActivityForResult(ChannelActivity_.class, CHANNELREQUEST);
    }

    @Click(R.id.top_head)
    protected void onMenu(View view) {
        if (side_drawer.isMenuShowing()) {
            side_drawer.showContent();
        }else {
            side_drawer.showMenu();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (isChange) {
                setChangeView();
                isChange = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }
        /**
         * 选择的Column里面的Tab
         */
        private void selectTab(int tab_position) {
            columnSelectIndex = tab_position;
            for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                View checkView = mRadioGroup_content.getChildAt(tab_position);
                int k = checkView.getMeasuredWidth();
                int l = checkView.getLeft();
                int i2 = l + k / 2 - mScreenWidth / 2;
                mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
            }
// 判断是否选中
            for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
                View checkView = mRadioGroup_content.getChildAt(j);
                boolean ischeck;
                if (j == tab_position) {
                    ischeck = true;
                }else {
                    ischeck = false;
                }
                checkView.setSelected(ischeck);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void initSlidingMenu() {
        mSlideMenuLeftView = SlideMenuLeftView_.build(this);
        side_drawer = SlidingMenuView.instance().initSlidingMenuView(this, mSlideMenuLeftView);
    }

}
