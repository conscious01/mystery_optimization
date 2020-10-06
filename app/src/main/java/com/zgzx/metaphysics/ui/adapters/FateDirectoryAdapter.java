package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;

import java.util.List;

public class FateDirectoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FateDirectoryAdapter(List<String> data) {
        super(R.layout.recycle_item_fate_book_directory, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_chapter, "第" + (helper.getLayoutPosition() + 1) + "章")
                .setText(R.id.tv_title, item);
    }

}
