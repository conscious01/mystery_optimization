package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 命书索引
 */
public interface FateBookIndexController {

    class Presenter extends RequestPresenter<View> {

        private int fateBookId;
        private Map<Integer, List<String>> mDirectoryMap = new HashMap<>();

        // 获取命书类型
        public void request(int fateBookId) {
            this.fateBookId = fateBookId;

            DataRepository.getInstance()
                    .fateBookTypes(fateBookId)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {

                        List<FateBookTypeEntity> data = entity.getData();

                        for (FateBookTypeEntity fateBookType : data) {
                            mDirectoryMap.put(fateBookType.getId(), fateBookType.getSub_cate_list());
                        }

                        // 命书类型
                        mView.renderFateBookType(data);

                        // 命书章节
                        if (!mDirectoryMap.isEmpty()) {
                            requestDirectory(data.get(0));
                        }

                    }));

        }


        // 获取命书章节
        public void requestDirectory(FateBookTypeEntity type) {
            if (mDirectoryMap != null && !mDirectoryMap.isEmpty()) {
//                if (type.getIs_buy() != 1) {
//                    mView.renderBuyView(); // 购买命书
//                }

                mView.renderDirectory(
                        type.getIs_buy() == 1, // 1, 已购买
                        type.getId(),
                        mDirectoryMap.get(type.getId()));
            }
        }


        // 购买命书
        public void buy(int id, String password) {
//            DataRepository.getInstance()
//                    .buyFateBook(id, password)
//                    .compose(SchedulersTransformer.transformer())
//                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
//                        request(fateBookId);
//
//                        mView.buySuccessful();
//                    }));
        }

    }


    interface View extends IStatusView {

        // 命书类型
        void renderFateBookType(List<FateBookTypeEntity> data);

        // 目录, 章节
        void renderDirectory(boolean isOwned, int chapterId, List<String> directory);

        // 显示购买
//        void renderBuyView();

        // 购买成功
        void buySuccessful();
    }


}
