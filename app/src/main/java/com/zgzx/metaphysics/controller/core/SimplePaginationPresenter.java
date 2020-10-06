package com.zgzx.metaphysics.controller.core;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.views.core.IPaginationView;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.network.rx.PaginationResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import io.reactivex.Observable;

/**
 * 分页数据 帮助对象
 */
public abstract class SimplePaginationPresenter<T> extends
        RequestPresenter<IPaginationView<T>> implements
        IPaginationPresenter<IPaginationView<T>, T> {

    protected int mPage;

    @Override
    public void setModelAndView(IPaginationView<T> view) {
        super.setModelAndView(view);
    }

    @Override
    public void setPaginationView(IPaginationView<T> view) {
        // 占位
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void requestRefresh() {
        mPage = 1;

        execute().compose(SchedulersTransformer.transformer(mView))
                .subscribe(new PaginationResponseObserver<>(this, mView, mPage));
    }

    @Override
    public void requestLoadMore() {
        ++mPage;

        execute().compose(SchedulersTransformer.transformer(mView))
                .subscribe(new PaginationResponseObserver<>(this, mView, mPage));
    }

    @Override
    public void anewRequest() {
        requestRefresh();
    }

    protected abstract Observable<BasicListResponseEntity<T>> execute();

}
