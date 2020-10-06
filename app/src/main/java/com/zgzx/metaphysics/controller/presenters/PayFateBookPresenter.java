package com.zgzx.metaphysics.controller.presenters;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.model.DataRepository;
import com.zgzx.metaphysics.network.rx.ResponseObserver;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;

import java.util.List;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/19
 * @Description: 购买命书
 */
public class PayFateBookPresenter extends RequestPresenter<ICallback> {

    // 购买命书
    public void buy(int id, String password, int isAll, int payType, String list, String ak, long timestamp, String sign) {
            DataRepository.getInstance()
                    .buyFateBook(id, password, isAll, payType, list,ak,timestamp,sign)
                    .compose(SchedulersTransformer.transformer())
                    .subscribe(new ResponseObserver<>(this, mView, entity -> {
                        mView.successful();
                    }));
    }

}
