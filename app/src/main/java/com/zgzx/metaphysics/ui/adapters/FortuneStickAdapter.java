package com.zgzx.metaphysics.ui.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;

import java.util.Arrays;
import java.util.List;

/**
 * 运势
 */
public class FortuneStickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FortuneStickAdapter(List<String> data) {
        super(R.layout.recycle_item_stick, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
    }
}
