package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.PasswordPresenter;
import com.zgzx.metaphysics.controller.presenters.SendAuthCodePresenter;
import com.zgzx.metaphysics.controller.views.IPasswordView;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityNavigateManager;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.watchers.EmptyRule;
import com.zgzx.metaphysics.utils.watchers.TextWatchers;
import com.zgzx.metaphysics.widgets.HorizontalSmoothScrollerLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 安全验证
 * <p>
 * 修改登录密码，修改资金密码
 */
public class SafetyVerificationActivity extends BaseRequestActivity implements IPasswordView {

    public static final int LOGIN_PASSWORD = 1, // 登录密码
            CREATE_ASSETS_PASSWORD = 2, // 创建资产密码
            ASSETS_PASSWORD = 3; // 修改资产密码

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, SafetyVerificationActivity.class)
                .putExtra("TYPE", type);
    }


    @BindView(R.id.but_next)
    Button mButNext;
    @BindView(R.id.but_complete)
    Button mButComplete;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.check_password)
    CheckBox mCheckPassword;
    @BindView(R.id.et_verify_password)
    EditText mEtVerifyPassword;
    @BindView(R.id.check_verify_password)
    CheckBox mCheckVerifyPassword;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.ll_code)
    LinearLayout ll_code;

    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_mobile_number)
    TextView mTvMobileNumber;
    @BindView(R.id.layout_alter_password)
    LinearLayout layoutAlterPassword;

    @BindView(R.id.smooth_scroller_layout)
    HorizontalSmoothScrollerLayout mSmoothScrollerLayout;

    private int mPageType;

    private SendAuthCodePresenter mSendPresenter;
    private PasswordPresenter mPresenter;

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_safety_verification;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.safety_verification);

        mPageType = getIntent().getIntExtra("TYPE", -1);

        // 获取用户手机号
        mTvMobileNumber.setText(LocalConfigStore.getInstance().getMobile());

        // 发送验证码
        mSendPresenter = SendAuthCodePresenter.newAndBind(mTvCode, getLifecycle());

        // 点击状态
        TextWatchers.add(mButNext, new EmptyRule(mEtCode));
        TextWatchers.add(mButComplete, new EmptyRule(mEtPassword, mEtVerifyPassword));

        // 密码可见性切换
        AndroidUtil.passwordVisibility(mCheckPassword, mEtPassword);
        AndroidUtil.passwordVisibility(mCheckVerifyPassword, mEtVerifyPassword);

        // 请求
        mPresenter = new PasswordPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
    }


    @OnClick({R.id.ll_code, R.id.but_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_code:
                mSendPresenter.send(mTvMobileNumber.getText().toString(),
                        mPageType == LOGIN_PASSWORD ? SendAuthCodePresenter.ALTER_LOGIN_PASSWORD
                                : SendAuthCodePresenter.ALTER_PAY_PASSWORD);
                break;

            case R.id.but_next:
                mPresenter.verify(
                        null,
                        mTvMobileNumber.getText().toString(),
                        mEtCode.getText().toString()
                );
                break;
        }
    }

    @Override
    public void verifySucceeded() {
        KeyboardUtils.hideSoftInput(this);
        mSmoothScrollerLayout.next();

        if (mPageType == LOGIN_PASSWORD) {
            ActivityTitleHelper.setTitle(layoutAlterPassword, R.string.alter_login_password);

        } else if (mPageType == ASSETS_PASSWORD) {
            mEtPassword.setHint(R.string.hint_assets_password);
            ActivityTitleHelper.setTitle(layoutAlterPassword, R.string.alter_assets_password);

        } else if (mPageType == CREATE_ASSETS_PASSWORD) {
            mEtPassword.setHint(R.string.hint_assets_password);
            ActivityTitleHelper.setTitle(layoutAlterPassword, R.string.create_assets_password);
        }

        // 修改密码
        mButComplete.setOnClickListener(v -> {
            String password = mEtPassword.getText().toString();
            String verifyPassword = mEtVerifyPassword.getText().toString();

            if (password.equals(verifyPassword)) {
                if (mPageType == LOGIN_PASSWORD) {
                    mPresenter.alterLoginPassword(password);

                } else if (mPageType == ASSETS_PASSWORD || mPageType == CREATE_ASSETS_PASSWORD) {
                    mPresenter.alterPayPassword(password);
                }

            } else {
                AppToast.showLong(getString(R.string.error_passwords_not_same));
            }

        });
    }

    @Override
    public void successful() {
        if (mPageType == LOGIN_PASSWORD) {
            AppToast.showShort(getString(R.string.reset_password_successful));
            ActivityNavigateManager.navigate(this, LoginActivity.class);

        } else {
            //已创建资金密码，修改本地数据先
            LocalConfigStore.getInstance().getUser().setHas_paypass(true);
            onBackPressed();
            AppToast.showShort(getString(R.string.successful));
        }
    }

}
