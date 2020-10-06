package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.SimplePaginationPresenter;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.MessageEntity;

import io.reactivex.Observable;

public interface MsgController {
    class Presenter extends SimplePaginationPresenter<MessageEntity.ItemsBean> {

        @Override
        protected Observable<BasicListResponseEntity<MessageEntity.ItemsBean>> execute() {
            return DataRepository.getInstance().getMessage(mPage);

        }
    }
}
