package com.zgzx.metaphysics.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.LoginPresenter;
import com.zgzx.metaphysics.controller.presenters.VersionUpdatePresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.event.LoginSuccessEvent;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.VersionUpdateDialog;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.filters.ChineseInputFilter;
import com.zgzx.metaphysics.utils.watchers.EmptyRule;
import com.zgzx.metaphysics.utils.watchers.TextWatchers;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseRequestActivity implements ISingleRequestView<UserDetailEntity> {

    @BindView(R.id.but_login) Button mButLogin;
    @BindView(R.id.tv_region) TextView mTvRegion;
    @BindView(R.id.et_password) EditText mEtPassword;
    @BindView(R.id.check_password) CheckBox mCheckPassword;
    @BindView(R.id.et_mobile_number) EditText mEtMobileNumber;
    @BindView(R.id.tv_title) TextView tv_title;
    private LoginPresenter mPresenter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_login;
    }

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, LoginActivity.class)
                .putExtra(Constants.TYPE, type);
    }

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        mPresenter = new LoginPresenter();
        mPresenter.setModelAndView(this);

        tv_title.setText(getResources().getString(R.string.login));
        mEtMobileNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // 登录
        AndroidUtil.passwordVisibility(mCheckPassword, mEtPassword);
        TextWatchers.add(mButLogin, new EmptyRule(mEtMobileNumber, mEtPassword));
        mEtPassword.setFilters(new InputFilter[]{new ChineseInputFilter()});

        // 版本更新
        VersionUpdatePresenter presenter = new VersionUpdatePresenter();
        presenter.setModelAndView(new VersionUpdateDialog(this));
        getLifecycle().addObserver(presenter);
        presenter.checkVersion();

//        startActivity(new Intent(this, PreviewActivity.class));

    }



    @OnClick(value = {R.id.tv_region, R.id.tv_register, R.id.tv_forget_password, R.id.but_login})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.but_login: // 登录
                KeyboardUtils.hideSoftInput(this);

                mPresenter.login(
                        mTvRegion.getText().toString(),
                        mEtMobileNumber.getText().toString(),
                        mEtPassword.getText().toString());
                break;

            case R.id.tv_region: // 手机区号
                startActivityForResult(PhoneAreaCodeActivity.newIntent(this), Constants.REQ_ARE_CODE);
                break;

            case R.id.tv_register: // 注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.tv_forget_password: // 忘记密码
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
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
    public void result(UserDetailEntity response) {
        LocalConfigStore.getInstance().saveUserInfo(response);
        LocalConfigStore.getInstance().saveUserLoginInfo(mEtMobileNumber.getText().toString(),mTvRegion.getText().toString(),
                mEtPassword.getText().toString());
        EventBus.getDefault().post(new LoginSuccessEvent());

       startActivityAndFinish(new Intent(this, MainActivity.class));
         if (getIntent()!=null){
             if (getIntent().getIntExtra(Constants.TYPE,-1)==1){
                 finish(Activity.RESULT_OK);
             }
         }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
