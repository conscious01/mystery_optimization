package com.zgzx.metaphysics.controller.presenters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.VersionUpdateEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

/**
 * 版本更新
 */
public class VersionUpdatePresenter extends RequestPresenter<ICallbackResult<VersionUpdateEntity>> {

    //    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void checkVersion() {
        DataRepository.getInstance()
                .versionUpdate()
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful(entity.getData())));
    }

}
