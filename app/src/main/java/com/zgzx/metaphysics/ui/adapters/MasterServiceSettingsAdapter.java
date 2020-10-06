package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.MasterServiceSettingEntity;
import com.zgzx.metaphysics.utils.NumberUtil;

/**
 * 师傅服务设置
 */
public class MasterServiceSettingsAdapter extends BaseQuickAdapter<MasterServiceSettingEntity.ItemsBean, BaseViewHolder> {

    public MasterServiceSettingsAdapter() {
        super(R.layout.recycle_item_master_service_settings);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MasterServiceSettingEntity.ItemsBean item) {
        MasterServiceSettingEntity.ItemsBean.ItemTypeBean type = item.getItem_type();
        helper.setText(R.id.tv_name, type.getName())
                .setText(R.id.et_price, NumberUtil.format(item.getCharge_amount()));
    }

}
