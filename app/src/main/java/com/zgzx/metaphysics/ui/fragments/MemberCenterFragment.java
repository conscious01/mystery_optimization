package com.zgzx.metaphysics.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.DepositController;
import com.zgzx.metaphysics.controller.PayMethorController;
import com.zgzx.metaphysics.controller.VipController;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.entity.VipTypeEntity;
import com.zgzx.metaphysics.ui.activities.RechargeActivity;
import com.zgzx.metaphysics.ui.activities.SelectePayActivity;
import com.zgzx.metaphysics.ui.adapters.VipMemberCenterAdapter;
import com.zgzx.metaphysics.ui.adapters.VipNavAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.NumberUtil;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberCenterFragment extends BaseRequestFragment implements VipController.View, BaseQuickAdapter.OnItemClickListener {
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

    private VipNavAdapter vipNavAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member_center_layout;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

        VipController.Presenter presenter = new VipController.Presenter();
        presenter.setModelAndView(this);
        getLifecycle().addObserver(presenter);


    }
    @OnClick(value = {R.id.go_pay_vip})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.go_pay_vip:
                startActivity(SelectePayActivity.newIntent(getActivity(),amount,type));
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
        vipPos=entity.getVip_info().getId();
        switch (vipPos){
            case 0://非会员
                tv_time_tips.setText(getActivity().getResources().getString(R.string.member_center_tips));
                break;
            case 1://青铜
                configVip(entity, R.drawable.icon_bronze_type_bg_1,1);
                break;
            case 2://白银
                configVip(entity, R.drawable.icon_silver_type_bg_1,2);
                break;
            case 3://黄金
                configVip(entity, R.drawable.icon_gold_type_bg_1,3);
                break;
            case 4://钻石
                configVip(entity, R.drawable.icon_diamonds_type_bg_1,4);
                break;
        }
    }

    private void configVip(UserDetailEntity entity, int drawable,int pos) {
        if (pos==4)
        {
            tv_time_tips.setText(getActivity().getResources().getString(R.string.vip_txt_expire));
        }else {
            tv_time_tips.setText(DateUtils.getTime(entity.getVip_info().getEnd_time(), DateUtils.PATTERN_2) + getActivity().getResources().getString(R.string.expire));
        }

        iv_inter.setBackgroundResource(drawable);
        iv_inter.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderVipType(boolean isSelect, List<VipTypeEntity> data) {
         vipNavAdapter=new VipNavAdapter(data);
        vip_nav_recycle.setAdapter(vipNavAdapter);

        vipNavAdapter.setOnItemClickListener(this);
        go_pay_vip.setText(getActivity().getResources().getString(R.string.diamonds_vip)+" ¥"+ NumberUtil.format(amount));
        inter_name_tv.setText(getActivity().getResources().getString(R.string.diamonds_vip_1));
        type=4;
        if (vipPos==0){
            vipNavAdapter.selected(3);
            amount=data.get(3).getPrice();
            renderVipTypeContent(data.get(3).getData_json());
        }else {
            vipNavAdapter.selected(vipPos-1);
            amount=data.get(vipPos-1).getPrice();
            renderVipTypeContent(data.get(vipPos-1).getData_json());
        }

    }

    @Override
    public void renderVipTypeContent(List<VipTypeEntity.DataJsonBean> directory) {
        VipMemberCenterAdapter vipMemberCenterAdapter=new VipMemberCenterAdapter(directory);
        vip_recycle.setAdapter(vipMemberCenterAdapter);
    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        VipTypeEntity vipTypeEntity=vipNavAdapter.getData().get(position);
        vipNavAdapter.selected(position);
        amount=vipTypeEntity.getPrice();
        switch (position)
        {
            case 0://青铜
                type=1;
                setAmount(R.string.bronze_vip_1, R.string.bronze_vip);
                break;
            case 1://白银
                type=2;
                setAmount(R.string.silver_vip_1, R.string.silver_vip);
                break;
            case 2://黄金
                type=3;
                setAmount(R.string.gold_vip_1, R.string.gold_vip);
                break;
            case 3://钻石
                type=4;
                setAmount(R.string.diamonds_vip_1, R.string.diamonds_vip);
                break;
        }
        renderVipTypeContent(vipTypeEntity.getData_json());
    }

    private void setAmount(int vip_interests, int vip_grade) {
        inter_name_tv.setText(getActivity().getResources().getString(vip_interests));
        go_pay_vip.setText(getActivity().getResources().getString(vip_grade) + " ¥ " + NumberUtil.format(amount));
    }
}
