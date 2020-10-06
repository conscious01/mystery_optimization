package com.zgzx.metaphysics.controller.presenters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.CalendarDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.Calendar;

/**
 * 万年历
 */
public class CalendarDetailPresenter extends RequestPresenter<ICallbackResult<CalendarDetailEntity>> {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void init(){
        request(Calendar.getInstance().getTimeInMillis() );
    }


    public void request(long timestamp){
        DataRepository.getInstance()
                .calendarDetail((int)(timestamp / 1000))
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful(entity.getData())));
    }

}
