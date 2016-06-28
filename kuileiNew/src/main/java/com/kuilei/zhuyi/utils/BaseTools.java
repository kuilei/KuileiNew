package com.kuilei.zhuyi.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by lenovog on 2016/6/28.
 */
public class BaseTools {
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
