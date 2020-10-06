package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zgzx.metaphysics.R;

import androidx.appcompat.app.AlertDialog;

public class OrderCommonDialog {


    static AlertDialog dialog;


    public static void show(Context context, String title, String content, DialogClick dialogClick,int closeIvShow) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_order_leave, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_content)).setText(content);
        view.findViewById(R.id.iv_close).setVisibility(closeIvShow);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_leave_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClick.onLeftButton();
            }
        });
        view.findViewById(R.id.tv_leave_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClick.onRightButton();

            }
        });
         dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public static void disMiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


    public interface DialogClick {
        void onLeftButton();

        void onRightButton();

    }

}
