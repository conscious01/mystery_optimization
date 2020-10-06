package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.OrderController;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.model.entity.QDetailEntity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.LunarCalendarUtil;
import com.zgzx.metaphysics.utils.StringUtil;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;
import com.zgzx.metaphysics.utils.image.GlideApp;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseRequestActivity implements OrderController.View {

    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_order_status_des)
    TextView tvOrderStatusDes;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.tv_ask_name)
    TextView tvAskName;
    @BindView(R.id.tv_q_name)
    TextView tvQName;
    @BindView(R.id.tv_q_gender)
    TextView tvQGender;
    @BindView(R.id.tv_q_birth_1)
    TextView tvQBirth1;
    @BindView(R.id.tv_q_birth_2)
    TextView tvQBirth2;
    @BindView(R.id.tv_q_birth_location)
    TextView tvQBirthLocation;
    @BindView(R.id.tv_q_content)
    TextView tvQContent;
    @BindView(R.id.rv_q)
    RecyclerView rvQ;
    @BindView(R.id.tv_master_name)
    TextView tvMasterName;
    @BindView(R.id.tv_answer_content)
    TextView tvAnswerContent;
    @BindView(R.id.tv_go2_pay)
    TextView tvGo2Pay;
    @BindView(R.id.tv_go_2_comment)
    TextView tvGo2Comment;
    @BindView(R.id.ll_user_bottom)
    RelativeLayout llUserBottom;
    @BindView(R.id.et_answer_content)
    EditText etAnswerContent;
    @BindView(R.id.ll_master_answer)
    LinearLayout llMasterAnswer;
    @BindView(R.id.tv_refuse_answer)
    TextView tvRefuseAnswer;
    @BindView(R.id.tv_done_answer)
    TextView tvDoneAnswer;
    @BindView(R.id.ll_master_buttons)
    LinearLayout llMasterButtons;
    @BindView(R.id.ll_master_bottom)
    RelativeLayout llMasterBottom;

    @Override
    protected int getContentLayoutId() {
        return R.layout.order_detail_activity;
    }


    OrderController.Presenter mPresenter;

    public static void start(Context context, OrderResultEntity orderResultEntity) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(Constants.EXT_TYPE, orderResultEntity);
        context.startActivity(intent);

    }

    public static void start(Context context, int orderID) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(Constants.KEY, orderID);
        context.startActivity(intent);
    }

    OrderResultEntity data;
    int mOrderId;
    String mAskerName;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            data = (OrderResultEntity) intent.getSerializableExtra(Constants.EXT_TYPE);
            mOrderId = intent.getIntExtra(Constants.KEY, 0);

        }
        ActivityTitleHelper.setTitle(this, R.string.order_detail);
        mPresenter = new OrderController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        int orderID;
        if (data != null) {
            orderID = data.getId();
        } else {
            orderID = mOrderId;
        }
        mPresenter.getOrderDetail(orderID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_go2_pay, R.id.tv_go_2_comment, R.id.tv_refuse_answer, R.id.tv_done_answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go2_pay:
                OrderPayActivity.start(this, data, Constants.PAY_ALI);
                finish();

                break;
            case R.id.tv_go_2_comment:
                OrderCommentActivity.start(this, mQDetailEntity.getId());
                finish();

                break;
            case R.id.tv_refuse_answer:
                OrderRefuseActivity.start(this, data, mAskerName);
                finish();
                break;
            case R.id.tv_done_answer:
                if (etAnswerContent.getText().toString().isEmpty()) {
                    ToastUtils.showShort(R.string.pls_enter_content);
                    return;
                }
                showDoneDialog();

                break;
        }
    }

    AlertDialog dialog;

    private void showDoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_done_answer, null);
        builder.setView(view);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long time = new Date().getTime() / 1000;
                Map<String, Object> map = new HashMap<>();
                map.put("issue_id", data.getId());
                map.put("content", etAnswerContent.getText().toString());
                map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
                String str = HMacMD5Util.getMapToString(map);
                String sign1 = null;
                try {
                    sign1 = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                mPresenter.doneAnswer(data.getId(), etAnswerContent.getText().toString(),
                        LocalConfigStore.getInstance().getAk(),
                        time + LocalConfigStore.getInstance().getTimestamp(), sign1);
            }
        });
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }

    @Override
    public void onQ_DetailMaster(QDetailEntity data) {
        llMasterBottom.setVisibility(View.VISIBLE);
        llUserBottom.setVisibility(View.GONE);
        setBasic(data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvQ.addItemDecoration(new EvenItemDecoration(rvQ, R.dimen.dp_8));
        rvQ.setLayoutManager(linearLayoutManager);
        rvQ.setAdapter(new PicAdapter(data.getPhotos()));

        int orderStatus = data.getStatus();
        //订单各种状态
        if (orderStatus == Constants.MASTER_ORDER_STATUS_WAIT_ANSWER) {
            tvOrderStatus.setText(getString(R.string.order_status_wait_answer));
            tvOrderStatusDes.setText(R.string.pls_wait_for_master_answer);
            GlideApp.with(ivStatus).load(R.drawable.order_not_wait_answer).into(ivStatus);

            llMasterButtons.setVisibility(View.VISIBLE);


        } else if (orderStatus == Constants.MASTER_ORDER_STATUS_DONE_1) {
            tvOrderStatus.setText(R.string.order_successful);
            tvOrderStatusDes.setVisibility(View.GONE);
            GlideApp.with(ivStatus).load(R.drawable.order_success).into(ivStatus);
            etAnswerContent.setText(data.getAnswer_content());
            //设置不可以编辑
            etAnswerContent.setFocusable(false);
            etAnswerContent.setFocusableInTouchMode(false);
        } else if (orderStatus == Constants.MASTER_ORDER_STATUS_DONT2) {
            tvOrderStatus.setText(R.string.order_successful);
            tvOrderStatusDes.setVisibility(View.GONE);
            GlideApp.with(ivStatus).load(R.drawable.order_success).into(ivStatus);
            etAnswerContent.setText(data.getAnswer_content());
            //设置不可以编辑
            etAnswerContent.setFocusable(false);
            etAnswerContent.setFocusableInTouchMode(false);
        } else if (StringUtil.ifOrderMasterClosed(orderStatus)) {
            tvOrderStatus.setText(R.string.order_closed);
            tvOrderStatusDes.setText(R.string.sorry_cant_answer);
            GlideApp.with(ivStatus).load(R.drawable.order_closed).into(ivStatus);

            etAnswerContent.setText(data.getAnswer_content());
            //设置不可以编辑
            etAnswerContent.setFocusable(false);
            etAnswerContent.setFocusableInTouchMode(false);

        }

    }

    QDetailEntity mQDetailEntity;

    @Override
    public void onQ_DetailUser(QDetailEntity data) {
        mQDetailEntity = data;
        llMasterBottom.setVisibility(View.GONE);
        llUserBottom.setVisibility(View.VISIBLE);
        setBasic(data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvQ.addItemDecoration(new EvenItemDecoration(rvQ, R.dimen.dp_8));
        rvQ.setLayoutManager(linearLayoutManager);
        rvQ.setAdapter(new PicAdapter(data.getPhotos()));


        int orderStatus = data.getStatus();
        //订单各种状态
        if (orderStatus == Constants.USER_ORDER_STATUS_NOT_PAID) {
            tvOrderStatus.setText(getString(R.string.order_status_not_paied));
            tvOrderStatusDes.setText(R.string.order_status_not_paied_des);
            GlideApp.with(ivStatus).load(R.drawable.order_not_paid).into(ivStatus);
            tvAnswerContent.setText(R.string.pay_will_answer);
            tvGo2Pay.setVisibility(View.VISIBLE);

        } else if (orderStatus == Constants.USER_ORDER_STATUS_WAIT_ANSWER) {
            tvOrderStatus.setText(getString(R.string.order_status_wait_answer));
            tvOrderStatusDes.setText(R.string.pls_wait_for_master_answer);
            GlideApp.with(ivStatus).load(R.drawable.order_not_wait_answer).into(ivStatus);
            tvAnswerContent.setText(R.string.pls_wait_for_master_answer);


        } else if (orderStatus == Constants.USER_ORDER_STATUS_WAIT_COMMENT) {
            tvOrderStatus.setText(R.string.order_successful);
            tvOrderStatusDes.setVisibility(View.GONE);
            GlideApp.with(ivStatus).load(R.drawable.order_success).into(ivStatus);
            tvGo2Comment.setVisibility(View.VISIBLE);
            tvAnswerContent.setText(data.getAnswer_content());

        } else if (orderStatus == Constants.USER_ORDER_STATUS_CANCELED) {
            tvOrderStatus.setText(R.string.order_closed);
            tvOrderStatusDes.setText(R.string.user_canceled_);
            GlideApp.with(ivStatus).load(R.drawable.order_success).into(ivStatus);
            tvGo2Comment.setVisibility(View.VISIBLE);
            tvAnswerContent.setText(data.getAnswer_content());

        } else if (orderStatus == Constants.USER_ORDER_STATUS_OVER_TIME) {
            tvOrderStatus.setText(R.string.order_closed);
            tvOrderStatusDes.setText(R.string.over_time_);
            GlideApp.with(ivStatus).load(R.drawable.order_success).into(ivStatus);
            tvGo2Comment.setVisibility(View.VISIBLE);
            tvAnswerContent.setText(data.getAnswer_content());

        } else if (StringUtil.getUserOrderString(orderStatus, OrderDetailActivity.this).equals(getString(R.string.order_status_closed))) {
            tvOrderStatus.setText(R.string.order_closed);
            tvOrderStatusDes.setText(R.string.sorry_cant_answer);
            GlideApp.with(ivStatus).load(R.drawable.order_closed).into(ivStatus);
            tvAnswerContent.setText(data.getReason());

        } else if (orderStatus == Constants.USER_ORDER_STATUS_COMPLETED) {
            tvOrderStatus.setText(R.string.order_successful);
            tvOrderStatusDes.setVisibility(View.GONE);
            GlideApp.with(ivStatus).load(R.drawable.order_success).into(ivStatus);
            tvAnswerContent.setText(data.getAnswer_content());
        } else if (orderStatus == Constants.USER_ORDER_STATUS_CANCELED) {
            tvOrderStatus.setText(R.string.order_closed);
            tvOrderStatusDes.setText(R.string.sorry_cant_answer);
            GlideApp.with(ivStatus).load(R.drawable.order_closed).into(ivStatus);
            tvAnswerContent.setText(data.getReason());


        }
    }

    @Override
    public void onDoneAnswer() {
        System.out.println("onDoneAnswer");
        finish();
    }

    private void setBasic(QDetailEntity data) {
        mAskerName = data.getNickname();
        tvAskName.setText(data.getNickname() + getString(R.string.whos_question));
        tvQName.setText(data.getNickname());
        tvQGender.setText(StringUtil.getGenderString(data.getSex()));

        if (data.getCalendar_type() == 1) {


            Calendar date1 = DateUtils.getCalendarViaTimestamp(data.getBirth_day());
            int year = date1.get(Calendar.YEAR);    //获取年
            int month = date1.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
            int day = date1.get(Calendar.DAY_OF_MONTH);    //获取当前天数
            String nongli = LunarCalendarUtil.getInstance().getLunarString(year, month, day);

            tvQBirth1.setText(StringUtil.getCalenderString(data.getCalendar_type(), this) + " " + nongli);
        } else {
            tvQBirth1.setText(StringUtil.getCalenderString(data.getCalendar_type(), this) + DateUtils.getTime(data.getBirth_day(), DateUtils.PATTERN_2));
        }

        tvQBirth2.setText(data.getBa_zi().getYear() + " " + data.getBa_zi().getMonth() + " " + data.getBa_zi().getDay() + " " + data.getBa_zi().getHour());
        tvQBirthLocation.setText(data.getBirth_area());
        tvQContent.setText(data.getIssue_content());
        tvMasterName.setText(data.getMaster_name());

    }


    class PicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public PicAdapter(@Nullable List<String> data) {
            super(R.layout.recycle_item_avater, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            GlideApp.with(helper.getView(R.id.iv_avatar)).load(item).into((ImageView) helper.getView(R.id.iv_avatar));
            helper.getView(R.id.iv_avatar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageViewActivity.start(helper.getView(R.id.iv_avatar).getContext(), item);
                }
            });
        }
    }
}
