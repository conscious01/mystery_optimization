package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.controller.core.SimplePaginationPresenter;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.InviteListEntity;

import io.reactivex.Observable;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description:
 */
public class InvitationRecordPresenter extends SimplePaginationPresenter<InviteListEntity> {

    @Override
    protected Observable<BasicListResponseEntity<InviteListEntity>> execute() {
        return DataRepository.getInstance().inviteList(1, 0, mPage);
    }

}
