package com.zgzx.metaphysics.ui.adapters;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.MasterDetailEntityNew;
import com.zgzx.metaphysics.utils.image.GlideApp;

import androidx.annotation.NonNull;

/**
 * 师傅照片
 */
public class MasterPhotoAdapter extends BaseQuickAdapter<MasterDetailEntityNew.PhotosBean, BaseViewHolder> {

    public MasterPhotoAdapter() {
        super(R.layout.recycle_item_master_photo);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MasterDetailEntityNew.PhotosBean item) {
        ImageView ivMasterPhoto = helper.getView(R.id.iv_master_photo);

        GlideApp.with(ivMasterPhoto)
                .load(item.getPath())
                .miniThumb()
                .into(ivMasterPhoto);
    }

}
