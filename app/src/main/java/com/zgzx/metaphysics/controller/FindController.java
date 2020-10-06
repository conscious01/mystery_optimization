package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.ArticleListEntity;
import com.zgzx.metaphysics.model.entity.BannerEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;

public interface FindController {
    class Presenter extends RequestPresenter<View> {
         public void getArticleList(int cate_id,int page,int pase_size){
             DataRepository.getInstance()
                     .articleCateList(cate_id,page,pase_size)
                     .compose(SchedulersTransformer.transformer())
                     .subscribe(new ResponseObserver<>(this, mView, entity -> mView.renderArticleList(entity.getData().getItems(),entity.getData().getTotal())));
         }
    }
    interface View extends IStatusView {

        void renderArticleList(List<ArticleListEntity.ItemsBean> data,int total);
    }
}
