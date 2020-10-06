package com.zgzx.metaphysics.controller.views.impl;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.views.core.IPaginationView;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.utils.AppToast;

import java.util.List;

/**
 * 分页 View
 */
public class PaginationView<T> implements IPaginationView<T> {

    private boolean isEnableLoadMore = false;

    private BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private IStatusView mStatusView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    private int mEmptyLayoutResId = R.layout.view_state_layout_empty;
    private int mHeaderLayoutResId;
    private int mNotMoreFooterLayoutResId;


    public PaginationView(RecyclerView recyclerView, @NonNull SmartRefreshLayout refreshLayout, @NonNull IStatusView view) {
        this.mStatusView = view;
        this.mRecyclerView = recyclerView;
        this.mSmartRefreshLayout = refreshLayout;

        // 类型检查
        if (mRecyclerView.getAdapter() instanceof BaseQuickAdapter) {
            this.mAdapter = (BaseQuickAdapter<T, BaseViewHolder>) mRecyclerView.getAdapter();

        } else {
            throw new IllegalStateException("RecyclerView.Adapter 必须实现 BaseQuickAdapter");
        }

        // 观察数据改变
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onChanged() {
                // 无数据时禁止上拉加载更多
                if (mAdapter.getData().isEmpty()) {
                    mSmartRefreshLayout.setEnableLoadMore(false);
                }
            }

        });
    }

    @Override
    public void renderRefresh(List<T> refreshData) {
        mSmartRefreshLayout.finishRefresh();

        mAdapter.setNewData(refreshData);

        // 初次加载数据时添加头部
        if (mHeaderLayoutResId != 0 && mAdapter.getItemCount() <= 0) {
            mAdapter.addHeaderView(LayoutInflater.from(mRecyclerView.getContext())
                    .inflate(mHeaderLayoutResId, mRecyclerView, false));
        }

        // 移除尾部布局
        if (mAdapter.getFooterLayoutCount() > 0) {
            mAdapter.removeAllFooterView();
        }

        // 延迟添加空布局，避免首次加载就呈现空布局
        if (mAdapter.getEmptyViewCount() == 0 && mEmptyLayoutResId != 0) {
           mAdapter.setEmptyView(mEmptyLayoutResId, mRecyclerView);
        }

        if (isEnableLoadMore) {
            mSmartRefreshLayout.setEnableLoadMore(true);
        }
    }

    @Override
    public void renderLoadMore(List<T> loadMoreData) {
        isEnableLoadMore = true;
        mSmartRefreshLayout.finishLoadMore();
        mAdapter.addData(loadMoreData);
    }

    @Override
    public void renderNoMore() {
        mSmartRefreshLayout.setEnableLoadMore(false);
        if (mAdapter.getFooterLayoutCount() < 1 && mNotMoreFooterLayoutResId != 0) {
            mAdapter.addFooterView(LayoutInflater.from(mRecyclerView.getContext()).inflate(
                    mNotMoreFooterLayoutResId, mRecyclerView, false));
        }
    }

    @Override
    public void loading() {
        if (mAdapter.getItemCount() <= 0) {
            mStatusView.loading();
        }
    }

    @Override
    public void empty(CharSequence charSequence) {
        if (mEmptyLayoutResId != 0) {
            mAdapter.setNewData(null);

        } else {
            mStatusView.empty(charSequence);
        }
    }

    @Override
    public void offline() {
        if (mAdapter.getData().isEmpty()) {
            mStatusView.offline();

        } else {
            AppToast.showLong("网络错误");
        }

        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void failure(Throwable throwable) {
        if (mAdapter.getData().isEmpty()) {
            mStatusView.failure(throwable);
            mSmartRefreshLayout.finishRefresh();

        } else {
            mSmartRefreshLayout.finishLoadMore();
            AppToast.showLong(throwable.getMessage());
        }
    }

    @Override
    public void complete() {
        mStatusView.complete();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    public PaginationView<T> setEmptyLayoutResId(int emptyLayoutResId) {
        this.mEmptyLayoutResId = emptyLayoutResId;
        return this;
    }

    public PaginationView<T> setNotMoreFooterLayoutResId(int notMoreFooterLayoutResId) {
        this.mNotMoreFooterLayoutResId = notMoreFooterLayoutResId;
        return this;
    }

    public PaginationView<T> addHeaderLayoutResId(int headerLayoutResId) {
        this.mHeaderLayoutResId = headerLayoutResId;
        return this;
    }

}
