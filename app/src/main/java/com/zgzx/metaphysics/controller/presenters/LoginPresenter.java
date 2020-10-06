package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;

import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.concurrent.TimeUnit;

public class LoginPresenter extends RequestPresenter<ISingleRequestView<UserDetailEntity>> {

    public void login(String countryCode, String phone, String password) {
        DataRepository.getInstance()
                .login(phone, countryCode, password)
                .delay(Constants.REQUEST_DELAY, TimeUnit.MILLISECONDS)
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.result(entity.getData())));
    }

}
