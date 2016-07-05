package com.kuilei.zhuyi.utils;

import android.content.Context;

import com.kuilei.zhuyi.http.NetWorkHelper;

/**
 * Created by lenovog on 2016/7/4.
 */
public class HttpUtil {


    public static boolean isNetworkAvailable(Context context)
    {
        return NetWorkHelper.isNetworkAvailable(context);
    }

}
