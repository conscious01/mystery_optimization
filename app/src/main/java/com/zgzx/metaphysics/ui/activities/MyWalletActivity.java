package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.TransUserInfoController;
import com.zgzx.metaphysics.controller.UserController;
import com.zgzx.metaphysics.controller.presenters.PropertyBillPresenter;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.model.entity.UserAssetEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.ui.adapters.PropertyBillAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.MathUtil;

import and.fast.statelayout.StateLayout;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseRequestActivity implements OnRefreshLoadMoreListener, TransUserInfoController.View {

    private final static String EXT_BALANCE = "BALANCE";
    private final static String EXT_TYPE = "TYPE";
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.tv_balance_kmq)
    TextView tvBalanceKmq;
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;
    @BindView(R.id.tv_translate)
    TextView tvTranslate;
    @BindView(R.id.layout_property_bill)
    LinearLayout layoutPropertyBill;
    @BindView(R.id.state_layout)
    StateLayout stateLayout;

    private PropertyBillPresenter mPresenter;

    public static Intent newIntent(Context context, float balance, int type) {
        return new Intent(context, MyWalletActivity.class)
                .putExtra(EXT_BALANCE, balance).putExtra(EXT_TYPE, type);
    }

    public static Intent newIntent(Context context, UserAssetEntity assetEntity) {
        return new Intent(context, MyWalletActivity.class)
                .putExtra("key", assetEntity);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestRefresh();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_my_wallet;
    }

    UserAssetEntity assetEntity;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.my_balance, R.string.translate);
        assetEntity = (UserAssetEntity) getIntent().getSerializableExtra("key");
        // 余额
        mTvBalance.setText(MathUtil.round(assetEntity.getCoin_available(),2) + "");
        tvBalanceKmq.setText((int) assetEntity.getCoin_freeze() + getString(R.string.zhang));

        DividerItemDecoration divider = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        mRecyclerView.addItemDecoration(divider);
        // 列表
        mRecyclerView.setAdapter(new PropertyBillAdapter());
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        // ·
        mPresenter = new PropertyBillPresenter();
        mPresenter.setModelAndView(new PaginationView<>(mRecyclerView, mSmartRefreshLayout, this));
        getLifecycle().addObserver(mPresenter);
        TransUserInfoController.Presenter presenter = new TransUserInfoController.Presenter();
        presenter.setModelAndView(MyWalletActivity.this);
        getLifecycle().addObserver(presenter);


    }

    @OnClick(value = {R.id.layout_property_bill, R.id.tv_action, R.id.tv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_property_bill: // 全部记录
                //startActivity(new Intent(view.getContext(), PropertyBillActivity.class));
                break;

            case R.id.tv_action:
                startActivity(TransferActivity.newIntent(this,
                        Float.valueOf(mTvBalance.getText().toString())));
                break;
            case R.id.tv_recharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void renderUserDetail(UserDetailEntity entity) {
        if (entity.getVip_info().getId() > 0) {
            tvAction.setVisibility(View.VISIBLE);
            tvAction.setEnabled(true);
        } else {
            tvAction.setVisibility(View.GONE);

        }

    }




}
