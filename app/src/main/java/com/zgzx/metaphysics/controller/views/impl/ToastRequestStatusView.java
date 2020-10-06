package com.zgzx.metaphysics.controller.views.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.core.BasePopupView;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.network.exceptions.IncompleteInformationException;
import com.zgzx.metaphysics.network.exceptions.RegisterException;
import com.zgzx.metaphysics.network.exceptions.UnauthorizedException;
import com.zgzx.metaphysics.ui.activities.LoginActivity;
import com.zgzx.metaphysics.ui.activities.PersonalInformationActivity;
import com.zgzx.metaphysics.ui.activities.SupplementInformationActivity;
import com.zgzx.metaphysics.ui.dialogs.SimpleDialog;
import com.zgzx.metaphysics.ui.dialogs.UnauthorizedDialog;
import com.zgzx.metaphysics.utils.AppToast;

import java.util.concurrent.TimeoutException;

import static com.zgzx.metaphysics.ui.activities.SupplementInformationActivity.PERFECT_INFORMATION;

public class ToastRequestStatusView implements IStatusView {

    private Context context;

    public ToastRequestStatusView(Context context) {
        this.context = context;
    }

    @Override
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {
        throwable.printStackTrace();

        if (throwable instanceof UnauthorizedException) {
            UnauthorizedDialog.show(context, throwable.getMessage());

        } else if (throwable instanceof RegisterException) {
            showLoginDialog();

        } else if (throwable instanceof IncompleteInformationException){ // 完善用户信息
            IncompleteInformationException exception = (IncompleteInformationException) throwable;
            LocalConfigStore.getInstance().saveUserToken(exception.getToken());
          //  context.startActivity(SupplementInformationActivity.newIntent(context, PERFECT_INFORMATION));
            context.startActivity(PersonalInformationActivity.newIntent(context, null,0));
        } else if (throwable instanceof TimeoutException){
            AppToast.showShort(context.getString(R.string.timeout_error));
        } else {
            AppToast.showShort(throwable.getMessage());
        }
    }


    @Override
    public void offline() {
        AppToast.showShort(context.getString(R.string.network_error));
    }


    // 已存在，注册失败
    private  BasePopupView popup;
    private void showLoginDialog() {
        popup = SimpleDialog.show(context,
                0,
                R.string.error_register_login,
                R.string.login, v -> context.startActivity(new Intent(context, LoginActivity.class)),
                R.string.cancel, v -> {
                    if (popup != null){
                        popup.dismiss();
                    }
                });
    }

}
