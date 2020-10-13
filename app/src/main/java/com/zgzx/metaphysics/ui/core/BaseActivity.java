package com.zgzx.metaphysics.ui.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.utils.ThemeUtil;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeCompat;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private Unbinder mUnbinder;
    protected final String TAG = this.getClass().getSimpleName();

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
        registerReceiver();
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
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_a3ad87), 0);
//        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
//        StatusBarUtil.setLightMode(this);
    }


    protected @ColorInt
    int getBackgroundColor() {
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


    @Override
    public Resources getResources() {
        //如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources());
        return super.getResources();
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
        unregisterReceiver();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    protected abstract @LayoutRes
    int getContentLayoutId();

    protected abstract void initialize(Bundle savedInstanceState);

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 网络状态变化Receiver
     */
    private class NetworkChangedReceiver extends BroadcastReceiver {
        //    private static final String TAG = NetworkChangedReceiver.class.getSimpleName();
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isConnected = NetworkUtils.isConnected();
            boolean isWifiConnected =
                    NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_WIFI;
            //  KLog.w("wangluo", String.format("网络已连接：%s, 是wifi：%s ---- %s", isConnected,
            //  isWifiConnected, BaseCommActivity.this.getClass().getSimpleName() + intent
            //  .getAction()));
            onNetworkChanged(isConnected, isWifiConnected);
        }


    }

    private NetworkChangedReceiver mNetworkChangedReceiver;

    /**
     * 注册网络变化的广播
     */
    private void registerReceiver() {
        if (null == mNetworkChangedReceiver) {
            mNetworkChangedReceiver = new NetworkChangedReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkChangedReceiver, filter);
    }

    /**
     * 解注册网络变化的广播
     */
    private void unregisterReceiver() {
        if (null != mNetworkChangedReceiver) {
            unregisterReceiver(mNetworkChangedReceiver);
        }
    }

    /**
     * 网络变化的回调方法
     *
     * @param isConnected     网络连接正常
     * @param isWifiConnected 网络为WiFi
     */
    protected void onNetworkChanged(boolean isConnected, boolean isWifiConnected) {
        if (isWifiConnected) {

        } else if (isConnected) {

        } else {
            ToastUtils.showShort(R.string.tv_net_tips_1);
//            SimpleDialog dialog = new SimpleDialog(this);
//            dialog.setMessage(R.string.tv_net_tips);
//            dialog.setNegative(R.string.cancel, view -> {
//                dialog.dismiss();
//                finish();
//                System.exit(0);
//            });
//            dialog.setPositive(R.string.setting_net, view -> {
//                Intent intent = null;
//                // 先判断当前系统版本
//                if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
//                    intent = new Intent(Settings.ACTION_SETTINGS);
//                } else {
//                    intent = new Intent();
//                    intent.setClassName("com.android.settings", "com.android.settings
//                    .WirelessSettings");
//                }
//                startActivity(intent);
//                dialog.dismiss();
//
//
//            });
//            new XPopup.Builder(this)
//                    .isDestroyOnDismiss(false)
//                    .enableShowWhenAppBackground(false)
//
//                    .asCustom(dialog)
//                    .show();
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                v.clearFocus();
                KeyboardUtils.hideSoftInput(v);
            }
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

}
