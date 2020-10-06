package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.SystemMessageEntity;
import com.zgzx.metaphysics.utils.DateUtils;

public class SystemMessageAdapter extends BaseQuickAdapter<SystemMessageEntity, BaseViewHolder> {

    public SystemMessageAdapter() {
        super(R.layout.recycle_item_system_message);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SystemMessageEntity item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_date, DateUtils.getTime(item.getCreate_time(), DateUtils.PATTERN_2));
    }

}
