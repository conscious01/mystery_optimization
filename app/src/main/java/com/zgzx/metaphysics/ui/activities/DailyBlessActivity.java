package com.zgzx.metaphysics.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.haibin.calendarview.CalendarView;
import com.jaeger.library.StatusBarUtil;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.DailyBlessController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.BlessDialog;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.DateUtils;


import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 每日祈福
 */
public class DailyBlessActivity extends BaseRequestActivity implements DailyBlessController.View, CalendarView.OnCalendarSelectListener {
    @BindView(R.id.mCalendarViewBless)
    CalendarView mCalendarViewBless;

    @BindView(R.id.tv_calendar_date)
    TextView tv_calendar_date;
    @BindView(R.id.img_ten)
    ImageView img_ten;

    @BindView(R.id.iv_arrow_back)
    ImageView iv_arrow_back;
    @BindView(R.id.img_ge)
    ImageView img_ge;

    @BindView(R.id.tv_bless_day)
    TextView tv_bless_day;
    @BindView(R.id.tv_bless_day_contiune)
    TextView tv_bless_day_contiune;

    @BindView(R.id.mSendContent)
    EditText mSendContent;
    private int[] imgs = {R.drawable.icon_zero, R.drawable.icon_one, R.drawable.icon_two, R.drawable.icon_three,
            R.drawable.icon_four, R.drawable.icon_five, R.drawable.icon_six, R.drawable.icon_seven,
            R.drawable.icon_eight, R.drawable.icon_nine};
    private String todays, date;
    private DailyBlessController.Presenter mPresenter;

    private int currentYear;
    private int month;

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_21221e));

    }

    @Override
    protected IStatusView createStatusView() {
        return new ToastRequestStatusView(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_daily_bless_layout;
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {
        mPresenter = new DailyBlessController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

        initCalendar();
//        mSendContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                KeyboardUtils.showSoftInput(v);
//            }
//        });
    }

    /**
     * 初始化日期
     */
    private void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        iv_arrow_back.setColorFilter(Color.parseColor("#FFC4B2A1"));
        //月
        month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10) {
            date = currentYear + "-" + "0" + month;
        } else {
            date = currentYear + "-" + month;
        }
        mPresenter.init(date);
        tv_calendar_date.setText(currentYear + "年" + month + "月" + getResources().getString(R.string.tv_daily_bless_calendar));
        mCalendarViewBless.setRange(
                currentYear - 10,
                1,
                1,
                currentYear + 10,
                12,
                31);
        // 设置日期
        mCalendarViewBless.scrollToCalendar(
                currentYear,
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        mCalendarViewBless.setOnCalendarSelectListener(this);
    }

    @OnClick(value = {R.id.iv_arrow_back, R.id.tvBtnSend})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_back:
                onBackPressed();
                break;

            case R.id.tvBtnSend: // 发送祈福
                if (!TextUtils.isEmpty(mSendContent.getText().toString())) {
                    mPresenter.addMonthParams(mSendContent.getText().toString());
                } else {
                    mPresenter.addMonthParams(mSendContent.getHint().toString());
                }

                KeyboardUtils.hideSoftInput(DailyBlessActivity.this);
                break;
        }
    }

    @Override
    public void addBlessSuccess() {
        mPresenter.init(date);
        mSendContent.setText("");
        ToastUtils.showShort(getResources().getString(R.string.successful));
    }

    private List<String> mContents;

    @Override
    public void renderMoths(List<String> content) {
        mContents = content;
        Map<String, com.haibin.calendarview.Calendar> map = new HashMap<>();


        for (int i = 0; i < content.size(); i++) {
            map.put(getSchemeCalendar(currentYear, month, i + 1, 0xFF40db25, content.get(i)).toString(),
                    getSchemeCalendar(currentYear, month, i + 1, 0xFF40db25, content.get(i)));
        }


        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarViewBless.setSchemeDate(map);

    }

    /**
     * 设置标记文本
     *
     * @param year
     * @param month
     * @param day
     * @param color
     * @param text
     * @return
     */
    private com.haibin.calendarview.Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void renderMothsDay(int continueDays, int userDays, int todayDays) {
        tv_bless_day.setText(String.format(getResources().getString(R.string.tv_total_day), userDays));
        tv_bless_day_contiune.setText(String.format(getResources().getString(R.string.tv_total_day_1), todayDays));
        if (continueDays < 10) {
            todays = "0" + continueDays;
        } else {
            todays = String.valueOf(continueDays);
        }


        img_ten.setBackgroundResource(imgs[Integer.valueOf(todays.substring(0, 1))]);
        img_ge.setBackgroundResource(imgs[Integer.valueOf(todays.substring(1))]);
    }


    @Override
    public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
        int weekMoth = 1;

        try {
            weekMoth = DateUtils.getWeeksMonth(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(mContents.get(calendar.getDay() - 1))) {
//              if (!"假".equals(calendar.getScheme())) {
//                  if (isSelected && !calendar.isCurrentDay()) {
//                      if (weekMoth>2) {

            if (calendar.getWeek() == 1) {
                BlessDialog.show(mContext, mCalendarViewBless, 10, -880 + 120 * weekMoth, calendar.getScheme());
            } else if ((calendar.getWeek() == 2)) {
                BlessDialog.show(mContext, mCalendarViewBless, 10 + 60 * calendar.getWeek(), -880 + 120 * weekMoth, calendar.getScheme());
            } else if ((calendar.getWeek() == 3)) {
                BlessDialog.show(mContext, mCalendarViewBless, 10 + 100 * calendar.getWeek(), -880 + 120 * weekMoth, calendar.getScheme());
            } else if ((calendar.getWeek() == 4)) {
                BlessDialog.show(mContext, mCalendarViewBless, 10 + 100 * calendar.getWeek(), -880 + 120 * weekMoth, calendar.getScheme());
            } else if ((calendar.getWeek() == 5)) {
                BlessDialog.show(mContext, mCalendarViewBless, 10 + 100 * calendar.getWeek(), -880 + 120 * weekMoth, calendar.getScheme());
            } else if ((calendar.getWeek() == 6)) {
                BlessDialog.show(mContext, mCalendarViewBless, 10 + 80 * calendar.getWeek(), -880 + 120 * weekMoth, calendar.getScheme());
            } else if ((calendar.getWeek() == 7)) {
                BlessDialog.show(mContext, mCalendarViewBless, 10 + 60 * calendar.getWeek(), -880 + 120 * weekMoth, calendar.getScheme());
            }


//                      }else {
//        BlessDialog.show(mContext, this, cx - 50, y - mItemHeight * weekMoth , calendar.getScheme());
//    }
//                  }
            // }
        }

    }


}
