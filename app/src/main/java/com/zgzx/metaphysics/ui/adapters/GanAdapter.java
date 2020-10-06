package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.GanEntity;
import com.zgzx.metaphysics.model.entity.MasterServiceItemEntity;

import java.util.List;

public class GanAdapter extends BaseQuickAdapter<GanEntity, BaseViewHolder> {
    public GanAdapter(List<GanEntity> data) {
        super(R.layout.recycle_gan_item_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GanEntity item) {
        helper.setText(R.id.tv_gan_text, item.getName());
        if (item.isSelect()) {
            helper.setBackgroundRes(R.id.tv_gan_text, R.drawable.icon_home_scyj_scd);
        }else {
            helper.setBackgroundRes(R.id.tv_gan_text, R.color.state_layout_background_color);
        }

    }
    public void selected(int position) {
        List<GanEntity> data = getData();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setSelect(i == position);
        }
        notifyDataSetChanged();
    }
}
