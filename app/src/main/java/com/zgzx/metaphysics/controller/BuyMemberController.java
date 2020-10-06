package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

public interface BuyMemberController {
    class Presenter extends RequestPresenter<View> {

        public void request(int pay_type, int member_id) {
//            DataRepository.getInstance()
//                    .buyMemberPrepay(pay_type, member_id)
//                    .compose(SchedulersTransformer.transformer(mView))
//                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
//                        mView.buyOk(entity.getData());
//                    }));
        }
    }

    interface View extends IStatusView {
        void buyOk(String _ID);
    }
}
