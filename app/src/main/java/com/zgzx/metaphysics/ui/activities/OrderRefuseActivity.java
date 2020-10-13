package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.OrderController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.model.event.RefreshOrderEvent;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;

import org.greenrobot.eventbus.EventBus;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderRefuseActivity extends BaseRequestActivity implements OrderController.RefuseView {


    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.tv_refused_title)
    TextView tvRefusedTitle;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.et_refused_content)
    EditText etRefusedContent;
    @BindView(R.id.ll_master_answer)
    RelativeLayout llMasterAnswer;
    @BindView(R.id.ll_master_bottom)
    RelativeLayout llMasterBottom;
    @BindView(R.id.tv_done)
    TextView tvDone;


    OrderController.PresenterRefused mPresenter;
    @BindView(R.id.tv_input_number)
    TextView tvInputNumber;
    private String mAskerName;

    @Override
    protected int getContentLayoutId() {
        return R.layout.order_refuse_activity;
    }

    @Override
    protected IStatusView createStatusView() {
        return new ToastRequestStatusView(this);
    }

    public static void start(Context context, OrderResultEntity orderResultEntity,String mAskerName) {
        Intent intent = new Intent(context, OrderRefuseActivity.class);
        intent.putExtra(Constants.EXT_TYPE, orderResultEntity);
        intent.putExtra(Constants.KEY_NAME, mAskerName);

        context.startActivity(intent);

    }

    OrderResultEntity data;
    String mName;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.refused_reason);
        mPresenter = new OrderController.PresenterRefused();
        mPresenter.setModelAndView(this);

        Intent intent = getIntent();
        if (intent != null) {
            data = (OrderResultEntity) intent.getSerializableExtra(Constants.EXT_TYPE);
            mAskerName = intent.getStringExtra(Constants.KEY_NAME);

        }

        tvRefusedTitle.setText(getString(R.string.you_have_refused) + mAskerName + getString(R.string.users_q));

        etRefusedContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvInputNumber.setText(s.toString().length() + "/250");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_done)
    public void onViewClicked() {
        if (etRefusedContent.getText().toString().isEmpty()) {
            ToastUtils.showShort(R.string.pls_enter_reason);
            return;
        }
        Long time = new Date().getTime() / 1000;
        Map<String, Object> map = new HashMap<>();
        map.put("issue_id", data.getId());
        map.put("reason", etRefusedContent.getText().toString());
        map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
        String str = HMacMD5Util.getMapToString(map);
        String sign1 = null;
        try {
            sign1 = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mPresenter.doneAnswer(data.getId(), etRefusedContent.getText().toString(), LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign1);
    }


    @Override
    public void onRefused() {
        ToastUtils.showShort(getResources().getString(R.string.successful));
        EventBus.getDefault().post(new RefreshOrderEvent());
        finish();
    }
}
