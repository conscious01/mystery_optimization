package com.zgzx.metaphysics.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.jaeger.library.StatusBarUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FateBookDetailController;
import com.zgzx.metaphysics.controller.views.impl.LayoutRequestStatusView;
import com.zgzx.metaphysics.model.entity.FateBookDetailEntity;
import com.zgzx.metaphysics.ui.adapters.FateBookDetailPagerAdapter;
import com.zgzx.metaphysics.ui.adapters.FateDirectoryAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.FateBookShareDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 命书详情
 */
public class FateBookDetailActivity extends BaseRequestActivity implements FateBookDetailController.View, FateBookShareDialog.OnDialogClickListener {

    private static final String EXT_BOOK_ID = "BOOK_ID",
            EXT_CHAPTER_ID = "CHAPTER_ID",
            EXT_INDEX = "INDEX";

    public static Intent newIntent(
            Context context,
            int bookId, // 命书
            int chapterId, // 章节
            int directoryIndex // 目录位置
    ) {
        return new Intent(context, FateBookDetailActivity.class)
                .putExtra(EXT_BOOK_ID, bookId)
                .putExtra(EXT_CHAPTER_ID, chapterId)
                .putExtra(EXT_INDEX, directoryIndex);
    }

    @BindView(R.id.iv_share) ImageView mIvShare;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.tv_chapter_name) TextView mTvChapterName;
    @BindView(R.id.recycle_chapter) RecyclerView mRecycleChapter;
    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.layout_title_container) View mLayoutTitleContainer;
    @BindView(R.id.layout_controller) View mLayoutController;

    private int mIndex;

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_fate_book_detail;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 参数
        int bookId = getIntent().getIntExtra(EXT_BOOK_ID, 0);
        int chapterId = getIntent().getIntExtra(EXT_CHAPTER_ID, 0);
        mIndex = getIntent().getIntExtra(EXT_INDEX, 0);

        // 标题
       // AndroidUtil.addStatusBarHeightMargin(mLayoutTitleContainer);
        ActivityTitleHelper.setTitle(this, "第" + (mIndex + 1) + "章");
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTvTitle.setText("第" + (position + 1) + "章");
            }

        });

        // 请求
        mStateView = new LayoutRequestStatusView(findViewById(R.id.state_layout));
        FateBookDetailController.Presenter presenter = new FateBookDetailController.Presenter();
        presenter.setModelAndView(this);
        getLifecycle().addObserver(presenter);
        presenter.request(bookId, chapterId);
    }


    @SuppressLint("WrongConstant")
    @OnClick(value = {R.id.layout_controller, R.id.iv_share_layout})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.layout_controller:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.iv_share_layout:
                FateBookDetailPagerAdapter adapter = (FateBookDetailPagerAdapter) mViewPager.getAdapter();
                FateBookDetailEntity.DataBean item = adapter.getItem(mViewPager.getCurrentItem());
                FateBookShareDialog.show(this,this,   mTvChapterName.getText().toString(),item.getTitle(),
                        TextUtils.join("\n", item.getComment()));




//                startActivity(FateBookShareActivity.newIntent(
//                        this,
//                        mTvChapterName.getText().toString(),
//                        item.getTitle(),
//                        TextUtils.join("\n", item.getComment())));
                break;
        }
    }


    @Override
    public void renderDirectory(String chapterName, List<String> directory) {
        mTvChapterName.setText(chapterName);

        FateDirectoryAdapter adapter = new FateDirectoryAdapter(directory);
        mRecycleChapter.setAdapter(adapter);
        mRecycleChapter.setHasFixedSize(true);

        // 跳转阅读位置
        adapter.setOnItemClickListener((a, v, position) -> {
            mViewPager.setCurrentItem(position);
            mDrawerLayout.closeDrawer(Gravity.START);
        });
    }

    @Override
    public void renderContent(List<FateBookDetailEntity.DataBean> data) {
        mViewPager.setAdapter(new FateBookDetailPagerAdapter(data));
        mViewPager.setCurrentItem(mIndex);
        mIvShare.setVisibility(View.VISIBLE);
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

    @Override
    public void onDialogClick(int i, ViewGroup v) {
        UMImage image = new UMImage(mContext, AndroidUtil.view2Bitmap(v));
        UMWeb web = new UMWeb("https://www.baidu.com/");
        switch (i) {
            case 0:

                if (!AndroidUtil.isWeixinAvilible(this)) {
                    AppToast.showShort("请先安装微信");
                    return;
                }
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withMedia(image)
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case 1:

                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .withMedia(image)
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case 2:
                saveImageLocal(v);
                break;
        }
    }

    @AfterPermissionGranted(Constants.RQC_WRITE_EXTERNAL_STORAGE)
    public void saveImageLocal(ViewGroup view) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            AndroidUtil.saveBitmap(view);
        } else {
            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.permiss_write_storage), Constants.RQC_WRITE_EXTERNAL_STORAGE, perms);
        }
    }
}
