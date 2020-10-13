package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

public interface SplashController {
    class Presenter extends RequestPresenter<View> {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void init() {
            DataRepository.getInstance().getConfigURL().compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onGetConfigBase(entity.getData())));

        }
    }

    interface View extends IStatusView {
        void onGetConfigBase(UrlConfigEntity urlConfigEntity);
    }
}
