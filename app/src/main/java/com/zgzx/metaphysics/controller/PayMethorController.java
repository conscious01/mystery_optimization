package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.OrderLifeBookEntity;
import com.zgzx.metaphysics.model.entity.OrderMemberEntity;
import com.zgzx.metaphysics.model.entity.PreOrderEntity;
import com.zgzx.metaphysics.model.entity.PrePayResult;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;

public interface PayMethorController {
    class Presenter extends RequestPresenter<View> {

        public void getOrderLifeBook(int lifebook_id,String cate_ids,String ak,
                                     long timestamp,
                                     String sign) {
            DataRepository.getInstance()
                    .orderLifeBookEntity(lifebook_id, cate_ids,ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.renderAssets(entity.getData());
                    }));
        }

        public void getOrderLifeBook(int member_id,String ak,
                                     long timestamp,
                                     String sign) {
            DataRepository.getInstance()
                    .orderMember(member_id,ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.renderMember(entity.getData());
                    }));
        }

        public void thirdPay(int pay_type, String no, int discount_id,String ak,
                             long timestamp,
                             String sign) {
            DataRepository.getInstance()
                    .buyFateBookPrepay(pay_type, no, discount_id,ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.buyOk(entity.getData());
                    }));
        }

        public void preMemberPay(int pay_type, String no, int discount_id,String ak,
                                 long timestamp,
                                 String sign) {
            DataRepository.getInstance()
                    .buyMemberPrepay(pay_type, no, discount_id,ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.buyOk(entity.getData());
                    }));
        }
    }

    interface View extends IStatusView {
        // 购买成功
        void buyOk(PrePayResult preOrderEntity);

        void renderAssets(OrderLifeBookEntity orderLifeBookEntity);

        void renderMember(OrderMemberEntity orderMemberEntity);
    }
}
