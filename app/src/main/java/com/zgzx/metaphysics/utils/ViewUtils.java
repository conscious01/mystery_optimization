package com.zgzx.metaphysics.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class ViewUtils {
    //倒计时定时器
    public static void countDown(TextView textView, int count) {

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//ui线程中进行控件更新
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long num) {
                textView.setText(DateUtils.secToTime(num.intValue()));
//                LogUtils.i("secToTime" + DateUtils.secToTime(num.intValue()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                //回复原来初始状态
                LogUtils.i("secToTime onComplete");
                textView.setText("");
            }
        });

    }


    /**
     * @param wholeText
     * @param targetString
     * @param tv
     * @param differentColor textview设置不同颜色
     */
    public static void textViewDifferentColor(String wholeText, String targetString, TextView tv,
                                              int differentColor) {
        SpannableString spanString = new SpannableString(wholeText);
        //构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(differentColor);
        //将这个Span应用于指定范围的字体
        int targetPosition = wholeText.indexOf(targetString);
        if (targetPosition==-1) {
            return;
        }
        spanString.setSpan(span, targetPosition, targetPosition + 1,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给TextView显示出来
        tv.setText(spanString);
    }

}
