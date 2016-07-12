package com.kuilei.zhuyi.initview;

import android.app.Activity;
import android.view.View;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.webget.slidingMenu.SlidingMenu;

/**
 * Created by lenovog on 2016/7/12.
 */
public class SlidingMenuView {
    private static SlidingMenuView slidingMenuView;

    public SlidingMenu slidingMenu;

    private SlidingMenu.CanvasTransformer mTransformer;

    public static SlidingMenuView instance() {
        if (slidingMenuView == null) {
            slidingMenuView = new SlidingMenuView();
        }
        return slidingMenuView;
    }

    public SlidingMenu initSlidingMenuView(Activity activity, View view) {

        slidingMenu = new SlidingMenu(activity);
        slidingMenu.setMode(SlidingMenu.LEFT);// 设置左滑菜单
        slidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW);// 设置要使菜单滑动，触碰屏幕的范围
     //   slidingMenu.setShadowWidth(R.dimen.shadow_width);// 设置阴影图片的宽度
        slidingMenu.setShadowDrawable(R.drawable.shadow);// 设置阴影图片
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// SlidingMenu划出时主页面显示的剩余宽度
        slidingMenu.setFadeDegree(0.35F);// SlidingMenu滑动时的渐变程度
        slidingMenu.attachToActivity(activity, SlidingMenu.SLIDING_WINDOW);// 使SlidingMenu附加在Activity右边
        slidingMenu.setMenu(view);// 设置menu的布局文件
        return slidingMenu;
    }
}
