package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FateBookIndexController;
import com.zgzx.metaphysics.controller.PayController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.LayoutRequestStatusView;
import com.zgzx.metaphysics.controller.views.impl.PayView;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.model.event.BuyFateBookEvent;
import com.zgzx.metaphysics.model.event.FateBookBuyEvent;
import com.zgzx.metaphysics.ui.adapters.FateBookChapterAdapter;
import com.zgzx.metaphysics.ui.adapters.FateDirectoryAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.FateBookCatePriceDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 命书索引
 */
public class FateBookIndexActivity extends BaseRequestActivity implements
        FateBookIndexController.View,
        Consumer<String> {

    private static final String EXT_ID = "FATE_BOOK_ID";

    public static Intent newIntent(Context context, int id) {
        return new Intent(context, FateBookIndexActivity.class)
                .putExtra(EXT_ID, id);
    }

    @BindView(R.id.recycle_index) RecyclerView mRecycleIndex;
    @BindView(R.id.recycle_chapter) RecyclerView mRecycleChapter;

    private int mBookId;
    private PayController.Presenter mBuyPresenter;
    private FateBookIndexController.Presenter mPresenter;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        StatusBarUtil.setLightMode(this);

        ViewGroup layoutContainer = findViewById(R.id.layout_title_container);
        AndroidUtil.addStatusBarHeightMargin(layoutContainer);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_fate_book_index;
    }

    @Override
    protected IStatusView createStatusView() {
        return new LayoutRequestStatusView(findViewById(R.id.state_layout));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FateBookBuyEvent event) {
        getPresenter();
    }

    private void getPresenter() {
        mPresenter = new FateBookIndexController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        mPresenter.request(mBookId);

        // 购买
        mBuyPresenter = new PayController.Presenter();
        mBuyPresenter.setModelAndView(new PayView(this, this));
        getLifecycle().addObserver(mBuyPresenter);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 参数
        mBookId = getIntent().getIntExtra(EXT_ID, 0);

        // 标题
        ActivityTitleHelper.setTitle(this, R.string.fate_book_index);
     //   new LocalDataManager().clearAllFateBookTypeCache(); // 清除所有命书类型缓存
        // 命书请求
        getPresenter();
    }


    @Override
    public void renderFateBookType(List<FateBookTypeEntity> data) {
        // 列表
        FateBookChapterAdapter adapter = new FateBookChapterAdapter(data);
        mRecycleIndex.setAdapter(adapter);
        mRecycleIndex.setHasFixedSize(true);

        // 请求目录
        adapter.setOnItemClickListener((a, view, position) -> {
            adapter.selected(position);
            mPresenter.requestDirectory(data.get(position));
        });
    }


    @Override
    public void renderDirectory(boolean isOwned, int chapterId, List<String> directory) {
        // 列表
        FateDirectoryAdapter adapter = new FateDirectoryAdapter(directory);
        mRecycleChapter.setAdapter(adapter);
        mRecycleChapter.setHasFixedSize(true);

        // 详情
        adapter.setOnItemClickListener((a, view, position) -> {
            if (isOwned){
                startActivity(FateBookDetailActivity.newIntent(view.getContext(), mBookId, chapterId, position));
            } else {
                FateBookCatePriceDialog.show(this, mBookId, chapterId);
            }
        });
    }



    @Override
    public void buySuccessful() {
        AppToast.showShort(getString(R.string.buy_successful));
    }

    @Override
    public void accept(String password) {
        mPresenter.buy(mBookId, password);
    }

    @Override
    public void loading() {
        if (mRecycleIndex.getAdapter() == null) {
            super.loading();
        }
    }

    @Override
    public void failure(Throwable throwable) {
        if (mRecycleIndex.getAdapter() == null) {
            super.failure(throwable);

        } else {
            AppToast.showShort(throwable.getMessage());
            LogUtils.i(throwable.getMessage());
        }
    }


    @Subscribe
    public void onEvent(BuyFateBookEvent event){
        mPresenter.request(mBookId);
    }

}
