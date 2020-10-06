package com.zgzx.metaphysics.ui.adapters;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mondo.logger.Logger;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.MasterServiceItemEntity;
import com.zgzx.metaphysics.utils.NumberUtil;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;


public class MasterServiceAdapter extends BaseQuickAdapter<MasterServiceItemEntity, BaseViewHolder> {

    public MasterServiceAdapter() {
        super(R.layout.recycle_item_master_service);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MasterServiceItemEntity item) {
        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_price, "¥ " + NumberUtil.format(item.getPrice()));

        helper.itemView.setSelected(item.isSelected());
        Logger.i("selected %s", item);

        // 服务图片
        ImageView ivService = helper.getView(R.id.iv_service);
        GlideApp.with(ivService)
                .load(item.getIcon())
                .normal()
                .into(ivService);
    }


    public void selected(int position) {
        List<MasterServiceItemEntity> data = getData();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setSelected(i == position);
        }

        notifyDataSetChanged();
    }

}
