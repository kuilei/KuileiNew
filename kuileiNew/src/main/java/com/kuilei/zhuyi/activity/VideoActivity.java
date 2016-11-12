package com.kuilei.zhuyi.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.MyOnClickListener;
import com.kuilei.zhuyi.adapter.NewsFragmentPagerAdapter;
import com.kuilei.zhuyi.fragment.VideoReDianFragment_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by lenovog on 2016/11/12.
 */
@EActivity(R.layout.activity_video)
public class VideoActivity extends BaseActivity {
    @ViewById(R.id.vPager)
    protected ViewPager mViewPager;
    @ViewById(R.id.video_redian)
    protected RadioButton mReDian;
    @ViewById(R.id.video_yule)
    protected RadioButton mYuLe;
    @ViewById(R.id.video_gaoxiao)
    protected RadioButton mGaoXiao;
    @ViewById(R.id.video_jingpin)
    protected RadioButton mJingPin;
    @ViewById(R.id.meitu)
    protected RadioButton mMeiTu;
    @ViewById(R.id.title)
    protected TextView mTitle;

    private ArrayList<Fragment> fragments;

    @AfterInject
    public void init() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new VideoReDianFragment_());
//        fragments.add(new VideoYuLeFragment_());
//        fragments.add(new VideoGaoXiaoFragment_());
//        fragments.add(new VideoJingPinFragment_());
    }


    @AfterViews
    public void initView() {
        try {
            mTitle.setText("视频新闻");
            initPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPager() {
        mViewPager.setOffscreenPageLimit(2);
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(
                getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mReDian.setOnClickListener(new MyOnClickListener(0, mViewPager));
        mYuLe.setOnClickListener(new MyOnClickListener(1, mViewPager));
        mGaoXiao.setOnClickListener(new MyOnClickListener(2, mViewPager));
        mJingPin.setOnClickListener(new MyOnClickListener(3, mViewPager));
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            mViewPager.setCurrentItem(position);
            switch (position) {
                case 0:
                    setRadioButtonCheck(true, false, false, false);
                    break;
                case 1:
                    setRadioButtonCheck(false, true, false, false);
                    break;
                case 2:
                    setRadioButtonCheck(false, false, true, false);
                    break;
                case 3:
                    setRadioButtonCheck(false, false, false, true);
                    break;
            }
        }

    }



    private void setRadioButtonCheck(boolean b, boolean c, boolean d, boolean e) {
        mReDian.setChecked(b);
        mYuLe.setChecked(c);
        mGaoXiao.setChecked(d);
        mJingPin.setChecked(e);
    }

}
