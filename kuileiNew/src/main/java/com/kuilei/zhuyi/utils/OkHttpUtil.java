package com.kuilei.zhuyi.utils;

import android.os.Handler;
import android.os.Looper;

import com.kuilei.zhuyi.fragment.FootBallFragment;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lenovog on 2016/7/4.
 */
public class OkHttpUtil {
    private final static  Class TAG = OkHttpUtil.class;

    private static OkHttpClient mOkHttpClient;

    private Handler mDelivery;

    private static OkHttpUtil mInstance;

    private HashMap mCallMap;

    public OkHttpUtil(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            mDelivery = new Handler(Looper.getMainLooper());
            mCallMap = new HashMap();
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
        final Request request = new Request.Builder().tag(url).url(url).build();
        Call call = mOkHttpClient.newCall(request);
        mCallMap.put(url,call);
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
        Logger.w(FootBallFragment.class,"getAsString start");
        Response execute = _getAsyn(url);
        String str = new String(execute.body().bytes(),"UTF-8");
        if (mCallMap.containsKey(url)) {
            mCallMap.remove(url);
        }
        Logger.w(FootBallFragment.class,"getAsString end " + str);
        return str;
    }

    /**
     * 同步get
     * @param url
     * @return
     * @throws IOException
     */
    private String _getJsString(String url) throws IOException {
        Logger.w(FootBallFragment.class,"getAsString start");
        Response execute = _getAsyn(url);
        String str = execute.body().string();
        Logger.w(FootBallFragment.class,"getAsString end " + str);
        return str;
    }

    private InputStream _getAsInputStream(String url) throws IOException {
        Response execute = _getAsyn(url);
        return execute.body().byteStream();
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
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
//                    final String str = response.body().toString();
                    final String str = new String(response.body().bytes(),"UTF-8");
                    sendSuccessResultCallback(str, callback);
                }catch (Exception e) {
                    e.printStackTrace();
                    sendFailedCallback(response.request(), e, callback);
                }
            }
        });
    }

    private void sendSuccessResultCallback(final String str, final ResultCallback callback) {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onResponse(str);
                }
            }
        });
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

    public static String getJsString(String url) throws IOException {
        return getInstance()._getJsString(url);
    }

    public static void getAsyn(String url, ResultCallback callback) {
        getInstance()._getAsyn(url, callback);
    }

    public static InputStream getAsInputStream(String url) throws IOException {
        return getInstance()._getAsInputStream(url);
    }

    public Call getCallMap(String url) {
        if (mCallMap != null)
        return (Call) mCallMap.get(url);
        return null;
    }

    public void callHttp(String url) {
        if (mCallMap != null) {
            if (mCallMap.get(url) != null) {
                ((Call)mCallMap.get(url)).cancel();
                mCallMap.remove(url);
            }
        }
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
