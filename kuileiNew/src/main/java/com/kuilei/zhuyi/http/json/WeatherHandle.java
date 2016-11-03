package com.kuilei.zhuyi.http.json;

import com.kuilei.zhuyi.utils.OkHttpUtil;

import okhttp3.Request;

/**
 * Created by lenovog on 2016/11/3.
 */
public class WeatherHandle extends OkHttpUtil.ResultCallback {
    private onResponse mOnResponse;

    public WeatherHandle(onResponse mOnResponse) {
        this.mOnResponse = mOnResponse;
    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onResponse(Object response) {
        mOnResponse.onResponse((String)response);
    }

    public interface onResponse{
        public void onResponse(String str);
    }
}
