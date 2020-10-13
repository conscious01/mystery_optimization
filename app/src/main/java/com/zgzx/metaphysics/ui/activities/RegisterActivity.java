package com.zgzx.metaphysics.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputFilter;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.RegisterPresenter;
import com.zgzx.metaphysics.controller.presenters.SendAuthCodePresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.SpannableStringHelper;
import com.zgzx.metaphysics.utils.filters.ChineseInputFilter;
import com.zgzx.metaphysics.utils.watchers.CheckedRule;
import com.zgzx.metaphysics.utils.watchers.EmptyRule;
import com.zgzx.metaphysics.utils.watchers.TextWatchers;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 注册
 */
public class RegisterActivity extends BaseRequestActivity implements ICallback {

    @BindView(R.id.but_register) Button mButRegister;
    @BindView(R.id.tv_region) TextView mTvRegion;
    @BindView(R.id.et_mobile_number) EditText mEtMobileNumber;
    @BindView(R.id.et_code) EditText mEtCode;
    @BindView(R.id.but_verify_code) Button mButVerifyCode;
    @BindView(R.id.et_password) EditText mEtPassword;
    @BindView(R.id.check_password) CheckBox mCheckPassword;
    @BindView(R.id.et_verify_password) EditText mEtVerifyPassword;
    @BindView(R.id.check_verify_password) CheckBox mCheckVerifyPassword;
    @BindView(R.id.et_invite_code) EditText mEtInviteCode;
    @BindView(R.id.tv_protocol) CheckBox mTvProtocol;
    @BindView(R.id.iv_scan)
    ImageView mivScan;


    private RegisterPresenter mPresenter;

    private SendAuthCodePresenter mAuthPresenter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.mobile_register);

        AndroidUtil.passwordVisibility(mCheckPassword, mEtPassword);
        AndroidUtil.passwordVisibility(mCheckVerifyPassword, mEtVerifyPassword);

        mEtPassword.setFilters(new InputFilter[]{new ChineseInputFilter()});
        mEtVerifyPassword.setFilters(new InputFilter[]{new ChineseInputFilter()});

        String language = LocalConfigStore.getInstance().getAcceptLanguage();

        String serviceURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                "/privacy_and_agreement/agreement?" +
                "language=" + language;
        String privateURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                "/privacy_and_agreement/privacy?" +
                "language=" + language;
        // 协议
        mTvProtocol.setText(
                SpannableStringHelper.newBuilder(getString(R.string.agree))
                        .append(getString(R.string.register_protocol))
                        .foregroundColor(getColorForRes(R.color.color_80993c))
                        .click(view -> startActivity(WebViewActivity.newIntent(this, serviceURL)))
                        .append(getString(R.string.with))
                        .append(getString(R.string.register_privacy_policy))
                        .foregroundColor(getColorForRes(R.color.color_80993c))
                        .click(view -> startActivity(WebViewActivity.newIntent(this, privateURL)))
                        .build()
        );

        mTvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        mEtMobileNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // 邀请码
        TextWatchers.add(mButVerifyCode, new EmptyRule(mEtMobileNumber));

        // 注册
        TextWatchers.add(mButRegister,
                new EmptyRule(mEtMobileNumber, mEtCode, mEtPassword, mEtVerifyPassword),
                new CheckedRule(mTvProtocol));

        // 请求
        mPresenter = new RegisterPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

        // 短信
        mAuthPresenter = SendAuthCodePresenter.newAndBind(mButVerifyCode, getLifecycle());
        RxView.clicks(mButRegister).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) throws Exception {
                register();
                KeyboardUtils.hideSoftInput(RegisterActivity.this);
            }
        });

    }


    @OnClick({R.id.tv_region, R.id.but_verify_code, R.id.but_register, R.id.tv_login,R.id.iv_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                onBackPressed();
                break;

            case R.id.tv_region:
                startActivityForResult(PhoneAreaCodeActivity.newIntent(this), Constants.REQ_ARE_CODE);
                break;

            case R.id.but_verify_code: // 发送验证码
                mAuthPresenter.send(
                        mTvRegion.getText().toString(),
                        mEtMobileNumber.getText().toString(),
                        SendAuthCodePresenter.REGISTER
                );
                break;
            case R.id.iv_scan:
                ScanActivity.startForResult(this, 911);
                break;
        }
    }

    private void register() {
        String password = mEtPassword.getText().toString();
        String verifyPassword = mEtVerifyPassword.getText().toString();
        if (!password.equals(verifyPassword)) {
            AppToast.showLong(getString(R.string.error_passwords_not_same));
            return;
        }

        mPresenter.register(
                mEtCode.getText().toString(),
                mTvRegion.getText().toString(),
                mEtMobileNumber.getText().toString(),
                password,
                mEtInviteCode.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== Constants.REQ_ARE_CODE) {
            // 手机区号
            if (resultCode == Activity.RESULT_OK && data != null) {
                String code = data.getStringExtra(Constants.EXTRA_AREA_CODE);
                mTvRegion.setText(code);
            }
        } else if (requestCode == 911) { //扫描邀请码之后

            if (resultCode == Activity.RESULT_OK && data != null) {
                String code = data.getStringExtra(Constants.EXTRA_AREA_CODE);
                mEtInviteCode.setText(code);
                mEtInviteCode.setSelection(code.length());
            }
        }

    }


    @Override
    public void successful() {
      LocalConfigStore.getInstance().saveUserLoginInfo( mEtCode.getText().toString(),
              mTvRegion.getText().toString(),mEtPassword.getText().toString());
        //startActivity(SupplementInformationActivity.newIntent(this, PERFECT_INFORMATION));
     //   AppToast.showShort(getString(R.string.register_successful));
        startActivity(PersonalInformationActivity.newIntent(this,null,0));
        finish();

    }

}
