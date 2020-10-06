package com.zgzx.metaphysics.controller.views.impl;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.utils.LifecycleCountDownTimer;

/**
 * 倒计时 view
 */
public class CountDownTimerView implements ISingleRequestView<Object> {

    private TextView mTextView;
    private Lifecycle mLifecycle;
    private IStatusView mStatusView;

    public CountDownTimerView(@NonNull TextView textView, Lifecycle lifecycle) {
        this.mTextView = textView;
        this.mLifecycle = lifecycle;
        this.mStatusView = new DialogRequestStatusView(textView.getContext());
    }

    @Override
    public void result() {
        new LifecycleCountDownTimer(mLifecycle, mTextView, "%d秒").start();
    }

    @Override
    public void loading() {
        mStatusView.loading();
    }

    @Override
    public void offline() {
        mStatusView.offline();
    }

    @Override
    public void failure(Throwable throwable) {
        mStatusView.failure(throwable);
    }

    @Override
    public void complete() {
        mStatusView.complete();
    }

}
