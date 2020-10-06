package com.zgzx.metaphysics.ui.dialogs;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupPosition;
import com.zgzx.metaphysics.R;

public class FateBookEditDialog extends AttachPopupView {
    private OnClickListener editListener;
    private OnClickListener createListener;
    private View mView;

    public static BasePopupView show(Context context, View view, OnClickListener editListener, OnClickListener createListener) {
        return new XPopup.Builder(context)
                .isDestroyOnDismiss(true)
                .watchView(view)
                .atView(view)
                .isCenterHorizontal(true)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(new FateBookEditDialog(context, view, editListener, createListener))
                .show();
    }

    public FateBookEditDialog(@NonNull Context context, View view, OnClickListener editListener, OnClickListener createListener) {
        super(context);
        this.createListener = createListener;
        this.editListener = editListener;
        this.mView = view;
    }

    public FateBookEditDialog(Context context) {
        super(context);

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_fate_edit_layout;
    }

    @Override
    protected void onCreate() {

        findViewById(R.id.add_txt).setOnClickListener(createListener);
        findViewById(R.id.edit_txt).setOnClickListener(editListener);
    }


}
