package com.zgzx.metaphysics.ui.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgzx.metaphysics.R;


public class PayDialog implements View.OnClickListener {


    private Context context;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private OnPayClickListener onPayClickListener;

    public final static int PAY_ALI = 1;
    public final static int PAY_WECHAT = 2;


    public interface OnPayClickListener {
        void onPay(int payType);
    }

    int defaultPayType;

    public PayDialog(Context context) {
        this.context = context;


        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();
        View view = LayoutInflater.from(context).inflate(R.layout.pay_layout, null);


        initView(context, view);
        initEvent(view);
    }

    LinearLayout llAli, llWechat;
    CheckBox cbAli, cbWechat;
    TextView tvPay;

    private void initView(Context context, View view) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);


        dialog.setCancelable(false);

        llAli = view.findViewById(R.id.ll_ali);
        llWechat = view.findViewById(R.id.ll_wechat);
        cbAli = view.findViewById(R.id.cb_ali);
        cbWechat = view.findViewById(R.id.cb_wechat);
        tvPay = view.findViewById(R.id.tv_pay);

        if (defaultPayType == PAY_ALI) {
            checkAli();
        } else if (defaultPayType == PAY_WECHAT) {
            checkWechat();
        }


    }


    public void show() {
//        BuriedPointManager.onEvent(InitUtil.getContext(), InitUtil.getContext().getString(R
//        .string.paint_id_enter_payment), InitUtil.getContext().getString(R.string
//        .paint_name_enter_payment));
//        initAnim(mLLContainerIndex);
        dialog.show();
    }


    private void checkWechat() {
        cbWechat.setChecked(true);
        cbAli.setChecked(false);
        selectPayType = PAY_WECHAT;
    }

    private void checkAli() {
        cbWechat.setChecked(false);
        cbAli.setChecked(true);
        selectPayType = PAY_ALI;
    }

    private void initEvent(View view) {
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.START | Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
        }

        llAli.setOnClickListener(this);
        llWechat.setOnClickListener(this);
    }

    private int selectPayType;

    @Override
    public void onClick(View v) {

        if (v == llAli) {
            checkAli();
            return;
        }

        if (v == llWechat) {
            checkWechat();
            return;
        }
        if (v == tvPay) {
            System.out.println("----------");
            return;
        }

    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void pay(String payType) {
//        this.payType = payType;
        progressDialog.show();

//        PostCreateOrderBean paramBean = new PostCreateOrderBean();
//        paramBean.setOrderType(orderType);
//        paramBean.setPaymentMethod(payType);
//        paramBean.setAppid(AppConstants.WECHAT_APP_ID);
//        paramBean.setPackageId(packageId);
//        paramBean.setLevel(level);
//        if (!TextUtils.isEmpty(period)) {
//            paramBean.setPeriod(period);
//        }
//
//        ApiHelper.doPost(ApiConstants.ORDER_CREAT, ApiHelper.CreateBody(paramBean),
//                new ApiProgressResultSubscriber(context) {
//                    @Override
//                    public void onResponse(JsonElement json) {
//                        hideProgressDialog();
//                        CreateOrderBean bean = ApiHelper.fromJsonBean(json, CreateOrderBean
//                        .class);
//                        if (AppConstants.PAYMENT_METHOD_WXPAY.equals(bean.getPaymentMethod())) {
//                            WeChatPayHelper.doPay(context, bean.getWeixinPay());
//                        } else if (AppConstants.PAYMENT_METHOD_ALIPAY.equals(bean
//                        .getPaymentMethod())) {
//                            AliPayHelper.doPay((Activity) context, bean.getOrderString());
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        hideProgressDialog();
//                    }
//                });

    }
}
