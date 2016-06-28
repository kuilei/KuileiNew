package com.kuilei.zhuyi.webget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

/**
 * Created by lenovog on 2016/6/27.
 */
public class ColumnHorizontalScrollView extends HorizontalScrollView{
    //传入整体布局
    private View content_ll;
    //传入更多栏目选择布局
    private View more_ll;
    //传入拖动栏布局
    private View column_rl;
    //左阴影图片
    private ImageView leftImage;
    //右阴影图片
    private ImageView rightImage;
    //屏幕宽度
    private int mScreenWidth = 0;
    //父类的活动activity
    private Activity mParentActivity;


    public ColumnHorizontalScrollView(Context context) {
        super(context);
    }

    public ColumnHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        shade_ShowOrHide();
        if (!mParentActivity.isFinishing() && content_ll != null && leftImage != null
                && rightImage != null && more_ll != null && column_rl != null)
        {
            if (content_ll.getWidth() <= mScreenWidth)
            {
                leftImage.setVisibility(View.GONE);
                rightImage.setVisibility(View.GONE);
            }
        } else
        {
            return;
        }

        if (l == 0)
        {
            leftImage.setVisibility(View.GONE);
            rightImage.setVisibility(View.VISIBLE);
            return;
        }

        if (content_ll.getWidth() - l + more_ll.getWidth() + column_rl.getLeft() == mScreenWidth)
        {
            leftImage.setVisibility(View.VISIBLE);
            rightImage.setVisibility(View.GONE);
            return;
        }

        leftImage.setVisibility(View.VISIBLE);
        rightImage.setVisibility(View.VISIBLE);
    }

    public void setParam(Activity activity, int mScreenWidth, View paramView1, ImageView paramView2,
                            ImageView paramView3, View paramView4, View paramView5)
    {
        mParentActivity = activity;
        this.mScreenWidth = mScreenWidth;
        content_ll = paramView1;
        leftImage = paramView2;
        rightImage = paramView3;
        more_ll = paramView4;
        column_rl = paramView5;
    }

    /*
    判断左右阴影的显示隐藏效果
     */
    private void shade_ShowOrHide() {
        if (!mParentActivity.isFinishing() && content_ll != null)
        {
            measure(0,0);
            //如果整体宽度小于屏幕宽度的话，那左右阴影都隐藏
            if (mScreenWidth >= getMeasuredWidth())
            {
                leftImage.setVisibility(View.GONE);
                rightImage.setVisibility(View.GONE);
            }
        } else
        {
            return;
        }
        //滑动在最左边时候，左边阴影隐藏，右边显示
        if (getLeft() == 0)
        {
            leftImage.setVisibility(View.GONE);
            rightImage.setVisibility(View.VISIBLE);
            return;
        }
        //滑动在最右边时候，右边阴影隐藏，左边显示
        if (getRight() == getMeasuredWidth() - mScreenWidth)
        {
            leftImage.setVisibility(View.VISIBLE);
            rightImage.setVisibility(View.GONE);
            return;
        }
        // 否则，说明在中间位置，左、右阴影都显示
        leftImage.setVisibility(View.VISIBLE);
        rightImage.setVisibility(View.VISIBLE);
    }
}
