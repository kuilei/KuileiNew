package com.kuilei.zhuyi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Toast;

import com.kuilei.zhuyi.utils.ACache;
import com.kuilei.zhuyi.utils.HttpUtils;
import com.kuilei.zhuyi.utils.StringUtils;
import com.kuilei.zhuyi.webget.slideingactivity.SlidingActivity;

/**
 * Created by lenovog on 2016/6/28.
 */
public class BaseActivity extends SlidingActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        initGestureDetector();

    }

    private void initGestureDetector() {

    }

    /**
     * 判断是否有网络
     */

    public boolean hasNetWork() {
        return HttpUtils.isNetworkAvailable(this);
    }

    /**
     * 显示LongToast
     */
    public void showShortToast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据key获取缓存数据
     */
    public String getCacheStr(String key)
    {
        return ACache.get(this).getAsString(key);
    }


    /**
     *  设置缓存数据（key,value）
     * @param key
     * @param value
     */
    public void setCacheStr(String key, String value)
    {
        if (!StringUtils.isEmpty(value))
        {
            ACache.get(this).put(key, value);
        }
    }
}
