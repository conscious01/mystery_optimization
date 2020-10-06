package com.zgzx.metaphysics.ui.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.activities.CalendarDetailActivity;
import com.zgzx.metaphysics.ui.adapters.SimpleFragmentStatePagerAdapter;
import com.zgzx.metaphysics.ui.core.BaseFragment;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.LunarCalendarUtil;
import com.zgzx.metaphysics.utils.image.GlideApp;
import com.zgzx.metaphysics.widgets.ArcView;


import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 运势
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_month) TextView mTvMonth;
    @BindView(R.id.tv_day) TextView mTvDay;
    @BindView(R.id.tv_week) TextView mTvWeek;
    @BindView(R.id.tv_lunar_calendar) TextView mTvLunarCalendar;
    @BindView(R.id.iv_avatar) ImageView mIvAvatar;

    private FortuneFragment mFortuneFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initialize(Bundle savedInstanceState) {
        AndroidUtil.addStatusBarHeightPadding(findViewById(R.id.layout_container));

        // 当前日历，时间
        String[] weekArray = getResources().getStringArray(R.array.calendar_week_string_array);
        String[] monthArray = getResources().getStringArray(R.array.calendar_month_string_array);
        Calendar calendar = Calendar.getInstance();
        mTvMonth.setText((monthArray[calendar.get(Calendar.MONTH)])); // 月
        mTvWeek.setText(weekArray[calendar.get(Calendar.DAY_OF_WEEK) - 1]); // 周
        mTvDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))); // 日
        mTvLunarCalendar.setText(LunarCalendarUtil.get(getResources(), Calendar.getInstance()));

        // 个人头像
        LocalConfigStore.getInstance().getAvatar()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(avatar ->
                        GlideApp.with(mIvAvatar)
                        .load(avatar)
                        .avatar()
                        .into(mIvAvatar));

        // 运势
        if (savedInstanceState != null){
            mFortuneFragment = (FortuneFragment) getChildFragmentManager()
                    .getFragment(savedInstanceState, FortuneFragment.class.getName());

        } else {
            mFortuneFragment = new FortuneFragment();
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mFortuneFragment)
                    .commit();
        }


    }

    @OnClick(value = {R.id.layout_calendar})
    public void onViewClicked(View v) {
        startActivity(CalendarDetailActivity.class);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtil.setLightMode(getActivity());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        getChildFragmentManager().putFragment(outState, mFortuneFragment.getClass().getName(), mFortuneFragment);
        super.onSaveInstanceState(outState);
    }
}
