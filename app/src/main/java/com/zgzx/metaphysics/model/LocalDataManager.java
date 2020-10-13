package com.zgzx.metaphysics.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mondo.logger.Logger;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.MetaphysicsApplication;
import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.DomesticJsonBean;
import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.ForeignJsonBean;
import com.zgzx.metaphysics.model.entity.AreaCodeEntity;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.CalendarDetailEntity;
import com.zgzx.metaphysics.model.entity.FateBookDetailEntity;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.model.entity.FateBooksEntity;
import com.zgzx.metaphysics.model.entity.FortuneEntity;
import com.zgzx.metaphysics.model.entity.HomeDataEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.WebApiConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;


/**
 * 本地数据
 */
@SuppressLint("DefaultLocale")
public class LocalDataManager {

    private final String

            // 手机区号
            PHONE_CODE_KEY = "phoneCode(%s)", // 语言类型

    // 中国地区数据
    AREA_CHINA_KEY = "areaChina(%s)", // 语言类型

    // 海外地区数据
    AREA_OVERSEA_KEY = "areaOversea(%s)", // 语言类型

    // 用户信息
    USER_DETAIL_KEY = "userDetail(%s)", // 用户ID

    // 首页数据
    HOME_DATA_KEY = "homeData",

    // 个人运势
    PERSONAL_FORTUNE_KEY = "personalFortune(%s,%d,%s,%s)", // 时间, 用户ID, 语言类型

    // 命书列表
    FATE_BOOKS_LIST_KEY = "fateBooksList(%s,%s,%s)", // 用户ID, 语言类型, 时间

    // 命书类型
    FATE_BOOK_TYPES_KEY = "fateBookTypes(%d,%s,%s,%s)", // 命书ID, 用户ID, 语言类型, 时间

    // 命书详情
    FATE_BOOK_DETAIL_KEY = "fateBookDetail(%d,%d,%s,%s,%s)", // 命书ID, 章节ID, 用户ID, 语言类型, 时间

    // 日历详情
    CALENDAR_DETAIL_KEY = "calendarDetail(%s,%s)"; // 时间, 语言类型


    private SharedPreferences mPreferences;

    public LocalDataManager() {
        mPreferences = MetaphysicsApplication.sInstance.getSharedPreferences(
                "cache_data", Context.MODE_PRIVATE);
    }


