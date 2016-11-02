package com.kuilei.zhuyi.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.activity.BaseActivity;
import com.kuilei.zhuyi.activity.MoreActivity_;
import com.kuilei.zhuyi.activity.WeatherActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;

/**
 * Created by lenovog on 2016/6/28.
 */
@EViewGroup(R.layout.activity_left)
public class LeftView extends LinearLayout {

    private final BaseActivity context;

    public LeftView(Context context) {
        super(context);
        this.context = (BaseActivity)context;
    }

    @Click(R.id.pics)
    public void enterPics(View view) {
     //   context.openActivity(TuPianSinaActivity_.class);
        isShow();
    }

    @Click(R.id.video)
    public void enterVideo(View view) {
      //  context.openActivity(VideoActivity_.class);
        isShow();
    }

    @Click(R.id.ties)
    public void enterMessage(View view) {
      //  context.openActivity(MessageActivity_.class);
        isShow();
    }

    @Click(R.id.tianqi)
    public void enterTianQi(View view) {
        context.openActivity(WeatherActivity_.class);
        isShow();
    }

    @Click(R.id.more)
    public void enterMore(View view) {
        context.openActivity(MoreActivity_.class);
        isShow();
    }

    public void isShow() {
//        if (SlidingMenuView.instance().slidingMenu.isMenuShowing()) {
//            SlidingMenuView.instance().slidingMenu.showContent();
//        }
    }
}
