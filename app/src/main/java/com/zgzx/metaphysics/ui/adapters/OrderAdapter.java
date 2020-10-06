package com.zgzx.metaphysics.ui.adapters;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.StringUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrderAdapter extends BaseQuickAdapter<OrderResultEntity, BaseViewHolder> {


    public OrderAdapter() {
        super(R.layout.recycle_item_order);
        handler.sendEmptyMessageDelayed(0, 1000);
    }


    OrderItem orderItem;
    int tempStatus;

    @Override
    public void setNewData(@Nullable List<OrderResultEntity> data) {
        super.setNewData(data);

    }


    public void clear() {
        if (handler != null) {
            handler.removeMessages(0);
        }
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            for (OrderResultEntity obj : OrderAdapter.this.getData()) {
                if (obj.end_time > 0)
                    --obj.end_time;
            }
            notifyDataSetChanged();
            sendEmptyMessageDelayed(0, 1000);
        }
    };
//    public OrderAdapter(List<OrderResultEntity> data) {
//        super(R.layout.recycle_item_order, data);
//    }

    public void setClick(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OrderResultEntity item) {

        helper.setText(R.id.tv_order_no, item.getOrderno());
        helper.setText(R.id.tv_product_name, item.getTrade_name());
        helper.setText(R.id.product_amount, "￥" + item.getPrice());
        helper.setText(R.id.tv_created_time, DateUtils.getTime(item.getCreate_time(),
                DateUtils.PATTERN_3) + "");


        helper.getView(R.id.ll_order).setOnClickListener(v -> orderItem.onOrderDetail(item));
        helper.getView(R.id.tv_cancel).setOnClickListener(v -> orderItem.onCancelOrder(item));
        helper.getView(R.id.tv_go2_pay).setOnClickListener(v -> orderItem.onGo2Pay(item));
        helper.getView(R.id.go_2_comment).setOnClickListener(v -> orderItem.onGo2Comment(item));


        if (item.getPay_time() > 0) {
            helper.getView(R.id.ll_paid_time).setVisibility(View.VISIBLE);
            helper.setText(R.id.paid_time, DateUtils.getTime(item.getPay_time(),
                    DateUtils.PATTERN_3) + "");
        } else {
            helper.getView(R.id.ll_paid_time).setVisibility(View.GONE);

        }

        if (item.getTrans_time() > 0) {
            helper.getView(R.id.ll_done_time).setVisibility(View.VISIBLE);

            helper.setText(R.id.done_time, DateUtils.getTime(item.getTrans_time(),
                    DateUtils.PATTERN_3) + "");
        } else {
            helper.getView(R.id.ll_done_time).setVisibility(View.GONE);

        }

        if (LocalConfigStore.getInstance().ifMaster()) {
            helper.getView(R.id.ll_order_buttons).setVisibility(View.GONE);

            ((TextView) helper.getView(R.id.order_status)).setTextColor(Color.parseColor("#4D5359"
            ));
            helper.getView(R.id.order_countdown_timer).setVisibility(View.GONE);

            tempStatus = item.getStatus();
            if (tempStatus == Constants.MASTER_ORDER_STATUS_WAIT_ANSWER) {
                helper.getView(R.id.ll_paid_time).setVisibility(View.VISIBLE);

                ((TextView) helper.getView(R.id.order_status)).setTextColor(Color.parseColor(
                        "#B26868"));
//                ViewUtils.countDown(((TextView) helper.getView(R.id.order_countdown_timer)),
//                        item.getEnd_time());
                helper.setText(R.id.order_countdown_timer, DateUtils.secToTime(item.getEnd_time()));
                helper.getView(R.id.order_countdown_timer).setVisibility(View.VISIBLE);


            } else if (tempStatus == Constants.MASTER_ORDER_STATUS_ALL) {
                helper.getView(R.id.ll_paid_time).setVisibility(View.VISIBLE);
            }

            //所有已关闭的不显示付款时间
            if (StringUtil.getMasterOrderString(tempStatus,
                    helper.getView(R.id.order_status).getContext()).equals(helper.getView(R.id.order_status)
                    .getContext().getString(R.string.closed))) {
                helper.getView(R.id.ll_paid_time).setVisibility(View.GONE);

            }

            helper.setText(R.id.order_status, StringUtil.getMasterOrderString(item.getStatus(),
                    helper.getView(R.id.order_status).getContext()));
        } else {
//            int USER_ORDER_STATUS_NOT_PAID = 1;
//            int USER_ORDER_STATUS_WAIT_ANSWER = 2;
//            int USER_ORDER_STATUS_WAIT_COMMENT = 3;
//            int USER_ORDER_STATUS_CLOSED = 4;
//            int USER_ORDER_STATUS_COMPLETED = 5;
            //1待支付,2待解答,3待评价,4已关闭,5交易完成
            tempStatus = item.getStatus();
            helper.setText(R.id.order_status, StringUtil.getUserOrderString(item.getStatus(),
                    helper.getView(R.id.ll_order_buttons).getContext()));
            ((TextView) helper.getView(R.id.order_status)).setTextColor(Color.parseColor("#4D5359"
            ));
            helper.getView(R.id.order_countdown_timer).setVisibility(View.GONE);
            if (tempStatus == Constants.USER_ORDER_STATUS_NOT_PAID) {

                helper.getView(R.id.ll_order_buttons).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_cancel).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_go2_pay).setVisibility(View.VISIBLE);
                helper.getView(R.id.go_2_comment).setVisibility(View.GONE);

                ((TextView) helper.getView(R.id.order_status)).setTextColor(Color.parseColor(
                        "#B26868"));
//                ViewUtils.countDown(((TextView) helper.getView(R.id.order_countdown_timer)),
//                item.getEnd_time());
                //根据下单时间计算

                helper.setText(R.id.order_countdown_timer, DateUtils.secToTime(item.getEnd_time()));

                helper.getView(R.id.order_countdown_timer).setVisibility(View.VISIBLE);

            } else if (tempStatus == Constants.USER_ORDER_STATUS_WAIT_ANSWER) {
                helper.getView(R.id.ll_order_buttons).setVisibility(View.GONE);

                ((TextView) helper.getView(R.id.order_status)).setTextColor(Color.parseColor(
                        "#5C7710"));

            } else if (tempStatus == Constants.USER_ORDER_STATUS_WAIT_COMMENT) {

                helper.getView(R.id.ll_order_buttons).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_cancel).setVisibility(View.GONE);
                helper.getView(R.id.tv_go2_pay).setVisibility(View.GONE);
                helper.getView(R.id.go_2_comment).setVisibility(View.VISIBLE);


            } else if (tempStatus == Constants.USER_ORDER_STATUS_CLOSED) {
                helper.getView(R.id.ll_order_buttons).setVisibility(View.GONE);
                helper.getView(R.id.ll_paid_time).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel).setVisibility(View.GONE);
                helper.getView(R.id.tv_go2_pay).setVisibility(View.GONE);

            } else if (tempStatus == Constants.USER_ORDER_STATUS_CANCELED) {
                helper.getView(R.id.ll_paid_time).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel).setVisibility(View.GONE);
                helper.getView(R.id.tv_go2_pay).setVisibility(View.GONE);

            } else if (tempStatus == Constants.USER_ORDER_STATUS_REJECT) {
                helper.getView(R.id.ll_paid_time).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel).setVisibility(View.GONE);
                helper.getView(R.id.tv_go2_pay).setVisibility(View.GONE);
            } else if (tempStatus == Constants.USER_ORDER_STATUS_OVER_TIME) {
                helper.getView(R.id.ll_paid_time).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel).setVisibility(View.GONE);
                helper.getView(R.id.tv_go2_pay).setVisibility(View.GONE);
            } else if (tempStatus == Constants.USER_ORDER_STATUS_COMPLETED) {
                helper.getView(R.id.ll_order_buttons).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel).setVisibility(View.GONE);
                helper.getView(R.id.tv_go2_pay).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.ll_order_buttons).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel).setVisibility(View.GONE);
                helper.getView(R.id.tv_go2_pay).setVisibility(View.GONE);
            }


        }

    }

    public interface OrderItem {
        void onOrderDetail(OrderResultEntity item);

        void onCancelOrder(OrderResultEntity item);

        void onGo2Pay(OrderResultEntity item);

        void onGo2Comment(OrderResultEntity item);

    }


}
