package com.zgzx.metaphysics.ui.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.utils.ThemeUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        // 屏幕方向
        setRequestedOrientation(screenOrientation());

        // view内容
        setContentView(getContentLayoutId());

        // 状态栏
        initStatusBar();

        // 弹出键盘
        if (popKeyboard()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        // 背景色
        if (getBackgroundColor() != 0) {
            getWindow().getDecorView().setBackgroundColor(getBackgroundColor());
        }

        // 初始化事件总线
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }

        // 初始化黄油刀
        mUnbinder = ButterKnife.bind(this);

        // 初始化
        initialize(savedInstanceState);
    }

    /**
     * 屏幕方向
     */
    protected int screenOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    /**
     * 弹出键盘
     */
    protected boolean popKeyboard() {
        return false;
    }

    /**
     * 事件总线
     */
    protected boolean useEventBus() {
        return false;
    }

    protected void initStatusBar() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.color_a3ad87),0);
//        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
//        StatusBarUtil.setLightMode(this);
    }


    protected @ColorInt int getBackgroundColor() {
        return ThemeUtil.getBackgroundColor(getTheme());
    }

    protected int getColorForRes(@ColorRes int id) {
        return getResources().getColor(id);
    }

    public int getInteger(@IntegerRes int id) {
        return getResources().getInteger(id);
    }

    public void startActivityAndFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    public BaseActivity startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
        return this;
    }

    protected void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    public void finish(int resultCode) {
        setResult(resultCode);
        super.finish();
    }

    public void finish(int resultCode, Intent intent) {
        setResult(resultCode, intent);
        super.finish();
    }

    public String getStringExtra(String name) {
        return getIntent().getStringExtra(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();

        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    protected abstract @LayoutRes int getContentLayoutId();

    protected abstract void initialize(Bundle savedInstanceState);

}
