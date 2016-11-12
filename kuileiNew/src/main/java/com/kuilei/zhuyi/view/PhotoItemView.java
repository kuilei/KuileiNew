package com.kuilei.zhuyi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.utils.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by lenovog on 2016/11/8.
 */
@EViewGroup(R.layout.item_photo)
public class PhotoItemView extends RelativeLayout {

    @ViewById(R.id.photo_img)
    protected ImageView photoImg;

    @ViewById(R.id.photo_title)
    protected TextView photoTitle;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    protected DisplayImageOptions options;

    public PhotoItemView(Context context) {
        super(context);
        options = Options.getListOptions();
    }

    public PhotoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        options = Options.getListOptions();
    }

    public PhotoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        options = Options.getListOptions();
    }

    public void setData(String title, String picUrl) {

        picUrl = picUrl.replace("auto", "854x480x75x0x0x3");

        photoTitle.setText(title);
        imageLoader.displayImage(picUrl, photoImg, options);
    }
}
