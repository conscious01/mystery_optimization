package com.zgzx.metaphysics.ui.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FateBookDetailEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

/**
 * 命书详情
 */
public class FateBookDetailPagerAdapter extends PagerAdapter {

    private List<FateBookDetailEntity.DataBean> mData;

    public FateBookDetailPagerAdapter(List<FateBookDetailEntity.DataBean> data) {
        this.mData = data;
    }

    public FateBookDetailEntity.DataBean getItem(int position){
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.pager_fate_book_detail, container, false);
        TextView tvContent = view.findViewById(R.id.tv_content);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        ImageView ivImage = view.findViewById(R.id.iv_image);

        FateBookDetailEntity.DataBean bean = mData.get(position);

        tvTitle.setText(bean.getTitle()); // 标题
        tvContent.setText(TextUtils.join("\n", bean.getComment())); // 内容

        // 图标
        GlideApp.with(ivImage)
                .load(bean.getIcon())
                .override(720)
                .into(ivImage);

        container.addView(view);
        return view;
    }
}
