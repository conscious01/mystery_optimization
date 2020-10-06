package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.FateBooksEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;

public interface FateBookListController {

    class Presenter extends RequestPresenter<View>{

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void init(){
            DataRepository.getInstance()
                    .fateBooksList()
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> mView.renderFateBooks(entity.getData())));
        }


        public void delete(int id, int position){
            DataRepository.getInstance()
                    .deleteFateBook(id)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity -> mView.deleteSuccessful(position)));
        }

    }



    interface View extends IStatusView{

        void renderFateBooks(List<FateBooksEntity> list);

        void deleteSuccessful(int position);
    }

}
