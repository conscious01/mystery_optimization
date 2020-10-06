package com.zgzx.metaphysics.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.PropertyBillPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.LayoutRequestStatusView;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.ui.activities.MyWalletActivity;
import com.zgzx.metaphysics.ui.activities.RechargeActivity;
import com.zgzx.metaphysics.ui.activities.TransferActivity;
import com.zgzx.metaphysics.ui.adapters.PropertyBillAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.utils.NumberUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class MyWalletFragment extends BaseRequestFragment implements OnRefreshLoadMoreListener {
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    private PropertyBillPresenter mPresenter;
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestRefresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_wallet;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 余额
        mTvBalance.setText(NumberUtil.format(getArguments().getFloat("amount", 0)));

        // 列表
        mRecyclerView.setAdapter(new PropertyBillAdapter());
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        // 请求
        mPresenter = new PropertyBillPresenter();
        mPresenter.setModelAndView(new PaginationView<>(mRecyclerView, mSmartRefreshLayout, this));
        getLifecycle().addObserver(mPresenter);
    }
    @OnClick(value = {R.id.layout_property_bill, R.id.tv_translate,R.id.tv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_property_bill: // 全部记录
                //startActivity(new Intent(view.getContext(), PropertyBillActivity.class));
                break;

            case R.id.tv_translate:
                startActivity(TransferActivity.newIntent(getActivity(), Float.valueOf(mTvBalance.getText().toString())));
                break;
            case R.id.tv_recharge:
                startActivity(new Intent(getActivity(), RechargeActivity.class));
                break;
        }
    }
}
