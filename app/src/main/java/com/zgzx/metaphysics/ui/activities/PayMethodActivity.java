package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
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

/**
 * 选择支付方式
 *
 * @author yu
 * @date 2020-8-18
 */
public class PayMethodActivity extends BaseRequestActivity implements PayMethorController.View, ICallback, PayUtils.payResult {

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

    @BindView(R.id.tv_percent_100)
    TextView tv_percent_100;
    @BindView(R.id.tv_percent_90)
    TextView tv_percent_90;
    @BindView(R.id.tv_percent_80)
    TextView tv_percent_80;

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


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_arrow_back)
    ImageView iv_arrow_back;
    private float coin_available, discount_rate, origin_amount;
    private float balance; // 购买价格
    private int fateBookId, type, isAll, discount_id;
    private int[] idArray;
    private PayController.Presenter mBuyPresenter;
    private PayMethorController.Presenter mPresenter;
    private ArrayList<Integer> list;
    private int FATEBOOK_PAY_FLAG = 0x01;
    private boolean tvIs_100, tvIs_90, tvIs_80, tvIs_70, tvIs_60, tvIs_50, tvIs_40, tvIs_30, tvIs_20, tvIs_10, isAliPay, isWeChat, isKMZ;
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
        return new Intent(context, PayMethodActivity.class)
                .putExtra(Constants.EXT_AMOUNT, amount)
                .putExtra("FATE_BOOK_ID", fateBookId)
                .putExtra("IS_ALL", isAll)
                .putExtra("ID", list).putExtra("TYPE", type);
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_to_be_pay_layout;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultEvent(PayResultEvent event) {
        if (event.isSuccess()) {//微信支付成功
            System.out.println("");
            onPaySuccess();
        } else {
            System.out.println("");
            onPayFailed();
        }
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        tv_title.setText(getResources().getString(R.string.select_pay));
        radio_alipay.setChecked(true);
        isAliPay = true;
        time = new Date().getTime() / 1000;
        mPresenter = new PayMethorController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        type = getIntent().getIntExtra("TYPE", -1);
        if (type == 1) {
            balance = getIntent().getFloatExtra(Constants.EXT_AMOUNT, 0);
            fateBookId = getIntent().getIntExtra("FATE_BOOK_ID", -1);
            isAll = getIntent().getIntExtra("IS_ALL", -1);
            list = getIntent().getIntegerArrayListExtra("ID");
            setArrayList(list);
            Map<String, Object> map = new HashMap<>();
            map.put("lifebook_id", fateBookId);
            map.put("cate_ids", setArray(list));

            map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
            String str = HMacMD5Util.getMapToString(map);
            String sign = null;
            try {
                sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            mPresenter.getOrderLifeBook(fateBookId, setArray(list), LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
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
            mPresenter.getOrderLifeBook(fateBookId, LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
        }

        //  pay_btn.setText(String.format(getResources().getString(R.string.wait_pay), NumberUtil.format(balance)));

        // 购买
        mStateView = new ToastRequestStatusView(this);
        PayFateBookPresenter payFateBookPresenter = new PayFateBookPresenter();
        payFateBookPresenter.setModelAndView(this);

        mBuyPresenter = new PayController.Presenter();
        mBuyPresenter.setModelAndView(new PayView(this, password -> {
            Map<String, Object> map = new HashMap<>();
            map.put("lifebook_id", fateBookId);
            map.put("pay_pass", password);
            map.put("cate_id", setArray(list));
            map.put("is_all", isAll);
            map.put("pay_type", WebApiConstants.PAY_TYPE_KMZ);

            map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
            String str = HMacMD5Util.getMapToString(map);
            String sign = null;
            try {
                sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            // 购买命书
            payFateBookPresenter.buy(fateBookId, password, isAll, WebApiConstants.PAY_TYPE_KMZ, setArray(list), LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
        }));

        getLifecycle().addObserver(mBuyPresenter);

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

    private void setArrayList(ArrayList<Integer> list) {
        idArray = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            idArray[i] = list.get(i);
        }
    }

    private String setArray(ArrayList<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                stringBuilder.append(list.get(i));
            } else {
                stringBuilder.append(list.get(i)).append(",");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void buyOk(PrePayResult preOrderEntity) {
        if (isAliPay) {
            PayUtils.startPayAli(preOrderEntity.getAlipay(), this, this);
        }
        if (isWeChat) {
            PayUtils.doWXPay(this, preOrderEntity.getWxpay());
        }

        if (isKMZ) {
            PayResultEntity payResultEntity = new PayResultEntity(true,
                    getString(R.string.payment_successful), "");
            PayResultActivity.start(this, payResultEntity);
            finish();
        }

    }

    @Override
    public void renderAssets(OrderLifeBookEntity orderLifeBookEntity) {
        mOrderLifeBookEntity = orderLifeBookEntity;
        coin_available = orderLifeBookEntity.getCoin_available();
        discount_rate = orderLifeBookEntity.getDiscount_rate();
        origin_amount = orderLifeBookEntity.getOrigin_amount();
        tv_product_name.setText(orderLifeBookEntity.getSubject());
        order_no = orderLifeBookEntity.getOrder_no();
        tv_product_amont.setText("¥ " + NumberUtil.format(origin_amount));
        tip_tv.setText(orderLifeBookEntity.getPay_tips());
        tv_kmz_balance.setText(String.format(getString(R.string.tv_kmz), NumberUtil.format(orderLifeBookEntity.getCoin_available())));


        tv_percent_100.setText(orderLifeBookEntity.getDiscount_list().get(0).getDiscount() + "%");
        tv_percent_90.setText(orderLifeBookEntity.getDiscount_list().get(1).getDiscount() + "%");
        tv_percent_80.setText(orderLifeBookEntity.getDiscount_list().get(2).getDiscount() + "%");
        tv_percent_70.setText(orderLifeBookEntity.getDiscount_list().get(3).getDiscount() + "%");
        tv_percent_60.setText(orderLifeBookEntity.getDiscount_list().get(4).getDiscount() + "%");
        tv_percent_50.setText(orderLifeBookEntity.getDiscount_list().get(5).getDiscount() + "%");
        tv_percent_40.setText(orderLifeBookEntity.getDiscount_list().get(6).getDiscount() + "%");
        tv_percent_30.setText(orderLifeBookEntity.getDiscount_list().get(7).getDiscount() + "%");
        tv_percent_20.setText(orderLifeBookEntity.getDiscount_list().get(8).getDiscount() + "%");
        tv_percent_10.setText(orderLifeBookEntity.getDiscount_list().get(9).getDiscount() + "%");

        tvIs_100 = coin_available >= origin_amount * 1 ? true : false;
        tvIs_90 = coin_available >= origin_amount * 0.9 ? true : false;
        tvIs_80 = coin_available >= origin_amount * 0.8 ? true : false;
        tvIs_70 = coin_available >= origin_amount * 0.7 ? true : false;
        tvIs_60 = coin_available >= origin_amount * 0.6 ? true : false;
        tvIs_50 = coin_available >= origin_amount * 0.5 ? true : false;
        tvIs_40 = coin_available >= origin_amount * 0.4 ? true : false;
        tvIs_30 = coin_available >= origin_amount * 0.3 ? true : false;
        tvIs_20 = coin_available >= origin_amount * 0.2 ? true : false;
        tvIs_10 = coin_available >= origin_amount * 0.1 ? true : false;


        if (tvIs_100) {
            setCommData(tv_percent_100, 1, 0);
            discount_id = orderLifeBookEntity.getDiscount_list().get(0).getId();
            mRadioGroupPay.setVisibility(View.GONE);
            isKMZ = true;
            isAliPay = false;
            isWeChat = false;
        } else {
            tv_percent_100.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_100.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_100.setClickable(false);
            mRadioGroupPay.setVisibility(View.VISIBLE);
            isKMZ = false;


        }
        if (tvIs_90) {
            if (!tvIs_100) {
                setCommData(tv_percent_90, 0.9, 0.1);
                discount_id = orderLifeBookEntity.getDiscount_list().get(1).getId();
            } else {
                tv_percent_90.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_90.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_90.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_90.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_90.setClickable(false);
        }
        if (tvIs_80) {
            if (!tvIs_100 && !tvIs_90) {
                setCommData(tv_percent_80, 0.8, 0.2);
                discount_id = orderLifeBookEntity.getDiscount_list().get(2).getId();
            } else {
                tv_percent_80.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_80.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_80.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_80.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_80.setClickable(false);
        }
        if (tvIs_70) {
            if (!tvIs_100 && !tvIs_90 && !tvIs_80) {
                setCommData(tv_percent_70, 0.7, 0.3);
                discount_id = orderLifeBookEntity.getDiscount_list().get(3).getId();
            } else {
                tv_percent_70.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_70.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }

        } else {
            tv_percent_70.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_70.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_70.setClickable(false);
        }

        if (tvIs_60) {
            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70) {
                setCommData(tv_percent_60, 0.6, 0.4);
                discount_id = orderLifeBookEntity.getDiscount_list().get(4).getId();
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
            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60) {
                setCommData(tv_percent_50, 0.5, 0.5);
                discount_id = orderLifeBookEntity.getDiscount_list().get(5).getId();
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
            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50) {
                setCommData(tv_percent_40, 0.4, 0.6);
                discount_id = orderLifeBookEntity.getDiscount_list().get(6).getId();
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
            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40) {
                setCommData(tv_percent_30, 0.3, 0.7);
                discount_id = orderLifeBookEntity.getDiscount_list().get(7).getId();
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
            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30) {
                setCommData(tv_percent_20, 0.2, 0.8);
                discount_id = orderLifeBookEntity.getDiscount_list().get(8).getId();
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
            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30 && !tvIs_20) {
                setCommData(tv_percent_10, 0.1, 0.9);
                discount_id = orderLifeBookEntity.getDiscount_list().get(9).getId();
            } else {
                tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
                tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
            }
        } else {
            tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
            tv_percent_10.setClickable(false);
        }

        if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30 && !tvIs_20 && !tvIs_10) {
            tv_percent_not.setTextColor(getResources().getColor(R.color.backgroundColor));
            tv_percent_not.setBackground(getResources().getDrawable(R.drawable.icon_percent_select));
            tv_kmz.setText("0.00");
            balance = origin_amount;
            tv_paid_balance.setText("¥ " + NumberUtil.format(balance));
            discount_id = 0;

            pay_btn.setText(String.format(getResources().getString(R.string.wait_pay), NumberUtil.format(balance)));
        }


    }

    @Override
    public void renderMember(OrderMemberEntity orderMemberEntity) {
//        mOrderMemberEntity = orderMemberEntity;
//        coin_available = orderMemberEntity.getCoin_available();
//        discount_rate = orderMemberEntity.getDiscount_rate();
//        origin_amount = orderMemberEntity.getOrigin_amount();
//        order_no = orderMemberEntity.getOrder_no();
//        tv_product_name.setText(orderMemberEntity.getSubject());
//        tip_tv.setText(orderMemberEntity.getPay_tips());
//        tv_product_amont.setText("¥ " + NumberUtil.format(origin_amount));
//        tv_kmz_balance.setText(String.format(getString(R.string.tv_kmz), NumberUtil.format(orderMemberEntity.getCoin_available())));
//
////        tv_percent_100.setText(orderMemberEntity.getDiscount_list().get(0).getDiscount() + "%");
////        tv_percent_90.setText(orderMemberEntity.getDiscount_list().get(1).getDiscount() + "%");
////        tv_percent_80.setText(orderMemberEntity.getDiscount_list().get(2).getDiscount() + "%");
//
//        tv_percent_100.setVisibility(View.GONE);
//        tv_percent_90.setVisibility(View.GONE);
//        tv_percent_80.setVisibility(View.GONE);
//
//
//        tv_percent_70.setText(orderMemberEntity.getDiscount_list().get(0).getDiscount() + "%");
//        tv_percent_60.setText(orderMemberEntity.getDiscount_list().get(1).getDiscount() + "%");
//        tv_percent_50.setText(orderMemberEntity.getDiscount_list().get(2).getDiscount() + "%");
//        tv_percent_40.setText(orderMemberEntity.getDiscount_list().get(3).getDiscount() + "%");
//        tv_percent_30.setText(orderMemberEntity.getDiscount_list().get(4).getDiscount() + "%");
//        tv_percent_20.setText(orderMemberEntity.getDiscount_list().get(5).getDiscount() + "%");
//        tv_percent_10.setText(orderMemberEntity.getDiscount_list().get(6).getDiscount() + "%");
//
//        tvIs_100 = false;
//        tvIs_90 = false;
//        tvIs_80 = false;
//        tvIs_70 = coin_available >= origin_amount * 0.7 ? true : false;
//        tvIs_60 = coin_available >= origin_amount * 0.6 ? true : false;
//        tvIs_50 = coin_available >= origin_amount * 0.5 ? true : false;
//        tvIs_40 = coin_available >= origin_amount * 0.4 ? true : false;
//        tvIs_30 = coin_available >= origin_amount * 0.3 ? true : false;
//        tvIs_20 = coin_available >= origin_amount * 0.2 ? true : false;
//        tvIs_10 = coin_available >= origin_amount * 0.1 ? true : false;
//
//        if (tvIs_100) {
//            setCommData(tv_percent_100, 1, 0);
//            discount_id = orderMemberEntity.getDiscount_list().get(0).getId();
//            mRadioGroupPay.setVisibility(View.GONE);
//            isKMZ = true;
//
//        } else {
//            tv_percent_100.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_100.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_100.setClickable(false);
//            mRadioGroupPay.setVisibility(View.VISIBLE);
//            isKMZ = false;
//        }
//        if (tvIs_90) {
//            if (!tvIs_100) {
//                setCommData(tv_percent_90, 0.9, 0.1);
//                discount_id = orderMemberEntity.getDiscount_list().get(1).getId();
//
//            } else {
//                tv_percent_90.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_90.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//
//        } else {
//            tv_percent_90.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_90.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_90.setClickable(false);
//        }
//        if (tvIs_80) {
//            if (!tvIs_100 && !tvIs_90) {
//                setCommData(tv_percent_80, 0.8, 0.2);
//                discount_id = orderMemberEntity.getDiscount_list().get(2).getId();
//            } else {
//                tv_percent_80.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_80.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//
//        } else {
//            tv_percent_80.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_80.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_80.setClickable(false);
//        }
//        if (tvIs_70) {
//            if (!tvIs_100 && !tvIs_90 && !tvIs_80) {
//                setCommData(tv_percent_70, 0.7, 0.3);
//                discount_id = orderMemberEntity.getDiscount_list().get(3).getId();
//            } else {
//                tv_percent_70.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_70.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//
//
//        } else {
//            tv_percent_70.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_70.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_70.setClickable(false);
//        }
//
//        if (tvIs_60) {
//            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70) {
//                setCommData(tv_percent_60, 0.6, 0.4);
//                discount_id = orderMemberEntity.getDiscount_list().get(4).getId();
//            } else {
//                tv_percent_60.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_60.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//
//        } else {
//            tv_percent_60.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_60.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_60.setClickable(false);
//        }
//
//        if (tvIs_50) {
//            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60) {
//                setCommData(tv_percent_50, 0.5, 0.5);
//                discount_id = orderMemberEntity.getDiscount_list().get(5).getId();
//            } else {
//                tv_percent_50.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_50.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//
//        } else {
//            tv_percent_50.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_50.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_50.setClickable(false);
//        }
//
//        if (tvIs_40) {
//            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50) {
//                setCommData(tv_percent_40, 0.4, 0.6);
//                discount_id = orderMemberEntity.getDiscount_list().get(6).getId();
//            } else {
//                tv_percent_40.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_40.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//
//        } else {
//            tv_percent_40.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_40.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_40.setClickable(false);
//        }
//
//        if (tvIs_30) {
//            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40) {
//                setCommData(tv_percent_30, 0.3, 0.7);
//                discount_id = orderMemberEntity.getDiscount_list().get(7).getId();
//            } else {
//                tv_percent_30.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_30.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//
//        } else {
//            tv_percent_30.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_30.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_30.setClickable(false);
//        }
//
//        if (tvIs_20) {
//            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30) {
//                setCommData(tv_percent_20, 0.2, 0.8);
//                discount_id = orderMemberEntity.getDiscount_list().get(8).getId();
//            } else {
//                tv_percent_20.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_20.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//
//        } else {
//            tv_percent_20.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_20.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_20.setClickable(false);
//        }
//
//        if (tvIs_10) {
//            if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30 && !tvIs_20) {
//                setCommData(tv_percent_10, 0.1, 0.9);
//                discount_id = orderMemberEntity.getDiscount_list().get(9).getId();
//            } else {
//                tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
//                tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
//            }
//        } else {
//            tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
//            tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_not_select));
//            tv_percent_10.setClickable(false);
//        }
//
//        if (!tvIs_100 && !tvIs_90 && !tvIs_80 && !tvIs_70 && !tvIs_60 && !tvIs_50 && !tvIs_40 && !tvIs_30 && !tvIs_20 && !tvIs_10) {
//            tv_percent_not.setTextColor(getResources().getColor(R.color.backgroundColor));
//            tv_percent_not.setBackground(getResources().getDrawable(R.drawable.icon_percent_select));
//            tv_kmz.setText("0.00");
//            balance = origin_amount;
//            tv_paid_balance.setText("¥ " + NumberUtil.format(balance));
//            discount_id = 0;
//            radio_alipay.setChecked(true);
//            pay_btn.setText(String.format(getResources().getString(R.string.wait_pay), NumberUtil.format(balance)));
//        }
    }

    @Override
    public void onQuestionDetail(OrderLifeBookEntity questionDetail) {

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
        pay_btn.setText(String.format(getResources().getString(R.string.wait_pay), NumberUtil.format(balance)));
    }

    @OnClick(value = {R.id.pay_btn, R.id.tv_tips, R.id.tv_percent_100, R.id.tv_percent_90, R.id.tv_percent_80, R.id.tv_percent_70, R.id.tv_percent_60,
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
                        mPresenter.thirdPay(1, order_no, discount_id, LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    } else if (type == 2) {
                        mPresenter.preMemberPay(1, order_no, discount_id, LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
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
                        mPresenter.thirdPay(2, order_no, discount_id, LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    } else if (type == 2) {
                        mPresenter.preMemberPay(2, order_no, discount_id, LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    }
                }

                if (isKMZ) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("pay_type", 3);
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
                        mPresenter.thirdPay(3, order_no, discount_id, LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    } else if (type == 2) {
                        mPresenter.preMemberPay(3, order_no, discount_id, LocalConfigStore.getInstance().getAk(), time + LocalConfigStore.getInstance().getTimestamp(), sign);
                    }
                }
                break;
            case R.id.tv_tips:
                FatePayTipsDialog.show(this);
                break;
            case R.id.tv_percent_10:
                setCommData(false, 9, tv_percent_10, tvIs_100, tv_percent_100, tvIs_80, tv_percent_80, tvIs_90, tv_percent_90, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50, tvIs_40, tv_percent_40, tvIs_30, tv_percent_30, tvIs_20, tv_percent_20, 0.1, 0.9, View.VISIBLE);


                break;
            case R.id.tv_percent_20:
                setCommData(false, 8, tv_percent_20, tvIs_100, tv_percent_100, tvIs_80, tv_percent_80, tvIs_90, tv_percent_90, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50, tvIs_40, tv_percent_40, tvIs_30, tv_percent_30, tvIs_10, tv_percent_10, 0.2, 0.8, View.VISIBLE);
                break;
            case R.id.tv_percent_30:
                setCommData(false, 7, tv_percent_30, tvIs_100, tv_percent_100, tvIs_80, tv_percent_80, tvIs_90, tv_percent_90, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50, tvIs_40, tv_percent_40, tvIs_10, tv_percent_10, tvIs_20, tv_percent_20, 0.3, 0.7, View.VISIBLE);
                break;
            case R.id.tv_percent_40:
                setCommData(false, 6, tv_percent_40, tvIs_100, tv_percent_100, tvIs_80, tv_percent_80, tvIs_90, tv_percent_90, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50, tvIs_30, tv_percent_30, tvIs_10, tv_percent_10, tvIs_20, tv_percent_20, 0.4, 0.6, View.VISIBLE);
                break;
            case R.id.tv_percent_50:
                setCommData(false, 5, tv_percent_50, tvIs_100, tv_percent_100, tvIs_80, tv_percent_80, tvIs_90, tv_percent_90, tvIs_70, tv_percent_70, tvIs_60, tv_percent_60, tvIs_40, tv_percent_40, tvIs_30, tv_percent_30, tvIs_10, tv_percent_10, tvIs_20, tv_percent_20, 0.5, 0.5, View.VISIBLE);
                break;
            case R.id.tv_percent_60:
                setCommData(false, 4, tv_percent_60, tvIs_100, tv_percent_100, tvIs_80, tv_percent_80, tvIs_90, tv_percent_90, tvIs_70, tv_percent_70, tvIs_40, tv_percent_40, tvIs_50, tv_percent_50, tvIs_30, tv_percent_30, tvIs_10, tv_percent_10, tvIs_20, tv_percent_20, 0.6, 0.4, View.VISIBLE);
                break;
            case R.id.tv_percent_70:
                setCommData(false, 3, tv_percent_70, tvIs_100, tv_percent_100, tvIs_80, tv_percent_80, tvIs_90, tv_percent_90, tvIs_40, tv_percent_40, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50, tvIs_30, tv_percent_30, tvIs_10, tv_percent_10, tvIs_20, tv_percent_20, 0.7, 0.3, View.VISIBLE);
                break;

            case R.id.tv_percent_80:
                setCommData(false, 2, tv_percent_80, tvIs_100, tv_percent_100, tvIs_70, tv_percent_70, tvIs_90, tv_percent_90, tvIs_40, tv_percent_40, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50, tvIs_30, tv_percent_30, tvIs_10, tv_percent_10, tvIs_20, tv_percent_20, 0.8, 0.2, View.VISIBLE);
                break;

            case R.id.tv_percent_90:

                setCommData(false, 1, tv_percent_90, tvIs_100, tv_percent_100, tvIs_70, tv_percent_70, tvIs_80, tv_percent_80, tvIs_40, tv_percent_40, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50, tvIs_30, tv_percent_30, tvIs_10, tv_percent_10, tvIs_20, tv_percent_20, 0.9, 0.1, View.VISIBLE);
                break;
            case R.id.tv_percent_100:
                setCommData(true, 0, tv_percent_100, tvIs_90, tv_percent_90, tvIs_70, tv_percent_70, tvIs_80, tv_percent_80, tvIs_40, tv_percent_40, tvIs_60, tv_percent_60, tvIs_50, tv_percent_50, tvIs_30, tv_percent_30, tvIs_10, tv_percent_10, tvIs_20, tv_percent_20, 1, 0, View.GONE);
                break;
            case R.id.tv_percent_not:
                isKMZ = false;

                mRadioGroupPay.setVisibility(View.VISIBLE);
                if (tvIs_100) {
                    tv_percent_100.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_100.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                if (tvIs_90) {
                    tv_percent_90.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_90.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
                if (tvIs_80) {
                    tv_percent_80.setTextColor(getResources().getColor(R.color.color_a3ad87));
                    tv_percent_80.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
                }
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

                pay_btn.setText(String.format(getResources().getString(R.string.wait_pay), NumberUtil.format(balance)));
                discount_id = 0;
                break;

        }
    }

    private void setCommData(boolean b, int i, TextView tv_percent_100, boolean tvIs_90, TextView tv_percent_90, boolean tvIs_70, TextView tv_percent_70, boolean tvIs_80, TextView tv_percent_80, boolean tvIs_40, TextView tv_percent_40, boolean tvIs_60, TextView tv_percent_60, boolean tvIs_50, TextView tv_percent_50, boolean tvIs_30, TextView tv_percent_30, boolean tvIs_10, TextView tv_percent_10, boolean tvIs_20, TextView tv_percent_20, double i2, double i3, int gone) {
        isKMZ = b;
        if (type == 1) {
            discount_id = mOrderLifeBookEntity.getDiscount_list().get(i).getId();
        } else if (type == 2) {
            discount_id = mOrderMemberEntity.getDiscount_list().get(i).getId();
        }
        tv_percent_100.setTextColor(getResources().getColor(R.color.backgroundColor));
        tv_percent_100.setBackground(getResources().getDrawable(R.drawable.icon_percent_select));
        tv_percent_not.setTextColor(getResources().getColor(R.color.color_a3ad87));
        tv_percent_not.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        if (tvIs_90) {
            tv_percent_90.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_90.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_70) {
            tv_percent_70.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_70.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_80) {
            tv_percent_80.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_80.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_40) {
            tv_percent_40.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_40.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_60) {
            tv_percent_60.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_60.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_50) {
            tv_percent_50.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_50.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_30) {
            tv_percent_30.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_30.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_10) {
            tv_percent_10.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_10.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }
        if (tvIs_20) {
            tv_percent_20.setTextColor(getResources().getColor(R.color.color_a3ad87));
            tv_percent_20.setBackground(getResources().getDrawable(R.drawable.icon_percent_unselect));
        }

        tv_kmz.setText(NumberUtil.format(origin_amount * i2 * discount_rate));
        balance = (float) (origin_amount * i3);
        tv_paid_balance.setText("¥ " + NumberUtil.format(balance));
        pay_btn.setText(String.format(getResources().getString(R.string.wait_pay), NumberUtil.format(balance)));

        mRadioGroupPay.setVisibility(gone);
        if (gone == View.GONE) {
            isAliPay = false;
            isWeChat = false;
        } else if (gone == View.VISIBLE) {
            if (radio_alipay.isChecked()) {
                isAliPay = true;
            } else {
                isAliPay = false;
            }

            if (radio_wechat_pay.isChecked()) {
                isWeChat = true;
            } else {
                isWeChat = false;
            }

        }

    }


    @Override
    public void successful() {


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
        if (type == 1) {
            EventBus.getDefault().post(new FateBookBuyEvent());
        } else if (type == 2) {
            // onBackPressed();
        }

        PayResultEntity payResultEntity = new PayResultEntity(true,
                getString(R.string.payment_successful), balance + "");
        PayResultActivity.start(this, payResultEntity);
        finish();

    }

    private void onPayFailed() {
        PayResultEntity payResultEntity = new PayResultEntity(false,
                getString(R.string.pay_failed), "");
        PayResultActivity.start(this, payResultEntity);
        finish();
    }
}
