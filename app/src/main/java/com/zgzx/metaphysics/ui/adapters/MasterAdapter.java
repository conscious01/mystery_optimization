package com.zgzx.metaphysics.ui.adapters;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.MasterServiceEntity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import androidx.annotation.NonNull;


/**
 * 发现，师傅列表
 */
public class MasterAdapter extends BaseQuickAdapter<MasterServiceEntity, BaseViewHolder> {

    public MasterAdapter() {
        super(R.layout.recycle_item_master_image);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MasterServiceEntity item) {


        // 头像
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        GlideApp.with(ivAvatar)
                .load(item.getAvatar())
                .squareAvatar()
                .into(ivAvatar);


//        helper.setText(R.id.tv_name, item.getMaster_name())
//                .setRating(R.id.rating_bar, item.getScore())
//                .setText(R.id.tv_price, "¥ " + NumberUtil.format(item.getCharge_amount()))
//                .setText(R.id.tv_introduction, item.getIntro())
//                .setText(R.id.tv_number_answers, "已解答 " + item.getAnswer_num())
//                .setText(R.id.tv_score, String.valueOf(item.getScore()));
//
//
//
//        // 师傅状态
//        helper.setEnabled(R.id.but_advisory,
//                item.getStatus() == MASTER_NORMAL_STATUS) // 1正常;2暂停
//                .setText(R.id.but_advisory, item.getStatus() == MASTER_NORMAL_STATUS
//                        ? R.string.consult_now : R.string.suspend_order);
    }

}
