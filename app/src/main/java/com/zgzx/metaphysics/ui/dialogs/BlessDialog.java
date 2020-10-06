package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.enums.PopupPosition;
import com.zgzx.metaphysics.R;

public class BlessDialog extends AttachPopupView {

    private String text;

    public static void show(Context context, View view,int x,int y,String text) {
        new XPopup.Builder(context)
                .hasShadowBg(false)
                .isDestroyOnDismiss(true)
                .offsetX(x)
                .offsetY(y)
                .atView(view)

                .asCustom(new BlessDialog(context, text))
                .show();
    }

    public BlessDialog(Context context, String text) {
        super(context);
        this.text=text;
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_bless_layout;
    }

    @Override
    protected void onCreate() {
     TextView mTvContent = findViewById(R.id.tv_content);
        mTvContent.setText(text);
    }
}
