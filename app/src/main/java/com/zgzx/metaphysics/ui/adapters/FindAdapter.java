package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.ArticleEntity;
import com.zgzx.metaphysics.model.entity.ArticleListEntity;

import java.util.List;

public class FindAdapter extends BaseQuickAdapter<ArticleListEntity.ItemsBean, BaseViewHolder> {
    public FindAdapter(@Nullable List<ArticleListEntity.ItemsBean> data) {
        super(R.layout.recycle_find_item_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleListEntity.ItemsBean item) {
        helper.setText(R.id.tv_find_title, item.getTitle());
        helper.setText(R.id.tv_find_content, item.getDesc());
        helper.setText(R.id.tv_find_num, String.valueOf(item.getLook_num()));
    }
}
