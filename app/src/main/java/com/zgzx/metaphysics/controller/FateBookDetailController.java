package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.FateBookDetailEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 命书详情
 */
public interface FateBookDetailController {

    class Presenter extends RequestPresenter<View> {

        public void request(int bookId, int chapterId) {
            DataRepository.getInstance()
                    .fateBookDetail(bookId, chapterId)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {

                        FateBookDetailEntity chapter = entity.getData();

                        // 章节名称，目录列表
                        List<String> directoryList = new ArrayList<>();
                        List<FateBookDetailEntity.DataBean> directoryData = chapter.getData();
                        for (FateBookDetailEntity.DataBean directory : directoryData) {
                            directoryList.add(directory.getTitle());
                        }

                        // 目录
                        mView.renderDirectory(chapter.getCate(), directoryList);

                        // 内容
                        mView.renderContent(directoryData);

                    }));
        }
    }

    interface View extends IStatusView {

        void renderDirectory(String chapterName, List<String> directory);

        void renderContent(List<FateBookDetailEntity.DataBean> data);
    }

}






