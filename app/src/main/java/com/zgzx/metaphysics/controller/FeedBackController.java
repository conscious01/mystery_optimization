package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.io.File;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public interface FeedBackController {
    class FeedBackPresenter extends RequestPresenter<FeedBackController.View> {

        public void applyWithImage(String content, List<File> files) {

            mView.loading();
            DataRepository.getInstance()
                    .upload(files)
                    // 提交申请
                    .flatMap((Function<BasicResponseEntity<List<String>>,
                                    ObservableSource<BasicResponseEntity<String>>>) entity -> {

                              return   DataRepository.getInstance()
                                        .sendFeedBack(content, setArray(entity.getData()));
                            }
                    )
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.onApplyResult();
                        mView.complete();

                    }));
        }


        public void apply(String content) {

            DataRepository.getInstance()
                    .sendFeedBack(content, "")
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.onApplyResult();
                    }));
        }

        private String setArray(List<String> list) {
            StringBuilder contentArray = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {

                if (i == list.size() - 1) {
                    contentArray.append(list.get(i));
                } else {
                    contentArray.append(list.get(i)).append(",");
                }
            }
            return contentArray.toString();
        }
    }


    interface View extends IStatusView {

        void onApplyResult();


    }
}

