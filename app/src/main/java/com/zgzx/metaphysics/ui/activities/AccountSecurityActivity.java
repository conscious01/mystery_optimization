package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity.ASSETS_PASSWORD;
import static com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity.CREATE_ASSETS_PASSWORD;
import static com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity.LOGIN_PASSWORD;

/**
 * 账号与安全
 */
public class AccountSecurityActivity extends BaseActivity {

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, AccountSecurityActivity.class)
                .putExtra("TYPE", type);
    }

    @BindView(R.id.tv_type) TextView mTvType;

    private int mPageType;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_account_security;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, R.string.account_security);

        // 类型
        mPageType = getIntent().getIntExtra("TYPE", -1);
        mTvType.setText(mPageType == CREATE_ASSETS_PASSWORD ? R.string.create_assets_password : R.string.change_fund_password);
    }


    @OnClick({R.id.layout_account_security, R.id.layout_fund_security})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.layout_account_security:
                startActivity(SafetyVerificationActivity.newIntent(this, LOGIN_PASSWORD));
                break;

            case R.id.layout_fund_security:
                startActivity(SafetyVerificationActivity.newIntent(this, mPageType));
                break;
        }
    }
}
