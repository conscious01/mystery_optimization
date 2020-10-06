package com.zgzx.metaphysics.widgets.calendar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Typeface;


import com.haibin.calendarview.Calendar;

import com.haibin.calendarview.MonthView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.dialogs.BlessDialog;
import com.zgzx.metaphysics.utils.DateUtils;

import java.text.ParseException;


public class CalendarBlessMothView extends MonthView {
    private int mRadius, mRoundRadius, mSpace;



    /**
     * 背景圆点
     */
    private Paint mPointPaint = new Paint();

    /**
     * 今天的背景色
     */
    private Paint mCurrentDayPaint = new Paint();

    /**
     * 圆点半径
     */
    private float mPointRadius;

    private int mPadding;

    private float mCircleRadius;
    private Context mContext;

    public CalendarBlessMothView(Context context) {
        super(context);
        this.mContext=context;
        // 字体
        AssetManager mgr = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(mgr, "din_bold.otf");


        // 今天的背景色
        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);


        // 背景圆点
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);

        // 文字
        mCurMonthTextPaint.setColor(0xFFE7741F);

        mCurDayTextPaint.setColor(0XFFFFE9C1);

        mSelectTextPaint.setTypeface(typeface);
        mSelectedLunarTextPaint.setTypeface(typeface);
        mCurMonthTextPaint.setTypeface(typeface);
        mCurDayLunarTextPaint.setTypeface(typeface);


        mSpace = dipToPx(context, 6);
        mRoundRadius = dipToPx(context, 4);
        mPointRadius = dipToPx(context, 2);
        mPadding = dipToPx(getContext(), 3);
        mCircleRadius = dipToPx(getContext(), 7);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 5;
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        return false;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6;

        // 当天背景
        if (calendar.isCurrentDay()) {
            //canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_calendar_bless);
            canvas.drawBitmap(bitmap, x + mSpace + bitmap.getWidth() / 6,
                    y + mSpace - bitmap.getHeight() / 5, mCurrentDayPaint);


        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_calendar_unbless);
            canvas.drawBitmap(bitmap, x + mSpace + bitmap.getWidth() / 6,
                    y + mSpace - bitmap.getHeight() / 5, mCurrentDayPaint);
        }

        String content =calendar.getScheme();
        if (!"假".equals(content)) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_calendar_circle);
            canvas.drawBitmap(bitmap, x + mSpace + bitmap.getWidth(),
                    y + mSpace + bitmap.getHeight() * 2 + mSpace, mCurrentDayPaint);
        }

        canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                calendar.isCurrentDay() ? mCurDayTextPaint : mCurMonthTextPaint);

    }


    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
