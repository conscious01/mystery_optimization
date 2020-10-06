package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.zgzx.metaphysics.R;

public class FatePayTipsDialog extends CenterPopupView {

    public static BasePopupView show(Context context) {
        return new XPopup.Builder(context)
                .isDestroyOnDismiss(true)
                .enableShowWhenAppBackground(false)
                .asCustom(new FatePayTipsDialog(context))
                .show();
    }

    public FatePayTipsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_fate_book_tips_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.closeImage).setOnClickListener(view -> dialog.dismiss());
    }
}
