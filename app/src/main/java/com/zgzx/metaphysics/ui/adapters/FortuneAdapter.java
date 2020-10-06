package com.zgzx.metaphysics.ui.adapters;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FortuneAdapter extends BaseQuickAdapter<Float, BaseViewHolder> {

    public FortuneAdapter(List<Float> data) {
        super(R.layout.recycle_item_fortune, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Float item) {
        Resources resources = helper.itemView.getContext().getResources();
        String[] array = resources.getStringArray(R.array.fortune_today);

        helper.setText(R.id.tv_name, array[helper.getLayoutPosition()] + "：")
                .setRating(R.id.rating_bar, item);
    }
}
