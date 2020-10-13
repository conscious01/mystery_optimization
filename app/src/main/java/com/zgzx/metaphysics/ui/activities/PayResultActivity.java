package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.PayResultEntity;
import com.zgzx.metaphysics.model.event.RefreshOrderEvent;
import com.zgzx.metaphysics.model.event.RefreshUseINFOEvent;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.image.GlideApp;

import org.greenrobot.eventbus.EventBus;

import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayResultActivity extends BaseActivity {

    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_pay_result)
    TextView tvPayResult;
    @BindView(R.id.tv_paid_money)
    TextView tvPaidMoney;
    @BindView(R.id.cardview)
    CardView cardview;


    public static void start(Context context, PayResultEntity data) {
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra(Constants.EXT_TYPE, data);
        context.startActivity(intent);

    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_pay_result;
    }

    PayResultEntity mPayResult;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.pay_result);
        mPayResult = (PayResultEntity) getIntent().getSerializableExtra(Constants.EXT_TYPE);
        tvPayResult.setText(mPayResult.getPayResultString());
        tvPaidMoney.setText(mPayResult.getPaidMoenyString());
        if (mPayResult.isPayResultBoolean()) {
            GlideApp.with(iv).load(R.drawable.pay_success).into(iv);
        } else {
            GlideApp.with(iv).load(R.drawable.icon_failed).into(iv);
            tvPayResult.setTextColor(Color.parseColor("#B26868"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.cardview)
    public void onViewClicked() {
        EventBus.getDefault().post(new RefreshUseINFOEvent());
        if (mPayResult.getCls() != null) {
            //问答支付成功去我的问答页面，待解答页面，问答支付失败去我的问答待付款页面
            if (mPayResult.isPayResultBoolean()) {
                EventBus.getDefault().post(new RefreshOrderEvent());
                startActivity(new Intent(PayResultActivity.this, mPayResult.getCls()).putExtra(Constants.KEY, 1));
            } else {
                EventBus.getDefault().post(new RefreshOrderEvent());
                startActivity(new Intent(PayResultActivity.this, mPayResult.getCls()).putExtra(Constants.KEY, 0));
            }
        }

        finish();
    }
}
