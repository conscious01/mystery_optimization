package com.zgzx.metaphysics.ui.core;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.core.IPaginationPresenter;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import butterknife.BindView;

public abstract class BaseListRequestActivity<P extends IPaginationPresenter, T> extends BaseRequestActivity
        implements OnRefreshLoadMoreListener {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @BindView(R.id.smart_refresh_layout) SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected int getTitleLayoutRes() {
        return R.layout.include_activity_title;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.include_smart_refresh_recycler_view;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, getTitleRes());

        // 列表
        mRecyclerView.setAdapter(getAdapter());
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        // 请求
        //RefreshAndLoadMoreView<T> view = new RefreshAndLoadMoreView<>(mRecyclerView, mSmartRefreshLayout, this);
        //view.setEmptyLayoutResId(R.layout.view_state_layout_empty);
        //mRequestPresenter.setModelAndView(view);
    }

    abstract protected @StringRes int getTitleRes();

    abstract protected BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    @Override
    public void onAnewRequestNetwork() {
        //mRequestPresenter.requestRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //mRequestPresenter.requestRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //mRequestPresenter.requestLoadMore();
    }

}
