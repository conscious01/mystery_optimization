package com.zgzx.metaphysics.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/22
 * @Description: 弧形view
 */
public class ArcView extends View {

    private Path mPath;
    private Paint mBackgroundPaint;
    private DrawFilter mPaintFilter;

    private @ColorInt int mBackgroundColor = Color.TRANSPARENT;

    private int mArcSize;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mArcSize = getDefaultArcSize();
        mPath = new Path();

        // 画笔
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        // 抗锯齿
        mPaintFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Y轴开始绘制位置
        float startYPosition = (float) (getHeight() * 0.7);

        // 弧形
        mPath.moveTo(0, 0);
        mPath.lineTo(0, startYPosition);
        mPath.quadTo(getWidth() >> 1, startYPosition + 2 * mArcSize, getWidth(), startYPosition);
        mPath.lineTo(getWidth(), 0);
        mPath.close();

        // 绘制
        mBackgroundPaint.setColor(mBackgroundColor);
        canvas.setDrawFilter(mPaintFilter);
        canvas.drawPath(mPath, mBackgroundPaint);
    }

    @Override
    public void setBackground(Drawable background) {
        if (background instanceof ColorDrawable) {
            this.setBackgroundColor(((ColorDrawable) background).getColor());

        } else {
            super.setBackground(background);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
        this.invalidate();
    }

    private int getDefaultArcSize() {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                getResources().getDisplayMetrics());
    }

}
