package com.kuilei.zhuyi.webget.viewimage.animations;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.webget.viewimage.slidertypes.BaseSliderView;

import java.util.ArrayList;

/**
 * Created by lenovog on 2016/7/8.
 */
public class SliderAdapter extends PagerAdapter implements BaseSliderView.ImageLoadListener{
    private final Class TAG = SliderAdapter.class;
    private final Context mContext;

    private final ArrayList<BaseSliderView> mImageContents;

    public SliderAdapter(Context mContext) {
        this.mContext = mContext;
        mImageContents = new ArrayList<BaseSliderView>();
    }

    @Override
    public int getCount() {
        return mImageContents.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public <T extends BaseSliderView> void addSlider(T slider) {
        Logger.w(TAG,"addslider = "+slider);
        slider.setOnImageLoadListener(this);
        mImageContents.add(slider);
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseSliderView b = mImageContents.get(position);
        //初始化具体view
        View v =  b.getView();
        container.addView(v);
        return v;
    }

    //需添加，不然图片轮播不显示
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



    @Override
    public void onStart(BaseSliderView target) {

    }

    @Override
    public void onEnd(boolean result, BaseSliderView target) {
        if (target.isErrorDisappear() == false || result == true) {
            return;
        }

        for (BaseSliderView slider : mImageContents) {
            if (slider.equals(target)) {
                removeSlider(target);
                break;
            }
        }
    }

    private void removeSlider(BaseSliderView target) {
        Logger.w(TAG,"remove slider="+target);
    }

    public void removeSliderAt(int position) {
        if (mImageContents.size() < position) {
            mImageContents.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeAllSliders() {
        mImageContents.clear();
        notifyDataSetChanged();
    }
}
