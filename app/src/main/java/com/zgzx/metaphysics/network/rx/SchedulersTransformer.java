package com.zgzx.metaphysics.network.rx;


import android.os.Looper;


import com.blankj.utilcode.util.LogUtils;
import com.zgzx.metaphysics.controller.views.core.IStatusView;


import java.net.ConnectException;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Author: LJD
 * Date: 2019/9/6
 * Desc: 转换请求
 */
public class SchedulersTransformer {

    /**
     * 线程调度
     */
    public static <T> ObservableTransformer<T, T> transformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static <T> ObservableTransformer<T, T> transformer(IStatusView view) {

        // 防止不在主线，调用闪退
        if (Looper.getMainLooper() == Looper.myLooper() && view != null) {
            view.loading();

        } else {
            LogUtils.w("transformer(IStatusView view)", Thread.currentThread().getName());
        }

        return upstream -> upstream
                .compose(transformer())
                .doOnComplete(() -> {
                    //if (view != null) view.complete();
                })
                .doOnError(throwable -> {
                    if (view != null) {
                        if (throwable instanceof ConnectException) {
                            view.offline();

                        } else {
                            view.failure(throwable);
                        }
                    }
                });
    }
}