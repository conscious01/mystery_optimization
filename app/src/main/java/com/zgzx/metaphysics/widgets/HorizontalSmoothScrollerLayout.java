package com.zgzx.metaphysics.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 平滑滚动子View
 */
public class HorizontalSmoothScrollerLayout extends ViewGroup {

    private Scroller mScroller;
    private int DEFAULT_DURATION = 450;
    private int currentDisplayPosition = 0;

    private OnChildrenSelectedListener mOnChildrenSelectedListener;

    public HorizontalSmoothScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

        setMeasuredDimension(getMeasuredWidth(), getChildAt(currentDisplayPosition).getMeasuredHeight());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childMeasuredWidth = childView.getMeasuredWidth();

            childView.layout(
                    i * childMeasuredWidth,
                    0,
                    (i + 1) * childMeasuredWidth,
                    childView.getMeasuredHeight()
            );
        }
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();

        } else {

            int childCount = getChildCount();

            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                if (view.getVisibility() == View.GONE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    /**
     * 滚动至指定位置
     */
    public void smoothScrollToPosition(int position) {
        int childCount = getChildCount();
        if (position > childCount || position < 0 || currentDisplayPosition == position) {
            return;
        }

        // 判断是否为跳页面, 隐藏其他页面
        if (position - currentDisplayPosition > 1) {
            for (int i = currentDisplayPosition + 1; i < position; i++) {
                getChildAt(i).setVisibility(View.GONE);
            }
        }

        currentDisplayPosition = position;
        int dx = position * getWidth() - getScrollX();
        mScroller.startScroll(getScrollX(), 0, dx, 0, DEFAULT_DURATION);
        invalidate();

        if (mOnChildrenSelectedListener != null) {
            mOnChildrenSelectedListener.onSelected(position);
        }
    }


    public boolean canGoBack(){
        if (currentDisplayPosition == 0){
            return true;
        }

        previous();
        return false;
    }


    /**
     * 滚动至下一个
     */
    public void previous() {
        smoothScrollToPosition(currentDisplayPosition - 1);
    }


    /**
     * 滚动至上一个
     */
    public void next() {
        smoothScrollToPosition(currentDisplayPosition + 1);
    }


    /**
     * @return 当前显示位置
     */
    public int getCurrentPosition() {
        return currentDisplayPosition;
    }


    // 滚动时间
    public void setSmoothScrollerDuration(int duration) {
        this.DEFAULT_DURATION = duration;
    }


    // 监听
    public void setOnChildrenSelectedListener(OnChildrenSelectedListener onChildrenSelectedListener) {
        this.mOnChildrenSelectedListener = onChildrenSelectedListener;
    }


    public interface OnChildrenSelectedListener {
        void onSelected(int position);
    }

}
