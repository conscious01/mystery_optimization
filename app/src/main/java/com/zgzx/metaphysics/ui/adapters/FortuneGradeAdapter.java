package com.zgzx.metaphysics.ui.adapters;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.DailyQuestionEntity;
import com.zgzx.metaphysics.model.entity.FortuneGradeEntity;

import java.util.List;

public class FortuneGradeAdapter extends BaseQuickAdapter<FortuneGradeEntity, BaseViewHolder> {
    private Context mContext;

    public FortuneGradeAdapter(@Nullable List<FortuneGradeEntity> data, Context mContext) {
        super(R.layout.recycle_fortune_grade_item_layout, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FortuneGradeEntity item) {
        helper.setText(R.id.tv_fortune_name, item.getName());
        ProgressBar progressBar = helper.getView(R.id.fortunr_progressbar);
        progressBar.setProgress(item.getScore());
        if ("运气".equals(item.getName()) || "運氣".equals(item.getName())) {
            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.draw_fortune_bg_1));
        } else if ("爱情".equals(item.getName()) || "愛情".equals(item.getName())) {
            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.draw_fortune_bg_2));
        } else if ("事业".equals(item.getName()) || "事業".equals(item.getName())) {
            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.draw_fortune_bg_3));
        } else if ("财运".equals(item.getName()) || "財運".equals(item.getName())) {
            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.draw_fortune_bg_4));
        } else if ("人际".equals(item.getName()) || "人際".equals(item.getName())) {
            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.draw_fortune_bg_5));
        } else if ("健康".equals(item.getName()) || "健康".equals(item.getName())) {
            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.draw_fortune_bg_6));
        }
    }
}
