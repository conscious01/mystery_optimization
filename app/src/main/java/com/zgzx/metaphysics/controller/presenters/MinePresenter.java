package com.zgzx.metaphysics.controller.presenters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

/**
 * 我的页面
 */
public class MinePresenter extends RequestPresenter<ICallbackResult<UserDetailEntity>> {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void init(){
        DataRepository.getInstance()
                .userDetail()
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful(entity.getData())));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume(){
        init();
    }

}
