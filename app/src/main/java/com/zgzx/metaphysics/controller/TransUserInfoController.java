package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.model.entity.UserAssetEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

public interface TransUserInfoController {
    class Presenter extends RequestPresenter<View> {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void init() {
            // 用户信息
            DataRepository.getInstance()
                    .userDetail()
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.renderUserDetail(entity.getData())));


        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        private void onResume() {
            init();
        }
    }


    interface View extends IStatusView {

        void renderUserDetail(UserDetailEntity entity);


    }
}
