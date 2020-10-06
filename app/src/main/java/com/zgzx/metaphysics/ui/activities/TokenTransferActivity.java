package com.zgzx.metaphysics.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import butterknife.BindView;

/**
 * token 划转
 */
@Deprecated
public class TokenTransferActivity extends BaseActivity {

    @BindView(R.id.iv_token) ImageView mIvToken;
    @BindView(R.id.et_address) EditText mEtAddress;
    @BindView(R.id.tv_token_name) TextView mTvTokenName;
    @BindView(R.id.tv_available_balance) TextView mTvAvailableBalance;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_token_transfer;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F2EFEB"));
        ActivityTitleHelper.setTitle(this, R.string.transfer);

        mIvToken.setBackgroundColor(Color.RED);
        mTvTokenName.setText("USDT");
        //mEtAddress.setText("0x24602722816b6cad0e143ce9fabf31f6026ec6226026ec6226026ec622");
        mTvAvailableBalance.setText(String.format(getString(R.string.placeholder_available_balance), 12.5, "USDT"));
    }

}
