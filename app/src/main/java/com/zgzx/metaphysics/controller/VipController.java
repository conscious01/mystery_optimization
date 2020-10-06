package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.entity.VipTypeEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public interface VipController {
    class Presenter extends RequestPresenter<VipController.View> {
        private UserDetailEntity userDetailEntity;

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void init() {
            DataRepository.getInstance()
                    // 用户信息
                    .userDetail()
                    // vip列表
                    .flatMap((Function<BasicResponseEntity<UserDetailEntity>, ObservableSource<BasicResponseEntity<List<VipTypeEntity>>>>) entity -> {
                        userDetailEntity = entity.getData();
                        return DataRepository.getInstance().vipList();
                    })
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.renderUserDetail(userDetailEntity);
                        mView.renderVipType(false, entity.getData());
                    }));
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        private void onResume() {
            init();
        }
    }

    interface View extends IStatusView {
        void renderUserDetail(UserDetailEntity entity);

        void renderVipType(boolean isSelect, List<VipTypeEntity> data);

        void renderVipTypeContent(List<VipTypeEntity.DataJsonBean> directory);
    }
}
