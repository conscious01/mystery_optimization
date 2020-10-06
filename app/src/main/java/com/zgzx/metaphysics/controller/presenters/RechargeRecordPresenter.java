package com.zgzx.metaphysics.controller.presenters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.SimplePaginationPresenter;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.RechargeRecordEntity;

import io.reactivex.Observable;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description:
 */
public class RechargeRecordPresenter extends SimplePaginationPresenter<RechargeRecordEntity> {

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected Observable<BasicListResponseEntity<RechargeRecordEntity>> execute() {
        return DataRepository.getInstance().rechargeRecord(1,0, mPage);
    }

}
