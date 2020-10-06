package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.activities.LoginActivity;
import com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity;
import com.zgzx.metaphysics.utils.ActivityNavigateManager;

import static com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity.CREATE_ASSETS_PASSWORD;

/**
 * 重新登录, 使用静态避免多次弹出
 */
public class UnauthorizedDialog {

//    private static AlertDialog sAlertDialog;

    private static SimpleDialog sDialog;

    public static void show(Context context, String message) {
        // 弹出过期对话框
        if (sDialog == null) {

            sDialog = new SimpleDialog(context);
            sDialog.setTitle(R.string.re_register);
            sDialog.setMessage(message);
//            sDialog.setNegative(R.string.cancel, v -> sDialog.dismiss());
            sDialog.setPositive(R.string.confirm, v -> {
                LocalConfigStore.getInstance().logout();
//                context.startActivity(new Intent(context, LoginActivity.class));
                ActivityNavigateManager.navigate(context, LoginActivity.class);
                sDialog.dismiss();
            });

            new XPopup.Builder(context)
                    .isDestroyOnDismiss(true)
                    .enableShowWhenAppBackground(false)
                    .setPopupCallback(new SimpleCallback() {

                        @Override
                        public void onDismiss(BasePopupView popupView) {
                            sDialog = null;
                        }

                    })
                    .asCustom(sDialog)
                    .show();
        }

    }

}
