package com.zgzx.metaphysics.ui.adapters;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;

import java.util.List;

public class CalendarDayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CalendarDayAdapter(List<String> data) {
        super(R.layout.recycleview_item_home_text, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        if (!TextUtils.isEmpty(item)){
            helper.setText(R.id.tv_yj_text, item);
        }else {
            helper.getView(R.id.tv_yj_text).setVisibility(View.GONE);
        }

    }
}
