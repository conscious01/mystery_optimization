package com.zgzx.metaphysics.ui.adapters;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.InviteListEntity;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.utils.DateUtils;

/**
 * 我的邀请
 */
public class MyInviteAdapter extends BaseQuickAdapter<InviteListEntity, BaseViewHolder> {

    private int role;

    public MyInviteAdapter(int role) {
        super(R.layout.recycle_item_visitor);
        this.role = role;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InviteListEntity item) {
        if (role == WebApiConstants.SUPER_SHAREHOLDER_ROLE){  // 大股东（超级股东）
            helper.setText(R.id.tv_uid, parseStringToStar(String.valueOf(item.getId())))
                    .setText(R.id.tv_today, String.valueOf(item.getToday())) // 今日邀请人数
                    .setText(R.id.tv_date, String.valueOf(item.getTotal())); // 累计邀请人数

        } else { // 普通用户,合伙人

            helper.setText(R.id.tv_uid, parseStringToStar(String.valueOf(item.getId())))
                    .setGone(R.id.tv_today, false)
                    .setText(R.id.tv_date, DateUtils.getTime(item.getCreate_time(), DateUtils.PATTERN_3)); // 注册时间
        }
    }

    // TODO 待优化
    private String parseStringToStar(String str) {
        StringBuilder sb = new StringBuilder();

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i > 0 && chars.length - i > 3) {
                sb.append("*");

            } else {
                sb.append(chars[i]);
            }
        }

        return sb.toString();
    }

}
