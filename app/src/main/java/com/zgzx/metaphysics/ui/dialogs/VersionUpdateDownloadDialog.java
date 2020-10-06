package com.zgzx.metaphysics.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.zgzx.metaphysics.R;


/**
 * 版本更新下载
 */
public class VersionUpdateDownloadDialog extends Dialog implements CustomDownloadingDialogListener {

    public VersionUpdateDownloadDialog(@NonNull Context context) {
        super(context, R.style.versionUpdateDialogStyle);
        setContentView(R.layout.dialog_version_update_download);
    }

    @Override
    public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
        return new VersionUpdateDownloadDialog(context);
    }

    @Override
    public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
        TextView tvProgress = dialog.findViewById(R.id.tv_progress);
        ProgressBar progressBar = dialog.findViewById(R.id.progress_bar);
        progressBar.setProgress(progress);
        tvProgress.setText(getContext().getResources().getString(R.string.versionchecklib_progress, progress));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = window.getAttributes();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.88F);
            window.setAttributes(params);
        }

    }

}
