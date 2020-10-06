package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;

import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.MasterServiceItemEntity;
import com.zgzx.metaphysics.model.entity.RechargeActivitiesEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;

public interface DepositController {
    class  Presenter extends RequestPresenter<DepositController.View> {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void init() {
            // 用户资产
            DataRepository.getInstance()
                    .balance()
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity -> mView.renderBlance(entity.getData())));

            DataRepository.getInstance()
                    .rechargeActivitiesList()
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.renderServices(entity.getData());
                    }));
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        private void onResume(){
            init();
        }
    }
    interface View extends IStatusView {

        void renderBlance(float amount);
        void renderServices(List<RechargeActivitiesEntity> entities);

    }
}
