package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.SwitchStatusEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

public interface SettingController {


    class SwitchPresenter extends RequestPresenter<SettingController.View> {

        public void getSwitchStatus() {
            DataRepository.getInstance()
                    .getSwitchStatus()
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.onGetSwtichStatus(entity.getData());
                    }));
        }


        public void updateSwitchFortune(int fortune_switch) {
            DataRepository.getInstance()
                    .updateSwitchFortune(fortune_switch)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.onSwtichFortuneUpdate();
                    }));
        }


        public void updateSwitchMsg(int msg_switch) {
            DataRepository.getInstance()
                    .updateSwitchMsg(msg_switch)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.onSwtichMsgUpdate();
                    }));
        }
    }


    interface View extends IStatusView {

        void onGetSwtichStatus(SwitchStatusEntity data);

        void onSwtichFortuneUpdate();

        void onSwtichMsgUpdate();

    }
}
