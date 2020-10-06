package com.zgzx.metaphysics.ui.adapters;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.model.entity.RechargeActivitiesEntity;
import com.zgzx.metaphysics.model.entity.VipTypeEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

public class VipNavAdapter extends BaseQuickAdapter<VipTypeEntity, BaseViewHolder> {
    public VipNavAdapter(List<VipTypeEntity> data) {
        super(R.layout.recycle_item_vip_nav_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VipTypeEntity item) {
        helper.setText(R.id.tv_name,item.getName());
        ImageView ivImage = helper.getView(R.id.iv_image);
        String icons = item.getIcon();
        if (icons != null && !icons.isEmpty()){
            GlideApp.with(ivImage)
                    .load(icons)
                    .miniThumb()
                    .into(ivImage);
        }
        if (item.isSelect()){
            ivImage.setAlpha(1f);
        }else {
            ivImage.setAlpha(0.5f);
        }

    }
    public void selected(int position) {
        List<VipTypeEntity> data = getData();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setSelect(i == position);
        }

        notifyDataSetChanged();
    }
}
