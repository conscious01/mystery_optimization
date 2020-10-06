package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.controller.core.SimplePaginationPresenter;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.MasterServiceEntity;

import io.reactivex.Observable;

public class MasterServiceListPresenter extends SimplePaginationPresenter<MasterServiceEntity> {

    private int mTypeId, mSortType, mPriceSort;

//    // 设置过渡类型
//    public void setParams(int typeId) {
//        this.mTypeId = typeId;
//        requestRefresh();
//    }
//
//    // 设置排序类型
//    public void setSortParams(int sortType) {
//        this.mSortType = sortType;
//        this.mPriceSort = 0;
//        requestRefresh();
//    }
//
//    // 设置价格排序类型
//    public void setSortPrice(boolean isDown) {
//        this.mSortType = WebApiConstants.MASTER_SORT_PRICE;
//        this.mPriceSort = isDown ? WebApiConstants.SORT_REVERSE_ORDER : WebApiConstants.SORT_POSITIVE_ORDER;
//        requestRefresh();
//    }

    @Override
    protected Observable<BasicListResponseEntity<MasterServiceEntity>> execute() {
//        return DataRepository.getInstance().serviceList(mPage, mTypeId, mSortType, mPriceSort);
        return DataRepository.getInstance().serviceList(mPage);

    }

}
