package com.zgzx.metaphysics.ui.dialogs;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.internal.$Gson$Preconditions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;

import com.lxj.xpopup.core.CenterPopupView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.fragments.V2HomeFragment;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.image.QRCodeUtil;


public class ShareDialog extends BottomPopupView {
    private Context mContext;

    private String message;
    private Bitmap path;
    private OnDialogClickListener onDialogClickListener;

    public static void show(Context context, OnDialogClickListener onDialogClickListener, String message, Bitmap path) {
        new XPopup.Builder(context)
                .enableDrag(true)
                .dismissOnTouchOutside(true)
                .isDestroyOnDismiss(true)
                .asCustom(new ShareDialog(context, onDialogClickListener, message, path))
                .show();
    }


    public ShareDialog(Context context, OnDialogClickListener onDialogClickListener, String message, Bitmap path) {
        super(context);
        this.mContext = context;
        this.onDialogClickListener = onDialogClickListener;
        this.message = message;
        this.path = path;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_share_bottom_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvMessage = findViewById(R.id.share_title);
        tvMessage.setText(message);
        ImageView img_bg = findViewById(R.id.img_bg);
        ImageView icon_qrcode = findViewById(R.id.icon_qrcode);
        img_bg.setImageBitmap(path);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(LocalConfigStore.getInstance().getRegisterPage(), 48, 48);
        icon_qrcode.setImageBitmap(mBitmap);
        // 微信
        findViewById(R.id.share_wx_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogClick(0, findViewById(R.id.share_layout));
            dialog.dismiss();
        });

        // 朋友圈
        findViewById(R.id.share_pyq_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogClick(1, findViewById(R.id.share_layout));
            dialog.dismiss();
        });

        // 保存相册
        findViewById(R.id.save_xc_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogClick(2, findViewById(R.id.share_layout));
            dialog.dismiss();
        });

        findViewById(R.id.tv_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        findViewById(R.id.tv_btn_1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public interface OnDialogClickListener {

        void onDialogClick(int i, View v);
    }
}
