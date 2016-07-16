package com.kuilei.zhuyi.activity;

import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.ImageAdapter;
import com.kuilei.zhuyi.bean.NewDetailModle;
import com.kuilei.zhuyi.bean.NewModle;
import com.kuilei.zhuyi.webget.flipview.FlipView;
import com.kuilei.zhuyi.webget.flipview.OverFlipMode;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by lenovog on 2016/7/13.
 */
@EActivity(R.layout.activity_image)
public class ImageDetailActivity extends BaseActivity  implements FlipView.OnFlipListener {
    @ViewById(R.id.flip_view)
    protected FlipView mFlipView;
    @ViewById(R.id.new_title)
    protected TextView newTitle;

    private NewModle newModle;
    private List<String> imgList;
    private String titleString;
    private NewDetailModle newDetailModle;

    @Bean
    protected ImageAdapter imageAdapter;
    @AfterInject
    public void init() {
        try {
            if (getIntent().getExtras().getSerializable("newDetailModle") != null) {
                newDetailModle = (NewDetailModle) getIntent().getExtras().getSerializable(
                        "newDetailModle");
                imgList = newDetailModle.getImgList();
                titleString = newDetailModle.getTitle();
            }else {
                newModle = (NewModle) getIntent().getExtras().getSerializable("newModle");
                imgList = newModle.getImagesModle().getImgList();
                titleString = newModle.getTitle();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    public void initView() {
        //将传进来数据设置
        newTitle.setText(titleString);
        imageAdapter.appendList(imgList);
        mFlipView.setOnFlipListener(this);
        mFlipView.setAdapter(imageAdapter);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
       // mFlipView.setOnOverFlipListener(this);
    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {

    }
}
