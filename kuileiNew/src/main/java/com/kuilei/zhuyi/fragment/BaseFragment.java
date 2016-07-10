package com.kuilei.zhuyi.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.kuilei.zhuyi.activity.BaseActivity;
import com.kuilei.zhuyi.http.Url;
import com.kuilei.zhuyi.utils.StringUtils;

/**
 * Created by lenovog on 2016/6/29.
 */
public class BaseFragment extends Fragment {
    private View mView;

    public int currentPagte = 1;

    public BaseActivity getMyActivity()
    {
        return (BaseActivity) getActivity();
    }

    public String getNewUrl(String index)
    {
        String urlString = Url.TopUrl + Url.TopId + "/" + index + Url.endUrl;
        return urlString;
    }


    public boolean isNullString(String imgUrl) {

        if (StringUtils.isEmpty(imgUrl)) {
            return true;
        }
        return false;
    }

}
