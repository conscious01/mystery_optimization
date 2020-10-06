package com.zgzx.metaphysics.controller.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.luck.picture.lib.compress.Luban;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 更改用户信息
 */
public class AlterUserInfoPresenter extends RequestPresenter<ICallback> {

    // 修改用户名称
    public void alterName(String name) {
        DataRepository.getInstance()
                .updateUserDetail(
                        null,
                        name,
                        null,
                        0,
                        0,
                        0,
                        0,
                        null,
                        null)
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful()));
    }

    // 修改用户昵称
    public void alterNickname(String nickname) {
        DataRepository.getInstance()
                .updateUserDetail(
                        null,
                        nickname,
                        null,
                        0,
                        0,
                        0,
                        0,
                        null,
                        null)
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful()));
    }

    // 修改师傅介绍
    public void alterMasterIntroduction(String introduction) {
        DataRepository.getInstance()
                .updateMasterIntro(introduction)
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful()));
    }

    // 修改出生地点
    public void alterBirthArea(String country, String province, String city, String area) {

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

        DataRepository.getInstance()
                .updateUserDetail(
                        null,
                        null,
                        null,
                        0,
                        0,
                        0,
                        0,
                        sb.delete(sb.length() - 1, sb.length()).toString(),
                        null)
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful()));
    }


    // 修改出生时间
    public void setTime(int timestamp, int hour, int calendarType, String birthTime,String name,int sex,String birth_area) {
        DataRepository.getInstance()
                .updateUserDetail(
                        birthTime,
                        name,
                        null,
                        sex,
                        timestamp,
                        hour,
                        calendarType,
                        birth_area,
                        null)
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful()));
    }

    // 修改居住地点
    public void alterLiveArea(String country, String province, String city, String area) {
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

        DataRepository.getInstance()
                .updateUserDetail(
                        null,
                        null,
                        null,
                        0,
                        0,
                        0,
                        0,
                        null,
                        sb.delete(sb.length() - 1, sb.length()).toString())
                .compose(SchedulersTransformer.transformer())
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful()));
    }


    // 修改用户头像
    public void alterAvatar(String path) {
        Observable.just(path)
                // 压缩
//                .map(s -> Luban.with(context).load(new File(path)).get())
                .map(s -> Collections.singletonList(new File(path)))

                // 上传图片
                .flatMap((Function<List<File>, ObservableSource<BasicResponseEntity<List<String>>>>) files ->
                        DataRepository.getInstance().upload(files))

                // 修改用户头像
                .flatMap((Function<BasicResponseEntity<List<String>>, ObservableSource<BasicResponseEntity<Object>>>) entity -> {
                    String avatarPath = entity.getData().get(0); // 头像地址
                    LocalConfigStore.getInstance().saveUserAvatar(avatarPath); // 保存至本地
                    return DataRepository.getInstance().updateAvatar(avatarPath);
                })

                .compose(SchedulersTransformer.transformer(mView))
                .subscribe(new ResponseObserver<>(this, mView, entity -> mView.successful()));
    }

}
