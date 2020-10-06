package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.util.Consumer;

import com.blankj.utilcode.util.KeyboardUtils;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.PayController;
import com.zgzx.metaphysics.controller.TransferFeeController;
import com.zgzx.metaphysics.controller.presenters.TransferPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.PayView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.NumberUtil;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;
import com.zgzx.metaphysics.utils.filters.NumberDecimalFilter;
import com.zgzx.metaphysics.utils.watchers.EmptyRule;
import com.zgzx.metaphysics.utils.watchers.TextWatchers;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 划转
 * <p>
 * 用户UID最大长度12位，前端限制，避免超过Long类型最大值出现闪退问题
 */
public class TransferActivity extends BaseRequestActivity implements ICallback, Consumer<String>, TransferFeeController.View {

    private final static String EXT_BALANCE = "BALANCE";

    public static Intent newIntent(Context context, float balance) {
        return new Intent(context, TransferActivity.class)
                .putExtra(EXT_BALANCE, balance);
    }


    @BindView(R.id.et_uid) EditText mEtUid;
    @BindView(R.id.et_amount) EditText mEtAmount;
    @BindView(R.id.but_confirm) Button mButConfirm;
    @BindView(R.id.tv_handling_fee) TextView mTvHandlingFee;
    @BindView(R.id.tv_available_balance) TextView mTvAvailableBalance;


    private float mBalance;
    private TransferPresenter mPresenter;
    private PayController.Presenter mPayPresenter;
    private TransferFeeController.Presenter mTransFerFeePresenter;

    @Override
    protected int getBackgroundColor() {
        return getResources().getColor(R.color.backgroundColorSecondary);
    }

    @Override
    protected IStatusView createStatusView() {
        return new ToastRequestStatusView(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, R.string.transfer);

        // 可用余额
        mBalance = getIntent().getFloatExtra(EXT_BALANCE, 0);
        mTvAvailableBalance.setText(String.format(
                getString(R.string.placeholder_available_balance),
                NumberUtil.format(mBalance),
                getString(R.string.default_currency_name)));



        // 点击效果
        TextWatchers.add(mButConfirm, new EmptyRule(mEtUid, mEtAmount));
        mEtAmount.setFilters(new InputFilter[]{new NumberDecimalFilter(2)});

        // 请求
        mPresenter = new TransferPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

        // 支付
        mPayPresenter = new PayController.Presenter();
        mPayPresenter.setModelAndView(new PayView(this, this));
        getLifecycle().addObserver(mPayPresenter);

        mTransFerFeePresenter = new TransferFeeController.Presenter();
        mTransFerFeePresenter.setModelAndView(this);
        getLifecycle().addObserver(mTransFerFeePresenter);
        mTransFerFeePresenter.getTranfer();

    }

    @OnClick(value = {R.id.but_confirm, R.id.tv_paste})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.but_confirm: // 划转
                transfer();
                break;

            case R.id.tv_paste:
                mEtUid.setText(AndroidUtil.paste(this));
                break;
        }
    }

    private void transfer() {
        if (mBalance > 0) {
            String amount = mEtAmount.getText().toString();
            mPayPresenter.pay(R.string.transfer, Float.parseFloat(amount));

        } else {
            AppToast.showShort(getString(R.string.insufficient_balance));
        }
    }


    @Override
    public void successful() {
        KeyboardUtils.hideSoftInput(this);
        AppToast.showShort(getString(R.string.successful));
        startActivity(MainActivity.class);
    }


    @Override
    public void accept(String password) {
        String uid = mEtUid.getText().toString();
        String amount = mEtAmount.getText().toString();
        long time = new Date().getTime() / 1000;
        Map<String, Object> map = new HashMap<>();
        map.put("to_uid", Long.parseLong(uid));
        map.put("num", Float.parseFloat(amount));
        map.put("pay_pass", password);
        map.put("timestamp", time+LocalConfigStore.getInstance().getTimestamp());
        String str= HMacMD5Util.getMapToString(map);
        String sign = null;
        try {
            sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mPresenter.transfer(Long.parseLong(uid), Float.parseFloat(amount), password,time+LocalConfigStore.getInstance().getTimestamp(),LocalConfigStore.getInstance().getAk(),sign);
    }

    @Override
    public void renderTranFer(float data) {
        mTvHandlingFee.setText(String.format(getString(R.string.placeholder_handling_fee), Integer.valueOf(NumberUtil.format1(data*100))+"%"));
    }
}
