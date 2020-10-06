package com.zgzx.metaphysics.controller;

import androidx.annotation.StringRes;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

public interface TransferFeeController {
    class Presenter extends RequestPresenter<View> {

        public void getTranfer(){
            DataRepository.getInstance().getTranFee().compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.renderTranFer(entity.getData());
                    }));
        }
    }
    interface View extends IStatusView {
        void renderTranFer(float data);

    }
}
