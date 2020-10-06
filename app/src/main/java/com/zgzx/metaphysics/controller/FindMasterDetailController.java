package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.MasterCommentEntity;
import com.zgzx.metaphysics.model.entity.MasterDetailEntityNew;
import com.zgzx.metaphysics.model.entity.MasterServiceItemEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

/**
 * 发现页面，师傅详情
 */
public interface FindMasterDetailController {

    class Presenter extends RequestPresenter<View> {

        // 选择师傅服务
        public void selectedService(MasterServiceItemEntity item) {
//            mView.renderSelectedService(item);
        }


        public void getMasterDetail(int masterid,int uid,int userid) {

            DataRepository.getInstance()
                    .masterDetail(masterid,uid,userid)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        MasterDetailEntityNew data = entity.getData();
                        // 师傅信息
                        mView.renderMasterDetail(data);
                    }));
        }


        public void getMasterComment(int page, int pageSize, int uid,int masterid) {
            DataRepository.getInstance()
                    .getMasterComment(page, pageSize, uid,masterid)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        MasterCommentEntity data = entity.getData();
                        mView.onGetComment(data,page);
                    }));
        }

    }


    interface View extends IStatusView {

        void renderMasterDetail(MasterDetailEntityNew detail);

        void onGetComment(MasterCommentEntity commentEntity,int nowPage);

//        void renderServices(List<MasterServiceItemEntity> entities);
//
//        void renderSelectedService(MasterServiceItemEntity item);
//
//        void renderPhotos(List<MasterPhotoEntity> photos);
//
//        void renderSpecialty(List<MasterServiceTypeEntity> list);
    }

}
