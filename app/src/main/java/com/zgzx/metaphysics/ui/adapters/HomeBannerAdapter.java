package com.zgzx.metaphysics.ui.adapters;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.adapter.BannerAdapter;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.MetaphysicsApplication;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.BannerEntity;
import com.zgzx.metaphysics.model.entity.HomeDataEntity;
import com.zgzx.metaphysics.ui.activities.DailyBlessActivity;
import com.zgzx.metaphysics.ui.activities.MainActivity;
import com.zgzx.metaphysics.ui.activities.WebViewActivity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/23
 * @Description: 首页广告图
 */
public class HomeBannerAdapter extends BannerAdapter<BannerEntity, BaseViewHolder> {

    public HomeBannerAdapter(List<BannerEntity> data) {
        super(data);
    }

    @Override
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_item_home, parent, false));
    }


    @Override
    public void onBindView(BaseViewHolder holder, BannerEntity data, int position, int size) {
        ImageView imageView = holder.getView(R.id.image_view);

        GlideApp.with(imageView)
                .load(data.getImage())
                .into(imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mbannerOnClickListener.BannerOnClickListener(position);
//            }
//        });
    }


//    private BannerOnClickListener mbannerOnClickListener;
//
//    public void BannerOnClickListener(BannerOnClickListener bannerOnClickListener) {
//        mbannerOnClickListener = bannerOnClickListener;
//    }
//
//    public interface BannerOnClickListener {
//        public void BannerOnClickListener(int position);
//    }
}
