package com.kuilei.zhuyi.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.bean.VideoModle;
import com.kuilei.zhuyi.utils.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by lenovog on 2016/11/12.
 */
@EViewGroup(R.layout.item_video)
public class VideoItemView extends LinearLayout {
    @ViewById(R.id.video_img)
    protected ImageView videoView;
    @ViewById(R.id.video_title)
    protected TextView videoTitle;
    @ViewById(R.id.video_time)
    protected TextView videoTime;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    protected DisplayImageOptions options;

    public VideoItemView(Context context) {
        super(context);
        options = Options.getListOptions();
    }

    public void setData(VideoModle videoModle) {
        videoTime.setText(videoModle.getLength());
        videoTitle.setText(videoModle.getTitle());
        imageLoader.displayImage(videoModle.getCover(), videoView, options);
    }
}
