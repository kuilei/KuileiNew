package com.kuilei.zhuyi.activity;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kuilei.zhuyi.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by lenovog on 2016/11/2.
 */
@EActivity(R.layout.activity_more)
public class MoreActivity extends BaseActivity {

    @ViewById(R.id.title)
    protected TextView mTitle;

    /**
    * 检查更新
    * */
    @ViewById(R.id.newest)
    public TextView mTextViewNewest;

    /**
     * 检查更新进度框
     */
    @ViewById(R.id.update_progress)
    public ProgressBar mProgressBar;

    @AfterViews
    public void initView() {
        mTitle.setText("more");
    }

    @Click
    void checkUpdateClicked() {
        mTextViewNewest.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        checkUpdate();
    }

    private void checkUpdate() {

    }

    @Click(R.id.restart)
    public void restart(View view) {
        setCacheStr("MoreActivity", "MoreActivity");
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }
}
