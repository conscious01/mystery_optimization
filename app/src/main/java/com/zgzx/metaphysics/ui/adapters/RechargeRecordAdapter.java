package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.RechargeRecordEntity;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.NumberUtil;

import java.util.List;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/20
 * @Description: 充值记录
 */
public class RechargeRecordAdapter extends BaseQuickAdapter<RechargeRecordEntity, BaseViewHolder> {

    private int role;

    public RechargeRecordAdapter(int role) {
        super(R.layout.recycle_item_recharge_record);
        this.role = role;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RechargeRecordEntity item) {
        float today = item.getToday(); // 今日充值
        float total = item.getTotal(); // 累计充值
        float amount = item.getAmount(); // 充值金额
        int create_time = item.getCreate_time(); // 充值时间

        helper.setText(R.id.tv_uid, parseStringToStar(String.valueOf(item.getId())));

        if (role == WebApiConstants.SUPER_SHAREHOLDER_ROLE) {
            helper.setText(R.id.tv_recharge_price, "¥"+NumberUtil.format(today))
                    .setText(R.id.tv_total_price, "¥"+NumberUtil.format(total));
        } else {
            helper.setText(R.id.tv_recharge_price, "¥"+NumberUtil.format(amount))
                    .setText(R.id.tv_total_price, DateUtils.getTime(create_time, DateUtils.PATTERN_3));
        }

    }

    private String parseStringToStar(String str) {
        StringBuilder sb = new StringBuilder();

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i > 0 && chars.length - i > 3) {
                sb.append("*");

            } else {
                sb.append(chars[i]);
            }
        }

        return sb.toString();
    }
}
