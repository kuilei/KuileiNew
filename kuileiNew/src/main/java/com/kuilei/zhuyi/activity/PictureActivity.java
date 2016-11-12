package com.kuilei.zhuyi.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.MyOnClickListener;
import com.kuilei.zhuyi.adapter.NewsFragmentPagerAdapter;
import com.kuilei.zhuyi.fragment.PictureDuJiaFragment_;
import com.kuilei.zhuyi.fragment.PictureReDianFragment_;
import com.kuilei.zhuyi.utils.Logger;

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

    private final Class TAG = PictureActivity.class;

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

    private NewsFragmentPagerAdapter mAdapetr;
    @AfterInject
    public void init() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new PictureReDianFragment_());
        fragments.add(new PictureDuJiaFragment_());
//        fragments.add(new TuPianMingXingFragment_());
//        fragments.add(new TuPianTiTanFragment_());
//        fragments.add(new TuPianMeiTuFragment_());
        mAdapetr = new NewsFragmentPagerAdapter(getSupportFragmentManager(),fragments);
    }

    @AfterViews
    public void initView() {
        Logger.w(TAG,"initViewPager");
        try {
            initPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPager() {
        Logger.w(TAG,"mAdapetr = " + mAdapetr.getCount());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mReDian.setOnClickListener(new MyOnClickListener(0, mViewPager));
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mViewPager.setCurrentItem(position);
            switch (position) {
                case 0:
                    setRadioButtonCheck(true, false, false, false, false);
                    break;
                case 1:
                    setRadioButtonCheck(false, true, false, false, false);
                    break;
                case 2:
//                    setRadioButtonCheck(false, false, true, false, false);
                    break;
                case 3:
//                    setRadioButtonCheck(false, false, false, true, false);
                    break;
                case 4:
//                    setRadioButtonCheck(false, false, false, false, true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    private void setRadioButtonCheck(boolean b, boolean c, boolean d, boolean e, boolean f) {
        mReDian.setChecked(b);
        mDuJia.setChecked(c);
        mMingXing.setChecked(d);
        mTiTan.setChecked(e);
        mMeiTu.setChecked(f);
    }
}
