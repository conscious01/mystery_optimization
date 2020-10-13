package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.BuyQuestionController;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.model.entity.PayResultEntity;
import com.zgzx.metaphysics.model.entity.PrePayResult;
import com.zgzx.metaphysics.model.event.PayResultEvent;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.PayUtils;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderPayActivity extends BaseRequestActivity implements BuyQuestionController.View,
        PayUtils.payResult {

    OrderResultEntity mOrderResultEntity;
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.product_amount)
    TextView productAmount;
    @BindView(R.id.tv_created_time)
    TextView tvCreatedTime;
    @BindView(R.id.cb_ali)
    CheckBox cbAli;
    @BindView(R.id.ll_ali)
    LinearLayout llAli;
    @BindView(R.id.cb_wechat)
    CheckBox cbWechat;
    @BindView(R.id.ll_wechat)
    LinearLayout llWechat;
    @BindView(R.id.tv_pay)
    TextView tvPay;

    private int mSelectPayType;

    private BuyQuestionController.Presenter mPresenter;


    public static void start(Context context, OrderResultEntity orderResultEntity,
                             int defaultPayType) {
        Intent intent = new Intent(context, OrderPayActivity.class);
        intent.putExtra(Constants.EXT_TYPE, orderResultEntity);
        intent.putExtra(Constants.TYPE, defaultPayType);
        context.startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultEvent(PayResultEvent event) {
        if (event.isSuccess()) {//微信支付成功
            System.out.println("");
            onPaySuccess();
        } else {
            System.out.println("");
            onPayFailed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.order_pay_activity;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.pay_order);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        if (intent != null) {
            mOrderResultEntity =
                    (OrderResultEntity) intent.getSerializableExtra(Constants.EXT_TYPE);
            mSelectPayType = intent.getIntExtra(Constants.TYPE, Constants.PAY_ALI);
        }
        if (mSelectPayType == Constants.PAY_ALI) {
            checkAli();
        } else if (mSelectPayType == Constants.PAY_WECHAT) {
            checkWechat();
        }

        tvOrderNo.setText(mOrderResultEntity.getOrderno());
        tvProductName.setText(mOrderResultEntity.getTrade_name());


        productAmount.setText("￥" + mOrderResultEntity.getPrice());
        tvCreatedTime.setText(DateUtils.getTime(mOrderResultEntity.getCreate_time(),
                DateUtils.PATTERN_3) + "");

        mPresenter = new BuyQuestionController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_ali, R.id.ll_wechat, R.id.tv_pay, R.id.iv_arrow_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_ali:
                checkAli();
                break;
            case R.id.ll_wechat:
                checkWechat();
                break;
            case R.id.tv_pay:
                long time = new Date().getTime() / 1000;
                Map<String, Object> map = new HashMap<>();
                map.put("pay_type", mSelectPayType);
                map.put("issue_id", mOrderResultEntity.getId());

                map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
                String str = HMacMD5Util.getMapToString(map);
                String sign = null;
                try {
                    sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
//                mPresenter.request(mSelectPayType, mOrderResultEntity.getId(), LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
                break;
            case R.id.iv_arrow_back:
                showLeaveDialog();
                break;
        }
    }


    private void checkWechat() {
        cbWechat.setChecked(true);
        cbAli.setChecked(false);
        mSelectPayType = Constants.PAY_WECHAT;
    }

    private void checkAli() {
        cbWechat.setChecked(false);
        cbAli.setChecked(true);
        mSelectPayType = Constants.PAY_ALI;
    }

    @Override
    public void buyOk(PrePayResult prePayResult) {
        if (mSelectPayType == Constants.PAY_ALI) {
            PayUtils.startPayAli(prePayResult.getAlipay(), this, this);

        } else if (mSelectPayType == Constants.PAY_WECHAT) {
            PayUtils.doWXPay(this, prePayResult.getWxpay());
        }
    }

    @Override
    public void onSuccess() {
        onPaySuccess();
    }


    private void onPaySuccess() {
        PayResultEntity payResultEntity = new PayResultEntity(true,
                getString(R.string.payment_successful), productAmount.getText().toString(),MyOrderActivity.class);
        PayResultActivity.start(this, payResultEntity);
        finish();
    }

    private void onPayFailed() {
        PayResultEntity payResultEntity = new PayResultEntity(false,
                getString(R.string.pay_failed), "",MyOrderActivity.class);
        PayResultActivity.start(this, payResultEntity);
        finish();
    }

    @Override
    public void onFailed() {
        onPayFailed();
    }

    @Override
    public void onError() {
        onPayFailed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showLeaveDialog();

    }

    AlertDialog dialog;

    private void showLeaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order_leave, null);
        builder.setView(view);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_leave_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        view.findViewById(R.id.tv_leave_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showLeaveDialog();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }
}
