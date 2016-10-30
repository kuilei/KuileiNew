package com.kuilei.zhuyi.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.kuilei.zhuyi.http.Url;
import com.kuilei.zhuyi.utils.ACache;
import com.kuilei.zhuyi.utils.DialogUtil;
import com.kuilei.zhuyi.utils.HttpUtils;
import com.kuilei.zhuyi.utils.StringUtils;
import com.kuilei.zhuyi.webget.slideingactivity.IntentUtils;
import com.kuilei.zhuyi.webget.slideingactivity.SlidingActivity;

/**
 * Created by lenovog on 2016/6/28.
 */
public class BaseActivity extends SlidingActivity {

    private Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //initGestureDetector();

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

    /**
     * 更具类打开acitvity
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null, 0);

    }

    public void openActivityForResult(Class<?> pClass, int requestCode){
        openActivity(pClass, null, requestCode);
    }


    public void openActivity(Class<?> pClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (requestCode == 0) {
            IntentUtils.startPreviewActivity(this, intent, 0);
        }else {
            IntentUtils.startPreviewActivity(this, intent, requestCode);
        }
    }


    public String getUrl(String newId) {
        return Url.NewDetail + newId + Url.endDetailUrl;
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = DialogUtil.createLoadingDialog(this);
        }
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }



    public void doBack(View view) {
        onBackPressed();
    }
}
