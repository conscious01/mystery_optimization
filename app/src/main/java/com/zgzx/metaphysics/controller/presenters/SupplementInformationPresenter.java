package com.zgzx.metaphysics.controller.presenters;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.event.SupplementInformationEvent;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 完善用户信息
 */
public class SupplementInformationPresenter extends RequestPresenter<ISingleRequestView<UserDetailEntity>> {

    private String address;
    private int timestamp, hour, calendarType;

    public void setTime(int timestamp, int hour, int calendarType) {
        this.timestamp = timestamp;
        this.hour = hour;
        this.calendarType = calendarType;
    }


    public void setAddress(String country, String province, String city, String area) {
        StringBuilder sb = new StringBuilder();

        if (!TextUtils.isEmpty(country)) {
            sb.append(country).append(",");
        }

        if (!TextUtils.isEmpty(province)) {
            sb.append(province).append(",");
        }

        if (!TextUtils.isEmpty(city)) {
            sb.append(city).append(",");
        }

        if (!TextUtils.isEmpty(area)) {
            sb.append(area).append(",");
        }

        address = sb.delete(sb.length() - 1, sb.length()).toString();
    }

    // 提交资料
    public void submit(String birth_time, String avater, String name, int sex) {
        if (!TextUtils.isEmpty(avater)) {


            List<File> fileList = new ArrayList<>();
            fileList.add(new File(avater));

            mView.loading();
            DataRepository.getInstance()
                    .upload(fileList)
                    .flatMap((Function<BasicResponseEntity<List<String>>,
                                    ObservableSource<BasicResponseEntity<UserDetailEntity>>>) entity ->

//                        String avatarPath = entity.getData().get(0); // 头像地址
//                        LocalConfigStore.getInstance().saveUserAvatar(avatarPath);
                                    DataRepository.getInstance()
                                            .completeInfo(birth_time, entity.getData().get(0),
                                                    name, sex, timestamp, hour,
                                                    calendarType, address)


                    )
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
//                        OrderResultEntity data = entity.getData();
//                        mView.onApplyResult(data);
//                        mView.complete();

                        mView.result(entity.getData());
                        LocalConfigStore.getInstance().completedInfo();

                    }));


//            Observable.just(avater)
//                    // 压缩
////                .map(s -> Luban.with(context).load(new File(path)).get())
//                    .map(s -> Collections.singletonList(new File(avater)))
//
//                    // 上传图片
//                    .flatMap((Function<List<File>,
//                            ObservableSource<BasicResponseEntity<List<String>>>>) files ->
//                            DataRepository.getInstance().upload(files))
//
//                    // 完善信息
//                    .flatMap((Function<BasicResponseEntity<List<String>>,
//                            ObservableSource<BasicResponseEntity<UserDetailEntity>>>) entity -> {
//                        String avatarPath = entity.getData().get(0); // 头像地址
//                        LocalConfigStore.getInstance().saveUserAvatar(avatarPath);
//                        // 保存至本地
//                        return DataRepository.getInstance()
//                                .completeInfo(
//                                        birth_time,
//                                        avatarPath,
//                                        name,
//                                        sex,
//                                        timestamp,
//                                        hour,
//                                        calendarType,
//                                        address);
//                    })
//
//                    .compose(SchedulersTransformer.transformer(mView))
//                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
//                        mView.result(entity.getData());
//
//                        LocalConfigStore.getInstance().completedInfo();
//                    }));
        } else {
            DataRepository.getInstance()
                    .completeInfo(
                            birth_time,
                            avater,
                            name,
                            sex,
                            timestamp,
                            hour,
                            calendarType,
                            address)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {

                        mView.result(entity.getData());

                        LocalConfigStore.getInstance().completedInfo();
                        EventBus.getDefault().post(new SupplementInformationEvent());

                    }));
        }

    }


    // 创建命书
    public void create(String name, int sex) {
        DataRepository.getInstance()
                .createFateBook(name, sex, timestamp, hour)
                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> {
                    mView.result(null);
                    LogUtils.e(entity.getData());
                }));
    }

}
