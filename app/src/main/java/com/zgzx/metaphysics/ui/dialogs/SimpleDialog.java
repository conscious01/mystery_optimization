package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.zgzx.metaphysics.R;

/**
 * 提示信息
 */
public class SimpleDialog extends CenterPopupView {

    public static BasePopupView show(
            Context context,
            @StringRes int message,
            @StringRes int title,
            @StringRes int positive, View.OnClickListener onPositiveListener,
            @StringRes int negative, View.OnClickListener onNegativeListener) {

        SimpleDialog simpleDialog = new SimpleDialog(context);
        simpleDialog.setMessage(message);
        simpleDialog.setTitle(title);
        simpleDialog.setNegative(negative, onNegativeListener);
        simpleDialog.setPositive(positive, onPositiveListener);

       return new XPopup.Builder(context)
                .isDestroyOnDismiss(true)
                .enableShowWhenAppBackground(false)
                .asCustom(simpleDialog)
                .show();
    }

    private @StringRes int title, // 标题
            message, // 消息
            negative,// 消极文字
            positive; // 积极文字

    private String messageStr;

    private View.OnClickListener mNegativeClickListener,
            mPositiveClickListener;

    public SimpleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_simple;
    }

    public void setTitle(@StringRes int title) {
        this.title = title;
    }

    public void setMessage(@StringRes int message) {
        this.message = message;
    }

    public void setMessage(String message){
        this.messageStr = message;
    }

    public void setNegative(@StringRes int negative, View.OnClickListener onClickListener) {
        this.negative = negative;
        this.mNegativeClickListener = onClickListener;
    }

    public void setPositive(@StringRes int positive, View.OnClickListener onClickListener) {
        this.positive = positive;
        this.mPositiveClickListener = onClickListener;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        // 初始化化view
        TextView tvTitle = findViewById(R.id.dialog_tv_title);
        if (title != 0) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }


        TextView tvMessage = findViewById(R.id.dialog_tv_message);

        if (message != 0){
            messageStr = getResources().getString(message);
        }

        if (messageStr != null) {
            tvMessage.setText(messageStr);
            tvMessage.setVisibility(View.VISIBLE);

        } else {
            tvMessage.setVisibility(View.GONE);
        }


        TextView tvNegative = findViewById(R.id.dialog_tv_negative);
        if (negative != 0) {
            tvNegative.setText(negative);
            tvNegative.setVisibility(View.VISIBLE);

        } else {
            tvNegative.setVisibility(View.GONE);
            findViewById(R.id.divider).setVisibility(View.GONE);
        }

        if (mNegativeClickListener != null) {
            tvNegative.setOnClickListener(v -> mNegativeClickListener.onClick(this));
        }


        TextView tvPositive = findViewById(R.id.dialog_tv_positive);
        if (positive != 0) {
            tvPositive.setText(positive);
            tvPositive.setVisibility(View.VISIBLE);

        } else {
            tvPositive.setVisibility(View.GONE);
        }

        if (mPositiveClickListener != null) {
            tvPositive.setOnClickListener(v->mPositiveClickListener.onClick(this));
        }

    }

}
