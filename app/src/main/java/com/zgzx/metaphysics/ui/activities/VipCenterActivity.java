package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.VipController;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.entity.VipTypeEntity;
import com.zgzx.metaphysics.ui.adapters.VipMemberCenterAdapter;
import com.zgzx.metaphysics.ui.adapters.VipNavAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.NumberUtil;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员中心
 */
public class VipCenterActivity extends BaseRequestActivity implements VipController.View, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;//头像
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_inter)
    ImageView iv_inter;//vip
    @BindView(R.id.tv_time_tips)
    TextView tv_time_tips;
    @BindView(R.id.inter_name_tv)
    TextView inter_name_tv;

    @BindView(R.id.go_pay_vip)
    Button go_pay_vip;

    @BindView(R.id.vip_nav_recycle)
    RecyclerView vip_nav_recycle;
    @BindView(R.id.vip_recycle)
    RecyclerView vip_recycle;//vip权益
    private float amount;
    private int vipPos;
    private int type;

    private final static String EXT_TYPE = "TYPE";
    private VipNavAdapter vipNavAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, VipCenterActivity.class);
    }

    @OnClick(value = {R.id.go_pay_vip})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.go_pay_vip:
                if (vipPos == 0) {
                    startActivity(SelectePayActivity.newIntent(this, amount, type));
                    finish();
                } else {
                    ToastUtils.showShort(getResources().getString(R.string.vip_tips));
                }

                break;

        }
    }

    @Override
    public void renderUserDetail(UserDetailEntity entity) {
        GlideApp.with(iv_avatar)
                .load(entity.getAvatar())
                .avatar()
                .into(iv_avatar);
        tv_name.setText(entity.getNickname());
        vipPos = entity.getVip_info().getId();
        switch (vipPos) {
            case 0://非会员
                tv_time_tips.setText(getResources().getString(R.string.member_center_tips));
                break;
            case 1://青铜
                configVip(entity, R.drawable.icon_bronze_type_bg_1, 1,R.string.bronze_vip_1);
                break;
            case 2://白银
                configVip(entity, R.drawable.icon_silver_type_bg_1, 2,R.string.silver_vip_1);
                break;
            case 3://黄金
                configVip(entity, R.drawable.icon_gold_type_bg_1, 3,R.string.gold_vip_1);
                break;
            case 4://钻石
                configVip(entity, R.drawable.icon_diamonds_type_bg_1, 4,R.string.diamonds_vip_1);
                break;
        }
    }

    private void configVip(UserDetailEntity entity, int drawable, int pos,int vipName) {
        if (pos == 4) {
            tv_time_tips.setText(getResources().getString(R.string.vip_txt_expire));
        } else {
            tv_time_tips.setText(DateUtils.getTime(entity.getVip_info().getEnd_time(), DateUtils.PATTERN_2) + getResources().getString(R.string.expire));
        }
        inter_name_tv.setText(getResources().getString(vipName));
        iv_inter.setBackgroundResource(drawable);
        iv_inter.setVisibility(View.VISIBLE);
        setPayBtnUnable(R.drawable.ic_but_bg_unenable);
    }

    @Override
    public void renderVipType(boolean isSelect, List<VipTypeEntity> data) {
        vipNavAdapter = new VipNavAdapter(data);
        vip_nav_recycle.setAdapter(vipNavAdapter);
        vipNavAdapter.setOnItemClickListener(this);

        if (vipPos == 0) {
            vipNavAdapter.selected(3);
            amount = data.get(3).getPrice();
            go_pay_vip.setText(getResources().getString(R.string.diamonds_vip) + " ¥" + NumberUtil.format(amount));
            inter_name_tv.setText(getResources().getString(R.string.diamonds_vip_1));
            type = 4;
            renderVipTypeContent(data.get(3).getData_json());
        } else {
            vipNavAdapter.selected(vipPos - 1);
            amount = data.get(vipPos - 1).getPrice();
            type = vipPos;
            renderVipTypeContent(data.get(vipPos - 1).getData_json());
        }

    }

    @Override
    public void renderVipTypeContent(List<VipTypeEntity.DataJsonBean> directory) {
        VipMemberCenterAdapter vipMemberCenterAdapter = new VipMemberCenterAdapter(directory);
        vip_recycle.setAdapter(vipMemberCenterAdapter);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        VipTypeEntity vipTypeEntity = vipNavAdapter.getData().get(position);
        vipNavAdapter.selected(position);
        amount = vipTypeEntity.getPrice();
        switch (position) {
            case 0://青铜
                setClickVipStatus(1, R.string.bronze_vip_1, R.string.bronze_vip);
                break;
            case 1://白银
                setClickVipStatus(2, R.string.silver_vip_1, R.string.silver_vip);
                break;
            case 2://黄金
                setClickVipStatus(3, R.string.gold_vip_1, R.string.gold_vip);
                break;
            case 3://钻石
                setClickVipStatus(4, R.string.diamonds_vip_1, R.string.diamonds_vip);
                break;
        }
        renderVipTypeContent(vipTypeEntity.getData_json());
    }

    /**
     * 设置点击状态，如果是当前会员显示已开通不能点击，否则显示价格点击提示暂不支持升级。
     *
     * @param i
     * @param vipName
     * @param btnText
     */
    private void setClickVipStatus(int i, int vipName, int btnText) {
        type = i;
        inter_name_tv.setText(getResources().getString(vipName));
        if (vipPos == i) {
            setPayBtnUnable(R.drawable.ic_but_bg_unenable);
        } else {
            setPayButton(btnText);
        }
    }


    /**
     * 会员按钮显示状态
     *
     * @param drawable
     */
    private void setPayBtnUnable(int drawable) {
        go_pay_vip.setBackground(getResources().getDrawable(drawable));
        go_pay_vip.setText(getResources().getText(R.string.vip_opend));
        go_pay_vip.setClickable(false);
    }

    /**
     * 非会员按钮显示状态
     *
     * @param text
     */
    private void setPayButton(int text) {
        go_pay_vip.setText(getResources().getString(text) + " ¥ " + NumberUtil.format(amount));
        go_pay_vip.setBackground(getResources().getDrawable(R.drawable.ic_but_bg));
        go_pay_vip.setClickable(true);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_member_center_layout;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, "VIP");
        VipController.Presenter presenter = new VipController.Presenter();
        presenter.setModelAndView(this);
        getLifecycle().addObserver(presenter);

    }

}
