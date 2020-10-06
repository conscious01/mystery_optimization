package com.zgzx.metaphysics.utils;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.zgzx.metaphysics.R;

import me.jessyan.autosize.utils.ScreenUtils;

/**
 * 提示工具
 */
public class AppToast {

    private static final int sYOffset = ScreenUtils.getStatusBarHeight() >> 1;

    public static void showLong(CharSequence message) {
      ToastUtils.setGravity(Gravity.CENTER, 0, sYOffset);
        ToastUtils.showCustomLong(getView(message));
    }


    public static void showShort(CharSequence message) {
     ToastUtils.setGravity(Gravity.CENTER, 0, sYOffset);
        ToastUtils.showCustomShort(getView(message));
    }


    private static View getView(CharSequence message){
        TextView view = new TextView(Utils.getApp());
        view.setText(message);
        view.setTextColor(Color.WHITE);
        view.setPadding(15,15,15,15);
        view.setBackgroundResource(R.drawable.shape_toast_bg);
        return view;
    }


}
