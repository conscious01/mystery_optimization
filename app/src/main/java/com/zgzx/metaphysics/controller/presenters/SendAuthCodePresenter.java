package com.zgzx.metaphysics.controller.presenters;

import android.widget.TextView;

import androidx.lifecycle.Lifecycle;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.controller.views.impl.CountDownTimerView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.concurrent.TimeUnit;

/**
 * 发送验证码
 */
public class SendAuthCodePresenter extends RequestPresenter<ISingleRequestView<Object>> {

    public static final int REGISTER = 1, // 注册
            FORGET_LOGIN_PASSWORD = 2,    // 忘记密码
            ALTER_LOGIN_PASSWORD = 3,     // 修改登录密码
            ALTER_PAY_PASSWORD = 4;       // 修改支付密码


    public static SendAuthCodePresenter newAndBind(TextView textView, Lifecycle lifecycle) {
        SendAuthCodePresenter presenter = new SendAuthCodePresenter();
        presenter.setModelAndView(new CountDownTimerView(textView, lifecycle));
        lifecycle.addObserver(presenter);
        return presenter;
    }

    // 发送验证码
    public void send(String countryCode, String phone, int scene) {
        DataRepository.getInstance()
                .send(countryCode, phone, scene)
                .delay(Constants.REQUEST_DELAY, TimeUnit.MILLISECONDS)
                .compose(SchedulersTransformer.transformer(this.mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.result()));
    }


    public void send(String phone, int type) {
        send(null, phone, type);
    }

}
