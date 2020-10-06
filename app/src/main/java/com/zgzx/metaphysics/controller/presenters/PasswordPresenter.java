package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.IPasswordView;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.concurrent.TimeUnit;

/**
 * 密码
 */
public class PasswordPresenter extends RequestPresenter<IPasswordView> {

    private String countryCode, phone, verifyCode;

    // 验证验证码
    public void verify(String countryCode, String phone, String verifyCode) {
        this.phone = phone;
        this.verifyCode = verifyCode;
        this.countryCode = countryCode;

        DataRepository.getInstance()
                .verify(phone, countryCode, verifyCode)
                .delay(Constants.REQUEST_DELAY, TimeUnit.MILLISECONDS)
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(
                        this,
                        mView,
                        entity -> mView.verifySucceeded()));
    }


    // 重置密码
    public void resetLoginPassword(String newPassword) {
        DataRepository.getInstance()
                .forgetLoginPassword(newPassword, phone, countryCode, verifyCode)
                .delay(Constants.REQUEST_DELAY, TimeUnit.MILLISECONDS)
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(
                        this,
                        mView,
                        entity -> mView.successful()));
    }

    // 更改登录密码
    public void alterLoginPassword(String password){
        DataRepository.getInstance()
                .updateLoginPassword(password, phone, countryCode, verifyCode)
                .delay(Constants.REQUEST_DELAY, TimeUnit.MILLISECONDS)
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(
                        this,
                        mView,
                        entity -> mView.successful()));
    }

    // 更改资金密码
    public void alterPayPassword(String password){
        DataRepository.getInstance()
                .updatePayPassword(password, phone, countryCode, verifyCode)
                .delay(Constants.REQUEST_DELAY, TimeUnit.MILLISECONDS)
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(
                        this,
                        mView,
                        entity -> mView.successful()));
    }

}
