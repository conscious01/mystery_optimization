package com.zgzx.metaphysics.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Desc: 时间计数器
 */
public class LifecycleCountDownTimer extends CountDownTimer implements LifecycleObserver {

    private static final long MILLIS_IN_FUTURE = 90 * 1000,
            COUNT_DOWN_INTERVAL = 1000;

    private TextView mTextView;
    private String mText, mFormat;

    public LifecycleCountDownTimer(Lifecycle lifecycle, TextView view) {
        this(lifecycle, view, null);
    }

    public LifecycleCountDownTimer(Lifecycle lifecycle, TextView view, String format) {
        super(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL);
        mTextView = view;
        mFormat = format;
        mText = view.getText().toString();
        view.setTextColor(Color.parseColor("#75797E"));
        lifecycle.addObserver(this);
    }

    @Override
    public void onFinish() {
        mTextView.setText(mText);
        mTextView.setEnabled(true);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setEnabled(false);

        long args = millisUntilFinished / COUNT_DOWN_INTERVAL;

        if (mFormat == null){
            mTextView.setText(String.valueOf(args));

        } else {
            mTextView.setText(String.format(mFormat, args));
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        this.cancel();
    }

}
