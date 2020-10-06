package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.concurrent.TimeUnit;

/**
 * 注册
 */
public class RegisterPresenter extends RequestPresenter<ICallback> {

    public void register(
            String verifyCode,
            String countryCode,
            String phone,
            String password,
            String inviteCode) {

        DataRepository.getInstance()
                .register(countryCode, phone, verifyCode, password, inviteCode)
                .delay(Constants.REQUEST_DELAY, TimeUnit.MILLISECONDS)
                .compose(SchedulersTransformer.transformer(this.mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> {mView.successful();
                        LocalConfigStore.getInstance().saveUserToken(entity.getData().getToken());
                    LocalConfigStore.getInstance().saveUserLoginInfo(phone,
                            countryCode,password);
                }));
    }


}
