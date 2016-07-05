package com.kuilei.zhuyi.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * Created by lenovog on 2016/7/4.
 */
public class NetWorkHelper {
    private static String TAG = NetWorkHelper.class.toString();

    public static Uri uri = Uri.parse("content://telephony/carriers");

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.
        CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
        {
            return false;
        } else {
            NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
            if (infos != null)
            {
                for (int i = 0; i < infos.length; i++)
                {
                    if (infos[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
