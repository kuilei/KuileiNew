package com.kuilei.zhuyi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.kuilei.zhuyi.webget.slideingactivity.SlidingActivity;

/**
 * Created by lenovog on 2016/6/28.
 */
public class BaseActivity extends SlidingActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        initGestureDetector();

    }

    private void initGestureDetector() {

    }
}
