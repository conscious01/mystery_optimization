package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.MyInviteController;
import com.zgzx.metaphysics.controller.presenters.InvitationSummaryPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.model.entity.InvitationSummaryEntity;
import com.zgzx.metaphysics.model.entity.InviteListEntity;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.ui.adapters.MyInviteAdapter;
import com.zgzx.metaphysics.ui.adapters.SimpleFragmentStatePagerAdapter;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.fragments.InvitationRecordFragment;
import com.zgzx.metaphysics.ui.fragments.RechargeRecordFragment;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.NumberUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的邀请
 */
public class MyInviteActivity extends BaseRequestActivity implements ICallbackResult<InvitationSummaryEntity> {

    private static final String EXT_ROLE = "ROLE";

    public static Intent newIntent(Context context, int role) {
        return new Intent(context, MyInviteActivity.class)
                .putExtra(EXT_ROLE, role);
    }

    @BindView(R.id.tv_today_deposit) TextView mTvTodayDeposit;
    @BindView(R.id.tv_cumulative_deposit) TextView mTvCumulativeDeposit;
    @BindView(R.id.iv_copy_uid) ImageView mIvCopyUid;
    @BindView(R.id.iv_copy_invite_uid) ImageView mIvCopyInviteUid;
    @BindView(R.id.tv_uid) TextView mTvUid;
    @BindView(R.id.tv_invite_uid) TextView mTvInviteUid;
    @BindView(R.id.tv_invite_number) TextView mTvInviteNumber;
    @BindView(R.id.tv_cumulative_invitation) TextView mTvCumulativeInvitation;
    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.layout_deposit) View mLayoutDeposit;
    @BindView(R.id.layout_user_id) View mLayoutUserId;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_visit;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 参数
        int role = getIntent().getIntExtra(EXT_ROLE, -1);
        // 合伙人, 大股东
        if (role == WebApiConstants.PARTNER_ROLE || role == WebApiConstants.SUPER_SHAREHOLDER_ROLE) {
            mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setAdapter(new SimpleFragmentStatePagerAdapter(getSupportFragmentManager())
                    .add(RechargeRecordFragment.instance(role), getString(R.string.recharge_record))
                    .add(InvitationRecordFragment.instance(role), getString(R.string.invitation_record)));
            // 标题
            ActivityTitleHelper.setTitle(this, getString(R.string.my_record));

        } else if (role == WebApiConstants.GENERAL_ROLE){ // 普通用户
            mTabLayout.setVisibility(View.GONE);
            mLayoutDeposit.setVisibility(View.GONE);
            mViewPager.setAdapter(new SimpleFragmentStatePagerAdapter(getSupportFragmentManager())
                    .add(InvitationRecordFragment.instance(role), getString(R.string.invitation_record)));
            // 标题
            ActivityTitleHelper.setTitle(this, getString(R.string.my_invitation));
        }

        // 大股东不显示用户ID
        if (role == WebApiConstants.SUPER_SHAREHOLDER_ROLE){
            mLayoutUserId.setVisibility(View.GONE);
        }

        // 请求
        InvitationSummaryPresenter presenter = new InvitationSummaryPresenter();
        presenter.setModelAndView(this);
        getLifecycle().addObserver(presenter);
    }

    @Override
    public void successful(InvitationSummaryEntity result) {
        mTvUid.setText(LocalConfigStore.getInstance().getUserId()); // 自己的uid
        mTvInviteUid.setText(String.valueOf(result.getInviter_id())); // 邀请人uid
        mTvInviteNumber.setText(String.valueOf(result.getToday_count())); // 今日邀请
        mTvCumulativeInvitation.setText(String.valueOf(result.getTotal_count())); // 累计邀请
        mTvCumulativeDeposit.setText(NumberUtil.format(result.getTotal_top_up())); // 累计充值
        mTvTodayDeposit.setText(NumberUtil.format(result.getToday_top_up())); // 今日充值

        // 用户id
        mIvCopyUid.setOnClickListener(v -> {
            AndroidUtil.copy(mTvUid);
            AppToast.showShort(getString(R.string.successful));
        });

        // 邀请id
        mIvCopyInviteUid.setOnClickListener(v -> {
            AndroidUtil.copy(mTvInviteUid);
            AppToast.showShort(getString(R.string.successful));
        });
    }

//    @Override
//    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//        mPresenter.requestLoadMore();
//    }
//
//    @Override
//    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//        mPresenter.requestRefresh();
//    }
//
//    @Override
//    public void renderInviteInfo(int inviterId, int todayCount, int totalCount) {
//        mTvInviteUid.setText(String.valueOf(inviterId)); // 邀请人uid
//        mTvInviteNumber.setText(String.valueOf(todayCount)); // 今日邀请
//        mTvUid.setText(LocalConfigStore.getInstance().getUserId()); // 自己的uid
//        mTvCumulativeInvitation.setText(String.valueOf(totalCount)); // 累计邀请
//
//        // 用户id
//        mIvCopyUid.setOnClickListener(v -> {
//            AndroidUtil.copy(mTvUid);
//            AppToast.showShort(getString(R.string.successful));
//        });
//
//        // 邀请id
//        mIvCopyInviteUid.setOnClickListener(v -> {
//            AndroidUtil.copy(mTvInviteUid);
//            AppToast.showShort(getString(R.string.successful));
//        });
//    }

}
