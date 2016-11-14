package com.kuilei.zhuyi.utils;

import android.graphics.Bitmap;

import com.kuilei.zhuyi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by lenovog on 2016/7/6.
 */
public class Options {
    public static DisplayImageOptions getListOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.base_article_bigimage)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.base_article_bigimage)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.base_article_bigimage)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)
                //设置图片的编码方式显示
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                //设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)

                .considerExifParams(true)
                //设置图片在下载前是否重置，复位
                .resetViewBeforeLoading(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .build();
        return options;
    }
}
