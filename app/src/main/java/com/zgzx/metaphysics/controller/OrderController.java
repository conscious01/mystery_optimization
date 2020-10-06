package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.core.SimplePaginationPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.model.entity.QDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import io.reactivex.Observable;

public interface OrderController {

    class Presenter extends RequestPresenter<OrderController.View> {
        public void getOrderDetail(int issue_id) {

            if (LocalConfigStore.getInstance().ifMaster()) {
                DataRepository.getInstance()
                        .getAnswerDetailMast(issue_id)
                        .compose(SchedulersTransformer.transformer())
                        .subscribe(new ResponseObserver<>(this, mView,
                                entity -> mView.onQ_DetailMaster(entity.getData())));
            } else {
                DataRepository.getInstance()
                        .getAnswerDetailUser(issue_id)
                        .compose(SchedulersTransformer.transformer())
                        .subscribe(new ResponseObserver<>(this, mView,
                                entity -> mView.onQ_DetailUser(entity.getData())));
            }
        }


        public void doneAnswer(int issue_id, String content,String ak,
                               long timestamp,
                               String sign) {
            DataRepository.getInstance()
                    .doneAnswer(issue_id, content, ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onDoneAnswer()));
        }


    }



    class PresenterRefused extends RequestPresenter<OrderController.RefuseView> {

        public void doneAnswer(int issue_id, String reason,String ak,
                               long timestamp,
                               String sign) {
            DataRepository.getInstance()
                    .orderRefused(issue_id, reason,ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onRefused()));
        }

    }

    class PresenterComment extends RequestPresenter<OrderController.CommentView> {

        public void doComment(int issue_id, int score) {
            DataRepository.getInstance()
                    .orderComment(issue_id, score)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onCommment()));
        }

        public void getOrderDetail(int issue_id) {

            if (LocalConfigStore.getInstance().ifMaster()) {
                DataRepository.getInstance()
                        .getAnswerDetailMast(issue_id)
                        .compose(SchedulersTransformer.transformer())
                        .subscribe(new ResponseObserver<>(this, mView,
                                entity -> mView.onQ_DetailMaster(entity.getData())));
            } else {
                DataRepository.getInstance()
                        .getAnswerDetailUser(issue_id)
                        .compose(SchedulersTransformer.transformer())
                        .subscribe(new ResponseObserver<>(this, mView,
                                entity -> mView.onQ_DetailUser(entity.getData())));
            }
        }

    }


    class PresenterCancel extends RequestPresenter<OrderController.OrderCancel> {

        public void cancel(int issue_id,String ak,
                           long timestamp,
                           String sign) {
            DataRepository.getInstance()
                    .orderCancel(issue_id,ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onCanceled()));
        }

    }


    class Presenter1 extends SimplePaginationPresenter<OrderResultEntity> {
        private int mStatus;
        public void setStatus(int status) {
            mStatus = status;
        }

        @Override
        protected Observable<BasicListResponseEntity<OrderResultEntity>> execute() {

            if (LocalConfigStore.getInstance().ifMaster()) {
                return DataRepository.getInstance().getOrderMaster(mStatus, mPage, 10);
            } else {
                return DataRepository.getInstance().getOrderUser(mStatus, mPage, 10);
            }
        }
    }


    interface View extends IStatusView {

        void onQ_DetailMaster(QDetailEntity data);

        void onQ_DetailUser(QDetailEntity data);

        void onDoneAnswer();
    }

    interface RefuseView extends IStatusView {
        void onRefused();
    }

    interface CommentView extends IStatusView {
        void onCommment();
        void onQ_DetailMaster(QDetailEntity data);
        void onQ_DetailUser(QDetailEntity data);

    }

    interface OrderCancel extends IStatusView {
        void onCanceled();
    }
}
