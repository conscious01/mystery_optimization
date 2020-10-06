package com.zgzx.metaphysics.ui.adapters;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FateBooksEntity;

public class FateBookAdapter extends BaseQuickAdapter<FateBooksEntity, BaseViewHolder> {

    private boolean isEdit;
    private int mItemWidth;

    public FateBookAdapter() {
        super(R.layout.recycle_item_fate_book);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        DisplayMetrics displayMetrics = recyclerView.getContext().getResources().getDisplayMetrics();
        mItemWidth = (int) (displayMetrics.widthPixels * 0.85);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.width = mItemWidth;
        viewHolder.itemView.setLayoutParams(layoutParams);
        return viewHolder;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FateBooksEntity item) {
//        FateBooksEntity.AbstractBean bean = item.getAbstractX();
//        FateBooksEntity.AbstractBean.Bean horoscope = bean.getHoroscope(); // 星座
//        FateBooksEntity.AbstractBean.Bean zodiac = bean.getZodiac(); // 生肖
//        FateBooksEntity.AbstractBean.BaziBean bazi = bean.getBazi(); // 八字
//        FateBooksEntity.AbstractBean.Bean numerology = bean.getNumerology(); // 生命灵数
//        FateBooksEntity.AbstractBean.StarBean star = bean.getStar(); // 人命封
//
//        Resources resources = helper.itemView.getResources();
//
//        // 数据
//        helper.setText(R.id.tv_name, item.getReal_name())
//                .setText(R.id.tv_constellation,
//                        resources.getString(R.string.signed_constellation) + horoscope.getValue())
//                .setText(R.id.tv_constellation_detail,
//                        resources.getString(R.string.signed_similar_constellation) + TextUtils.join(",", horoscope.getMatch()))
//                .setText(R.id.tv_zodiac,
//                        resources.getString(R.string.signed_zodiac) + zodiac.getValue())
//                .setText(R.id.tv_zodiac_detail,
//                        resources.getString(R.string.signed_similar_zodiac) + TextUtils.join(",", zodiac.getMatch()))
//                .setText(R.id.tv_character,
//                        resources.getString(R.string.signed_character) + TextUtils.join(",", bazi.getValue()))
//                .setText(R.id.tv_character_detail,
//                        resources.getString(R.string.signed_similar_character) + TextUtils.join(",", bazi.getMatch()))
//                .setText(R.id.tv_seal,
//                        resources.getString(R.string.signed_life_gua) + star.getValue())
//                .setText(R.id.tv_seal_detail,
//                        resources.getString(R.string.signed_harmony_life) + TextUtils.join(",", star.getMatch()))
//                .setText(R.id.tv_spirit_number,
//                        resources.getString(R.string.signed_vitality) + numerology.getValue())
//                .setText(R.id.tv_spirit_number_detail,
//                        resources.getString(R.string.signed_similar_harmony) + TextUtils.join(",", numerology.getMatch()))
//                .addOnClickListener(R.id.tv_enter_fate_book, R.id.iv_delete);
//
//        // 删除
//        helper.setGone(R.id.iv_delete, isEdit && item.getIs_my() != 1);
    }

    public void edit(boolean edit) {
        this.isEdit = edit;
    }

}
