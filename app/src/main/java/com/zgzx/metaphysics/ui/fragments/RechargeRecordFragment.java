package com.zgzx.metaphysics.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.RechargeRecordPresenter;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.ui.adapters.RechargeRecordAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;

import butterknife.BindView;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description: 充值记录
 */
public class RechargeRecordFragment extends BaseRequestFragment implements OnLoadMoreListener {

    private static final String EXT_ROLE = "ROLE";

    public static Fragment instance(int role) {
        RechargeRecordFragment fragment = new RechargeRecordFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(EXT_ROLE, role);
        fragment.setArguments(arguments);
        return fragment;
    }


    @BindView(R.id.text_view1)
    TextView mTextView1;
    @BindView(R.id.text_view2)
    TextView mTextView2;
    @BindView(R.id.text_view3)
    TextView mTextView3;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    private RechargeRecordPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge_record;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 参数
        int role = getArguments().getInt(EXT_ROLE);

        // 列表
        mRecyclerView.setAdapter(new RechargeRecordAdapter(role));
        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setOnLoadMoreListener(this);

        // 请求
        mPresenter = new RechargeRecordPresenter();
        mPresenter.setModelAndView(new PaginationView<>(mRecyclerView, mSmartRefreshLayout, this));
        getLifecycle().addObserver(mPresenter);

        // 角色
        if (role == WebApiConstants.SUPER_SHAREHOLDER_ROLE) {
            mTextView1.setText(R.string.super_partner_id);
            mTextView2.setText(R.string.today_deposit);
            mTextView3.setText(R.string.cumulative_deposit);

        } else {
            mTextView1.setText(R.string.user_id);
            mTextView2.setText(R.string.recharge_amount);
            mTextView3.setText(R.string.recharge_date);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestLoadMore();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mRecyclerView != null && mSmartRefreshLayout != null) {
                mPresenter = new RechargeRecordPresenter();
                mPresenter.setModelAndView(new PaginationView<>(mRecyclerView, mSmartRefreshLayout, this));
                getLifecycle().addObserver(mPresenter);
            }
        }
    }
}
