package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.AdEntity;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.model.entity.UserAssetEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public interface UserController {

    class Presenter extends RequestPresenter<View> {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void init() {
            // 用户信息
            DataRepository.getInstance()
                    .userDetail()
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.renderUserDetail(entity.getData())));

            // 用户资产
            DataRepository.getInstance()
                    .balance()
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.renderAssets(entity.getData())));


            DataRepository.getInstance()
                    .getUserAssets()
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onUserAssets(entity.getData())));


//            DataRepository.getInstance().getH5URL().compose(SchedulersTransformer.transformer())
//                    .subscribe(new ResponseObserver<>(this, mView,
//                            entity -> mView.onGetH5Base(entity.getData())));

            DataRepository.getInstance().getConfigURL().compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onGetConfigBase(entity.getData())));

            DataRepository.getInstance().getAdConfig().compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onGetAdConfig(entity.getData())));

            DataRepository.getInstance().getAdCount().compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onRenderCount(entity.getData().getCount())));
        }


        public void getRongToken() {
            DataRepository.getInstance().getRongToken().compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView,
                            entity -> mView.onGetRongToken(entity.getData())));
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        private void onResume() {
            init();
        }
    }


    interface View extends IStatusView {

        void renderUserDetail(UserDetailEntity entity);

        void renderAssets(float amount);

        void onUserAssets(UserAssetEntity userAssetEntity);

        void onGetH5Base(H5BaseEntity h5BaseEntity);

        void onGetConfigBase(UrlConfigEntity urlConfigEntity);

        void onGetAdConfig(AdEntity urlConfigEntity);

        void onGetRongToken(String rongToken);
        void onRenderCount(int count);
    }

}
