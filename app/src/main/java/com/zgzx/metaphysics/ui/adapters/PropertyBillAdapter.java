package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mondo.logger.Logger;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.PropertyBillEntity;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.NumberUtil;

/**
 * 财务记录
 */
public class PropertyBillAdapter extends BaseQuickAdapter<PropertyBillEntity, BaseViewHolder> {

    public PropertyBillAdapter() {
        super(R.layout.recycle_item_property_bill);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PropertyBillEntity item) {
        Logger.i(String.valueOf(item.getAmount()));
        String num;
        if (item.getMoney_type()==6){
            num= "¥"+NumberUtil.format(item.getAmount());
        }else {
            num=NumberUtil.format(item.getAmount());
        }

        helper.setText(R.id.tv_date, DateUtils.getTime(item.getCreate_time(), DateUtils.PATTERN_5))
                .setText(R.id.tv_status, R.string.completed)
                // opt 1增加, 2减少
                .setText(R.id.tv_amount, (item.getOpt() == 1 ? "+" : "-")+num)
                .setText(R.id.tv_type, item.getRemark());
    }

}
