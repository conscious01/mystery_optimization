package com.zgzx.metaphysics.ui.adapters;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;

import java.util.List;

public class FortuneLuckLocationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FortuneLuckLocationAdapter(List<String> data) {
        super(R.layout.recycle_item_luck_location, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        Resources resources = helper.itemView.getContext().getResources();
        String[] array = resources.getStringArray(R.array.fortune_location);

        helper.setText(R.id.tv_location, array[helper.getLayoutPosition()])
                .setText(R.id.tv_value, item);
    }

}
