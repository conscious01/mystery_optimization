package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zgzx.metaphysics.BuildConfig;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.VersionUpdatePresenter;
import com.zgzx.metaphysics.controller.views.core.CallbackResultDecorator;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.model.entity.VersionUpdateEntity;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.VersionUpdateDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AppToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseRequestActivity implements ICallbackResult<VersionUpdateEntity> {

    @BindView(R.id.view_tips) View mViewTips;
    @BindView(R.id.tv_version_name) TextView mTvVersionName;


    private VersionUpdatePresenter mPresenter;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, R.string.about_us);

        // 版本名称
        mTvVersionName.setText(BuildConfig.VERSION_NAME);

        // 请求
        mPresenter = new VersionUpdatePresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        mPresenter.checkVersion();
    }


    @OnClick(value = {R.id.layout_version_update, R.id.layout_privacy_policy, R.id.layout_user_protocol})
    public void onViewClicked(View v) {
        String language = LocalConfigStore.getInstance().getAcceptLanguage();

        switch (v.getId()) {
            case R.id.layout_version_update: // 版本更新
                versionUpdate();
                break;

            case R.id.layout_privacy_policy: //  隐私协议
                startActivity(WebViewActivity.newIntent(this, Constants.POLICY_PROTOCOL + language));
                break;

            case R.id.layout_user_protocol: // 用户协议
                startActivity(WebViewActivity.newIntent(this, Constants.SERVICE_PROTOCOL + language));
                break;
        }
    }

    private void versionUpdate() {
        mPresenter.setModelAndView(new CallbackResultDecorator<VersionUpdateEntity>(
                new VersionUpdateDialog(this)) {

            @Override
            public void successful(VersionUpdateEntity result) {
                super.successful(result);
                if (result.getVersion_code() <= BuildConfig.VERSION_CODE) {
                    AppToast.showShort(getString(R.string.no_new_version));
                }
            }

        });

        mPresenter.checkVersion();
    }


    @Override
    public void successful(VersionUpdateEntity result) {
        if (result.getVersion_code() > BuildConfig.VERSION_CODE) {
            mViewTips.setVisibility(View.VISIBLE);
        }
    }

}
