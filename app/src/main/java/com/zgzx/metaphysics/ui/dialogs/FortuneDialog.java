package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.zgzx.metaphysics.R;

/**
 * 运势dialog
 */
public class FortuneDialog extends CenterPopupView {

    public static void show(Context context, String message) {
        new XPopup.Builder(context)
                .asCustom(new FortuneDialog(context, message))
                .show();
    }


    private String message;

    public FortuneDialog(@NonNull Context context, String message) {
        super(context);
        this.message = message;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_fortune;
    }


    @Override
    protected void onCreate() {
        super.onCreate();

        // 消息
        TextView tvMessage = findViewById(R.id.dialog_tv_message);
        tvMessage.setText(message);

        // 关闭
        findViewById(R.id.dialog_iv_close).setOnClickListener(v -> dismiss());
    }


    @Override
    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? (int) (XPopupUtils.getWindowWidth(getContext()) * 0.9f)
                : popupInfo.maxWidth;
    }

}
