package com.kuilei.zhuyi.utils;

import android.content.Context;

import com.kuilei.zhuyi.http.NetWorkHelper;

/**
 * Created by lenovog on 2016/7/12.
 */
public class HttpUtils {

    /**
     * 判断网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context)
    {
        return NetWorkHelper.isNetworkAvailable(context);
    }
}
