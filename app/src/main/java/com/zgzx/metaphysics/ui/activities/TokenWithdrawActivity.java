package com.zgzx.metaphysics.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import butterknife.BindView;

/**
 * 提币
 */
@Deprecated
public class TokenWithdrawActivity extends BaseActivity {

    @BindView(R.id.iv_token) ImageView mIvToken;
    @BindView(R.id.tv_address) TextView mTvAddress;
    @BindView(R.id.tv_token_name_1) TextView mTvTokenName1;
    @BindView(R.id.tv_token_name_2) TextView mTvTokenName2;
    @BindView(R.id.tv_token_name_3) TextView mTvTokenName3;
    @BindView(R.id.tv_available_balance) TextView mTvAvailableBalance;
    @BindView(R.id.tv_handling_fee) TextView mTvHandlingFee;
    @BindView(R.id.tv_amount) TextView mTvAmount;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_token_withdraw;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F2EFEB"));
        ActivityTitleHelper.setTitle(this, R.string.withdraw_toekn);

        // 信息
        mIvToken.setBackgroundColor(Color.RED);
        mTvTokenName1.setText("USDT");
        mTvTokenName2.setText("USDT ｜ ");
        mTvTokenName3.setText("USDT");
        mTvAddress.setText("0x24602722816b6cad0e143ce9fabf31f6026ec6226026ec6226026ec622");
        mTvAvailableBalance.setText(String.format(getString(R.string.placeholder_available_balance), 12.5, "USDT"));
        mTvHandlingFee.setText(String.format(getString(R.string.placeholder_handling_fee),"1%"));
        mTvAmount.setText("1024");
    }

}
