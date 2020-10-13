package com.zgzx.metaphysics.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.mondo.logger.Logger;
import com.zgzx.metaphysics.BuildConfig;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.entity.VersionUpdateEntity;
import com.zgzx.metaphysics.ui.activities.MainActivity;

/**
 * 版本更新
 */
public class VersionUpdateDialog extends Dialog implements ICallbackResult<VersionUpdateEntity> {

    //private View mDialogButCancel;

    public VersionUpdateDialog(@NonNull Context context) {
        super(context, R.style.versionUpdateDialogStyle);
        setContentView(R.layout.dialog_version_update);
        //mDialogButCancel = findViewById(R.id.versionchecklib_version_dialog_cancel);
    }


    @Override
    public void successful(VersionUpdateEntity data) {
        Resources resources = getContext().getResources();
        LocalConfigStore.getInstance().updateRgisterPage(data.getUregister_page());
        if (data.getVersion_code() > BuildConfig.VERSION_CODE) {

            // 设置是否可以返回退出dialog
            setCancelable(false);

            // 版本更新
            DownloadBuilder downloadBuilder = AllenVersionChecker.getInstance()
                    .downloadOnly(
                            UIData.create()
                                    .setDownloadUrl(data.getDownload_url())
                                    .setContent(data.getUpdate_content().replace("<br>", "\n"))
                    );

            // 强制更新
            if (data.isForce()) {
                //mDialogButCancel.setVisibility(View.GONE);
            }

            downloadBuilder
                    .setShowDownloadingDialog(true)
                    .setShowDownloadFailDialog(true)
                    .setApkName(resources.getString(R.string.app_name) + "(" + data.getVersion_name() + ")")
                    .setNewestVersionCode(data.getVersion_code())
                    .setCustomVersionDialogListener((context, versionBundle) -> {
                        VersionUpdateDialog dialog = new VersionUpdateDialog(context);

                        // 更新内容
                        TextView tvUpdateContent = dialog.findViewById(R.id.dialog_tv_update_content);
                        tvUpdateContent.setText(versionBundle.getContent());

                        // 强制更新样式
                        View space = dialog.findViewById(R.id.space);
                        View dialogButCancel = dialog.findViewById(R.id.versionchecklib_version_dialog_cancel);
                        dialogButCancel.setVisibility(data.isForce() ? View.GONE : View.VISIBLE);
                        dialog.setCancelable(data.isForce()?false:true);
                        space.setVisibility(dialogButCancel.getVisibility());

                        return dialog;
                    })
                    // 此处context没实质意义
                    .setCustomDownloadingDialogListener(new VersionUpdateDownloadDialog(getContext()))
                    .setDownloadAPKPath(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/")
                    .executeMission((getContext()));
        }
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

    @Override
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {

    }

}
