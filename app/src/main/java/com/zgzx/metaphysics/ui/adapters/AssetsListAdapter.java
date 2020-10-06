package com.zgzx.metaphysics.ui.adapters;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;

import java.util.Arrays;

@Deprecated
public class AssetsListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public AssetsListAdapter() {
        super(R.layout.recycle_item_assets, Arrays.asList(null, null));
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Object item) {
        helper.setBackgroundColor(R.id.iv_token, Color.RED)
                .setText(R.id.tv_token_name, "ETH")
                .setText(R.id.tv_amount, "12.2");
    }
}
