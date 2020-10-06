package com.zgzx.metaphysics.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.UserController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.model.entity.AdEntity;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.model.entity.UserAssetEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.event.LoginSuccessEvent;
import com.zgzx.metaphysics.model.event.RefreshUseINFOEvent;
import com.zgzx.metaphysics.ui.activities.InviteActivity;
import com.zgzx.metaphysics.ui.activities.LoginActivity;
import com.zgzx.metaphysics.ui.activities.MasterHomepageActivity;
import com.zgzx.metaphysics.ui.activities.MyMessageActivity;
import com.zgzx.metaphysics.ui.activities.MyOrderActivity;
import com.zgzx.metaphysics.ui.activities.MyWalletActivity;
import com.zgzx.metaphysics.ui.activities.PersonalInformationActivity;
import com.zgzx.metaphysics.ui.activities.SettingsActivity;
import com.zgzx.metaphysics.ui.activities.WebViewActivity;
import com.zgzx.metaphysics.ui.activities.WebViewVipCenter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.ui.dialogs.SimpleDialog;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.MathUtil;
import com.zgzx.metaphysics.utils.image.GlideApp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MineFragment extends BaseRequestFragment implements UserController.View {

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.master_homepage_layout)
    LinearLayout mMasterHomepage;
    @BindView(R.id.tv_master_name)
    TextView tvMasterName;
    @BindView(R.id.tv_companion)
    TextView tvCompanion;
    @BindView(R.id.iv_label_bg)
    ImageView ivLabelBg;
    @BindView(R.id.iv_super_tv)
    TextView iv_super_tv;
    @BindView(R.id.layout_label_bg)
    FrameLayout layoutLabelBg;
    @BindView(R.id.iv_master_mark)
    ImageView ivMasterMark;
    @BindView(R.id.vip_type_img)
    ImageView vip_type_img;
    @BindView(R.id.tv_vip_type)
    TextView tv_vip_type;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.view_now_btn)
    Button view_now_btn;
    @BindView(R.id.vip_layout)
    LinearLayout vip_layout;
    @BindView(R.id.tv_km_moeny)
    TextView tvKmMoeny;
    @BindView(R.id.tv_kmq_money)
    TextView tvKmqMoney;
    @BindView(R.id.tv_invitation)
    TextView tv_invitation;
    @BindView(R.id.tv_invite_number)
    TextView mTvInviteNumber;
    @BindView(R.id.layout_invite)
    LinearLayout layout_invite;
    @BindView(R.id.but_invite)
    Button but_invite;
    @BindView(R.id.layout_share)
    LinearLayout layout_share;
    @BindView(R.id.tv_my_ask)
    TextView tv_my_ask;
    @BindView(R.id.layout_my_ask)
    LinearLayout layoutMyAsk;
    @BindView(R.id.layout_msg)
    LinearLayout layoutMsg;
    @BindView(R.id.layout_setting)
    LinearLayout layoutSetting;
    @BindView(R.id.layout_container)
    LinearLayout layoutContainer;
    @BindView(R.id.tv_uid)
    TextView mTvUid;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.ll_tags)
    LinearLayout llTags;
    @BindView(R.id.ll_user_info)
    LinearLayout llUserInfo;


    private float balance;
    private String token;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected IStatusView createRequestView() {
        return new ToastRequestStatusView(getContext());
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogged(LoginSuccessEvent event) {
        setUserInfo();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshUserINFO(RefreshUseINFOEvent event) {
        setUserInfo();

    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        AndroidUtil.addStatusBarHeightPadding(findViewById(R.id.layout_container));

        setUserInfo();


    }

    private void setUserInfo() {
        mTvUid.setText("UID:" + LocalConfigStore.getInstance().getUserId());
        token = LocalConfigStore.getInstance().getUserToken();
        if (LocalConfigStore.getInstance().isLogin()) {
            // 逻辑
            UserController.Presenter presenter = new UserController.Presenter();
            presenter.setModelAndView(this);
            getLifecycle().addObserver(presenter);
        }
        if (LocalConfigStore.getInstance().ifMaster()) {
            tv_my_ask.setText(getResources().getString(R.string.my_answer));
        } else {
            tv_my_ask.setText(getResources().getString(R.string.my_questions_answer));
        }

        if (LocalConfigStore.getInstance().isLogin()) {
            vip_layout.setVisibility(View.VISIBLE);
        } else {
            vip_layout.setVisibility(View.INVISIBLE);
        }

        if (LocalConfigStore.getInstance().ifMaster()) {
            mMasterHomepage.setVisibility(View.VISIBLE);
        } else {
            mMasterHomepage.setVisibility(View.GONE);
        }
//        view_now_btn.setText(getString(R.string.view_now));

    }

    @OnClick({R.id.layout_setting, R.id.iv_avatar, R.id.master_homepage_layout,
            R.id.layout_msg,
            R.id.tv_uid,
            R.id.layout_my_ask, R.id.ll_user_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
/*                if (result.isHas_completed_info()) {
                    startActivity(PersonalInformationActivity.newIntent(getContext(), result,1));
                } else { // 完善用户信息
                    showPerfectInformationDialog();
                }*/
                break;

            case R.id.layout_setting:
                if (LocalConfigStore.getInstance().isLogin()) {
                    startActivity(new Intent(view.getContext(), SettingsActivity.class));
                } else {
                    startActivity(LoginActivity.class);
                }
                break;

            case R.id.master_homepage_layout:
                startActivity(MasterHomepageActivity.newIntent(getContext(),0,LocalConfigStore.getInstance().getUser().getUid()));
//                  startActivity(new Intent(view.getContext(), EditMasterHomepageActivity.class));
                break;
            case R.id.layout_my_ask://我的问答
                if (LocalConfigStore.getInstance().isLogin()) {
                    startActivity(new Intent(view.getContext(), MyOrderActivity.class));
                } else {
                    startActivity(LoginActivity.class);
                }

                break;

//            case R.id.tv_uid:
//                AndroidUtil.copy(view.getContext(), LocalConfigStore.getInstance().getUserId());
//                AppToast.showShort(getString(R.string.successful));
//                break;

            case R.id.layout_msg:
                if (LocalConfigStore.getInstance().isLogin()) {
                    startActivity(new Intent(getContext(), MyMessageActivity.class));
                } else {
                    startActivity(LoginActivity.class);
                }

                break;
            case R.id.ll_user_info:
                if (!LocalConfigStore.getInstance().isLogin()) {
                    startActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    public void renderUserDetail(UserDetailEntity result) {
        LocalConfigStore.getInstance().saveUserInfo(result);

        mTvNickname.setText(result.getNickname());
        mTvInviteNumber.setText(result.getInvite_count() + "");

        GlideApp.with(mIvAvatar)
                .load(result.getAvatar())
                .avatar()
                .into(mIvAvatar);

        // 显示师傅
        if (LocalConfigStore.getInstance().ifMaster()) {
            mTvNickname.setGravity(Gravity.TOP);
            tv_my_ask.setText(getResources().getString(R.string.my_answer));
            //      mIvEnterMaster.setVisibility(View.VISIBLE);
            mMasterHomepage.setVisibility(View.VISIBLE);
            tvMasterName.setText(getString(R.string.nav_master));
            tvMasterName.setVisibility(View.GONE);
            llTags.setVisibility(View.VISIBLE);
        }
        switch (result.getRole()) {
            case 4://大股东（超级股东）
                setPartnerText(tv_invitation, getText(R.string.my_record), iv_super_tv,
                        getResources().getString(R.string.super_shareholder),
                        R.drawable.ic_but_bg_accent);
                tvCompanion.setText(getString(R.string.super_shareholder));
                tvCompanion.setVisibility(View.VISIBLE);
                llTags.setVisibility(View.VISIBLE);

                break;
            case 5://小股东（超级合伙人）
                setPartnerText(tv_invitation, getText(R.string.my_record), iv_super_tv,
                        getResources().getString(R.string.super_partner), R.drawable.ic_but_bg);
                tvCompanion.setText(getString(R.string.super_partner));
                tvCompanion.setVisibility(View.VISIBLE);

                llTags.setVisibility(View.VISIBLE);

                break;
        }
        String endtime = DateUtils.getTime(result.getVip_info().getEnd_time(), DateUtils.PATTERN_2);
        switch (result.getVip_info().getId()) {
            case 0://非会员
                setNotVip();
                break;
            case 1://青铜
                coinVip(R.drawable.icon_m5, R.string.qt_hy,
                        R.drawable.icon_bronze_type_1, R.drawable.icon_bronze_type_bg_1,
                        result.getVip_info().getId(), endtime);
                break;
            case 2://白银
                coinVip(R.drawable.icon_m1, R.string.by_hy,
                        R.drawable.icon_silver_type_1, R.drawable.icon_silver_type_bg_1,
                        result.getVip_info().getId(), endtime);
                break;
            case 3://黄金
                coinVip(R.drawable.icon_m4, R.string.hj_hy,
                        R.drawable.icon_gold_type_1, R.drawable.icon_gold_type_bg_1,
                        result.getVip_info().getId(), endtime);
                break;
            case 4://钻石
                coinVip(R.drawable.icon_m2, R.string.zs_hy,
                        R.drawable.icon_diamonds_type_1, R.drawable.icon_diamonds_type_bg_1,
                        result.getVip_info().getId(), endtime);
                break;
        }
        // 个人设置页面
        mIvAvatar.setOnClickListener(v -> {
            if (LocalConfigStore.getInstance().isLogin()) {
                if (result.isHas_completed_info()) {
                    startActivity(PersonalInformationActivity.newIntent(getContext(), result, 1));
                } else { // 完善用户信息
                    showPerfectInformationDialog();
                }
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        // 设置页面
//        mIvSettings.setOnClickListener(v ->
//                startActivity(SettingsActivity.newIntent(getContext(), result)));

        // 邀请
        layout_share.setOnClickListener(v -> {
                    if (LocalConfigStore.getInstance().isLogin()) {
                        startActivity(InviteActivity.newIntent(getContext(), result));
                    } else {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }
        );

//        // 我的邀请
//        layout_invite.setOnClickListener(v ->
//                startActivity(MyInviteActivity.newIntent(getContext(), result.getRole())));

        // 我的邀请
        layout_invite.setOnClickListener(v -> {
                    if (LocalConfigStore.getInstance().isLogin()) {
                        String inviteRecoredURL = LocalConfigStore.getInstance().getH5_Base() +
                                "/pages/record/record?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&status_bar_height=" + 24 + "&role=" + result.getRole();
                        startActivity(WebViewActivity.newIntent(getActivity(), inviteRecoredURL));
                    } else {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }
        );

        // 立即开通
        vip_layout.setOnClickListener(view -> {
            if (LocalConfigStore.getInstance().isLogin()) {
                String hyURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                        "/member_center" +
                        "/member?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&status_bar_height=" + 24;
                startActivity(WebViewVipCenter.newIntent(getContext(),1, hyURL));
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    /**
     * 非vip设置
     */
    private void setNotVip() {
        vip_layout.setBackgroundResource(R.drawable.icon_not_open_member);
        tv_vip_type.setText(getText(R.string.vip_tips_txt));
        vip_type_img.setVisibility(View.INVISIBLE);
        view_now_btn.setText(getText(R.string.open_now));
        vip_layout.setOnClickListener(v -> {
            String hyURL = LocalConfigStore.getInstance().getH5_Base() + "/pages/member_center" +
                    "/member?language=" + LocalConfigStore.getInstance().getAcceptLanguage() +
                    "&status_bar_height=" + 24;
            startActivity(WebViewActivity.newIntent(getContext(), hyURL));
        });

//        mIvMasterMark.setVisibility(View.GONE);
    }

    /**
     * 设置合伙人角色
     *
     * @param tv_invitation
     * @param text
     * @param iv_super_tv
     * @param string
     * @param superTextBg
     */
    private void setPartnerText(TextView tv_invitation, CharSequence text, TextView iv_super_tv,
                                CharSequence string, int superTextBg) {
        iv_super_tv.setVisibility(View.VISIBLE);
        tv_invitation.setText(text);
        iv_super_tv.setText(string);
        iv_super_tv.setBackground(getResources().getDrawable(superTextBg));
    }

    /**
     * 设置vip
     *
     * @param vip_layout_bg
     * @param tv_vip_type_txt
     * @param vip_type_img_bg
     * @param type_bg
     */
    private void coinVip(int vip_layout_bg, int tv_vip_type_txt, int vip_type_img_bg, int type_bg
            , int type, String endtime) {
        endtime = getString(R.string.experiod_time) + endtime;
        vip_layout.setBackgroundResource(vip_layout_bg);
        view_now_btn.setText(getText(R.string.view_now));
        tv_vip_type.setVisibility(View.VISIBLE);
        tv_vip_type.setText(getText(tv_vip_type_txt));
        vip_type_img.setVisibility(View.VISIBLE);
        tv_end_time.setText(endtime);
//        vip_type_img.setBackgroundResource(vip_type_img_bg);
//        mIvMasterMark.setVisibility(View.VISIBLE);
//        mIvMasterMark.setBackgroundResource(type_bg);

        switch (type) {
            case 1://青铜
                tv_vip_type.setTextColor(Color.parseColor("#603518"));
                tv_end_time.setTextColor(Color.parseColor("#B87648"));
                view_now_btn.setTextColor(Color.parseColor("#8C5530"));
                view_now_btn.setBackground(getResources().getDrawable(R.drawable.qt_bt_bg));
                break;
            case 2://白银
                tv_vip_type.setTextColor(Color.parseColor("#44566F"));
                tv_end_time.setTextColor(Color.parseColor("#6E82A3"));
                view_now_btn.setTextColor(Color.parseColor("#455471"));
                view_now_btn.setBackground(getResources().getDrawable(R.drawable.by_bt_bg));
                break;
            case 3://黄金
                tv_vip_type.setTextColor(Color.parseColor("#9D651C"));
                tv_end_time.setTextColor(Color.parseColor("#D19931"));
                view_now_btn.setTextColor(Color.parseColor("#FFF9E8"));
                view_now_btn.setBackground(getResources().getDrawable(R.drawable.hj_bt_bg));
                break;
            case 4://钻石
                tv_vip_type.setTextColor(Color.parseColor("#3B3E90"));
                tv_end_time.setTextColor(Color.parseColor("#797BB0"));
                view_now_btn.setTextColor(Color.parseColor("#FFFFFF"));
                view_now_btn.setBackground(getResources().getDrawable(R.drawable.zs_bt_bg));
                tv_end_time.setText(R.string.memeber_for_life);

                break;

        }
    }


    // 提示完善用户信息
    private void showPerfectInformationDialog() {
        SimpleDialog.show(getContext(),
                R.string.please_perfect_information,
                0,
                R.string.perfect_information, v -> {
                    ((SimpleDialog) v).dismiss();
                    startActivity(PersonalInformationActivity.newIntent(getContext(), null,
                            0));
                },
                R.string.later, v -> ((SimpleDialog) v).dismiss()
        );
    }

    @Override
    public void renderAssets(float amount) {
//        mTvBalance.setText(NumberUtil.format(amount));
        balance = amount;
        // 钱包
//        layout_balance.setOnClickListener(v ->
//                startActivity(MyWalletActivity.newIntent(getActivity(), balance, 0)));
    }

    @Override
    public void onUserAssets(UserAssetEntity userAssetEntity) {
        tvKmMoeny.setText(MathUtil.round(userAssetEntity.getCoin_available(),2) + "");
        tvKmqMoney.setText((int) userAssetEntity.getCoin_freeze() + getString(R.string.zhang));
        llDetail.setOnClickListener(v ->
                startActivity(MyWalletActivity.newIntent(getActivity(), userAssetEntity)));
    }

    @Override
    public void onGetH5Base(H5BaseEntity h5BaseEntity) {
        LocalConfigStore.getInstance().setH5Base(h5BaseEntity.getDomain());
    }

    @Override
    public void onGetConfigBase(UrlConfigEntity urlConfigEntity) {
        long time = new Date().getTime() / 1000;//获取系统时间的10位的时间戳
        long diff = urlConfigEntity.getTimestamp() - time;
        LocalConfigStore.getInstance().setConfirgUrl(diff, urlConfigEntity.getAk(), urlConfigEntity.getKey());
    }

    @Override
    public void onGetAdConfig(AdEntity urlConfigEntity) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtil.setLightMode(getActivity());
        }
    }

}
