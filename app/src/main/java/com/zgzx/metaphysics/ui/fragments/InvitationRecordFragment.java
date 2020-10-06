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
import com.zgzx.metaphysics.controller.presenters.InvitationRecordPresenter;
import com.zgzx.metaphysics.controller.presenters.RechargeRecordPresenter;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.ui.adapters.MyInviteAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;

import butterknife.BindView;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description: 邀请记录
 */
public class InvitationRecordFragment extends BaseRequestFragment implements OnLoadMoreListener {

    private static final String EXT_ROLE = "ROLE";

    public static Fragment instance(int role){
        InvitationRecordFragment fragment = new InvitationRecordFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(EXT_ROLE, role);
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.text_view1) TextView mTextView1;
    @BindView(R.id.text_view2) TextView mTextView2;
    @BindView(R.id.text_view3) TextView mTextView3;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout) SmartRefreshLayout mSmartRefreshLayout;

    private InvitationRecordPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invitation_record;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        int role = getArguments().getInt(EXT_ROLE);

        mRecyclerView.setAdapter(new MyInviteAdapter(role));
        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setOnLoadMoreListener(this);

        mPresenter = new InvitationRecordPresenter();
        mPresenter.setModelAndView(new PaginationView<>(mRecyclerView, mSmartRefreshLayout, this));
        getLifecycle().addObserver(mPresenter);

        // 角色
        if (role == WebApiConstants.SUPER_SHAREHOLDER_ROLE){
            mTextView1.setText(R.string.super_partner_id);
            mTextView2.setText(getResources().getString(R.string.invite_today)+"("+getResources().getString(R.string.people)+")");
            mTextView3.setText(getResources().getString(R.string.cumulative_invitation)+"("+getResources().getString(R.string.people)+")");

        } else {
            mTextView1.setText(R.string.user_id);
            mTextView2.setVisibility(View.GONE);
            mTextView3.setText(R.string.registration_time);
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
            if (mRecyclerView!=null&&mSmartRefreshLayout!=null){
                mPresenter = new InvitationRecordPresenter();
                mPresenter.setModelAndView(new PaginationView<>(mRecyclerView, mSmartRefreshLayout, this));
                getLifecycle().addObserver(mPresenter);
            }

        }

    }
}
