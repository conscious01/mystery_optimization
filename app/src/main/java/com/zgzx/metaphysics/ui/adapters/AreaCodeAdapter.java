package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.AreaCodeEntity;

import java.util.Arrays;
import java.util.List;

public class AreaCodeAdapter extends BaseQuickAdapter<AreaCodeEntity, BaseViewHolder> {

    public AreaCodeAdapter() {
        super(R.layout.recycle_item_area_code);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AreaCodeEntity item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_code, item.getCode());
    }

}
