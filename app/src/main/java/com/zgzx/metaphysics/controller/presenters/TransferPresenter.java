package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.concurrent.TimeUnit;

public class TransferPresenter extends RequestPresenter<ICallback> {

    public void transfer(long uid, float amount, String password,long timestamp, String ak, String sign){
        DataRepository.getInstance()
                .transfer(uid, amount, password,timestamp,ak,sign)
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful()));
    }

}
