package com.zgzx.metaphysics.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.LunarCalendarUtil;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 分享命书
 */
public class FateBookShareActivity extends BaseActivity {

    private static final String EXT_TITLE = "TITLE", // 章节标题
            EXT_CHAPTER = "CHAPTER", // 类型标题
            EXT_CONTENT = "CONTENT"; // 内容

    public static Intent newIntent(Context context, String chapterName, String title, String content) {
        return new Intent(context, FateBookShareActivity.class)
                .putExtra(EXT_CHAPTER, chapterName)
                .putExtra(EXT_TITLE, title)
                .putExtra(EXT_CONTENT, content);
    }


    @BindView(R.id.tv_action) TextView mTvAction;
    @BindView(R.id.tv_date) TextView mTvDate;
    @BindView(R.id.tv_user) TextView mTvUser;
    @BindView(R.id.tv_lunar_date) TextView mTvLunarDate;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.tv_book_title) TextView mTvBookTitle;
    @BindView(R.id.iv_avatar) RoundedImageView mIvAvatar;
    @BindView(R.id.iv_rq_code) ImageView mIvRqCode;
    @BindView(R.id.tv_chapter_name) TextView mTvChapterName;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_fate_book_share;
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 参数
        String title = getIntent().getStringExtra(EXT_TITLE);
        String content = getIntent().getStringExtra(EXT_CONTENT);
        String chapterName = getIntent().getStringExtra(EXT_CHAPTER);

        // 标题
        mTvAction.setEnabled(true);
        ActivityTitleHelper.setTitle(this, R.string.fate_book_sharing, R.string.save);

        // 当前日历，时间
        String[] weekArray = getResources().getStringArray(R.array.calendar_week_string_array);
        String[] monthArray = getResources().getStringArray(R.array.calendar_month_string_array);
        Calendar calendar = Calendar.getInstance();

        mTvDate.setText(monthArray[calendar.get(Calendar.MONTH)] + " · " + calendar.get(Calendar.DAY_OF_MONTH));
        mTvLunarDate.setText(LunarCalendarUtil.get(getResources(),
                Calendar.getInstance()) + " · " + weekArray[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        mTvChapterName.setText(chapterName + getString(R.string.articles));
        mTvBookTitle.setText(title); // 标题
        mTvContent.setText(content); // 书籍内容
        mTvUser.setText(String.format(getString(R.string.placeholder_fate_book_detailed_approval),
                LocalConfigStore.getInstance().getUserName()));

        // 个人头像
        GlideApp.with(mIvAvatar)
                .load(LocalConfigStore.getInstance().getAvatar().blockingFirst())
                .avatar()
                .into(mIvAvatar);

        // 下载二维码
        GlideApp.with(mIvRqCode)
                .load(LocalConfigStore.getInstance().getRqCode())
                .into(mIvRqCode);
    }

    @OnClick(R.id.tv_action)
    public void onViewClicked() {
        saveImageLocal();
    }

    @AfterPermissionGranted(Constants.RQC_WRITE_EXTERNAL_STORAGE)
    private void saveImageLocal() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            AndroidUtil.saveBitmap(findViewById(R.id.scroll_view));
            AppToast.showShort(getString(R.string.successful));

        } else {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.permiss_write_storage), Constants.RQC_WRITE_EXTERNAL_STORAGE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
