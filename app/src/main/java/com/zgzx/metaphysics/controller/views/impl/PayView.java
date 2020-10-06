package com.zgzx.metaphysics.controller.views.impl;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.core.util.Consumer;

import com.lxj.xpopup.XPopup;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.PayController;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity;
import com.zgzx.metaphysics.ui.dialogs.PayPasswordDialog;
import com.zgzx.metaphysics.ui.dialogs.SimpleDialog;


import static com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity.CREATE_ASSETS_PASSWORD;


public class PayView implements PayController.View {

    private Context context;
    private IStatusView mStatusView;
    private Consumer<String> mConsumer;

    private PayPasswordDialog mPasswordDialog;

    public PayView(Context context, Consumer<String> consumer) {
        this.context = context;
        this.mConsumer = consumer;
        this.mStatusView = new ToastRequestStatusView(context);

//        mPasswordDialog = new PayPasswordDialog(context);
//        mPasswordDialog.setListener(consumer);
    }


    @Override
    public void renderSettingView() {
        // 创建密码提示dialog
        SimpleDialog dialog = new SimpleDialog(context);
        dialog.setTitle(R.string.set_assets_password_title);
        dialog.setNegative(R.string.later, v -> dialog.dismiss());
        dialog.setPositive(R.string.settings, v -> {
            context.startActivity(SafetyVerificationActivity.newIntent(context, CREATE_ASSETS_PASSWORD));
            dialog.dismiss();
        });

        new XPopup.Builder(context)
                .isDestroyOnDismiss(true)
                .enableShowWhenAppBackground(false)
                .asCustom(dialog)
                .show();
    }


    @Override
    public void renderPasswordView(@StringRes int message, float amount, float balance) {
        mPasswordDialog = new PayPasswordDialog(context)
                .setMessage(message)
                .setAmount(amount)
                .setBalance(balance)
                .setListener(mConsumer);

        new XPopup.Builder(context)
                .isDestroyOnDismiss(true) // 对于只使用一次的弹窗，推荐设置这个
                .autoOpenSoftInput(true)
                .isRequestFocus(false)
                .asCustom(mPasswordDialog)
                .show();
    }


    @Override
    public void loading() {
        // mStatusView.loading();
    }


    @Override
    public void failure(Throwable throwable) {
        if (mPasswordDialog != null) {
            mPasswordDialog.setErrorMessage(throwable.getMessage());

        } else {
            mStatusView.failure(throwable);
        }
    }

//    @Override
//    public void complete() {
//        if (mPasswordDialog != null && mPasswordDialog.isShow()) {
//            //mPasswordDialog.dismiss();
//        }
//    }

}
