package com.zgzx.metaphysics.controller.presenters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.InvitationSummaryEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description: 邀请
 */
public class InvitationSummaryPresenter extends RequestPresenter<ICallbackResult<InvitationSummaryEntity>> {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void init(){
        DataRepository.getInstance()
                .invitationSummary()
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful(entity.getData())));
    }

}
