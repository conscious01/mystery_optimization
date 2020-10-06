package com.zgzx.metaphysics.utils;


import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * 可以感知生命周期的handler
 */
public class LifecycleHandler implements LifecycleEventObserver {

    private Runnable r;
    private Handler mHandler;

    public LifecycleHandler(Lifecycle lifecycle, Looper looper) {
        lifecycle.addObserver(this);
        this.mHandler = new Handler(looper);
    }

    public final boolean postDelayed(@NonNull Runnable r, long delayMillis) {
        this.r = r;
        return mHandler.postDelayed(r, delayMillis);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        // 页面销毁时 移除 回调
        if (event == Lifecycle.Event.ON_DESTROY) {
            mHandler.removeCallbacks(this.r);
        }
    }

}
