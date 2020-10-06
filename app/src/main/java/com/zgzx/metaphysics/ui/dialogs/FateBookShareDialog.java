package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.utils.image.GlideApp;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class FateBookShareDialog extends BottomPopupView {
    private OnDialogClickListener onDialogClickListener;
    private String book_name;
    private String dictory_name;
    private String content;

    public static void show(Context context, OnDialogClickListener onDialogClickListener, String book_name, String dictory_name, String content) {
        new XPopup.Builder(context)
                .enableDrag(true)
                .dismissOnTouchOutside(true)
                .isDestroyOnDismiss(true)
                .asCustom(new FateBookShareDialog(context, onDialogClickListener, book_name, dictory_name, content))
                .show();
    }


    public FateBookShareDialog(@NonNull Context context, OnDialogClickListener onDialogClickListener, String book_name, String dictory_name, String content) {
        super(context);
        this.book_name = book_name;
        this.onDialogClickListener = onDialogClickListener;
        this.dictory_name = dictory_name;
        this.content = content;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_fate_book_share_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(String.format(getResources().getString(R.string.tv_share_name),LocalConfigStore.getInstance().getUserName()));
        ImageView iv_avatar = findViewById(R.id.iv_avatar);
        LocalConfigStore.getInstance().getAvatar()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(avatar ->
                        GlideApp.with(iv_avatar)
                                .load(avatar)
                                .avatar()
                                .into(iv_avatar));
        ImageView qr_code_img = findViewById(R.id.qr_code_img);
        GlideApp.with(qr_code_img)
                .load(LocalConfigStore.getInstance().getRqCode())
                .into(qr_code_img);

        TextView tv_book_name = findViewById(R.id.tv_book_name);
        tv_book_name.setText(String.format(getResources().getString(R.string.tv_share_name_1),book_name));


        TextView tv_dictory_name = findViewById(R.id.tv_dictory_name);
        tv_dictory_name.setText(dictory_name);
        TextView tvContent = findViewById(R.id.tvContent);
        tvContent.setText(content);


        // 微信
        findViewById(R.id.share_wx_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogClick(0, findViewById(R.id.shareView));
            dialog.dismiss();
        });

        // 朋友圈
        findViewById(R.id.share_pyq_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogClick(1, findViewById(R.id.shareView));
            dialog.dismiss();
        });

        // 保存相册
        findViewById(R.id.save_xc_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogClick(2, findViewById(R.id.shareView));
            dialog.dismiss();
        });
    }

    public interface OnDialogClickListener {

        void onDialogClick(int i, ViewGroup v);
    }
}
