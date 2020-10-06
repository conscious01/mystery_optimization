package com.zgzx.metaphysics.ui.core;

import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;


import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.LayoutRequestStatusView;

import and.fast.statelayout.OnAnewRequestNetworkListener;
import and.fast.statelayout.StateLayout;

public abstract class BaseRequestActivity extends BaseActivity
        implements OnAnewRequestNetworkListener,
        IStatusView {

    protected IStatusView mStateView;
    protected StateLayout mStateLayout;

    @Override
    public void setContentView(int layoutResID) {
        // 创建状态布局, 添加标题
        int titleLayoutRes = getTitleLayoutRes();

        if (titleLayoutRes != 0) {
            LinearLayout rootView = new LinearLayout(this);
            rootView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            rootView.setOrientation(LinearLayout.VERTICAL);

            // 添加标题
            rootView.addView(LayoutInflater.from(this).inflate(titleLayoutRes, rootView, false));

            // 添加状态布局
            mStateLayout = new StateLayout(this);
            mStateLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            rootView.addView(mStateLayout);

            // 添加内容布局
            mStateLayout.addView(LayoutInflater.from(this).inflate(layoutResID, mStateLayout, false));
            super.setContentView(rootView);

            // 重新请求网络监听
            mStateLayout.setOnAnewRequestNetworkListener(this);

        } else {
            super.setContentView(layoutResID);
        }

        mStateView = createStatusView();
    }

    @Override
    public void loading() {
        mStateView.loading();
    }

    @Override
    public void offline() {
        mStateView.offline();
    }

    @Override
    public void failure(Throwable throwable) {
        mStateView.failure(throwable);
    }

    @Override
    public void complete() {
        mStateView.complete();
    }

    @Override
    public void empty(CharSequence charSequence) {
        mStateView.empty(charSequence);
    }

    /**
     * 状态布局
     */
    protected IStatusView createStatusView() {
        return new LayoutRequestStatusView(this.mStateLayout);
    }

    @Override
    public void onAnewRequestNetwork() {
        //mPresenter.anewRequest();
    }

    /**
     * 标题栏
     */
    protected @LayoutRes int getTitleLayoutRes() {
        return 0;
    }

}
