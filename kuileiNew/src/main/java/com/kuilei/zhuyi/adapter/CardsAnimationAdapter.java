package com.kuilei.zhuyi.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by lenovog on 2016/7/4.
 */
public class CardsAnimationAdapter extends AnimationAdapter {
    private final float mTranslationY = 400;

    private final float mRotationX = 15;

    private final long mDuration = 400;

    private final long mDelay = 30;

    public CardsAnimationAdapter(BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @Override
    protected long getAnimationDelayMillis() {
        return mDelay;
    }

    @Override
    protected long getAnimationDurationMillis() {
        return mDuration;
    }

    @Override
    public Animator[] getAnimators(ViewGroup viewGroup, View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view,"translationY", mTranslationY, 0),
                ObjectAnimator.ofFloat(view,"translationX", mRotationX, 0)
        };
    }
}
