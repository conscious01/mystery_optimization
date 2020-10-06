package com.zgzx.metaphysics.ui.dialogs;


import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.activities.StateActivity;

import androidx.annotation.NonNull;

/**
 * 付款
 */
public class PaymentDialog extends BottomPopupView {

    public static void show(Context context) {
        new XPopup.Builder(context)
                .enableDrag(true)
                .isDestroyOnDismiss(true)
                .asCustom(new PaymentDialog(context))
                .show();
    }


    public PaymentDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_payment;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.dialog_iv_close).setOnClickListener(v -> dismiss());

        // 支付宝
        findViewById(R.id.dialog_layout_alipay).setOnClickListener(v -> {

            // 调起支付宝
            getContext().startActivity(StateActivity.newIntent(v.getContext(), StateActivity.PAY));
        });
    }

}
