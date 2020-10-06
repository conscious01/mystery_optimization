package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.io.File;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 申请师傅
 */
public class ApplyMasterPresenter extends RequestPresenter<ISingleRequestView<Void>> {

    public void apply(String content, String phone, List<File> files){
        DataRepository.getInstance()
                .upload(files)

                // 提交申请
                .flatMap((Function<BasicResponseEntity<List<String>>, ObservableSource<BasicResponseEntity<Object>>>) entity ->
                        DataRepository.getInstance()
                                .applyMaster(content, phone, entity.getData()))

                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.result()));
    }

}
