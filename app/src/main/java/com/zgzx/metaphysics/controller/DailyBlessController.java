package com.zgzx.metaphysics.controller;


import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;

import com.zgzx.metaphysics.model.entity.MonthBlessEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.ArrayList;
import java.util.List;

public interface DailyBlessController {
    class Presenter extends RequestPresenter<View> {
        private List<String> content = new ArrayList<>();

        public void init(String month) {
            DataRepository.getInstance()
                    .getMonthBless(month)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        MonthBlessEntity monthBlessEntity = entity.getData();
                        mView.renderMothsDay(monthBlessEntity.getContinue_day(), monthBlessEntity.getUser_total(), monthBlessEntity.getToday_total());
                        for (int i = 0; i < entity.getData().getMonth_data().size(); i++) {
                            content.add(entity.getData().getMonth_data().get(i).getContent());
                        }
                        mView.renderMoths(content);
                    }));

        }

        public void addMonthParams(String content){
            DataRepository.getInstance()
                    .addMonthBless(content)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.addBlessSuccess();
                    }));
        }
    }

    interface View extends IStatusView {
        void addBlessSuccess();

        void renderMoths(List<String> content);

        void renderMothsDay(int continueDays, int userDays, int todayDays);
    }
}
