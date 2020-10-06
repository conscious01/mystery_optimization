package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.PropertyBillPresenter;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.ui.adapters.PropertyBillAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import butterknife.BindView;

/**
 * 财务记录
 */
public class PropertyBillActivity extends BaseRequestActivity implements OnRefreshLoadMoreListener {

    @BindView(R.id.tv_filter) TextView mTvFilter;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout) SmartRefreshLayout mSmartRefreshLayout;

    private PropertyBillPresenter mPresenter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_property_bill;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.financial_records);

        // 列表
        mRecyclerView.setAdapter(new PropertyBillAdapter());
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        // 请求
        mPresenter = new PropertyBillPresenter();
        mPresenter.setModelAndView(new PaginationView<>(mRecyclerView,mSmartRefreshLayout, this));
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestRefresh();
    }

}
