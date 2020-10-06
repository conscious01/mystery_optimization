package com.zgzx.metaphysics.network.rx;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IPaginationView;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;

public class PaginationResponseObserver<T> extends ResponseObserver<BasicListResponseEntity<T>> {

    private int mPage;

    private IPaginationView mView;

    public PaginationResponseObserver(RequestPresenter presenter, IPaginationView view, int page) {
        super(presenter, view, null);
        this.mPage = page;
        this.mView = view;
    }


    @Override
    public void onNext(BasicListResponseEntity<T> entity) {
        super.onNext(entity);

        BasicListResponseEntity.PageDataEntity<T> pageDataEntity = entity.getData();

        if (mPage <= 1) {
            mView.renderRefresh(pageDataEntity.getList());

        } else {
            mView.renderLoadMore(pageDataEntity.getList());
        }

        if (!pageDataEntity.hasMore()) {
            mView.renderNoMore();
        }

        mView.complete();
    }

}
