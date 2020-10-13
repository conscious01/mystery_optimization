package com.zgzx.metaphysics.ui.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.penfeizhou.animation.apng.APNGAssetLoader;
import com.github.penfeizhou.animation.apng.APNGDrawable;
import com.haibin.calendarview.CalendarView;
import com.jaeger.library.StatusBarUtil;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.DailyBlessController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.widgets.sign.view.DateAdapter;
import com.zgzx.metaphysics.widgets.sign.view.SignView;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 每日祈福
 */
public class DailyBlessActivity extends BaseRequestActivity implements DailyBlessController.View {

    @BindView(R.id.signDate)
    SignView signDate;
    @BindView(R.id.tv_calendar_date)
    TextView tv_calendar_date;
    @BindView(R.id.img_ten)
    ImageView img_ten;

    @BindView(R.id.iv_arrow_back)
    ImageView iv_arrow_back;
    @BindView(R.id.img_ge)
    ImageView img_ge;
    @BindView(R.id.bless_btn)
    ImageView bless_btn;
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
    private static final String BLESS_IMAGE_PATH = "assets://bless.png";
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
//                    bless_btn.setVisibility(View.VISIBLE);
//                    APNGAssetLoader assetLoader = new APNGAssetLoader(this, "bless.png");
//
//                    APNGDrawable apngDrawable = new APNGDrawable(assetLoader);
//                    apngDrawable.setLoopLimit(1);
//                    apngDrawable.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
//                        @Override
//                        public void onAnimationStart(Drawable drawable) {
//                            super.onAnimationStart(drawable);
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Drawable drawable) {
//                            super.onAnimationEnd(drawable);
//
//
//                            bless_btn.setVisibility(View.GONE);
//                        }
//                    });
//
//                    bless_btn.setImageDrawable(apngDrawable);


                } else {
                    mPresenter.addMonthParams(mSendContent.getHint().toString());
//                    bless_btn.setVisibility(View.VISIBLE);
//                    APNGAssetLoader assetLoader = new APNGAssetLoader(this, "bless.png");
//
//                    APNGDrawable apngDrawable = new APNGDrawable(assetLoader);
//                    apngDrawable.setLoopLimit(1);
//                    apngDrawable.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
//                        @Override
//                        public void onAnimationStart(Drawable drawable) {
//                            super.onAnimationStart(drawable);
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Drawable drawable) {
//                            super.onAnimationEnd(drawable);
//
//                            mPresenter.addMonthParams(mSendContent.getHint().toString());
//                            bless_btn.setVisibility(View.GONE);
//                        }
//                    });
//                    bless_btn.setImageDrawable(apngDrawable);
//                    ApngLoaderStart.loadImage(ApngImageUtils.Scheme.ASSETS.wrap("bless.png"), bless_btn, new ApngImageLoadingListener(new ApngPlayListener() {
//                        @Override
//                        public void onAnimationStart(ApngDrawable drawable) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(ApngDrawable drawable) {
//                            mPresenter.addMonthParams(mSendContent.getHint().toString());
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(ApngDrawable drawable) {
//                            bless_btn.setVisibility(View.GONE);
//                            drawable.stop();
//                        }
//                    }));

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
        LocalConfigStore.mContents.clear();
        LocalConfigStore.mContents.addAll(content);
        signDate.init();


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


}
