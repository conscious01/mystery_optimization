package com.zgzx.metaphysics.ui.adapters;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.model.entity.VipTypeEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

public class VipMemberCenterAdapter extends BaseQuickAdapter<VipTypeEntity.DataJsonBean, BaseViewHolder> {
    public VipMemberCenterAdapter(List<VipTypeEntity.DataJsonBean> data) {
        super(R.layout.recycle_item_vip_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VipTypeEntity.DataJsonBean item) {
        helper.setText(R.id.tv_name, item.getName());
        ImageView ivImage = helper.getView(R.id.iv_image);
        String icons = item.getIcon();
        if (icons != null && !icons.isEmpty()) {
            GlideApp.with(ivImage)
                    .load(icons)
                    .miniThumb()
                    .into(ivImage);
        }
    }
}
