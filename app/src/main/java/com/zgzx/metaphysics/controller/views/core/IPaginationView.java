package com.zgzx.metaphysics.controller.views.core;

import java.util.List;

/**
 * 分页 view
 */
public interface IPaginationView<T> extends IStatusView {

    void renderRefresh(List<T> refreshData);

    void renderLoadMore(List<T> loadMoreData);

    void renderNoMore();

}
