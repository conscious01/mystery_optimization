package com.zgzx.metaphysics.network.rx;

import com.mondo.logger.Logger;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.exceptions.IncompleteInformationException;
import com.zgzx.metaphysics.network.exceptions.RegisterException;
import com.zgzx.metaphysics.network.exceptions.UnauthorizedException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import androidx.core.util.Consumer;

import java.net.ConnectException;

public class ResponseObserver<T extends BasicResponseEntity> implements Observer<T> {

    private final IStatusView mView;

    private Consumer<T> mConsumer;

    private RequestPresenter mPresenter;


    public ResponseObserver(RequestPresenter presenter, IStatusView view, Consumer<T> consumer) {
        this.mView = view;
        this.mConsumer = consumer;
        this.mPresenter = presenter;
    }


    @Override
    public void onSubscribe(Disposable d) {
        this.mPresenter.add(d);
    }


    @Override
    public void onNext(T t) {

        if (t.getCode() != 0) { // 失败
            Exception exception;

            if (t.getCode() == 401) { // 登录过期
                exception = new UnauthorizedException(t.getMsg());

            } else if (t.getCode() == 1003) { // 1003 注册失败，用户已存在
                exception = new RegisterException(t.getMsg());
            }else if (t.getCode() == 1002) { // 1002 用户注册未完善用户信息

                UserDetailEntity user = (UserDetailEntity) t.getData();
                String token = null;

                if (user != null){
                    token = user.getToken();
                }

                exception = new IncompleteInformationException(token);

            }
            else {
                exception = new Exception(t.getMsg());
            }

            onError(exception);

        } else if (mConsumer != null) { // 成功

            try{

                mConsumer.accept(t);

                if (mView != null){
                    mView.complete();
                }

            }catch (Exception e){
                onError(e);
            }

        }

    }


    @Override
    public void onError(Throwable e) {
        if (mView != null) {
            if (e instanceof ConnectException) {
                mView.offline();

            } else {
                mView.failure(e);
            }
        }

        //Logger.e(e, e.getMessage());
    }


    @Override
    public void onComplete() {
        Logger.d("onComplete");
//
//        if (mView != null) {
//            //mView.complete();
//        }
    }

}
