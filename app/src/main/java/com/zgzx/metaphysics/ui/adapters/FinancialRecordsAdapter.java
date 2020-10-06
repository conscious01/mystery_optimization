package com.zgzx.metaphysics.ui.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;

import java.util.Arrays;

import androidx.annotation.NonNull;

@Deprecated
public class FinancialRecordsAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public FinancialRecordsAdapter() {
        super(R.layout.recycle_item_financial_records, Arrays.asList(null, null, null));
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Object item) {
        helper.setText(R.id.tv_type, "提币")
                .setText(R.id.tv_amount, "-100 USDT")
                .setText(R.id.tv_date, "2020.10.09 24:10:10")
                .setText(R.id.tv_status, "已完成");
    }
}
