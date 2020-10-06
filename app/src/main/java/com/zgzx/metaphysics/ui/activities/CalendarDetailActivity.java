package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.jaeger.library.StatusBarUtil;
import com.lxj.xpopup.XPopup;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.CalendarDetailPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.entity.CalendarDetailEntity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.CalendarDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日历详情
 */
public class CalendarDetailActivity extends BaseRequestActivity implements
        ICallbackResult<CalendarDetailEntity>,
        CalendarView.OnCalendarSelectListener {

    @BindView(R.id.tv_date) TextView mTvDate;
    @BindView(R.id.tv_day) TextView mTvDay;
    @BindView(R.id.tv_lunar) TextView mTvLunar;
    @BindView(R.id.tv_week) TextView mTvWeek;
    @BindView(R.id.tv_lash) TextView mTvLash;
    @BindView(R.id.tv_avoid) TextView mTvAvoid;
    @BindView(R.id.tv_should) TextView mTvShould;
    @BindView(R.id.group) RadioGroup mGroup;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.text_view1) TextView mTextView1;
    @BindView(R.id.text_view2) TextView mTextView2;


    private CalendarDialog mCalendarDialog;
    private CalendarDetailPresenter mPresenter;

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected int getBackgroundColor() {
        return getColorForRes(R.color.backgroundColorSecondary);
    }

    @Override
    protected int getTitleLayoutRes() {
        return R.layout.include_activity_cloud_title;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_calendar_detail;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, R.string.perpetual_calendar);
        AndroidUtil.addStatusBarHeightPadding(findViewById(R.id.layout_title_container));

        // 请求
        mPresenter = new CalendarDetailPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

        // 日历
        mCalendarDialog = new CalendarDialog(this)
                .setOnCalendarSelectListener(this);
        mTvDate.setOnClickListener(v ->
                new XPopup.Builder(this)
                        .enableDrag(true)
                        .isDestroyOnDismiss(true)
                        .asCustom(mCalendarDialog)
                        .show()
        );

        // 周
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        String[] weekArray = getResources().getStringArray(R.array.calendar_week_string_array);
        mTvWeek.setText(weekArray[calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1]);
    }


    @Override
    public void successful(CalendarDetailEntity result) {
        // TODO 未国际化
        mTvDate.setText(String.format(getString(R.string.placeholder_year_month_day),
                result.getYear(), result.getMonth(), result.getDay()));
        mTvDay.setText(String.valueOf(result.getDay()));

        // 农历
        mTvLunar.setText(getString(R.string._xpopup_ext_lunar_calendar)
                + result.getMonth_ag() + result.getDay_ag());

        // 相冲
        mTvLash.setText(result.getLash());

        // 忌
        mTvAvoid.setText(result.getAvoid());

        // 宜
        mTvShould.setText(result.getSuitable());

        // 其他数据
        mGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton button = mGroup.findViewById(checkedId);
            switch (checkedId) {
                case R.id.radio_peng_zu: // 彭祖百忌
                    CalendarDetailEntity.PengZuBean pengZu = result.getPeng_zu();
                    render(button.getText().toString(),
                            TextUtils.join("\n", pengZu.getShi()),
                            TextUtils.join("\n", pengZu.getExplain()));
                    break;

                case R.id.radio_character: // 八字
                    render(button.getText().toString(),
                            null,
                            result.getYear_column()
                                    + " " + result.getMonth_column()
                                    + " " + result.getDay_column()
                                    + " " + result.getHour_column());
                    break;

                case R.id.radio_night_28:
                    render(button.getText().toString(),
                            result.getNight28().getName(),
                            result.getNight28().getExplain());
                    break;

                case R.id.radio_twelve_gods:
                    render(button.getText().toString(),
                            result.getTwelve_gods().getName(),
                            result.getTwelve_gods().getExplain());
                    break;
            }
        });

        mGroup.clearCheck();
        mGroup.check(R.id.radio_night_28);
    }

    private void render(String name, String string1, String string2) {
        mTvName.setText(name);
        mTextView1.setText(string1);
        mTextView2.setText(string2);
        mTextView1.setVisibility(TextUtils.isEmpty(string1) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mCalendarDialog.dismiss();
        mPresenter.request(calendar.getTimeInMillis());

        // 周
        String[] weekArray = getResources().getStringArray(R.array.calendar_week_string_array);
        mTvWeek.setText(weekArray[calendar.getWeek()]);
    }

    @Override
    public void loading() {
        if (TextUtils.isEmpty(mTvDate.getText())){
            super.loading();
        }
    }

    @Override
    public void failure(Throwable throwable) {
        if (TextUtils.isEmpty(mTvDate.getText())){
            super.failure(throwable);

        } else {
            AppToast.showLong(throwable.getMessage());
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

}
