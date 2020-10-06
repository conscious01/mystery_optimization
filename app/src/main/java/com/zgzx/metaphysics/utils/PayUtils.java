package com.zgzx.metaphysics.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.PayResult;
import com.zgzx.metaphysics.model.entity.PrePayResult;

import java.util.Map;

public class PayUtils {
    public static int MEMBER_PAY_FLAG = 0x01;
    public static payResult mPayResult;
    public static Context mContext;

    public static void startPayAli(String _ID, Activity activity, payResult payResult) {
        mPayResult = payResult;
        mContext = activity;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(_ID, true);
                Message msg = new Message();
                msg.what = MEMBER_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult result = new PayResult((Map<String, String>) msg.obj);

            if ("9000".equals(result.getResultStatus())) {//成功
                System.out.println("9000");
                AppToast.showShort(mContext.getString(R.string.pay_ok));
                mPayResult.onSuccess();
            } else if ("4000".equals(result.getResultStatus())) {//失败
                System.out.println("4000");
                AppToast.showShort(mContext.getString(R.string.pay_failed));

                mPayResult.onError();
            } else {//异常
                mPayResult.onFailed();
                System.out.println("onFailed");
                AppToast.showShort(mContext.getString(R.string.pay_ex));


            }
        }
    };


    public static interface payResult {

        void onSuccess();

        void onFailed();

        void onError();


    }

    public static void doWXPay(Context context, PrePayResult.WxpayBean bean) {
        LogUtils.i(bean);
        IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.WX_APP_ID);
        PayReq req = new PayReq();
        req.appId = Constants.WX_APP_ID;
        req.partnerId = bean.getPartnerId();
        req.prepayId = bean.getPrepayId();
        req.nonceStr = bean.getNoncestr();
        req.timeStamp = bean.getTimestamp();
        req.packageValue = bean.getPackageX();
        req.nonceStr = bean.getNoncestr();
        req.sign = bean.getSign();
        api.sendReq(req);
    }


}