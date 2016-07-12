package com.kuilei.zhuyi.utils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by lenovog on 2016/7/4.
 */
public class OkHttpUtil {
    private final static  Class TAG = OkHttpUtil.class;

    private static OkHttpClient mOkHttpClient;

    private static OkHttpUtil mInstance;

    public OkHttpUtil(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }else {
            mOkHttpClient = okHttpClient;
        }
    }

    public static OkHttpUtil initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtil getInstance() {
        return initClient(null);
    }

    /**
     *同步get
     * @param url
     * @return
     * @throws IOException
     */
    private Response _getAsyn(String url) throws IOException {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步get
     * @param url
     * @return
     * @throws IOException
     */
    private String _getAsString(String url) throws IOException {
        Response execute = _getAsyn(url);
        String str = new String(execute.body().bytes(),"UTF-8");
        return str;
    }


    /**
     * 异步get
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    private void deliveryResult(final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailedCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final String str = request.body().toString();
                        sendSuccessResultCallback(str, callback);
                }catch (Exception e) {
                    e.printStackTrace();
                    sendFailedCallback(response.request(), e, callback);
                }

            }
        });
    }

    private void sendSuccessResultCallback(String str, ResultCallback callback) {

    }

    private void sendFailedCallback(Request request, Exception e, ResultCallback callback) {

    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }


    public static Response getAsyn(String url) throws Exception {
        return getInstance()._getAsyn(url);
    }

    public static String getAsString(String url) throws IOException {
        return getInstance()._getAsString(url);
    }

    public static void getAsyn(String url, ResultCallback callback) {
        getInstance()._getAsyn(url, callback);
    }

//    public static String getByHttpClient(Context context, String strUrl) throws Exception {
//        String str1 = new String();
//        StringBuilder sb = new StringBuilder();
//        sb.append(strUrl);
//        Logger.w(TAG,"url = "+strUrl.toString());
//        final Request request = new Request.Builder().url(strUrl).build();
//        Call call = okHttpClient.newCall(request);
//        Response response = call.execute();
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
//        String str = new String(response.body().bytes(),"UTF-8");
//        Logger.w(TAG,"response3 = "+ str);
//        return str;
//    }



}
