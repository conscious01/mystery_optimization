package com.zgzx.metaphysics.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.PasswordPresenter;
import com.zgzx.metaphysics.controller.presenters.SendAuthCodePresenter;
import com.zgzx.metaphysics.controller.views.IPasswordView;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.watchers.EmptyRule;
import com.zgzx.metaphysics.utils.watchers.TextWatchers;
import com.zgzx.metaphysics.widgets.HorizontalSmoothScrollerLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseRequestActivity implements IPasswordView {

    @BindView(R.id.et_password) EditText mEtPassword;
    @BindView(R.id.check_password) CheckBox mCheckPassword;
    @BindView(R.id.et_verify_password) EditText mEtVerifyPassword;
    @BindView(R.id.check_verify_password) CheckBox mCheckVerifyPassword;
    @BindView(R.id.but_verify_code) Button mButVerifyCode;
    @BindView(R.id.but_complete) Button mButComplete;
    @BindView(R.id.et_mobile_number) EditText mEtMobileNumber;
    @BindView(R.id.et_code) EditText mEtCode;
    @BindView(R.id.but_next) Button mButNext;
    @BindView(R.id.tv_region) TextView mTvRegion;
    @BindView(R.id.tv_title) TextView mTvTitle;

    @BindView(R.id.smooth_scroller_layout)
    HorizontalSmoothScrollerLayout mSmoothScrollerLayout;

    private PasswordPresenter mPresenter;
    private SendAuthCodePresenter mAuthPresenter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.forget_password);

        AndroidUtil.passwordVisibility(mCheckPassword, mEtPassword);
        AndroidUtil.passwordVisibility(mCheckVerifyPassword, mEtVerifyPassword);

        mEtMobileNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // 邀请码
        TextWatchers.add(mButVerifyCode, new EmptyRule(mEtMobileNumber));

        // 下一步
        TextWatchers.add(mButNext, new EmptyRule(mEtMobileNumber, mEtCode));

        // 完成
        TextWatchers.add(mButComplete, new EmptyRule(mEtPassword, mEtVerifyPassword));

        // 请求
        mPresenter = new PasswordPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

        // 短信
        mAuthPresenter = SendAuthCodePresenter.newAndBind(mButVerifyCode, getLifecycle());
    }


    @OnClick({R.id.tv_region, R.id.but_verify_code, R.id.but_next, R.id.but_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_region: // 手机区号
                startActivityForResult(PhoneAreaCodeActivity.newIntent(this), Constants.REQ_ARE_CODE);
                break;

            case R.id.but_verify_code: // 发送短信
                mAuthPresenter.send(
                        mTvRegion.getText().toString(),
                        mEtMobileNumber.getText().toString(),
                        SendAuthCodePresenter.FORGET_LOGIN_PASSWORD
                );
                break;

            case R.id.but_next: // 验证
                mPresenter.verify(
                        mTvRegion.getText().toString(),
                        mEtMobileNumber.getText().toString(),
                        mEtCode.getText().toString()
                );
                break;

            case R.id.but_complete: // 重置密码
                resetPassword();
                break;
        }
    }


    private void resetPassword() {
        String password = mEtPassword.getText().toString();
        String verifyPassword = mEtVerifyPassword.getText().toString();

        if (!password.equals(verifyPassword)){
            AppToast.showLong(getString(R.string.error_passwords_not_same));
            return;
        }

        mPresenter.resetLoginPassword(password);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            String code = data.getStringExtra(Constants.EXTRA_AREA_CODE);
            mTvRegion.setText(code);
        }
    }


    @Override
    public void verifySucceeded() {
        mTvTitle.setText(R.string.reset_password);
        KeyboardUtils.hideSoftInput(this);
        mSmoothScrollerLayout.next();
    }


    @Override
    public void successful() {
        onBackPressed();
    }

}