    // 获取手机区号
    public Observable<BasicResponseEntity<List<AreaCodeEntity>>> getPhoneCode(
            Observable<BasicResponseEntity<List<AreaCodeEntity>>> observable) {
        return Observable.create(emitter -> {
            String key = String.format(PHONE_CODE_KEY,
                    LocalConfigStore.getInstance().getAcceptLanguage());

            BasicResponseEntity<List<AreaCodeEntity>> response;
            String data = mPreferences.getString(key, null);

            if (data != null) {
                response =  new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<List<AreaCodeEntity>>>() {
                            // ...
                        }.getType()
                );

            } else {

                response = observable.doOnError(emitter::onError).blockingFirst();

                mPreferences
                        .edit()
                        .putString(key, new Gson().toJson(response))
                        .apply();
            }

            emitter.onNext(response);
            emitter.onComplete();
        });
    }


    /**
     * 中国地区数据
     */
    public Observable<BasicResponseEntity<List<DomesticJsonBean>>> areaChina(
            Observable<BasicResponseEntity<List<DomesticJsonBean>>> observable) {
        return Observable.create(emitter -> {
            String key = String.format(AREA_CHINA_KEY,
                    LocalConfigStore.getInstance().getAcceptLanguage());

            BasicResponseEntity<List<DomesticJsonBean>> response;
            String data = mPreferences.getString(key, null);

            if (data != null) {
                response =  new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<List<DomesticJsonBean>>>() {
                            // ...
                        }.getType()
                );

            } else {

                response = observable.doOnError(emitter::onError).blockingFirst();

                mPreferences
                        .edit()
                        .putString(key, new Gson().toJson(response))
                        .apply();
            }

            emitter.onNext(response);
            emitter.onComplete();
        });
    }


    /**
     * 海外地区数据
     */
    public Observable<BasicResponseEntity<List<ForeignJsonBean>>> areaOversea(
            Observable<BasicResponseEntity<List<ForeignJsonBean>>> observable) {
        return Observable.create(emitter -> {
            String key = String.format(AREA_OVERSEA_KEY,
                    LocalConfigStore.getInstance().getAcceptLanguage());

            BasicResponseEntity<List<ForeignJsonBean>> response;
            String data = mPreferences.getString(key, null);

            if (data != null) {
                response =  new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<List<ForeignJsonBean>>>() {
                            // ...
                        }.getType()
                );

            } else {

                response = observable.doOnError(emitter::onError).blockingFirst();

                mPreferences
                        .edit()
                        .putString(key, new Gson().toJson(response))
                        .apply();
            }

            emitter.onNext(response);
            emitter.onComplete();
        });
    }

    /**
     * 首页数据
     */
    public Observable<BasicResponseEntity<HomeDataEntity>> homeData(
            Observable<BasicResponseEntity<HomeDataEntity>> observable){
        return Observable.create(emitter -> {
            String key = String.format(HOME_DATA_KEY, LocalConfigStore.getInstance().getUserId());

            String data = mPreferences.getString(key, null);

            // 获取数据
            if (data == null) {
                BasicResponseEntity<HomeDataEntity> response =
                        observable.doOnError(emitter::onError).blockingFirst();

                if (response.isSucceed()) {
                    mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                }

                emitter.onNext(response);
            } else {

                // 设置缓存数据
                emitter.onNext(new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<HomeDataEntity>>() {
                            // ...
                        }.getType()
                ));

                try {

                    // 重新获取远程数据
                    BasicResponseEntity<HomeDataEntity> response = observable.blockingFirst();

                    if (response.isSucceed()) {
                        mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("重新获取远程数据, homeData： %s", e.getMessage());
                }
            }

            emitter.onComplete();
        });
    }

    /**
     * 用户详情
     */
    public Observable<BasicResponseEntity<UserDetailEntity>> userDetail(
            Observable<BasicResponseEntity<UserDetailEntity>> observable) {
        return Observable.create(emitter -> {

            String key = String.format(USER_DETAIL_KEY, LocalConfigStore.getInstance().getUserId());

            String data = mPreferences.getString(key, null);

            // 获取数据
            if (data == null) {
                BasicResponseEntity<UserDetailEntity> response =
                        observable.doOnError(emitter::onError).blockingFirst();

                if (response.isSucceed()) {
                    mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                }

                emitter.onNext(response);
            } else {

                // 设置缓存数据
                emitter.onNext(new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<UserDetailEntity>>() {
                            // ...
                        }.getType()
                ));

                try {

                    // 重新获取远程数据
                    BasicResponseEntity<UserDetailEntity> response = observable.blockingFirst();

                    if (response.isSucceed()) {
                        mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("重新获取远程数据, userDetail： %s", e.getMessage());
                }
            }

            emitter.onComplete();
        });
    }

    public  void clearUserCache(){
        // 简体缓存
        String zh_cn_key = String.format(USER_DETAIL_KEY,
                LocalConfigStore.getInstance().getUserId(), // 用户
                WebApiConstants.LANGUAGE_ZH_CN, // 语言
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) // 时间
        );

        // 繁体缓存
        String zh_tw_key = String.format(USER_DETAIL_KEY,
                LocalConfigStore.getInstance().getUserId(), // 用户
                WebApiConstants.LANGUAGE_ZH_TW, // 语言
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) // 时间
        );
        mPreferences.edit().remove(zh_cn_key).remove(zh_tw_key).apply();
    }

    /**
     * 个人运势
     */
    public Observable<BasicResponseEntity<FortuneEntity>> personalFortune(
            int timestamp, int type,
            Observable<BasicResponseEntity<FortuneEntity>> observable){
        return Observable.create(emitter -> {
            String key = String.format(PERSONAL_FORTUNE_KEY,
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp * 1000L)),
                    type,
                    LocalConfigStore.getInstance().getUserId(),
                    LocalConfigStore.getInstance().getAcceptLanguage());

            String data = mPreferences.getString(key, null);

            // 获取数据
            BasicResponseEntity<FortuneEntity> response;
            if (data == null) {
                response = observable.doOnError(emitter::onError).blockingFirst();
                if (response.isSucceed()) {
                    mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                }
            } else {
                response = new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<FortuneEntity>>() {
                            // ...
                        }.getType()
                );

                Logger.i("personalFortune 缓存数据, %s", response.toString());
            }

            emitter.onNext(response);
            emitter.onComplete();
        });
    }


    /**
     * 命书列表
     */
    public Observable<BasicResponseEntity<List<FateBooksEntity>>> fateBooksList(
            Observable<BasicResponseEntity<List<FateBooksEntity>>> observable){
        return Observable.create(emitter -> {
            String key = String.format(FATE_BOOKS_LIST_KEY,
                    LocalConfigStore.getInstance().getUserId(), // 用户
                    LocalConfigStore.getInstance().getAcceptLanguage(), // 语言
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // 时间

            String data = mPreferences.getString(key, null);

            // 获取数据
            BasicResponseEntity<List<FateBooksEntity>> response;
            if (data == null) {
                response = observable.doOnError(emitter::onError).blockingFirst();
                if (response.isSucceed()) {
                    mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                }

            } else {
                response = new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<List<FateBooksEntity>>>() {
                            // ...
                        }.getType()
                );
            }

            emitter.onNext(response);
            emitter.onComplete();
        });
    }


    /**
     * 命书详情
     */
    public Observable<BasicResponseEntity<FateBookDetailEntity>> fateBookDetail(
            int fateBookId,
            int cateId,
            Observable<BasicResponseEntity<FateBookDetailEntity>> observable){
        return Observable.create(emitter -> {
            String key = String.format(FATE_BOOK_DETAIL_KEY,
                    fateBookId, // 命书
                    cateId, // 章节
                    LocalConfigStore.getInstance().getUserId(), // 用户
                    LocalConfigStore.getInstance().getAcceptLanguage(), // 语言
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()) // 时间
            );

            String data = mPreferences.getString(key, null);

            BasicResponseEntity<FateBookDetailEntity> response;
            if (data == null) {
                response = observable.doOnError(emitter::onError).blockingFirst();
                if (response.isSucceed()) {
                    mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                }

            } else {
                // 获取缓存数据
                response = new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<FateBookDetailEntity>>() {
                            // ...
                        }.getType()
                );
            }

            emitter.onNext(response);
            emitter.onComplete();
        });
    }


    /**
     * 日历详情
     */
    public Observable<BasicResponseEntity<CalendarDetailEntity>> calendarDetail(
            int timestamp,
            Observable<BasicResponseEntity<CalendarDetailEntity>> observable) {
        return Observable.create(emitter -> {
            String key = String.format(CALENDAR_DETAIL_KEY,
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp * 1000L)),
                    LocalConfigStore.getInstance().getAcceptLanguage());

            String data = mPreferences.getString(key, null);

            BasicResponseEntity<CalendarDetailEntity> response;
            if (data == null) {
                response = observable.doOnError(emitter::onError).blockingFirst();
                if (response.isSucceed()) {
                    mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                }

            } else {
                // 获取缓存数据
                response = new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<CalendarDetailEntity>>() {
                            // ...
                        }.getType()
                );
            }

            emitter.onNext(response);
            emitter.onComplete();
        });
    }

    /**
     * 命书类型
     */
    public Observable<BasicResponseEntity<List<FateBookTypeEntity>>> fateBookTypes(
            int fateBookId,
            Observable<BasicResponseEntity<List<FateBookTypeEntity>>> observable){

        return Observable.create(emitter -> {
            String key = String.format(FATE_BOOK_TYPES_KEY,
                    fateBookId, // 命书
                    LocalConfigStore.getInstance().getUserId(), // 用户
                    LocalConfigStore.getInstance().getAcceptLanguage(), // 语言
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()) // 时间
            );

            String data = mPreferences.getString(key, null);

            // 获取数据
            BasicResponseEntity<List<FateBookTypeEntity>> response;
            if (data == null) {
                response = observable.doOnError(emitter::onError).blockingFirst();
                if (response.isSucceed()) {
                    mPreferences.edit().putString(key, new Gson().toJson(response)).apply();
                }

            } else {
                response = new Gson().fromJson(
                        data,
                        new TypeToken<BasicResponseEntity<List<FateBookTypeEntity>>>() {
                            // ...
                        }.getType()
                );
            }

            emitter.onNext(response);
            emitter.onComplete();
        });
    }


    /**
     * 清除命书列表缓存
     */
    public void clearFateBooksListCache() {
        // 简体缓存
        String zh_cn_key = String.format(FATE_BOOKS_LIST_KEY,
                LocalConfigStore.getInstance().getUserId(), // 用户
                WebApiConstants.LANGUAGE_ZH_CN, // 语言
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) // 时间
        );

        // 繁体缓存
        String zh_tw_key = String.format(FATE_BOOKS_LIST_KEY,
                LocalConfigStore.getInstance().getUserId(), // 用户
                WebApiConstants.LANGUAGE_ZH_TW, // 语言
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) // 时间
        );

        // 清除命书列表缓存
        mPreferences.edit()
                .remove(zh_cn_key)
                .remove(zh_tw_key)
                .apply();
    }


    /**
     * 清除命书类型缓存
     *
     * @param fateBookId 命书ID
     */
    public void clearFateBookTypeCache(int fateBookId) {
        // 简体缓存
        String zh_cn_key = String.format(FATE_BOOK_TYPES_KEY,
                fateBookId, // 命书
                LocalConfigStore.getInstance().getUserId(), // 用户
                WebApiConstants.LANGUAGE_ZH_CN, // 语言
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) // 时间
        );

        // 繁体缓存
        String zh_tw_key = String.format(FATE_BOOK_TYPES_KEY,
                fateBookId, // 命书
                LocalConfigStore.getInstance().getUserId(), // 用户
                WebApiConstants.LANGUAGE_ZH_TW, // 语言
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) // 时间
        );

        // 清除
        mPreferences.edit()
                .remove(zh_cn_key)
                .remove(zh_tw_key)
                .apply();
    }

    /**
     * 清除所有命书类型缓存
     */
    public void clearAllFateBookTypeCache(){
        Map<String, ?> map = mPreferences.getAll();
        Set<? extends Map.Entry<String, ?>> entries = map.entrySet();
        for (Map.Entry<String, ?> entry : entries) {
            if (entry.getKey().contains("fateBookTypes")) {
                mPreferences.edit().remove(entry.getKey()).apply();
            }
        }
    }

    /**
     * 清除用户数据
     */
    public void clearUserDetail(){
        String key = String.format(USER_DETAIL_KEY, LocalConfigStore.getInstance().getUserId());
        mPreferences.edit().remove(key).apply();





    }

    /**
     * 清除所有数据
     */
    public void clear(){
        Map<String, ?> all = mPreferences.getAll();
        Set<? extends Map.Entry<String, ?>> entries = all.entrySet();
        for (Map.Entry<String, ?> entry : entries) {
            mPreferences.edit().remove(entry.getKey()).apply();
        }

        SharedPreferences  sSharedPreferences = MetaphysicsApplication.sInstance.getSharedPreferences(
                "P_qbw", Context.MODE_PRIVATE);
        sSharedPreferences.edit().clear().commit();
    }

}
