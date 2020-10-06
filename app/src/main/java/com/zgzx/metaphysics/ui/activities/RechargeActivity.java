package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.DepositController;
import com.zgzx.metaphysics.controller.views.impl.LayoutRequestStatusView;
import com.zgzx.metaphysics.model.entity.RechargeActivitiesEntity;
import com.zgzx.metaphysics.ui.adapters.RechargeActivityAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.NumberUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值
 */
public class RechargeActivity extends BaseRequestActivity implements DepositController.View, BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.total_kmz)
    TextView total_kmz;
    @BindView(R.id.recycle_rechage)
    RecyclerView recycle_rechage;
    @BindView(R.id.recharge_btn)
    Button recharge_btn;

    private RechargeActivityAdapter rechargeActivityAdapter;
    private float amount;
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_recharge_kmz_layout;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.recharg_kmz);

        mStateView = new LayoutRequestStatusView(findViewById(R.id.state_layout));

        DepositController.Presenter presenter = new DepositController.Presenter();
        presenter.setModelAndView(this);
        getLifecycle().addObserver(presenter);
    }

    @OnClick(value = {R.id.recharge_btn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.recharge_btn:
                startActivity(SelectePayActivity.newIntent(this,amount,-1));
                break;
        }

    }

    @Override
    public void renderBlance(float amount) {
        total_kmz.setText(NumberUtil.format(amount));

    }

    @Override
    public void renderServices(List<RechargeActivitiesEntity> entities) {
        rechargeActivityAdapter=new RechargeActivityAdapter(entities);
        recycle_rechage.setAdapter(rechargeActivityAdapter);
        rechargeActivityAdapter.setOnItemClickListener(this);
        rechargeActivityAdapter.selected(0);
        recharge_btn.setText(String.format(getResources().getString(R.string.go_pay), NumberUtil.format(entities.get(0).getPrice())));
        amount=entities.get(0).getPrice();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RechargeActivitiesEntity item=rechargeActivityAdapter.getItem(position);
        recharge_btn.setText(String.format(getResources().getString(R.string.go_pay), NumberUtil.format(item.getPrice())));
        rechargeActivityAdapter.selected(position);
        amount=item.getPrice();
    }
}