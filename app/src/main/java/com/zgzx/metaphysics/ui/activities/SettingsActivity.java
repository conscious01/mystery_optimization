package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.SettingController;
import com.zgzx.metaphysics.model.entity.SwitchStatusEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.dialogs.SimpleDialog;
import com.zgzx.metaphysics.utils.ActivityNavigateManager;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.CacheUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity.ASSETS_PASSWORD;
import static com.zgzx.metaphysics.ui.activities.SafetyVerificationActivity.CREATE_ASSETS_PASSWORD;

/**
 * 设置
 * <p>
 * 审核状态，0-未申请，1-申请资质审核中，2-资质审核通过，3-资质审核失败，4-申请注销审核中，5-申请注销通过，6-申请注销失败
 */
public class SettingsActivity extends BaseActivity implements SettingController.View {

    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.switch_fortune_warning)
    Switch switchFortuneWarning;
    @BindView(R.id.switch_recommend_msg)
    Switch switchRecommendMsg;
    @BindView(R.id.layout_language)
    LinearLayout layoutLanguage;
    @BindView(R.id.layout_account_security)
    RelativeLayout layoutAccountSecurity;
    @BindView(R.id.layout_cache)
    LinearLayout layoutCache;
    @BindView(R.id.setting_suggestion_feedback_layout)
    LinearLayout settingSuggestionFeedbackLayout;
    @BindView(R.id.layout_about_us)
    LinearLayout layoutAboutUs;
    @BindView(R.id.layout_apply_master)
    LinearLayout layoutApplyMaster;

    public static Intent newIntent(Context context, UserDetailEntity entity) {
        return new Intent(context, SettingsActivity.class)
                .putExtra("USER_DETAIL", entity);
    }


    @BindView(R.id.tv_language)
    TextView mTvLanguage;
    @BindView(R.id.tv_apply_status)
    TextView mTvApplyStatus;
    @BindView(R.id.tv_cache)
    TextView tv_cache;
    @BindView(R.id.but_quit)
    TextView but_quit;

    private UserDetailEntity mUserDetail;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_settings;
    }


    SettingController.SwitchPresenter mPresenter;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, R.string.settings);

        // 数据
//        mUserDetail = getIntent().getParcelableExtra("USER_DETAIL");
        mUserDetail = LocalConfigStore.getInstance().getUser();
        // 语言
        String language = LocalConfigStore.getInstance().getAcceptLanguage();
        mTvLanguage.setText("zh-CN".equals(language) ? R.string.simplified_chinese :
                R.string.traditional_chinese);
        try {
            tv_cache.setText(CacheUtil.getInstance().getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (LocalConfigStore.getInstance().isLogin()) {
            but_quit.setVisibility(View.VISIBLE);


            mPresenter = new SettingController.SwitchPresenter();
            mPresenter.setModelAndView(this);
            getLifecycle().addObserver(mPresenter);
            mPresenter.getSwitchStatus();




        } else {

//        if (!LocalConfigStore.getInstance().isLogin()) {
            but_quit.setVisibility(View.GONE);

        }


        //   }
    }

    private void initSwitchesListener() {
        switchFortuneWarning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPresenter.updateSwitchFortune(1);

                } else {
                    mPresenter.updateSwitchFortune(2);

                }
            }
        });

        switchRecommendMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPresenter.updateSwitchMsg(1);

                } else {
                    mPresenter.updateSwitchMsg(2);

                }
            }
        });
    }


    @OnClick(value = {R.id.layout_account_security, R.id.layout_apply_master, R.id.but_quit,
            R.id.layout_about_us, R.id.layout_language, R.id.layout_cache,R.id.setting_suggestion_feedback_layout})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.layout_about_us: // 关于我们
                startActivity(AboutUsActivity.class);
                break;

            case R.id.layout_account_security: // 安全验证
                if (mUserDetail != null && mUserDetail.isHas_paypass()) {
                    startActivity(AccountSecurityActivity.newIntent(this, ASSETS_PASSWORD));
                } else {
                    startActivity(AccountSecurityActivity.newIntent(this, CREATE_ASSETS_PASSWORD));
                }
                break;

            case R.id.layout_apply_master: // 申请师傅
                startActivity(new Intent(this, ApplyMasterActivity.class));
                break;

            case R.id.but_quit: // 退出
                exit();
                break;

            case R.id.layout_language: // 语言设置
                startActivity(LanguageSettingsActivity.class);
                break;

            case R.id.layout_cache:
                SimpleDialog dialog = new SimpleDialog(this);
                dialog.setMessage(R.string.dialog_confirm_clear);
                dialog.setNegative(R.string.cancel, view -> dialog.dismiss());
                dialog.setPositive(R.string.confirm, view -> {
                    CacheUtil.getInstance().clearAllCache(this);
                    dialog.dismiss();
                    try {
                        tv_cache.setText(CacheUtil.getInstance().getTotalCacheSize(this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                new XPopup.Builder(this)
                        .isDestroyOnDismiss(true)
                        .enableShowWhenAppBackground(false)
                        .asCustom(dialog)
                        .show();
                break;
            case R.id.setting_suggestion_feedback_layout:

                startActivity(new Intent(this, FeedBackActivity.class));
                break;
        }
    }

    private void exit() {
        SimpleDialog.show(this,
                0,
                R.string.exit_login,
                R.string.confirm, v -> {
                    LocalConfigStore.getInstance().logout();
                    ActivityNavigateManager.navigate(this, LoginActivity.class);

                }, R.string.cancel, v -> ((BasePopupView) v).dismiss());
    }

    @Override
    public void onGetSwtichStatus(SwitchStatusEntity data) {

        switchFortuneWarning.setChecked(data.getFortune_switch() == 1);
        switchRecommendMsg.setChecked(data.getMsg_switch() == 1);
        initSwitchesListener();

    }

    @Override
    public void onSwtichFortuneUpdate() {

    }

    @Override
    public void onSwtichMsgUpdate() {

    }

    @Override
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
