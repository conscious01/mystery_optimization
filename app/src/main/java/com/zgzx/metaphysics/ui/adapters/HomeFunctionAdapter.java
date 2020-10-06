package com.zgzx.metaphysics.ui.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.HomeDataEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/23
 * @Description: 功能
 */
public class HomeFunctionAdapter extends BaseQuickAdapter<HomeDataEntity.EntryBean, BaseViewHolder> {

    public HomeFunctionAdapter(List<HomeDataEntity.EntryBean> data) {
        super(R.layout.recycle_item_home_function, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeDataEntity.EntryBean item) {

        helper.setText(R.id.tv_name, item.getName());

        ImageView imageView = helper.getView(R.id.image_view);

        GlideApp.with(imageView).load(item.getIcon()).into(imageView);
    }

}
