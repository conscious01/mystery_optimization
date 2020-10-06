package com.zgzx.metaphysics.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.dialogs.ShareDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.Imageutils;
import com.zgzx.metaphysics.utils.image.GlideApp;

import androidx.annotation.NonNull;
import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 邀请
 */
public class InviteActivity extends BaseActivity implements ShareDialog.OnDialogClickListener {

    public static Intent newIntent(Context context, UserDetailEntity entity) {
        return new Intent(context, InviteActivity.class)
                .putExtra("USER_DETAIL", entity);
    }


    @BindView(R.id.tv_invite_code)
    TextView mTvInviteCode;
    @BindView(R.id.iv_rq_code)
    ImageView mIvRqCode;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    private UserDetailEntity mUserDetail;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_invite;
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, R.string.share_friends);

        // 数据
        mUserDetail = (UserDetailEntity) getIntent().getParcelableExtra("USER_DETAIL");


        // 邀请码
        mTvInviteCode.setText(mUserDetail.getInvite_code().replace("", " ").trim());

        // 邀请码图片
        GlideApp.with(mIvRqCode)
                .load(mUserDetail.getInvite_qrcode())
                .into(mIvRqCode);

        // 保存图片
        mTvSave.setOnClickListener(v -> {
            ShareDialog.show(this, this, "孔明在线·命运玄机即刻探寻\n" +
                    "\n", AndroidUtil.view2Bitmap(findViewById(R.id.ll)));

        });

    }

    @AfterPermissionGranted(Constants.RQC_WRITE_EXTERNAL_STORAGE)
    private void saveImageLocal() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mTvSave.setVisibility(View.GONE); // 隐藏保存按键
            AndroidUtil.saveBitmap(mScrollView);
            mTvSave.setVisibility(View.VISIBLE);
            AppToast.showShort(getString(R.string.successful));

        } else {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.permiss_write_storage),
                    Constants.RQC_WRITE_EXTERNAL_STORAGE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onDialogClick(int i, View v) {
        UMImage image = new UMImage(mContext, AndroidUtil.view2Bitmap(v));
        UMImage logo = new UMImage(mContext, Imageutils.getBitmapViaDrawable(InviteActivity.this,
                R.drawable.icon_logo_bg));
        String title = getString(R.string.share_Text);
        String content = getString(R.string.share_content);
        String shareURL = mUserDetail.getInvite_link();
        switch (i) {
            case 0:

                if (!AndroidUtil.isWeixinAvilible(this)) {
                    AppToast.showShort("请先安装微信");
                    return;
                }
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withMedia(new UMWeb(shareURL, title, content, logo))
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case 1:

                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .withMedia(new UMWeb(shareURL, title, content, logo))
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case 2:
                saveImageLocal();
                break;
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            AppToast.showShort(getResources().getString(R.string.successful));
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            AppToast.showShort(getResources().getString(R.string.tv_failed));
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            AppToast.showShort(getResources().getString(R.string.cancel));
        }
    };
}
