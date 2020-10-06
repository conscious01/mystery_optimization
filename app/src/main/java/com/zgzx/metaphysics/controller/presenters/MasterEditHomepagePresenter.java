package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.entity.MasterDetailEntityNew;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 师傅编辑
 */
public class MasterEditHomepagePresenter extends RequestPresenter<ICallbackResult<MasterDetailEntityNew>> {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void init(){

        String userid = LocalConfigStore.getInstance().getUserId();
        int useridInt = 0;
        if (!userid.isEmpty()) {
            useridInt = Integer.valueOf(userid);
        }

//        DataRepository.getInstance()
//                .masterDetail(useridInt,useridInt)
//                .compose(SchedulersTransformer.transformer(mView))
//                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful(entity.getData())));
    }

}
