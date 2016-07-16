package com.kuilei.zhuyi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.utils.Options;
import com.kuilei.zhuyi.webget.ProgressButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by lenovog on 2016/7/14.
 */
@EViewGroup(R.layout.item_image)
public class NewImageView extends RelativeLayout implements ImageLoadingListener,ImageLoadingProgressListener {
    @ViewById(R.id.current_image)
    protected ImageView currentImage;

    @ViewById(R.id.current_page)
    protected TextView currentPage;

    @ViewById(R.id.progressButton)
    protected ProgressButton progressButton;

    protected Context context;

    protected DisplayImageOptions options;

    protected CompoundButton.OnCheckedChangeListener checkedChangeListener;

    protected  ImageLoader imageLoader = ImageLoader.getInstance();
    public NewImageView(Context context) {
        super(context);
        this.context = context;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = Options.getListOptions();
        progress(context);
    }

    @AfterViews
    public void initView() {
        progressButton.setOnCheckedChangeListener(checkedChangeListener);
    }

    public void setImage(List<String> imageList, int position) {
            currentPage.setText((position + 1) + "/" + imageList.size());
        imageLoader.displayImage(imageList.get(position), currentImage, options, this, this);
    }

    private void progress(Context context) {
        checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        };
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        progressButton.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        progressButton.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        progressButton.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        progressButton.setVisibility(View.GONE);
    }

    @Override
    public void onProgressUpdate(String s, View view, int current, int total) {
        int currentpro = (current * 100 / total);
        if (currentpro == 100) {
            progressButton.setVisibility(View.GONE);
        } else {
            progressButton.setVisibility(View.VISIBLE);
        }

        if (currentpro >= 0 && currentpro <= 100) {
            progressButton.setProgress(currentpro);
        }

        updatePinProgressContentDescription(progressButton,context);
    }


    private void updatePinProgressContentDescription(ProgressButton button, Context context) {
        int progress = button.getProgress();
        if (progress <= 0) {
            button.setContentDescription(context.getString(button.isChecked() ? R.string.content_desc_pinned_not_downloaded : R.string.content_desc_unpinned_not_downloaded));
        } else if (progress >= button.getMax()) {
            button.setContentDescription(context.getString(button.isChecked()? R.string.content_desc_pinned_downloaded : R.string.content_desc_unpinned_downloaded));
        } else {
            button.setContentDescription(context.getString(button.isChecked() ? R.string.content_desc_pinned_downloading : R.string.content_desc_unpinned_downloading));
        }
    }
}
