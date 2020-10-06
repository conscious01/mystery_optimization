package com.zgzx.metaphysics.ui.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.DailyQuestionEntity;

import java.util.List;

import androidx.annotation.NonNull;

public class HomeAskQuestionAdapter extends BaseQuickAdapter<DailyQuestionEntity, BaseViewHolder> {
    public HomeAskQuestionAdapter(List<DailyQuestionEntity> data) {
        super(R.layout.recycle_ask_item_layout,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DailyQuestionEntity item) {
       helper.setText(R.id.tv_ask,item.getQuestion());
    }
}
