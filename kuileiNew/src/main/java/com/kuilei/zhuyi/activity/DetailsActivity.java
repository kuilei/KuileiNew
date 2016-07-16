package com.kuilei.zhuyi.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.bean.NewDetailModle;
import com.kuilei.zhuyi.bean.NewModle;
import com.kuilei.zhuyi.http.json.NewDetailJson;
import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.utils.OkHttpUtil;
import com.kuilei.zhuyi.utils.Options;
import com.kuilei.zhuyi.utils.StringUtils;
import com.kuilei.zhuyi.webget.ProgressPieView;
import com.kuilei.zhuyi.webget.htmltextview.HtmlTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

/**
 * Created by lenovog on 2016/7/16.
 */
@EActivity(R.layout.activity_details)
public class DetailsActivity extends BaseActivity implements ImageLoadingListener,ImageLoadingProgressListener{

    @ViewById(R.id.new_title)
    protected TextView newTitle;
    @ViewById(R.id.new_time)
    protected TextView newTime;
    @ViewById(R.id.wb_details)
    protected HtmlTextView webView;
    // @ViewById(R.id.progressBar)
    // protected ProgressBar progressBar;
    @ViewById(R.id.progressPieView)
    protected ProgressPieView mProgressPieView;
    @ViewById(R.id.new_img)
    protected ImageView newImg;
    @ViewById(R.id.img_count)
    protected TextView imgCount;
    @ViewById(R.id.play)
    protected ImageView mPlay;
    private String newUrl;
    private NewModle newModle;
    private String newID;
    protected ImageLoader imageLoader;
    private String imgCountString;

    private DisplayImageOptions options;

    private NewDetailModle newDetailModle;

    @AfterInject
    public void init() {
        newModle = (NewModle)getIntent().getExtras().getSerializable("newModle");
        newID = newModle.getDocid();
        newUrl = getUrl(newID);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = Options.getListOptions();
    }

    @AfterViews
    public void initView() {
        mProgressPieView.setShowText(true);
        mProgressPieView.setShowImage(false);
        showProgressDialog();
        loadData(newUrl);
    }

    private void loadData(String url) {
        if (hasNetWork()) {
            loadNewDetailData(newUrl);
        } else {
            dismissProgressDialog();
            showShortToast(getString(R.string.not_network));
            String result = getCacheStr(newUrl);
            if (!StringUtils.isEmpty(result)) {
                getResult(result);
            }
        }
    }

    @Background
    public void loadNewDetailData(String url) {
        String result;
        try {
            result = OkHttpUtil.getAsString(url);
            getResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getResult(String result) {
        newDetailModle = NewDetailJson.instance(this).readJsonNewModles(result, newID);

        if (newDetailModle == null) {
            return;
        }
        setCacheStr(newUrl, result);
        if (!"".equals(newDetailModle.getUrl_mp4())) {
            imageLoader.displayImage(newDetailModle.getCover(), newImg, options, this, this);
            newImg.setVisibility(View.VISIBLE);
        } else {
            if (newDetailModle.getImgList().size() > 0) {
                imgCountString = "共" + newDetailModle.getImgList().size() + "张";
                imageLoader.displayImage(newDetailModle.getImgList().get(0), newImg, options, this, this);
                newImg.setVisibility(View.VISIBLE);
            }
        }
        newTitle.setText(newDetailModle.getTitle());
        newTime.setText("来源:" + newDetailModle.getSource() + " " + newDetailModle.getPtime());
        String content = newDetailModle.getBody();
        content = content.replace("<!--VIDEO#1--></p><p>", "");
        content = content.replace("<!--VIDEO#2--></p><p>", "");
        content = content.replace("<!--VIDEO#3--></p><p>", "");
        content = content.replace("<!--VIDEO#4--></p><p>", "");
        content = content.replace("<!--REWARD#0--></p><p>", "");
        webView.setHtmlFromString(content, false);
        dismissProgressDialog();
    }

    @Click(R.id.new_img)
    public void imageMore(View view) {
        Logger.w(DetailsActivity.class,"imageMore");
        Bundle bundle = new Bundle();
        bundle.putSerializable("newDetailModle", newDetailModle);
        if (!"".equals(newDetailModle.getUrl_mp4())) {

        } else {
            openActivity(ImageDetailActivity_.class, bundle, 0);
        }
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        mProgressPieView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        mProgressPieView.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        if (!"".equals(newDetailModle.getUrl_mp4())) {
            mPlay.setVisibility(View.VISIBLE);
        } else {
            imgCount.setVisibility(View.VISIBLE);
            imgCount.setText(imgCountString);
        }
        mProgressPieView.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        mProgressPieView.setVisibility(View.GONE);
    }

    @Override
    public void onProgressUpdate(String s, View view, int current, int total) {
        int currentpro = (current * 100 / total);
        if (currentpro == 100) {
            mProgressPieView.setVisibility(View.GONE);
            mProgressPieView.setShowText(false);
        } else {
            mProgressPieView.setVisibility(View.VISIBLE);
            mProgressPieView.setProgress(currentpro);
            mProgressPieView.setText(currentpro + "%");
        }
    }
}
