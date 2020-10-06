package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.CatePriceEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

public class FateBookCatePresenter extends RequestPresenter<ICallbackResult<CatePriceEntity>> {

    public void request(int fateBookId) {
        DataRepository.getInstance()
                .getCatePrice(fateBookId)
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful(entity.getData())));
    }

}
