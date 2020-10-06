package com.zgzx.metaphysics.ui.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.HomeDataEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/23
 * @Description: 首页推荐师傅
 */
public class HomeMasterAdapter extends BaseQuickAdapter<HomeDataEntity.MasterBean, BaseViewHolder> {

    public HomeMasterAdapter(@Nullable List<HomeDataEntity.MasterBean> data) {
        super(R.layout.recycle_item_home_master, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeDataEntity.MasterBean item) {
        ImageView imageView = helper.getView(R.id.image_view);
        GlideApp.with(imageView).load(item.getBanner()).into(imageView);
    }

}
