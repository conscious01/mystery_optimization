package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.controller.core.SimplePaginationPresenter;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.SystemMessageEntity;
import com.zgzx.metaphysics.ui.activities.SystemMessageActivity;

import io.reactivex.Observable;

/**
 * 系统消息
 */
public class SystemMessagePresenter extends SimplePaginationPresenter<SystemMessageEntity> {

    private int mType;

    public SystemMessagePresenter(int type) {
        this.mType = type;
    }

    @Override
    protected Observable<BasicListResponseEntity<SystemMessageEntity>> execute() {

        if (mType == SystemMessageActivity.SYSTEM_NOTICE_TYPE){
            return DataRepository.getInstance().noticeList(mPage);

        } else if (mType == SystemMessageActivity.SYSTEM_MSG_TYPE){
            return DataRepository.getInstance().systemMessage(mPage);
        }

        return null;
    }

}
