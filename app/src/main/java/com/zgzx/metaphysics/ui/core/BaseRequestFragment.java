package com.zgzx.metaphysics.ui.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.LayoutRequestStatusView;

import org.greenrobot.eventbus.EventBus;

import and.fast.statelayout.OnAnewRequestNetworkListener;
import and.fast.statelayout.StateLayout;

public abstract class BaseRequestFragment
//        <P extends RequestPresenter>
        extends BaseFragment implements
        OnAnewRequestNetworkListener,
        IStatusView {

//    protected P mPresenter;
    protected StateLayout mNetworkStateLayout;
    private IStatusView mRequestStateView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mNetworkStateLayout == null) {
            mNetworkStateLayout = new StateLayout(container.getContext());
            mNetworkStateLayout.setOnAnewRequestNetworkListener(this);
            mNetworkStateLayout.addView(super.onCreateView(inflater, mNetworkStateLayout, savedInstanceState));
        }

        return mNetworkStateLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRequestStateView = createRequestView();

    }


    //
//    private void injection() {
//        try {
//            // 注入P层
//            Type genericSuperclass = getClass().getGenericSuperclass();
//            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
//            mPresenter = ((Class<P>) type).newInstance();
//            mRequestStateView = createRequestView(); // 代理对象
//            mPresenter.setModelAndView(this);
//            getLifecycle().addObserver(mPresenter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    protected IStatusView createRequestView() {
        return new LayoutRequestStatusView(this.mNetworkStateLayout);
    }

    @Override
    public void loading() {
        mRequestStateView.loading();
    }

    @Override
    public void offline() {
        mRequestStateView.offline();
    }

    @Override
    public void failure(Throwable throwable) {
        mRequestStateView.failure(throwable);
    }

    @Override
    public void complete() {
        mRequestStateView.complete();
    }

    @Override
    public void empty(CharSequence charSequence) {
        mRequestStateView.empty(charSequence);
    }

    @Override
    public void onAnewRequestNetwork() {
        //mPresenter.anewRequest();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //mPresenter.onHiddenChanged(hidden);
    }

}
