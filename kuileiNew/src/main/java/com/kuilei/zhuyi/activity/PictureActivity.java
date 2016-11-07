package com.kuilei.zhuyi.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;

import com.kuilei.zhuyi.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by lenovog on 2016/11/7.
 */
@EActivity(R.layout.activity_picture)
public class PictureActivity extends BaseActivity {
    @ViewById(R.id.vPager)
    protected ViewPager mViewPager;
    @ViewById(R.id.redian)
    protected RadioButton mReDian;
    @ViewById(R.id.dujia)
    protected RadioButton mDuJia;
    @ViewById(R.id.mingxing)
    protected RadioButton mMingXing;
    @ViewById(R.id.titan)
    protected RadioButton mTiTan;
    @ViewById(R.id.meitu)
    protected RadioButton mMeiTu;

    private ArrayList<Fragment> fragments;


    @AfterInject
    public void init() {
        fragments = new ArrayList<Fragment>();
//        fragments.add(new TuPianReDianFragment_());
//        fragments.add(new TuPianDuJiaFragment_());
//        fragments.add(new TuPianMingXingFragment_());
//        fragments.add(new TuPianTiTanFragment_());
//        fragments.add(new TuPianMeiTuFragment_());
    }

    @AfterViews
    public void initView() {
        try {
            initPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPager() {
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
//        mReDian.setOnClickListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
