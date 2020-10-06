package com.zgzx.metaphysics.ui.adapters;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FortuneGradeEntity;

import java.util.List;

public class FortuneDeatailAdapter extends BaseQuickAdapter<FortuneGradeEntity, BaseViewHolder> {
    private Context mContext;
    public FortuneDeatailAdapter(List<FortuneGradeEntity> data,Context mContext) {
        super(R.layout.recycle_fortune_deatil_item_grade_layout,data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FortuneGradeEntity item) {
        helper.setText(R.id.fortune_name,item.getName());
        helper.setText(R.id.fortune_grade,String.valueOf(item.getScore()));
        ImageView img = helper.getView(R.id.img_lable);
        if ("运气".equals(item.getName()) || "運氣".equals(item.getName())) {
            img.setBackground(mContext.getResources().getDrawable(R.drawable.shape_e9d9cf_circle));
        } else if ("爱情".equals(item.getName()) || "愛情".equals(item.getName())) {
            img.setBackground(mContext.getResources().getDrawable(R.drawable.shape_f39689_circle));
        } else if ("事业".equals(item.getName()) || "事業".equals(item.getName())) {
            img.setBackground(mContext.getResources().getDrawable(R.drawable.shape_6ebde1_circle));
        } else if ("财运".equals(item.getName()) || "財運".equals(item.getName())) {
            img.setBackground(mContext.getResources().getDrawable(R.drawable.shape_e3d3a5_circle));
        } else if ("人际".equals(item.getName()) || "人際".equals(item.getName())) {
            img.setBackground(mContext.getResources().getDrawable(R.drawable.shape_78c792_circle));
        } else if ("健康".equals(item.getName()) || "健康".equals(item.getName())) {
            img.setBackground(mContext.getResources().getDrawable(R.drawable.shape_f5ab7a_circle));
        }
    }
}
