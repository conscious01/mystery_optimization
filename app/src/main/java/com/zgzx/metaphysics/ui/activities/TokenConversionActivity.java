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
 * 兑换
 */
@Deprecated
public class TokenConversionActivity extends BaseActivity {

    @BindView(R.id.iv_token_1) ImageView mIvToken1;
    @BindView(R.id.iv_token_2) ImageView mIvToken2;
    @BindView(R.id.tv_token_name_1) TextView mTvTokenName1;
    @BindView(R.id.tv_token_name_2) TextView mTvTokenName2;
    @BindView(R.id.et_amount) EditText mEtAmount;
    @BindView(R.id.et_conversion_amount) EditText mEtConversionAmount;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_token_conversion;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F2EFEB"));
        ActivityTitleHelper.setTitle(this, R.string.conversion);

        mIvToken1.setBackgroundColor(Color.RED);
        mIvToken2.setBackgroundColor(Color.BLUE);
        mTvTokenName1.setText("ETH");
        mTvTokenName2.setText("USDT");
    }


}
