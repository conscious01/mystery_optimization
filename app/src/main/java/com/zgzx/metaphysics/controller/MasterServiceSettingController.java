package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.MasterServiceSettingEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

/**
 * 师傅服务设置
 */
public interface MasterServiceSettingController {

    class Presenter extends RequestPresenter<View> {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void init() {
            DataRepository.getInstance()
                    .masterServices()
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> mView.result(entity.getData())));
        }

    }


    interface View extends ISingleRequestView<MasterServiceSettingEntity> {

    }

}
