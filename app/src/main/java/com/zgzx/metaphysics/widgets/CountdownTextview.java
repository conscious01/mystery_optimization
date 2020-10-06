package com.zgzx.metaphysics.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zgzx.metaphysics.utils.DateUtils;

/**
 * created by alexwu
 * on 2020/10/2 15:50
 *
 * @description
 */
public class CountdownTextview extends androidx.appcompat.widget.AppCompatTextView implements Runnable {

    private int time;

    public CountdownTextview(Context context) {
        super(context);
    }

    public CountdownTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTime(int time) {  //设定初始值
        this.time = time;
        run();
    }


    @Override
    public void run() {

        if (run) {
            ComputeTime();
            this.setText(DateUtils.secToTime(time));
            postDelayed(this, 1000);

        } else {
            removeCallbacks(this::run);
        }


    }

    private boolean run = true; //觉得是否执行run方法

    public void beginRun() {
        this.run = true;
        run();
    }

    public void stopRun() {
        this.run = false;
    }

    private void ComputeTime() {
        time--;
        if (time == 0) {
            stopRun();
        }
    }
}