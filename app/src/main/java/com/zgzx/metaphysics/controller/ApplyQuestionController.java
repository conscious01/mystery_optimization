package com.zgzx.metaphysics.controller;

import android.content.Context;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.OrderLifeBookEntity;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 发现页面，师傅详情
 */
public interface ApplyQuestionController {

    class ApplyQuestionPresenter extends RequestPresenter<ApplyQuestionController.View> {


        public void testUploadImages(List<File> files) {
            DataRepository.getInstance()
                    .upload(files)
                    // 提交申请
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        System.out.println(entity);
                    }));

        }


        public void applyWithImage(String nickname, int gender,
                                    int master_id, int birth_day,
                                    int birth_hour, int calendar_type,
                                    String birth_area, String content, List<File> files,
                                    Context context, String ak
        ) {

            mView.loading();
            DataRepository.getInstance()
                    .upload(files)
                    // 提交申请
                    .flatMap((Function<BasicResponseEntity<List<String>>,
                                    ObservableSource<BasicResponseEntity<OrderLifeBookEntity>>>) entity ->{
                                Long time = new Date().getTime() / 1000;
                                Map<String, Object> map = new HashMap<>();
                                map.put("nickname", nickname);
                                map.put("sex", gender);
                                map.put("master_id", master_id);
                                map.put("birth_day", birth_day);
                                map.put("birth_hour", birth_hour);
                                map.put("calendar_type", calendar_type);
                                map.put("birth_area", birth_area);
                                map.put("content", content);
                                map.put("photo_path", setArray(entity.getData()));
                                map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
                                String str = HMacMD5Util.getMapToString(map);
                                String sign1 = null;
                                try {
                                    sign1 = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                }
                                return   DataRepository.getInstance()
                                        .askQuestion(nickname, gender, master_id, birth_day,
                                                birth_hour,
                                                calendar_type, birth_area, content,
                                                setArray(entity.getData()), ak,
                                                time + LocalConfigStore.getInstance().getTimestamp(), sign1);
                            }



                    )

                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        OrderLifeBookEntity data = entity.getData();
                        mView.onApplyResult(data);
                        mView.complete();

                    }));
        }


        public void apply(String nickname, int gender,
                          int master_id, int birth_day,
                          int birth_hour, int calendar_type,
                          String birth_area, String content, String ak,
                          long timestamp,
                          String sign) {

            DataRepository.getInstance()
                    .askQuestion(nickname, gender, master_id, birth_day, birth_hour, calendar_type,
                            birth_area, content, null, ak, timestamp, sign)
                    .compose(SchedulersTransformer.transformer(mView))
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        OrderLifeBookEntity data = entity.getData();
                        mView.onApplyResult(data);
                    }));
        }


        private String setArray(List<String> list) {
            StringBuilder contentArray = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {

                if (i==list.size()-1){
                    contentArray.append(list.get(i));
                }else {
                    contentArray.append(list.get(i)).append(",");
                }
            }
            return contentArray.toString();
        }
    }


    interface View extends IStatusView {

        void onApplyResult(OrderLifeBookEntity detail);


    }

}
