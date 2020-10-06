package com.zgzx.metaphysics.controller.views.impl;

import com.blankj.utilcode.util.LogUtils;
import com.mondo.logger.Logger;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.network.exceptions.UnauthorizedException;
import com.zgzx.metaphysics.ui.dialogs.UnauthorizedDialog;

import and.fast.statelayout.StateLayout;

public class LayoutRequestStatusView implements IStatusView {

    private StateLayout mStateLayout;

    public LayoutRequestStatusView(StateLayout layout) {
        this.mStateLayout = layout;
    }

    @Override
    public void loading() {
        if (mStateLayout == null) {
            Logger.i("loading, %s", "状态布局未初始化");
            return;
        }

        mStateLayout.showStateView(StateLayout.LOADING_STATE);
    }

    @Override
    public void offline() {
        if (mStateLayout == null) {
            Logger.i("offline, %s", "状态布局未初始化");
            return;
        }

        mStateLayout.showStateView(StateLayout.OFFLINE_STATE);
    }

    @Override
    public void failure(Throwable throwable) {
        if (mStateLayout == null) {
            Logger.i("failure, %s", "状态布局未初始化");
            return;
        }

        if (throwable instanceof UnauthorizedException) {
            UnauthorizedDialog.show(mStateLayout.getContext(), throwable.getMessage());

        } else {
            mStateLayout.showStateView(StateLayout.FAILURE_STATE, "");
        }

    }

    @Override
    public void complete() {
        if (mStateLayout == null) {
            LogUtils.i("complete, %s", "mStateLayout == null");
            return;
        }

        mStateLayout.showContentView();
    }

    @Override
    public void empty(CharSequence charSequence) {
        if (mStateLayout == null) {
            LogUtils.i("empty, %s", "mStateLayout == null");
            return;
        }

        mStateLayout.showStateView(StateLayout.EMPTY_STATE, charSequence);
    }

}
