package com.zgzx.metaphysics.ui.adapters;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.model.entity.MasterServiceItemEntity;
import com.zgzx.metaphysics.model.entity.RechargeActivitiesEntity;
import com.zgzx.metaphysics.utils.NumberUtil;

import java.util.List;

public class RechargeActivityAdapter extends BaseQuickAdapter<RechargeActivitiesEntity, BaseViewHolder> {
    public RechargeActivityAdapter(List<RechargeActivitiesEntity> data) {
        super(R.layout.recycleview_item_rechage_kmz_layout,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RechargeActivitiesEntity item) {
        helper.setText(R.id.regchar_money, NumberUtil.format(item.getPrice()));
        Resources resources = helper.itemView.getContext().getResources();
        String give = resources.getString(R.string.give);
        if (item.getGive_num() > 0) {
            helper.setText(R.id.regchar_tips, item.getCoin_num() + give + item.getGive_num());
        } else {
            helper.setText(R.id.regchar_tips, item.getCoin_num()+"");
        }
        helper.itemView.setSelected(item.isSelected());
    }
    public void selected(int position) {
        List<RechargeActivitiesEntity> data = getData();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setSelected(i == position);
        }

        notifyDataSetChanged();
    }
}
