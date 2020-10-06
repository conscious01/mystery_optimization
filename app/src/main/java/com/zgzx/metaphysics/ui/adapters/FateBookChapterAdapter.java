package com.zgzx.metaphysics.ui.adapters;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

/**
 * 命书
 */
public class FateBookChapterAdapter extends BaseQuickAdapter<FateBookTypeEntity, BaseViewHolder> {

    private int mSelectedPosition;

    public FateBookChapterAdapter(List<FateBookTypeEntity> data) {
        super(R.layout.recycle_item_fate_book_chapter, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FateBookTypeEntity item) {
        helper.setText(R.id.tv_name, item.getName());

        TextView tvName = helper.getView(R.id.tv_name);

        // 选择
        tvName.setSelected(mSelectedPosition == helper.getLayoutPosition());

        ImageView ivImage = helper.getView(R.id.iv_image);
        List<String> icons = item.getIcons();
        if (icons != null && !icons.isEmpty()) {

            if (item.getIs_buy() == 1){ // 购买
                GlideApp.with(ivImage)
                        .load(icons.get(0))
                        .miniThumb()
                        .into(ivImage);

                ivImage.setAlpha(mSelectedPosition == helper.getLayoutPosition() ? 1.0F : 0.6F);

            } else {
                GlideApp.with(ivImage)
                        .load(mSelectedPosition == helper.getLayoutPosition() ? icons.get(1) : icons.get(2))
                        .miniThumb()
                        .into(ivImage);
            }
        }

    }

    public void selected(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();
    }

}
