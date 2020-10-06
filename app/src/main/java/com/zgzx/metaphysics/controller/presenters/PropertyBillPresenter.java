package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.core.SimplePaginationPresenter;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.PropertyBillEntity;

import io.reactivex.Observable;

public class PropertyBillPresenter extends SimplePaginationPresenter<PropertyBillEntity> {

    @Override
    protected Observable<BasicListResponseEntity<PropertyBillEntity>> execute() {
        return DataRepository.getInstance().propertyBill(mPage, 0);
    }

}
