package com.zgzx.metaphysics.controller.views.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.views.core.IStatusView;

public class DialogRequestStatusView implements IStatusView {

    private Context context;
    private CharSequence message;

    private BasePopupView mLoading;
//    private ProgressDialog progressDialog;

    private IStatusView mToastStatusView;

    public DialogRequestStatusView(Context context) {
        this(context, context.getString(R.string.loading));
        mToastStatusView = new ToastRequestStatusView(context);
    }

    public DialogRequestStatusView(Context context, CharSequence message) {
        this.context = context;
        this.message = message;
    }

    @Override
    public void loading() {
        if (mLoading == null) {
//            progressDialog = ProgressDialog.show(context, null, message);

            mLoading = new XPopup.Builder(context)
                    .asLoading(context.getString(R.string.loading))
                    .show();
        } else {

            mLoading.show();
//            progressDialog.show();
        }
    }

    @Override
    public void offline() {
        dismiss();
        mToastStatusView.offline();
    }

    @Override
    public void failure(Throwable throwable) {
        dismiss();
        mToastStatusView.failure(throwable);
    }

    @Override
    public void complete() {
        dismiss();
    }

    private void dismiss() {
        if (mLoading != null && mLoading.isShow()) {
//            progressDialog.dismiss();
            mLoading.dismiss();
        }
    }

}
