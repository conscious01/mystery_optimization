package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.BuyMemberController;

import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.model.LocalDataManager;
import com.zgzx.metaphysics.model.entity.PayResult;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.PayMethodDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.NumberUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择支付方式
 */
public class SelectePayActivity extends BaseRequestActivity implements BuyMemberController.View {


    BuyMemberController.Presenter mBuyPresenter;
    private int MEMBER_PAY_FLAG = 0x01;
    private int type;
    private float balance;

    public static Intent newIntent(Context context, float amount, int type) {
        return new Intent(context, SelectePayActivity.class).putExtra(Constants.EXT_AMOUNT, amount).putExtra(Constants.EXT_TYPE, type);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult result = new PayResult((Map<String, String>) msg.obj);

            if ("9000".equals(result.getResultStatus())) {
                ToastUtils.showShort(getText(R.string.pay_ok));
                startActivity(VipCenterActivity.newIntent(SelectePayActivity.this));
                new LocalDataManager().clearUserCache();
                finish();
            } else if ("4000".equals(result.getResultStatus())) {
                ToastUtils.showShort(getText(R.string.pay_failed));
            } else {
                ToastUtils.showShort(getText(R.string.pay_ex));
            }
        }
    };


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_select_pay_layout;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.select_pay);

        balance = getIntent().getFloatExtra(Constants.EXT_AMOUNT, 0);
        type = getIntent().getIntExtra(Constants.EXT_TYPE, -1);

        mStateView = new ToastRequestStatusView(this);
        mBuyPresenter = new BuyMemberController.Presenter();
        mBuyPresenter.setModelAndView(this);
        getLifecycle().addObserver(mBuyPresenter);

    }


    @OnClick(value = {R.id.wechat_layout, R.id.alipay_layout})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.wechat_layout://微信支付
                ToastUtils.showShort("暂不支持该支付方式，敬请期待");
                break;
            case R.id.alipay_layout://支付宝支付
                PayMethodDialog.show(this, balance, LocalConfigStore.getInstance().getMobile(), 1, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (type != -1) {
                            mBuyPresenter.request(1, type);
                        }
                    }
                });
                break;

        }
    }

    /**
     * 设置radiobutton监听
     */
//    private void initRadioButtonCheckListener() {
//        radio_wechat_pay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    radio_alipay.setChecked(false);
//                }
//
//            }
//        });
//        radio_alipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    radio_wechat_pay.setChecked(false);
//                }
//            }
//        });
//
//    }
    @Override
    public void buyOk(String _ID) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(SelectePayActivity.this);
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
}
