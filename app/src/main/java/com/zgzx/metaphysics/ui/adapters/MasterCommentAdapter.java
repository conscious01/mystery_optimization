package com.zgzx.metaphysics.ui.adapters;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.MasterCommentEntity;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.image.GlideApp;

import androidx.annotation.NonNull;


public class MasterCommentAdapter extends BaseQuickAdapter<MasterCommentEntity.ItemsBean,
        BaseViewHolder> {

    public MasterCommentAdapter() {
        super(R.layout.recycle_item_master_comment);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MasterCommentEntity.ItemsBean item) {


        // 服务图片
        ImageView ivService = helper.getView(R.id.iv_master_photo);
        GlideApp.with(ivService)
                .load(item.getAvatar())
                .normal()
                .into(ivService);


        helper.setText(R.id.tv_name, item.getNickname());

        helper.setText(R.id.tv_time, DateUtils.getTime(item.getCreate_time(), DateUtils.PATTERN_2));


        setStar(helper, item.getScore());


    }

    private void setStar(BaseViewHolder helper, int score) {
        if (score == 1) {

            helper.getView(R.id.iv1).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv2).setVisibility(View.GONE);
            helper.getView(R.id.iv3).setVisibility(View.GONE);
            helper.getView(R.id.iv4).setVisibility(View.GONE);
            helper.getView(R.id.iv5).setVisibility(View.GONE);


        } else if (score == 2) {

            helper.getView(R.id.iv1).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv2).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv3).setVisibility(View.GONE);
            helper.getView(R.id.iv4).setVisibility(View.GONE);
            helper.getView(R.id.iv5).setVisibility(View.GONE);


        } else if (score == 3) {
            helper.getView(R.id.iv1).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv2).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv3).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv4).setVisibility(View.GONE);
            helper.getView(R.id.iv5).setVisibility(View.GONE);

        } else if (score == 4) {
            helper.getView(R.id.iv1).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv2).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv3).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv4).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv5).setVisibility(View.GONE);

        } else if (score == 5) {
            helper.getView(R.id.iv1).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv2).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv3).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv4).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv5).setVisibility(View.VISIBLE);

        } else {
            helper.getView(R.id.iv1).setVisibility(View.GONE);
            helper.getView(R.id.iv2).setVisibility(View.GONE);
            helper.getView(R.id.iv3).setVisibility(View.GONE);
            helper.getView(R.id.iv4).setVisibility(View.GONE);
            helper.getView(R.id.iv5).setVisibility(View.GONE);
        }
    }


}
