package com.zgzx.metaphysics.widgets.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;

import androidx.core.content.res.ResourcesCompat;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.zgzx.metaphysics.R;

public class CalendarMonthView extends MonthView {

    private int mRadius, mRoundRadius, mSpace;

    /**
     * 自定义魅族标记的文本画笔
     */
//    private Paint mTextPaint = new Paint();

    /**
     * 24节气画笔
     */
    private Paint mSolarTermTextPaint = new Paint();

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

    /**
     * 自定义魅族标记的圆形背景
     */
    //private Paint mSchemeBasicPaint = new Paint();

    //private float mSchemeBaseLine;
    public CalendarMonthView(Context context) {
        super(context);

        // 字体
        Typeface typeface = ResourcesCompat.getFont(context, R.font.font_family);


        // 文本画笔
//        mTextPaint.setAntiAlias(true);
//        mTextPaint.setFakeBoldText(true);
//        mTextPaint.setTypeface(typeface);
//        mTextPaint.setTextSize(dipToPx(context, 16));

        // 节气画笔
        mSolarTermTextPaint.setAntiAlias(true);
        mSolarTermTextPaint.setTypeface(typeface);
        mSolarTermTextPaint.setTextAlign(Paint.Align.CENTER);
        mSolarTermTextPaint.setTextSize(dipToPx(context, 12));
        mSolarTermTextPaint.setColor(getResources().getColor(R.color.colorPrimary));

        // 背景
//        mSchemeBasicPaint.setAntiAlias(true);
//        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
//        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
//        mSchemeBasicPaint.setFakeBoldText(true);
//        mSchemeBasicPaint.setColor(Color.WHITE);

        // 今天的背景色
        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);
        mCurrentDayPaint.setColor(0XFFEAEAEA);

        // 背景圆点
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);

        // 文字
        mCurMonthTextPaint.setColor(0xff333333);
        mOtherMonthTextPaint.setColor(0XFFC0C0C0);
        mCurMonthLunarTextPaint.setColor(0xffCFCFCF);
        mOtherMonthLunarTextPaint.setColor(0XFFC0C0C0);

        mSelectTextPaint.setTypeface(typeface);
        mSelectedLunarTextPaint.setTypeface(typeface);
        mCurMonthTextPaint.setTypeface(typeface);
        mCurMonthTextPaint.setTypeface(typeface);
        mOtherMonthTextPaint.setTypeface(typeface);
        mCurMonthLunarTextPaint.setTypeface(typeface);
        mCurDayLunarTextPaint.setTypeface(typeface);
        mOtherMonthLunarTextPaint.setTypeface(typeface);

        // mSchemeTextPaint.setColor(0xff333333);
        //mSchemeLunarTextPaint.setColor(0xffCFCFCF); // 农历


        mSpace = dipToPx(context, 4);
        mRoundRadius = dipToPx(context, 4);
        mPointRadius = dipToPx(context, 2);
        mPadding = dipToPx(getContext(), 3);
        mCircleRadius = dipToPx(getContext(), 7);

        //Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        //mSchemeBaseLine = mCircleRadius - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);
    }

    @Override
    protected void onPreviewHook() {
        mSolarTermTextPaint.setTextSize(mCurMonthLunarTextPaint.getTextSize());
        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 5;
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        //int cx = x + mItemWidth / 2;
        //int cy = y + mItemHeight / 2;
        //canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);

        canvas.drawRoundRect(new RectF(
                x + mSpace,
                y + mSpace,
                x + mItemWidth - mSpace,
                y + mItemHeight - mSpace),
                mRoundRadius, mRoundRadius, mSelectedPaint);
        return true;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        boolean isSelected = isSelected(calendar);

        if (isSelected) {
            mPointPaint.setColor(Color.WHITE);

        } else {
            mPointPaint.setColor(Color.GRAY);
        }

        canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        //int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6;

        // 当天背景
        if (calendar.isCurrentDay() && !isSelected) {
            //canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);

            canvas.drawRoundRect(new RectF(
                            x + mSpace,
                            y + mSpace,
                            x + mItemWidth - mSpace,
                            y + mItemHeight - mSpace),
                    mRoundRadius, mRoundRadius, mCurrentDayPaint);
        }

//        if (hasScheme) {
//            canvas.drawCircle(x + mItemWidth - mPadding - mCircleRadius / 2, y + mPadding + mCircleRadius, mCircleRadius, mSchemeBasicPaint);
//            mTextPaint.setColor(calendar.getSchemeColor());
//            canvas.drawText(calendar.getScheme(), x + mItemWidth - mPadding - mCircleRadius, y + mPadding + mSchemeBaseLine, mTextPaint);
//        }

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top, mSelectTextPaint);
            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);

//        } else if (hasScheme) {
//            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
//                    calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);
//
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
//                    !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint : mSchemeLunarTextPaint);
        } else {

            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                    calendar.isCurrentDay() ? mCurDayLunarTextPaint :
                            calendar.isCurrentMonth() ? !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint :
                                    mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
        }
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

