package com.zgzx.metaphysics.ui.adapters;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FortuneListImageEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

public class FortuneBagShareAdapter extends BaseQuickAdapter<FortuneListImageEntity, BaseViewHolder> {
    public FortuneBagShareAdapter(@Nullable List<FortuneListImageEntity> data) {
        super(R.layout.recycle_fortune_bag_item_share_layout,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FortuneListImageEntity item) {
        helper.setText(R.id.tv_name,item.getName());
        ImageView image_view = helper.getView(R.id.image_view);
        GlideApp.with(image_view)
                .load(item.getUrl())
                .into(image_view);
    }
}
