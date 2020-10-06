package com.zgzx.metaphysics.ui.adapters;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;

import java.util.Arrays;

public class MyQuestionsAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public MyQuestionsAdapter() {
        super(R.layout.recycle_item_my_questions, Arrays.asList(null, null, null, null));
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Object item) {
        helper.setText(R.id.tv_question_type, "婚姻情感")
                .setText(R.id.but_state, "待接单")
                .setText(R.id.tv_question, "哇库扑领")
                .setText(R.id.tv_master_name, "程师傅")
                .setText(R.id.tv_date, "2020-06-21 星期一 ｜ ")
                .setText(R.id.tv_time, "15:00-18:00")
                .setText(R.id.tv_tips, "已支付，去提问  >")
                .setBackgroundColor(R.id.iv_avatar, Color.RED);
    }

}
