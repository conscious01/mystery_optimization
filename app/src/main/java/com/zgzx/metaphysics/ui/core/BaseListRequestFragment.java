package com.zgzx.metaphysics.ui.core;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.core.IPaginationPresenter;

import butterknife.BindView;

public abstract class BaseListRequestFragment<P extends IPaginationPresenter, T> extends BaseRequestFragment
        implements OnRefreshLoadMoreListener {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.smart_refresh_layout)
    protected SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.include_smart_refresh_recycler_view;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 列表
        mRecyclerView.setAdapter(getAdapter());
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        // 请求
        //RefreshAndLoadMoreView<T> view = new RefreshAndLoadMoreView<>(mRecyclerView, mSmartRefreshLayout, this);
        //view.setEmptyLayoutResId(R.layout.view_state_layout_empty);
        //mRequestPresenter.setModelAndView(view);
        //mRequestPresenter.requestRefresh();

    }

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
