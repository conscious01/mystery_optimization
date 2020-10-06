package com.zgzx.metaphysics.ui.adapters;

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
 * @Description: 首页，小工具
 */
public class HomeToolAdapter extends BaseQuickAdapter<HomeDataEntity.ToolBean, BaseViewHolder> {

    public HomeToolAdapter(@Nullable List<HomeDataEntity.ToolBean> data) {
        super(R.layout.recycle_item_home_tool, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeDataEntity.ToolBean item) {
        ImageView imageView = helper.getView(R.id.image_view);
        GlideApp.with(imageView).load(item.getBanner()).into(imageView);
    }
}
