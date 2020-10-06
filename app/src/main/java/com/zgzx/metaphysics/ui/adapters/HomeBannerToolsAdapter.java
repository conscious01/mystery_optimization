package com.zgzx.metaphysics.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.adapter.BannerAdapter;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.HomeFouncationEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

public class HomeBannerToolsAdapter extends BannerAdapter<HomeFouncationEntity.BannerBean, BaseViewHolder> {
    public HomeBannerToolsAdapter(List<HomeFouncationEntity.BannerBean> datas) {
        super(datas);
    }

    @Override
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_item_home, parent, false));
    }

    @Override
    public void onBindView(BaseViewHolder holder, HomeFouncationEntity.BannerBean data, int position, int size) {
        ImageView imageView = holder.getView(R.id.image_view);

        GlideApp.with(imageView)
                .load(data.getIcon())
                .into(imageView);
    }
}


