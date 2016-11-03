package com.kuilei.zhuyi.webget.gesture;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by lenovog on 2016/11/3.
 */
public class EnableChildSlideFrameLayout extends FrameLayout {
    private ViewPager vp;

    private float lastX;
    private float lastY;
    private boolean isScrolling = false;

    public EnableChildSlideFrameLayout(Context context) {
        super(context);
    }

    public EnableChildSlideFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnableChildSlideFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
