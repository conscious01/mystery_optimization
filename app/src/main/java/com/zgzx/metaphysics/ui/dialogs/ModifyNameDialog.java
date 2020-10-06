package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.utils.AppToast;

public class ModifyNameDialog extends CenterPopupView {
    public static void show(Context context, OnDialogClickListener onDialogClickListener, String name) {
        new XPopup.Builder(context)
                .asCustom(new ModifyNameDialog(context, onDialogClickListener, name))
                .show();
    }


    private String name;
    private OnDialogClickListener onDialogClickListener;

    public ModifyNameDialog(@NonNull Context context, OnDialogClickListener onDialogClickListener, String name) {
        super(context);
        this.name = name;
        this.onDialogClickListener = onDialogClickListener;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_modify_name_layout;
    }


    @Override
    protected void onCreate() {
        super.onCreate();

        EditText mEtContent = findViewById(R.id.et_content);
        mEtContent.setInputType(InputType.TYPE_CLASS_TEXT);


        // 关闭
        findViewById(R.id.clearImg).setOnClickListener(v -> {
            mEtContent.setText("");
        });
        findViewById(R.id.cancel_button).setOnClickListener(v -> dismiss());
        findViewById(R.id.confirm_button).setOnClickListener(v -> {

            if (mEtContent.getText().length() > 0) {
                onDialogClickListener.onDialogClick(mEtContent.getText().toString(), v);
                dismiss();
            } else {
                AppToast.showShort(getResources().getString(R.string.alter_name));
            }

        });
    }

    public interface OnDialogClickListener {

        void onDialogClick(String name, View v);
    }

}
