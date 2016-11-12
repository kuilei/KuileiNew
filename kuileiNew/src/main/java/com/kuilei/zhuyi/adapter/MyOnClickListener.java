package com.kuilei.zhuyi.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by lenovog on 2016/11/8.
 */
public class MyOnClickListener implements View.OnClickListener {
    private int index = 0;
    private final ViewPager viewPager;

    public MyOnClickListener(int i, ViewPager viewPager) {
        index = i;
        this.viewPager = viewPager;
    }

    @Override
    public void onClick(View v) {
        viewPager.setCurrentItem(index);
    }
}