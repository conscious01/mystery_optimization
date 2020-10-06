package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.IPaginationPresenter;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IPaginationView;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.InviteListEntity;
import com.zgzx.metaphysics.network.rx.PaginationResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

@Deprecated
public interface MyInviteController {

//    class Presenter extends RequestPresenter<View> implements IPaginationPresenter<View, InviteListEntity.ItemsBean> {
//
//        private int mPage;
//        private IPaginationView<InviteListEntity.ItemsBean> mPaginationView;
//
//        @Override
//        public void setPaginationView(IPaginationView<InviteListEntity.ItemsBean> view) {
//            this.mPaginationView = view;
//        }
//
//        @Override
//        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//        public void requestRefresh() {
//            mPage = 1;
//            execute();
//        }
//
//        @Override
//        public void requestLoadMore() {
//            ++mPage;
//            execute();
//        }
//
//        private void execute() {
//            DataRepository.getInstance()
//                    .inviteList(mPage)
//                    .compose(SchedulersTransformer.transformer(mView))
//                    .map(entity -> {
//                        InviteListEntity data = entity.getData();
//
//                        if (mPage == 1){
//                            mView.renderInviteInfo(data.getInviter_id(), data.getToday_count(), data.getTotal_count());
//                        }
//
//                        return new BasicListResponseEntity<>(
//                                data.getPage_data(),
//                                entity.getCode(),
//                                entity.getMsg()
//                        );
//                    })
//                    .subscribe(new PaginationResponseObserver<>(this, mPaginationView, mPage));
//        }
//
//    }
//
//
//    interface View extends IStatusView {
//
//        void renderInviteInfo(int inviterId, int todayCount, int totalCount);
//
//    }

}
