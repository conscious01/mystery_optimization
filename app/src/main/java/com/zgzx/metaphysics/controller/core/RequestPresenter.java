package com.zgzx.metaphysics.controller.core;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.views.core.IStatusView;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 请求
 */
public abstract class RequestPresenter<V extends IStatusView> implements LifecycleObserver, IPresenter<V> {

    protected V mView;

    private CompositeDisposable mCompositeDisposable;

    public RequestPresenter() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setModelAndView(V view) {
        this.mView = view;
    }

    // fragment
    public void onHiddenChanged(boolean hidden){

    }

    public void add(@NonNull Disposable d){

        mCompositeDisposable.add(d);
    }

    /**
     * 重新进行请求
     */
    public void anewRequest(){

    }

    /**
     * 清除所有订阅
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clear() {

        mCompositeDisposable.clear();
    }

}
