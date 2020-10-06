package com.zgzx.metaphysics.controller.core;

import com.zgzx.metaphysics.controller.views.core.IPaginationView;
import com.zgzx.metaphysics.controller.views.core.IView;

/**
 * 分页数据
 */
public interface IPaginationPresenter<V extends IView, T> extends IPresenter<V> {

    void setPaginationView(IPaginationView<T> view);

    void requestRefresh();

    void requestLoadMore();

}
