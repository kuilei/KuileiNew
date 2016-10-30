package com.kuilei.zhuyi.webget;

import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kuilei.zhuyi.adapter.DragAdapter;
import com.kuilei.zhuyi.utils.DataTools;

/**
 * Created by lenovog on 2016/10/30.
 */
public class DragGrid extends GridView {
    /** 点击时候的X位置 */
    public int downX;
    /** 点击时候的Y位置 */
    public int downY;
    /** 点击时候对应整个界面的X位置 */
    public int windowX;
    /** 点击时候对应整个界面的Y位置 */
    public int windowY;
    /** 屏幕上的X */
    private int win_view_x;
    /** 屏幕上的Y */
    private int win_view_y;
    /** 拖动的里x的距离 */
    int dragOffsetX;
    /** 拖动的里Y的距离 */
    int dragOffsetY;
    /** 长按时候对应postion */
    public int dragPosition;
    /** Up后对应的ITEM的Position */
    private int dropPosition;
    /** 开始拖动的ITEM的Position */
    private int startPosition;
    /** item高 */
    private int itemHeight;
    /** item宽 */
    private int itemWidth;
    /** 拖动的时候对应ITEM的VIEW */
    private View dragImageView = null;
    /** 长按的时候ITEM的VIEW */
    private ViewGroup dragItemView = null;
    /** WindowManager管理器 */
    private WindowManager windowManager = null;
    /** */
    private WindowManager.LayoutParams windowParams = null;
    /** item总量 */
    private int itemTotalCount;
    /** 一行的ITEM数量 */
    private final int nColumns = 4;
    /** 行数 */
    private int nRows;
    /** 剩余部分 */
    private int Remainder;
    /** 是否在移动 */
    private boolean isMoving = false;
    /** */
    private int holdPosition;
    /** 拖动的时候放大的倍数 */
    private final double dragScale = 1.2D;
    /** 震动器 */
    private Vibrator mVibrator;
    /** 每个ITEM之间的水平间距 */
    private int mHorizontalSpacing = 15;
    /** 每个ITEM之间的竖直间距 */
    private final int mVerticalSpacing = 15;
    /* 移动时候最后个动画的ID */
    private String LastAnimationID;

    public DragGrid(Context context) {
        super(context);
    }

    public DragGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void init(Context context) {
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mHorizontalSpacing = DataTools.dip2px(context,mHorizontalSpacing);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = (int) ev.getX();
            downY = (int) ev.getY();
            windowX = (int) ev.getX();
            windowY = (int) ev.getY();
            setOnItemClickListener(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 长按点击监听
     *
     * @param ev
     */
    public void setOnItemClickListener(final MotionEvent ev) {
        setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean bool = true;
        if (dragImageView != null && dragPosition != AdapterView.INVALID_POSITION) {
            bool = super.onTouchEvent(ev);
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) ev.getX();
                    windowX = (int) ev.getX();
                    downY = (int) ev.getY();
                    windowY = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    stopDrag();
                    onDrop(x,y);
                    requestDisallowInterceptTouchEvent(false);
                    break;
                default:
                    break;
            }

        }
        return super.onTouchEvent(ev);
    }

    /** 停止拖动 ，释放并初始化 */
    private void stopDrag() {
        if (dragImageView != null) {
            windowManager.removeView(dragImageView);
            dragImageView = null;
        }
    }

    /** 在松手下放的情况 */
    private void onDrop(int x, int y) {
        // 根据拖动到的x,y坐标获取拖动位置下方的ITEM对应的POSTION
        int tempPostion = pointToPosition(x, y);
        dropPosition = tempPostion;
        DragAdapter mDragAdapter = (DragAdapter) getAdapter();
        mDragAdapter.setShowDropItem(true);
        mDragAdapter.notifyDataSetChanged();
    }
}
