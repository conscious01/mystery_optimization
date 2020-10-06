package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.ArticleListEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface LoginController {
    class Presenter extends RequestPresenter<View> {
        public void login(String countryCode, String phone, String password) {
            DataRepository.getInstance()
                    .login(phone, countryCode, password)
                    .delay(Constants.REQUEST_DELAY, TimeUnit.MILLISECONDS)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> mView.loginOk(entity.getData())));
        }
    }

    interface View extends IStatusView {

        void loginOk(UserDetailEntity userDetailEntity);
    }

}
