package com.kuilei.zhuyi.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.kuilei.zhuyi.activity.BaseActivity;

/**
 * Created by lenovog on 2016/6/29.
 */
public class BaseFragment extends Fragment {
    private View mView;

    public BaseActivity getMyActivity()
    {
        return (BaseActivity) getActivity();
    }

    public String getNewUrl(String index)
    {
        //String urlString = Url.TopUrl + Url.TopId + "/" + index + Url.endUrl;
       // return urlString;
        return null;
    }
}
