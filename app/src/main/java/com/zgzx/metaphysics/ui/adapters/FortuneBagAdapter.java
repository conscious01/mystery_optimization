package com.zgzx.metaphysics.ui.adapters;

import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FortuneGradeEntity;
import com.zgzx.metaphysics.model.entity.FortuneListImageEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

public class FortuneBagAdapter extends BaseQuickAdapter<FortuneListImageEntity, BaseViewHolder> {
    public FortuneBagAdapter(@Nullable List<FortuneListImageEntity> data) {
        super(R.layout.recycle_fortune_bag_item_layout,data);
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
