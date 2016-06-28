package com.kuilei.zhuyi.webget.slideingactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kuilei.zhuyi.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by lenovog on 2016/6/28.
 */
public class SlidingActivity extends FragmentActivity {
    private static final float MIN_SCALE = 0.85f;

    private View mPreview;

    private float mInitOffset;
    private boolean hideTitle = false;
    private int titleResId = -1;
    Bitmap bmp;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.slide_layout);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mInitOffset = (1 - MIN_SCALE) * metrics.widthPixels / 2.0f;
        mPreview = findViewById(R.id.iv_preview);
        FrameLayout contentView = (FrameLayout) findViewById(R.id.content_view);

        hideTitle = true;

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM);

        final int marginTop = hideTitle ? 0 : (int) (metrics.density * 48.0f + 0.5f);
        layoutParams.setMargins(0, marginTop, 0, 0);

        contentView.addView(inflater.inflate(layoutResID,null),layoutParams);

        SlidingLayout slidingLayout = (SlidingLayout) findViewById(R.id.slide_layout);
        slidingLayout.setShadowResource(R.drawable.sliding_back_shadow);
        slidingLayout.setPanelSlideListener(new SlidingLayout.SimpleSlideListener(){
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                final int sdkInt = Build.VERSION.SDK_INT;

                if (slideOffset <= 0)
                {
                    if (sdkInt >= Build.VERSION_CODES.HONEYCOMB)
                    {
                        mPreview.setScaleX(MIN_SCALE);
                        mPreview.setScaleY(MIN_SCALE);;
                    } else
                    {
                        ViewHelper.setScaleX(mPreview, MIN_SCALE);
                        ViewHelper.setScaleY(mPreview, MIN_SCALE);
                    }
                } else if (slideOffset < 1)
                {
                    float scaleFactor = MIN_SCALE + Math.abs(slideOffset) * (1 - MIN_SCALE);
                    if (sdkInt >= Build.VERSION_CODES.HONEYCOMB) {
                        mPreview.setAlpha(slideOffset);
                        mPreview.setTranslationX(mInitOffset * (1 - slideOffset));
                        mPreview.setScaleX(scaleFactor);
                        mPreview.setScaleY(scaleFactor);
                    } else {
                        ViewHelper.setAlpha(mPreview, slideOffset);
                        ViewHelper.setTranslationX(mPreview, mInitOffset * (1 - slideOffset));
                        ViewHelper.setScaleX(mPreview, scaleFactor);
                        ViewHelper.setScaleY(mPreview, scaleFactor);
                    }
                } else {
                    if (sdkInt >= Build.VERSION_CODES.HONEYCOMB) {
                        mPreview.setScaleX(1);
                        mPreview.setScaleY(1);
                        mPreview.setAlpha(1);
                        mPreview.setTranslationX(0);
                    } else {
                        ViewHelper.setScaleX(mPreview, 1);
                        ViewHelper.setScaleY(mPreview, 1);
                        ViewHelper.setAlpha(mPreview, 1);
                        ViewHelper.setTranslationX(mPreview, 0);
                    }
                    finish();
                    overridePendingTransition(0, 0);
                }
            }

            @Override
            public void onPanelOpened(View panel) {
                super.onPanelOpened(panel);
            }

            @Override
            public void onPanelClosed(View panel) {
                super.onPanelClosed(panel);
            }
        });
            byte[] byteArray = getIntent().getByteArrayExtra(IntentUtils.KEY_PREVIEW_IMAGE);
            if (null != byteArray &&  byteArray.length > 0) {
                try {
                    bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    if (null != bmp) {
                        ((ImageView) mPreview).setImageBitmap(bmp);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            mPreview.setScaleX(MIN_SCALE);
                            mPreview.setScaleY(MIN_SCALE);
                        } else {
                            ViewHelper.setScaleX(mPreview, MIN_SCALE);
                            ViewHelper.setScaleY(mPreview, MIN_SCALE);
                        }
                    } else {
                        slidingLayout.setSlideable(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != bmp && bmp.isRecycled()) {
                        bmp = null;
                    }
                }
            } else
            {
                slidingLayout.setSlideable(false);
            }
    }
    protected void setContentView(int layoutResID, int titleResId) {
        this.titleResId = titleResId;
        setContentView(layoutResID);
    }

    protected void setContentView(int layoutResID, boolean hideTitle) {
        this.hideTitle = hideTitle;
        setContentView(layoutResID);
    }
}
