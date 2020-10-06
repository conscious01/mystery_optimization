package com.zgzx.metaphysics.controller.presenters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.AreaCodeEntity;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 区号数据
 */
public class CountryCodePresenter extends RequestPresenter<ISingleRequestView<List<AreaCodeEntity>>> {

    private List<AreaCodeEntity> mData;

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    private void init() {
        DataRepository.getInstance()
                .phoneCode()
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, this::render));
    }

    private void render(BasicResponseEntity<List<AreaCodeEntity>> entity) {
        mData = entity.getData();
        mView.result(mData);
    }

    // 搜索
    public void search(CharSequence keyword) {
        if (mData != null && keyword != null) {
            List<AreaCodeEntity> results = new ArrayList<>();

            for (AreaCodeEntity entity : mData) {
                String content = entity.getCode() + entity.getName();
                if (content.contains(keyword)) {
                    results.add(entity);
                }
            }

            mView.result(results);

        } else if (keyword == null) {
            mView.result(mData);
        }
    }

}
