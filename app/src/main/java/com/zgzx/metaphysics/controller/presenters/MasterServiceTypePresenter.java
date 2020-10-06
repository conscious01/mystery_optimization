package com.zgzx.metaphysics.controller.presenters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.MasterServiceTypeEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;

/**
 * 师傅服务类型
 */
public class MasterServiceTypePresenter extends RequestPresenter<ISingleRequestView<List<MasterServiceTypeEntity>>> {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void init() {
        DataRepository.getInstance()
                .serviceType()
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, this::render));
    }


    private void render(BasicResponseEntity<List<MasterServiceTypeEntity>> entity) {
        List<MasterServiceTypeEntity> list = entity.getData();
        list.add(0, new MasterServiceTypeEntity(0, "全部"));
        mView.result(list);
    }

}
