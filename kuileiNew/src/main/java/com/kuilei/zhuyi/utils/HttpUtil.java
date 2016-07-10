package com.kuilei.zhuyi.utils;

import android.content.Context;

import com.kuilei.zhuyi.http.NetWorkHelper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by lenovog on 2016/7/4.
 */
public class HttpUtil {
    private final static  Class TAG = HttpUtil.class;

    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static String getByHttpClient(Context context, String strUrl) throws Exception {
        String str1 = new String();
        StringBuilder sb = new StringBuilder();
        sb.append(strUrl);
        Logger.w(TAG,"url = "+strUrl.toString());
        final Request request = new Request.Builder().url(strUrl).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
//        call.execute(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Logger.w(TAG,"response1 = "+response.body().toString());
//                String str = new String(response.body().bytes(),"UTF-8");
//                str1 = str;
//                Logger.w(TAG,"response3 = "+ str);
//            }
//        });
        String str = new String(response.body().bytes(),"UTF-8");
        Logger.w(TAG,"response3 = "+ str);
        return str;
    }

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
