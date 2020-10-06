package com.zgzx.metaphysics.controller.presenters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.FateBooksEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;

/**
 * 命书列表
 */
public class FateBooksListPresenter extends RequestPresenter<ICallbackResult<List<FateBooksEntity>>> {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init(){
        DataRepository.getInstance()
                .fateBooksList()
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful(entity.getData())));
    }

}
