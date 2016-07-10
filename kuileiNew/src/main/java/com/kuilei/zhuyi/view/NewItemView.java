package com.kuilei.zhuyi.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.bean.NewModle;
import com.kuilei.zhuyi.utils.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by lenovog on 2016/7/6.
 */
@EViewGroup(R.layout.item_new)
public class NewItemView extends LinearLayout {
    @ViewById(R.id.left_image)
    protected ImageView leftImage;

    @ViewById(R.id.item_title)
    protected TextView itemTitle;

    @ViewById(R.id.item_content)
    protected TextView itemConten;

    @ViewById(R.id.article_top_layout)
    protected RelativeLayout articleLayout;

    @ViewById(R.id.image_layout)
    protected LinearLayout imageLayout;

    @ViewById(R.id.item_image_0)
    protected ImageView item_image0;

    @ViewById(R.id.item_image_1)
    protected ImageView item_image1;

    @ViewById(R.id.item_image_2)
    protected ImageView item_image2;

    @ViewById(R.id.item_abstract)
    protected TextView itemAbstract;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    protected DisplayImageOptions options;

    public NewItemView(Context context) {
        super(context);
        options = Options.getListOptions();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    public void setTexts(String titleText, String contentText, String imgUrl, String currentItem) {
        articleLayout.setVisibility(View.VISIBLE);
        imageLayout.setVisibility(View.GONE);
        itemTitle.setText(titleText);
        if ("北京".equals(currentItem)) {

        } else {
            itemConten.setText(contentText);
        }
        if (!"".equals(imgUrl)) {
            leftImage.setVisibility(View.VISIBLE);
            imageLoader.displayImage(imgUrl, leftImage, options);
        } else {
            leftImage.setVisibility(View.GONE);
        }
    }
    public void setImages(NewModle newModle) {
        imageLayout.setVisibility(View.VISIBLE);
        articleLayout.setVisibility(View.GONE);
        itemAbstract.setText(newModle.getTitle());
        List<String> imageModle = newModle.getImagesModle().getImgList();
        imageLoader.displayImage(imageModle.get(0), item_image0, options);
        imageLoader.displayImage(imageModle.get(1), item_image1, options);
        imageLoader.displayImage(imageModle.get(2), item_image2, options);
    }
}
