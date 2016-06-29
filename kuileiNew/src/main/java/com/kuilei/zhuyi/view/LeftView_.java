package com.kuilei.zhuyi.view;

import android.content.Context;
import android.view.View;

import com.kuilei.zhuyi.R;

import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

/**
 * Created by lenovog on 2016/6/28.
 */
public class LeftView_ extends LeftView implements HasViews, OnViewChangedListener {

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public LeftView_(Context context) {
        super(context);
        init_();
    }

    public static LeftView build(Context context) {
        LeftView_ instance = new LeftView_(context);
        instance.onFinishInflate();
        return instance;
    }

    private void init_() {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    protected void onFinishInflate() {
        if (!alreadyInflated_) {
            alreadyInflated_ = true;
            inflate(getContext(), R.layout.activity_left, this);
            onViewChangedNotifier_.notifyViewChanged(this);
        }
        super.onFinishInflate();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        View view = hasViews.findViewById(R.id.more);
        if (view != null)
        {
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
