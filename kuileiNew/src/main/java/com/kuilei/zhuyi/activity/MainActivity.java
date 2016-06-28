package com.kuilei.zhuyi.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.bean.ChannelItem;
import com.kuilei.zhuyi.utils.BaseTools;
import com.kuilei.zhuyi.view.LeftView;
import com.kuilei.zhuyi.view.LeftView_;
import com.kuilei.zhuyi.webget.ColumnHorizontalScrollView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    /** 自定义HorizontalScrollView */
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
    /** 屏幕宽度 */
    private int mScreenWidth = 0;
    /** Item宽度 */
    private int mItemWidth = 0;
    /** head 头部 的左侧菜单 按钮 */
//    (R.id.top_head)
    protected ImageView top_head;
    /** head 头部 的右侧菜单 按钮 */
 //   (R.id.top_more)
    protected ImageView top_more;
    /** 用户选择的新闻分类列表 */
    protected static ArrayList<ChannelItem> userChannelLists;
    /** 请求CODE */
    public final static int CHANNELREQUEST = 1;
    /** 调整返回的RESULTCODE */
    public final static int CHANNELRESULT = 10;
    /** 当前选中的栏目 */
    private int columnSelectIndex = 0;
    private ArrayList<Fragment> fragments;

    private Fragment newfragment;
    private double back_pressed;

    public static boolean isChange = false;

  //  private NewsFragmentPagerAdapter mAdapetr;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
    @AfterInject
    void init()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        UmengUpdateAgent.update(this);
        MobclickAgent.updateOnlineConfig(this);
        mScreenWidth = BaseTools.getWindowsWidth(this);
        mItemWidth = mScreenWidth / 7;
        userChannelLists = new ArrayList<ChannelItem>();
        fragments = new ArrayList<Fragment>();
    }

    void initViews()
    {
        try {
            initSlidingMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSlidingMenu() {
        mLeftView = LeftView_.build(this);
//        side_drawer = Sli
    }

}
