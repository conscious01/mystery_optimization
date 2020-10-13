package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.PayController;
import com.zgzx.metaphysics.controller.PayMethorController;
import com.zgzx.metaphysics.controller.presenters.PayFateBookPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.controller.views.impl.PayView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.model.entity.OrderLifeBookEntity;
import com.zgzx.metaphysics.model.entity.OrderMemberEntity;
import com.zgzx.metaphysics.model.entity.PayResultEntity;
import com.zgzx.metaphysics.model.entity.PrePayResult;
import com.zgzx.metaphysics.model.event.FateBookBuyEvent;
import com.zgzx.metaphysics.model.event.PayResultEvent;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.FatePayTipsDialog;
import com.zgzx.metaphysics.ui.dialogs.SimpleDialog;
import com.zgzx.metaphysics.utils.CacheUtil;
import com.zgzx.metaphysics.utils.NumberUtil;
import com.zgzx.metaphysics.utils.PayUtils;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class PayMethordMemberActivity extends BaseRequestActivity implements PayMethorController.View, ICallback, PayUtils.payResult {
    @BindView(R.id.radio_wechat_pay)
    RadioButton radio_wechat_pay;
    @BindView(R.id.radio_alipay)
    RadioButton radio_alipay;

    @BindView(R.id.pay_btn)
    Button pay_btn;
    @BindView(R.id.radio_group_pay)
    RadioGroup mRadioGroupPay;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.tv_product_amont)
    TextView tv_product_amont;
    @BindView(R.id.tv_kmz_balance)
    TextView tv_kmz_balance;
    @BindView(R.id.tv_kmz)
    TextView tv_kmz;
    @BindView(R.id.tv_paid_balance)
    TextView tv_paid_balance;

    @BindView(R.id.tv_percent_70)
    TextView tv_percent_70;
    @BindView(R.id.tv_percent_60)
    TextView tv_percent_60;
    @BindView(R.id.tv_percent_50)
    TextView tv_percent_50;

    @BindView(R.id.tv_percent_40)
    TextView tv_percent_40;
    @BindView(R.id.tv_percent_30)
    TextView tv_percent_30;
    @BindView(R.id.tv_percent_20)
    TextView tv_percent_20;

    @BindView(R.id.tv_percent_10)
    TextView tv_percent_10;
    @BindView(R.id.tv_percent_not)
    TextView tv_percent_not;
    @BindView(R.id.tip_tv)
    TextView tip_tv;
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_tips)
    ImageView tvTips;
    @BindView(R.id.alipay_layout)
    LinearLayout alipayLayout;
    @BindView(R.id.wechat_layout)
    LinearLayout wechatLayout;
    @BindView(R.id.ll_order_no)
    LinearLayout llOrderNo;


    private float coin_available, discount_rate, origin_amount;
    private float balance; // 购买价格
    private int fateBookId, type, isAll, discount_id;
    private int[] idArray;
    private PayController.Presenter mBuyPresenter;
    private PayMethorController.Presenter mPresenter;
    private ArrayList<Integer> list;
    private int FATEBOOK_PAY_FLAG = 0x01;
    private boolean tvIs_70, tvIs_60, tvIs_50, tvIs_40, tvIs_30, tvIs_20, tvIs_10, isAliPay,
            isWeChat;
    private String order_no;

    private OrderMemberEntity mOrderMemberEntity;
    private OrderLifeBookEntity mOrderLifeBookEntity;
    private long time;

    public static Intent newIntent(
            Context context,
            float amount,
            int fateBookId,
            int isAll,
            ArrayList<Integer> list, int type
    ) {
        return new Intent(context, PayMethordMemberActivity.class)
                .putExtra(Constants.EXT_AMOUNT, amount)
                .putExtra("FATE_BOOK_ID", fateBookId)
                .putExtra("IS_ALL", isAll)
                .putExtra("ID", list).putExtra("TYPE", type);
    }

    public static Intent newIntent(Context context, OrderLifeBookEntity orderResultEntity,
                                   int type) {
        return new Intent(context, PayMethordMemberActivity.class)
                .putExtra(Constants.EXT_TYPE, orderResultEntity).putExtra("TYPE", type);
    }

    public static Intent newIntent(Context context, int questionID, int type) {
        return new Intent(context, PayMethordMemberActivity.class).putExtra(Constants.KYE_QUESTION_ID, questionID).putExtra("TYPE", type);
    }


    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_to_be_pay_layout_1;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultEvent(PayResultEvent event) {
        if (event.isSuccess()) {//微信支付成功
            onPaySuccess();
        } else {
            onPayFailed();
        }
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {
        tvTitle.setText(getResources().getString(R.string.select_pay));
        radio_alipay.setChecked(true);
        isAliPay = true;
        mRadioGroupPay.setVisibility(View.VISIBLE);
        time = new Date().getTime() / 1000;
        mPresenter = new PayMethorController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        type = getIntent().getIntExtra("TYPE", -1);
        if (type == 1) {

        } else if (type == 2) {
            fateBookId = getIntent().getIntExtra("FATE_BOOK_ID", -1);
            Map<String, Object> map = new HashMap<>();
            map.put("member_id", fateBookId);
            map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
            String str = HMacMD5Util.getMapToString(map);
            String sign = null;
            try {
                sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            mPresenter.getOrderLifeBook(fateBookId, LocalConfigStore.getInstance().getAk(),
                    time + LocalConfigStore.getInstance().getTimestamp(), sign);
        } else if (type == Constants.TYPE_QUESTION_PAYING) {
            mOrderLifeBookEntity =
                    (OrderLifeBookEntity) getIntent().getSerializableExtra(Constants.EXT_TYPE);
            setQuestionInfo(mOrderLifeBookEntity);
        } else if (type == Constants.TYPE_QUESTION_PAYING_QUERY) {
            int questionID = getIntent().getIntExtra(Constants.KYE_QUESTION_ID, -1);
            if (questionID > 0) {
                mPresenter.getNotPaidQuestionDetail(questionID);

            }
        }

        //  pay_btn.setText(String.format(getResources().getString(R.string.wait_pay), NumberUtil
        //  .format(balance)));

//        // 购买
//        mStateView = new ToastRequestStatusView(this);
//        PayFateBookPresenter payFateBookPresenter = new PayFateBookPresenter();
//        payFateBookPresenter.setModelAndView(this);
//
//        mBuyPresenter = new PayController.Presenter();
//        mBuyPresenter.setModelAndView(new PayView(this, password -> {
//            Map<String, Object> map = new HashMap<>();
//            map.put("lifebook_id", fateBookId);
//            map.put("pay_pass", password);
//            map.put("cate_id", setArray(list));
//            map.put("is_all", isAll);
//            map.put("pay_type", WebApiConstants.PAY_TYPE_KMZ);
//
//            map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
//            String str = HMacMD5Util.getMapToString(map);
//            String sign = null;
//            try {
//                sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            // 购买命书
//            payFateBookPresenter.buy(fateBookId, password, isAll, WebApiConstants.PAY_TYPE_KMZ,
//                    setArray(list), LocalConfigStore.getInstance().getAk(),
//                    time + LocalConfigStore.getInstance().getTimestamp(), sign);
//        }));

        //    getLifecycle().addObserver(mBuyPresenter);

        radio_alipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    radio_wechat_pay.setChecked(false);
                }
                isAliPay = true;
                isWeChat = false;
            }
        });
        radio_wechat_pay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    radio_alipay.setChecked(false);
                }
                isAliPay = false;
                isWeChat = true;
            }
        });
    }

    private void setQuestionInfo(OrderLifeBookEntity mOrderEntity) {

        mOrderLifeBookEntity = mOrderEntity;
        coin_available = mOrderLifeBookEntity.getCoin_available();
        discount_rate = mOrderLifeBookEntity.getDiscount_rate();
        origin_amount = mOrderLifeBookEntity.getOrigin_amount();
        tv_product_name.setText(mOrderLifeBookEntity.getSubject());
        order_no = mOrderLifeBookEntity.getOrder_no();
        tv_product_amont.setText("¥ " + NumberUtil.format(origin_amount));
        tip_tv.setText(mOrderLifeBookEntity.getPay_tips());
        tv_kmz_balance.setText(String.format(getString(R.string.tv_kmz),
                NumberUtil.format(mOrderLifeBookEntity.getCoin_available())));

        tv_percent_70.setText(mOrderLifeBookEntity.getDiscount_list().get(0).getDiscount() + "%");
        tv_percent_60.setText(mOrderLifeBookEntity.getDiscount_list().get(1).getDiscount() + "%");
        tv_percent_50.setText(mOrderLifeBookEntity.getDiscount_list().get(2).getDiscount() + "%");
        tv_percent_40.setText(mOrderLifeBookEntity.getDiscount_list().get(3).getDiscount() + "%");
        tv_percent_30.setText(mOrderLifeBookEntity.getDiscount_list().get(4).getDiscount() + "%");
        tv_percent_20.setText(mOrderLifeBookEntity.getDiscount_list().get(5).getDiscount() + "%");
        tv_percent_10.setText(mOrderLifeBookEntity.getDiscount_list().get(6).getDiscount() + "%");

        tvIs_70 = coin_available >= origin_amount * 0.7 ? true : false;
        tvIs_60 = coin_available >= origin_amount * 0.6 ? true : false;
        tvIs_50 = coin_available >= origin_amount * 0.5 ? true : false;
        tvIs_40 = coin_available >= origin_amount * 0.4 ? true : false;
        tvIs_30 = coin_available >= origin_amount * 0.3 ? true : false;
        tvIs_20 = coin_available >= origin_amount * 0.2 ? true : false;
        tvIs_10 = coin_available >= origin_amount * 0.1 ? true : false;

        if (tvIs_70) {
            setCommData(tv_percent_70, 0.7, 0.3);
            discount_id = mOrderLifeBookEntity.getDiscount_list().get(0).getId();
        } else {
            tv_percent_70.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_70.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_70.setClickable(false);
        }

        if (tvIs_60) {
            if (!tvIs_70) {
                setCommData(tv_percent_60, 0.6, 0.4);
                discount_id = mOrderLifeBookEntity.getDiscount_list().get(1).getId();
            } else {
                tv_percent_60.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_60.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_60.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_60.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_60.setClickable(false);
        }

        if (tvIs_50) {
            if (!tvIs_70 && !tvIs_60) {
                setCommData(tv_percent_50, 0.5, 0.5);
                discount_id = mOrderLifeBookEntity.getDiscount_list().get(2).getId();
            } else {
                tv_percent_50.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_50.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_50.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_50.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_50.setClickable(false);
        }

        if (tvIs_40) {
            if (!tvIs_70 && !tvIs_60 && !tvIs_50) {
                setCommData(tv_percent_40, 0.4, 0.6);
                discount_id = mOrderLifeBookEntity.getDiscount_list().get(3).getId();
            } else {
                tv_percent_40.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_40.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_40.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_40.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_40.setClickable(false);
        }

        if (tvIs_30) {
            if (!tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40) {
                setCommData(tv_percent_30, 0.3, 0.7);
                discount_id = mOrderLifeBookEntity.getDiscount_list().get(4).getId();
            } else {
                tv_percent_30.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_30.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_30.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_30.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_30.setClickable(false);
        }

        if (tvIs_20) {
            if (!tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30) {
                setCommData(tv_percent_20, 0.2, 0.8);
                discount_id = mOrderLifeBookEntity.getDiscount_list().get(5).getId();
            } else {
                tv_percent_20.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_20.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_20.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_20.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_20.setClickable(false);
        }

        if (tvIs_10) {
            if (!tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30 && !tvIs_20) {
                setCommData(tv_percent_10, 0.1, 0.9);
                discount_id = mOrderLifeBookEntity.getDiscount_list().get(6).getId();
            } else {
                tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }
        } else {
            tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_10.setClickable(false);
        }

        if (!tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30 && !tvIs_20 && !tvIs_10) {
            tv_percent_not.setTextColor(getResources().getColor(R.color.backgroundColor));
            tv_percent_not.setBackground(getResources().getDrawable(R.drawable.icon_percent_select));
            tv_kmz.setText("0.00");
            balance = origin_amount;
            tv_paid_balance.setText("¥ " + NumberUtil.format(balance));
            discount_id = 0;
            pay_btn.setText(String.format(getResources().getString(R.string.wait_pay),
                    NumberUtil.format(balance)));
        }


    }

//    private void setArrayList(ArrayList<Integer> list) {
//        idArray = new int[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            idArray[i] = list.get(i);
//        }
//    }
//
//    private String setArray(ArrayList<Integer> list) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            if (i == list.size() - 1) {
//                stringBuilder.append(list.get(i));
//            } else {
//                stringBuilder.append(list.get(i)).append(",");
//            }
//        }
//        return stringBuilder.toString();
//    }

    @Override
    public void buyOk(PrePayResult preOrderEntity) {
        if (isAliPay) {
            PayUtils.startPayAli(preOrderEntity.getAlipay(), this, this);
        }
        if (isWeChat) {
            PayUtils.doWXPay(this, preOrderEntity.getWxpay());
        }

    }

    @Override
    public void renderAssets(OrderLifeBookEntity orderLifeBookEntity) {


    }

    @Override
    public void renderMember(OrderMemberEntity orderMemberEntity) {
        mOrderMemberEntity = orderMemberEntity;
        coin_available = orderMemberEntity.getCoin_available();
        discount_rate = orderMemberEntity.getDiscount_rate();
        origin_amount = orderMemberEntity.getOrigin_amount();
        order_no = orderMemberEntity.getOrder_no();
        tv_product_name.setText(orderMemberEntity.getSubject());
        tip_tv.setText(orderMemberEntity.getPay_tips());
        tv_product_amont.setText("¥ " + NumberUtil.format(origin_amount));
        tv_kmz_balance.setText(String.format(getString(R.string.tv_kmz),
                NumberUtil.format(orderMemberEntity.getCoin_available())));

        tv_percent_70.setText(orderMemberEntity.getDiscount_list().get(0).getDiscount() + "%");
        tv_percent_60.setText(orderMemberEntity.getDiscount_list().get(1).getDiscount() + "%");
        tv_percent_50.setText(orderMemberEntity.getDiscount_list().get(2).getDiscount() + "%");
        tv_percent_40.setText(orderMemberEntity.getDiscount_list().get(3).getDiscount() + "%");
        tv_percent_30.setText(orderMemberEntity.getDiscount_list().get(4).getDiscount() + "%");
        tv_percent_20.setText(orderMemberEntity.getDiscount_list().get(5).getDiscount() + "%");
        tv_percent_10.setText(orderMemberEntity.getDiscount_list().get(6).getDiscount() + "%");

        tvIs_70 = coin_available >= origin_amount * 0.7 ? true : false;
        tvIs_60 = coin_available >= origin_amount * 0.6 ? true : false;
        tvIs_50 = coin_available >= origin_amount * 0.5 ? true : false;
        tvIs_40 = coin_available >= origin_amount * 0.4 ? true : false;
        tvIs_30 = coin_available >= origin_amount * 0.3 ? true : false;
        tvIs_20 = coin_available >= origin_amount * 0.2 ? true : false;
        tvIs_10 = coin_available >= origin_amount * 0.1 ? true : false;


        if (tvIs_70) {
            setCommData(tv_percent_70, 0.7, 0.3);
            discount_id = orderMemberEntity.getDiscount_list().get(0).getId();

        } else {
            tv_percent_70.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_70.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_70.setClickable(false);
        }

        if (tvIs_60) {
            if (!tvIs_70) {
                setCommData(tv_percent_60, 0.6, 0.4);
                discount_id = orderMemberEntity.getDiscount_list().get(1).getId();
            } else {
                tv_percent_60.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_60.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_60.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_60.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_60.setClickable(false);
        }

        if (tvIs_50) {
            if (!tvIs_70 && !tvIs_60) {
                setCommData(tv_percent_50, 0.5, 0.5);
                discount_id = orderMemberEntity.getDiscount_list().get(2).getId();
            } else {
                tv_percent_50.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_50.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_50.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_50.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_50.setClickable(false);
        }

        if (tvIs_40) {
            if (!tvIs_70 && !tvIs_60 && !tvIs_50) {
                setCommData(tv_percent_40, 0.4, 0.6);
                discount_id = orderMemberEntity.getDiscount_list().get(3).getId();
            } else {
                tv_percent_40.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_40.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_40.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_40.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_40.setClickable(false);
        }

        if (tvIs_30) {
            if (!tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40) {
                setCommData(tv_percent_30, 0.3, 0.7);
                discount_id = orderMemberEntity.getDiscount_list().get(4).getId();
            } else {
                tv_percent_30.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_30.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_30.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_30.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_30.setClickable(false);
        }

        if (tvIs_20) {
            if (!tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30) {
                setCommData(tv_percent_20, 0.2, 0.8);
                discount_id = orderMemberEntity.getDiscount_list().get(5).getId();
            } else {
                tv_percent_20.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_20.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_20.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_20.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_20.setClickable(false);
        }

        if (tvIs_10) {
            if (!tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30 && !tvIs_20) {
                setCommData(tv_percent_10, 0.1, 0.9);
                discount_id = orderMemberEntity.getDiscount_list().get(6).getId();
            } else {
                tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }
        } else {
            tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_10.setClickable(false);
        }

        if (!tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30 && !tvIs_20 && !tvIs_10) {
            tv_percent_not.setTextColor(getResources().getColor(R.color.backgroundColor));
            tv_percent_not.setBackground(getResources().getDrawable(R.drawable.icon_percent_select));
            tv_kmz.setText("0.00");
            balance = origin_amount;
            tv_paid_balance.setText("¥ " + NumberUtil.format(balance));
            discount_id = 0;
            pay_btn.setText(String.format(getResources().getString(R.string.wait_pay),
                    NumberUtil.format(balance)));
        }
    }

    @Override
    public void onQuestionDetail(OrderLifeBookEntity questionDetail) {
        setQuestionInfo(questionDetail);
    }

    /**
     * 共同数据抽取
     *
     * @param tv_percent_10
     * @param v
     * @param v2
     */
    private void setCommData(TextView tv_percent_10, double v, double v2) {
        tv_percent_10.setTextColor(getResources().getColor(R.color.backgroundColor));
        tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_select));
        tv_kmz.setText(NumberUtil.format(origin_amount * v * discount_rate));
        balance = (float) (origin_amount * v2);
        tv_paid_balance.setText("¥ " + NumberUtil.format(balance));
        tv_percent_not.setTextColor(getResources().getColor(R.color.color_a3ad87));
        tv_percent_not.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        pay_btn.setText(String.format(getResources().getString(R.string.wait_pay),
                NumberUtil.format(balance)));
    }

    @OnClick(value = {R.id.pay_btn, R.id.tv_tips, R.id.tv_percent_70, R.id.tv_percent_60,
            R.id.tv_percent_50, R.id.tv_percent_40, R.id.tv_percent_30,
            R.id.tv_percent_20, R.id.tv_percent_10, R.id.tv_percent_not, R.id.iv_arrow_back})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_back:
                SimpleDialog dialog = new SimpleDialog(this);
                dialog.setMessage(R.string.cancle_pay);
                dialog.setNegative(R.string.think_again, view -> dialog.dismiss());
                dialog.setPositive(R.string.confirm, view -> {

                    dialog.dismiss();
                    onBackPressed();

                });
                new XPopup.Builder(this)
                        .isDestroyOnDismiss(true)
                        .enableShowWhenAppBackground(false)
                        .asCustom(dialog)
                        .show();
                break;
            case R.id.pay_btn:


                if (isAliPay) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("pay_type", 1);
                    map.put("order_no", order_no);
                    map.put("discount_id", discount_id);
                    map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
                    String str = HMacMD5Util.getMapToString(map);
                    String sign = null;
                    try {
                        sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    if (type == 1) {
                        mPresenter.thirdPay(1, order_no, discount_id,
                                LocalConfigStore.getInstance().getAk(),
                                time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    } else if (type == 2) {
                        mPresenter.preMemberPay(1, order_no, discount_id,
                                LocalConfigStore.getInstance().getAk(),
                                time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    } else if (type == Constants.TYPE_QUESTION_PAYING || type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                        prePayQuestion(1);
                    }
                }


                if (isWeChat) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("pay_type", 2);
                    map.put("order_no", order_no);
                    map.put("discount_id", discount_id);
                    map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
                    String str = HMacMD5Util.getMapToString(map);
                    String sign = null;
                    try {
                        sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    if (type == 1) {
                        mPresenter.thirdPay(2, order_no, discount_id,
                                LocalConfigStore.getInstance().getAk(),
                                time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    } else if (type == 2) {
                        mPresenter.preMemberPay(2, order_no, discount_id,
                                LocalConfigStore.getInstance().getAk(),
                                time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    } else if (type == Constants.TYPE_QUESTION_PAYING || type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                        prePayQuestion(2);
                    }
                }
                break;
            case R.id.tv_tips:
                FatePayTipsDialog.show(this);
                break;
            case R.id.tv_percent_10:
                if (!tvIs_10) {
                    return;
                }
                setClickData(tv_percent_10, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60,
                        tvIs_50, tv_percent_50, tvIs_40, tv_percent_40, tvIs_30, tv_percent_30,
                        tvIs_20, tv_percent_20, 0.1, 0.9);
                if (type == 1) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(6).getId();
                } else if (type == 2) {
                    discount_id = mOrderMemberEntity.getDiscount_list().get(6).getId();
                } else if (type == Constants.TYPE_QUESTION_PAYING) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(6).getId();
                } else if (type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(6).getId();
                }
                break;
            case R.id.tv_percent_20:
                if (!tvIs_20) {
                    return;
                }
                setClickData(tv_percent_20, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60,
                        tvIs_50, tv_percent_50, tvIs_40, tv_percent_40, tvIs_30, tv_percent_30,
                        tvIs_10, tv_percent_10, 0.2, 0.8);
                if (type == 1) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(5).getId();
                } else if (type == 2) {
                    discount_id = mOrderMemberEntity.getDiscount_list().get(5).getId();
                }else if (type == Constants.TYPE_QUESTION_PAYING) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(5).getId();
                } else if (type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(5).getId();
                }
                break;
            case R.id.tv_percent_30:
                if (!tvIs_30) {
                    return;
                }
                if (type == 1) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(4).getId();
                } else if (type == 2) {
                    discount_id = mOrderMemberEntity.getDiscount_list().get(4).getId();
                }else if (type == Constants.TYPE_QUESTION_PAYING) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(4).getId();
                } else if (type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(4).getId();
                }
                setClickData(tv_percent_30, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60,
                        tvIs_50, tv_percent_50, tvIs_40, tv_percent_40, tvIs_20, tv_percent_20,
                        tvIs_10, tv_percent_10, 0.3, 0.7);
                break;
            case R.id.tv_percent_40:
                if (!tvIs_40) {
                    return;
                }
                if (type == 1) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(3).getId();
                } else if (type == 2) {
                    discount_id = mOrderMemberEntity.getDiscount_list().get(3).getId();
                }else if (type == Constants.TYPE_QUESTION_PAYING) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(3).getId();
                } else if (type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(3).getId();
                }
                setClickData(tv_percent_40, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60,
                        tvIs_50, tv_percent_50, tvIs_30, tv_percent_30, tvIs_20, tv_percent_20,
                        tvIs_10, tv_percent_10, 0.4, 0.6);
                break;
            case R.id.tv_percent_50:
                if (!tvIs_50) {
                    return;
                }
                if (type == 1) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(2).getId();
                } else if (type == 2) {
                    discount_id = mOrderMemberEntity.getDiscount_list().get(2).getId();
                }else if (type == Constants.TYPE_QUESTION_PAYING) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(2).getId();
                } else if (type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(2).getId();
                }
                setClickData(tv_percent_50, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60,
                        tvIs_40, tv_percent_40, tvIs_30, tv_percent_30, tvIs_20, tv_percent_20,
                        tvIs_10, tv_percent_10, 0.5, 0.5);
                break;
            case R.id.tv_percent_60:
                if (!tvIs_60) {
                    return;
                }
                if (type == 1) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(1).getId();
                } else if (type == 2) {
                    discount_id = mOrderMemberEntity.getDiscount_list().get(1).getId();
                }else if (type == Constants.TYPE_QUESTION_PAYING) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(1).getId();
                } else if (type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(1).getId();
                }
                setClickData(tv_percent_60, tvIs_70, tv_percent_70, tvIs_50, tv_percent_50,
                        tvIs_40, tv_percent_40, tvIs_30, tv_percent_30, tvIs_20, tv_percent_20,
                        tvIs_10, tv_percent_10, 0.6, 0.4);
                break;
            case R.id.tv_percent_70:
                if (!tvIs_70) {
                    return;
                }
                if (type == 1) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(0).getId();
                } else if (type == 2) {
                    discount_id = mOrderMemberEntity.getDiscount_list().get(0).getId();
                }else if (type == Constants.TYPE_QUESTION_PAYING) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(0).getId();
                } else if (type == Constants.TYPE_QUESTION_PAYING_QUERY) {
                    discount_id = mOrderLifeBookEntity.getDiscount_list().get(0).getId();
                }
                setClickData(tv_percent_70, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50,
                        tvIs_40, tv_percent_40, tvIs_30, tv_percent_30, tvIs_20, tv_percent_20,
                        tvIs_10, tv_percent_10, 0.7, 0.3);
                break;
            case R.id.tv_percent_not:
                if (tvIs_70) {
                    tv_percent_70.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_70.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                if (tvIs_60) {
                    tv_percent_60.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_60.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                if (tvIs_50) {
                    tv_percent_50.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_50.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                if (tvIs_40) {
                    tv_percent_40.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_40.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                if (tvIs_30) {
                    tv_percent_30.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_30.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                if (tvIs_20) {
                    tv_percent_20.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_20.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                if (tvIs_10) {
                    tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                tv_percent_not.setTextColor(getResources().getColor(R.color.backgroundColor));
                tv_percent_not.setBackground(getResources().getDrawable(R.drawable.icon_percent_select));
                tv_kmz.setText("0.00");
                balance = origin_amount;
                tv_paid_balance.setText("¥ " + NumberUtil.format(balance));

                pay_btn.setText(String.format(getResources().getString(R.string.wait_pay),
                        NumberUtil.format(balance)));
                discount_id = 0;
                break;

        }
    }

    private void prePayQuestion(int i) {
        Map<String, Object> map = new HashMap<>();
        map.put("pay_type", i);
        map.put("issue_id", mOrderLifeBookEntity.getId());
        map.put("discount_id", discount_id);
        map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
        String str = HMacMD5Util.getMapToString(map);
        String sign = null;
        try {
            sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mPresenter.preQuestionPay(i, mOrderLifeBookEntity.getId(), discount_id,
                LocalConfigStore.getInstance().getAk(),
                time + LocalConfigStore.getInstance().getTimestamp(), sign);
    }

    /**
     * 选择百分比
     *
     * @param tv_percent_1
     * @param tvIs_7
     * @param tv_percent_7
     * @param tvIs_6
     * @param tv_percent_6
     * @param tvIs_5
     * @param tv_percent_5
     * @param tvIs_4
     * @param tv_percent_4
     * @param tvIs_3
     * @param tv_percent_3
     * @param tvIs_2
     * @param tv_percent_2
     * @param v2
     * @param v3
     */
    private void setClickData(TextView tv_percent_1, boolean tvIs_7, TextView tv_percent_7,
                              boolean tvIs_6, TextView tv_percent_6, boolean tvIs_5,
                              TextView tv_percent_5,
                              boolean tvIs_4, TextView tv_percent_4, boolean tvIs_3,
                              TextView tv_percent_3, boolean tvIs_2, TextView tv_percent_2,
                              double v2, double v3) {
        tv_percent_1.setTextColor(getResources().getColor(R.color.backgroundColor));
        tv_percent_1.setBackground(getResources().getDrawable(R.drawable.icon_percent_select));
        tv_percent_not.setTextColor(getResources().getColor(R.color.color_a3ad87));
        tv_percent_not.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        if (tvIs_7) {
            tv_percent_7.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_7.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_6) {
            tv_percent_6.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_6.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_5) {
            tv_percent_5.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_5.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_4) {
            tv_percent_4.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_4.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_3) {
            tv_percent_3.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_3.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_2) {
            tv_percent_2.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_2.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }

        tv_kmz.setText(NumberUtil.format(origin_amount * v2 * discount_rate));
        balance = (float) (origin_amount * v3);
        tv_paid_balance.setText("¥ " + NumberUtil.format(balance));
        pay_btn.setText(String.format(getResources().getString(R.string.wait_pay),
                NumberUtil.format(balance)));
    }


    @Override
    public void successful() {
//        AppToast.showShort(getString(R.string.successful));
//        EventBus.getDefault().post(new BuyFateBookEvent());
//        onBackPressed();

        onPaySuccess();
    }

    @Override
    public void onSuccess() {
        onPaySuccess();
    }

    @Override
    public void onFailed() {
        onPayFailed();

    }

    @Override
    public void onError() {
        onPayFailed();

    }

    private void onPaySuccess() {
        PayResultEntity payResultEntity = null;
        if (type == 1) {
            payResultEntity = new PayResultEntity(true,
                    getString(R.string.payment_successful), "¥ " + balance);
            EventBus.getDefault().post(new FateBookBuyEvent());
            // onBackPressed();
        } else if (type == 2) {
            payResultEntity = new PayResultEntity(true,
                    getString(R.string.payment_successful), "¥ " + balance);
            // onBackPressed();
        } else if (type == Constants.TYPE_QUESTION_PAYING || type == Constants.TYPE_QUESTION_PAYING_QUERY) {
            payResultEntity = new PayResultEntity(true,
                    getString(R.string.payment_successful), "¥ " + balance, MyOrderActivity.class);
        }
        PayResultActivity.start(this, payResultEntity);
        finish();

    }

    private void onPayFailed() {
        PayResultEntity payResultEntity = null;
        if (type == Constants.TYPE_QUESTION_PAYING) {
            payResultEntity = new PayResultEntity(false,
                    getString(R.string.pay_failed), "", MyOrderActivity.class);
        } else {
            payResultEntity = new PayResultEntity(false,
                    getString(R.string.pay_failed), "");
        }
        PayResultActivity.start(this, payResultEntity);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
