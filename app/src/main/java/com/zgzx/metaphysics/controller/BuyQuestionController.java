package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.PrePayResult;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

public interface BuyQuestionController {
    class Presenter extends RequestPresenter<View> {

        public void request(int pay_type, int member_id, String ak,
                            long timestamp,
                            String sign) {
            DataRepository.getInstance()
                    .buyQuestion(pay_type, member_id,ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.buyOk(entity.getData());
                    }));
        }
    }

    interface View extends IStatusView {
        void buyOk(PrePayResult prePayResult);
    }
}
