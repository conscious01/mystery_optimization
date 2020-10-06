package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.zgzx.metaphysics.R;

/**
 * 日历
 */
public class CalendarDialog extends BottomPopupView implements CalendarView.OnCalendarSelectListener {

    private int mYear;
    private TextView mTvMonth, mTvYear;
    private CalendarView.OnCalendarSelectListener mOnCalendarSelectListener;

    public CalendarDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_calendar;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        CalendarView mCalendarView = findViewById(R.id.calendar_view);

        mYear = mCalendarView.getCurYear();
        mTvYear = findViewById(R.id.tv_year);
        mTvMonth = findViewById(R.id.tv_month);
        View tvToday = findViewById(R.id.tv_today);
        View ivNextMonth = findViewById(R.id.iv_next_month);
        View ivPreviousMonth = findViewById(R.id.iv_previous_month);
        View ivNextYear = findViewById(R.id.iv_next_year);
        View ivPreviousYear = findViewById(R.id.iv_previous_year);

        mTvYear.setText(String.valueOf(mCalendarView.getCurYear())); // 当前年份
        mTvMonth.setText(String.format(getContext().getResources().getString(R.string.placeholder_month), mCalendarView.getCurMonth()));

        tvToday.setOnClickListener(view -> mCalendarView.scrollToCurrent(true));
        ivNextMonth.setOnClickListener(view -> mCalendarView.scrollToNext(true));
        ivPreviousMonth.setOnClickListener(view -> mCalendarView.scrollToPre(true));

        // 下一年份
        ivNextYear.setOnClickListener(view -> {
            Calendar maxRangeCalendar = mCalendarView.getMaxRangeCalendar();
            if (mYear < maxRangeCalendar.getYear()) {
                mCalendarView.scrollToCalendar(++mYear, mCalendarView.getCurMonth(), mCalendarView.getCurDay());
            }
        });

        // 上一年份
        ivPreviousYear.setOnClickListener(view -> {
            Calendar minRangeCalendar = mCalendarView.getMinRangeCalendar();
            if (mYear > minRangeCalendar.getYear()) {
                mCalendarView.scrollToCalendar(--mYear, mCalendarView.getCurMonth(), mCalendarView.getCurDay());
            }
        });

        // 设置日期
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int currentYear = calendar.get(java.util.Calendar.YEAR);
        mCalendarView.setRange(
                currentYear - 10,
                1,
                1,
                currentYear + 10,
                12,
                31);

        // 设置日期
        mCalendarView.scrollToCalendar(
                currentYear,
                calendar.get(java.util.Calendar.MONTH) + 1,
                calendar.get(java.util.Calendar.DAY_OF_MONTH));
        mCalendarView.setOnCalendarSelectListener(this);
    }

    public CalendarDialog setOnCalendarSelectListener(CalendarView.OnCalendarSelectListener onCalendarSelectListener) {
        mOnCalendarSelectListener = onCalendarSelectListener;
        return this;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTvYear.setText(String.valueOf(calendar.getYear()));
        mTvMonth.setText(String.format(getContext().getResources().getString(R.string.placeholder_month), calendar.getMonth()));

        if (isClick){
            // 点击
            mOnCalendarSelectListener.onCalendarSelect(calendar, isClick);
        }

    }


}
