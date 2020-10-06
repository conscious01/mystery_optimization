package com.zgzx.metaphysics.controller;

import androidx.annotation.StringRes;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 支付逻辑
 */
public interface PayController {

    class Presenter extends RequestPresenter<View> {

        private float mBalance;

        public void pay(int message, float amount) {
            DataRepository.getInstance()

                    // 余额
                    .balance()

                    // 用户信息
                    .flatMap((Function<BasicResponseEntity<Float>, ObservableSource<BasicResponseEntity<UserDetailEntity>>>) entity -> {
                        mBalance = entity.getData();
                        return DataRepository.getInstance().userDetail();
                    })

                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        UserDetailEntity data = entity.getData();
                        if (data.isHas_paypass()) { // 是否创建支付密码
                            mView.renderPasswordView(message, amount, mBalance);
                        } else {
                           mView.renderSettingView();
                        }

                    }));
        }


    }


    interface View extends IStatusView {

        // 设置样式
        void renderSettingView();

        // 密码样式
        void renderPasswordView(@StringRes int message, float amount, float balance);



    }

}
