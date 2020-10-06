package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.ArticleEntity;
import com.zgzx.metaphysics.model.entity.ArticleListEntity;
import com.zgzx.metaphysics.model.entity.BannerEntity;
import com.zgzx.metaphysics.model.entity.CalendarDayEntity;
import com.zgzx.metaphysics.model.entity.CalendarHourEntity;
import com.zgzx.metaphysics.model.entity.DailyQuestionEntity;
import com.zgzx.metaphysics.model.entity.FortuneEntity;
import com.zgzx.metaphysics.model.entity.HomeFouncationEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/23
 * @Description: 首页
 */
public interface HomeController {

    class Presenter extends RequestPresenter<View> {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void init() {
            // banner
            DataRepository.getInstance()
                    .banner(1)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity -> mView.renderBanner(entity.getData())));

            //功能模块
            DataRepository.getInstance()
                    .entry()
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity ->
                    {
                        mView.renderNomalFouncation(entity.getData().getIcon1());
                        mView.renderToolsFouncation(entity.getData().getIcon2());
                        mView.renderFouncationBanner(entity.getData().getBanner());
                    }
                    ));

            //今日宜忌
            DataRepository.getInstance()
                    .calendarDay()
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity ->
                    {
                        mView.renderCalendarDay(entity.getData());

                    }
                    ));

            //时辰宜忌
            DataRepository.getInstance()
                    .calendarHour()
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity ->
                    {
                        mView.rendercalendarHour(entity.getData());

                    }
                    ));
            //推荐文章
            DataRepository.getInstance()
                    .articleCateList(-1,1,5)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity ->
                    {
                        mView.renderNotice(entity.getData().getItems());

                    }
                    ));




        }

        public void fortune() {
            DataRepository.getInstance()
                    .personalFortune((int) (new Date().getTime() / 1000), LocalConfigStore.getInstance().isCompletedInfo() ? 2 : 1)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {

                        mView.renderFortuneTip(entity.getData());
                        // 今日运势
                        FortuneEntity data = entity.getData();
                        FortuneEntity.FortuneBean fortune = data.getFortune();
                        FortuneEntity.FortuneBean.Bean whole = fortune.getWhole(); // 运气
                        FortuneEntity.FortuneBean.Bean business = fortune.getBusiness(); // 事业
                        FortuneEntity.FortuneBean.Bean finances = fortune.getFinances(); // 财运
                        FortuneEntity.FortuneBean.Bean love = fortune.getLove(); // 爱情
                        FortuneEntity.FortuneBean.Bean relation = fortune.getRelation(); // 人际
                        FortuneEntity.FortuneBean.Bean health = fortune.getHealth(); // 健康

                        // 运势
                        mView.renderFortune(Arrays.asList(whole, love, business, finances, relation, health));
                    }));
        }

        @Override
        public void anewRequest() {
             init();
        }
        public void getDailyQuestion(int index) {
                        //每日一问
            DataRepository.getInstance()
                    .getDailyQuestion(index)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity ->
                    {
                        mView.renderAskQuestion(entity.getData());

                    }
                    ));
        }
    }


    interface View extends IStatusView {

        // 广告
        void renderBanner(List<BannerEntity> data);

        // 工能模块1
        void renderNomalFouncation(List<HomeFouncationEntity.Icon1Bean> data);

        // 工能模块2
        void renderToolsFouncation(List<HomeFouncationEntity.Icon2Bean> data);

        // 工能模块banner
        void renderFouncationBanner(List<HomeFouncationEntity.BannerBean> data);

        // 今日宜忌
        void renderCalendarDay(CalendarDayEntity data);
//        // 今日宜忌
//        void renderCalendarDayList(CalendarDayEntity.DataBean data);
        // 时辰宜忌
        void rendercalendarHour(List<CalendarHourEntity> data);

        // 运势
        void renderFortune(List<FortuneEntity.FortuneBean.Bean> data);

        void renderFortuneTip(FortuneEntity data);
        // 每日一问
        void renderAskQuestion(List<DailyQuestionEntity> weather);
        // 通知
        void renderNotice(List<ArticleListEntity.ItemsBean> data);



    }


}
