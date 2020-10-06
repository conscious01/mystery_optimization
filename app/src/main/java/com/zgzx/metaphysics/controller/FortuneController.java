package com.zgzx.metaphysics.controller;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.AddfortuneDataEntity;
import com.zgzx.metaphysics.model.entity.FortuneEntity;
import com.zgzx.metaphysics.model.entity.TrendEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;
import com.zgzx.metaphysics.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 运势
 */
public interface FortuneController {

    class Presenter extends RequestPresenter<View> {


        public void init(int time) {
            DataRepository.getInstance()
                    .personalFortune(time, 2)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {

                        FortuneEntity data = entity.getData();

                        // 今日运势
                        FortuneEntity.FortuneBean fortune = data.getFortune();
                        FortuneEntity.FortuneBean.Bean whole = fortune.getWhole(); // 运气
                        FortuneEntity.FortuneBean.Bean business = fortune.getBusiness(); // 事业
                        FortuneEntity.FortuneBean.Bean finances = fortune.getFinances(); // 财运
                        FortuneEntity.FortuneBean.Bean love = fortune.getLove(); // 爱情
                        FortuneEntity.FortuneBean.Bean relation = fortune.getRelation(); // 人际
                        FortuneEntity.FortuneBean.Bean health = fortune.getHealth(); // 健康


                        // 运势
                        mView.renderFortune(Arrays.asList(whole, love, business, finances,
                                relation, health));

                        // 个人运势
                        mView.renderPersonalFortune(data.getGeneral_comment().getAverage(),
                                data.getDetail());

                        mView.renderMoudleImg(data.getDetail_icons());
                        mView.renderFortuneAll(entity.getData());
                    }));
        }

        public void get_trend_data() {
            DataRepository.getInstance()
                    .getTrendData()
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        List<TrendEntity.YaxisBean> yaxisBeans = entity.getData().getYaxis();
                        List<TrendEntity.XaxisBean> xaxisBeans = entity.getData().getXaxis();
                        List<String> yTipValue = new ArrayList<>();
                        List<String> xDateValue = new ArrayList<>();
                        List<Integer> xKeyValue = new ArrayList<>();
                        List<Float> yNumberValue = new ArrayList<>();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
                        for (TrendEntity.YaxisBean yaxisBean : yaxisBeans) {
                            yTipValue.add(yaxisBean.getTitle());
                        }

                        for (TrendEntity.XaxisBean xaxisBean : xaxisBeans) {
                            yNumberValue.add((float) xaxisBean.getValue());
                            xKeyValue.add(xaxisBean.getDay_type());
                            xDateValue.add(DateUtils.getTime(xaxisBean.getKey(),
                                    DateUtils.PATTERN_4));
                        }
                        mView.renderTrend(xDateValue, yTipValue, xKeyValue, yNumberValue);

                    }));
        }


        public void getAddfortuneData(int timestamp) {
            DataRepository.getInstance()
                    .getAddfortuneData(timestamp)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.onAddFourtune(entity.getData());
                    }));
        }

    }


    interface View extends IStatusView {


        // 运势
        void renderFortune(List<FortuneEntity.FortuneBean.Bean> data);

        void renderFortuneAll(FortuneEntity data);

        // 个人运程
        void renderPersonalFortune(int score, String fortune);

        // 功能图片
        void renderMoudleImg(List<String> imgs);

        void renderTrend(List<String> xDateValue, List<String> yTipValue, List<Integer> xKeyValue
                , List<Float> yNumberValue);

        void onAddFourtune(AddfortuneDataEntity addfortuneDataEntity);
    }

}
