package com.zgzx.metaphysics.widgets;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomRunnable implements Runnable {

  public long millisUntilFinished ;
  public TextView textView;
  Handler handler;

  public CustomRunnable(Handler handler, TextView textView, long millisUntilFinished) {
    this.handler = handler;
    this.textView = textView;
    this.millisUntilFinished = millisUntilFinished;
  }

  @Override
  public void run() {

    textView.setText(millisUntilFinished+"");

    millisUntilFinished = millisUntilFinished - 1;
    if (millisUntilFinished>0) {
      handler.postDelayed(this, 1000);

    }

    /* and here comes the "trick" */
  }

}