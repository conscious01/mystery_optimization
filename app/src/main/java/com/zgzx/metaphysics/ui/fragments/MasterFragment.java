package com.zgzx.metaphysics.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.rade_view.utils.Constant;
import com.zgzx.metaphysics.ui.activities.MasterHomepageActivity;
import com.zgzx.metaphysics.ui.core.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MasterFragment extends BaseFragment {
    @BindView(R.id.ll_master_top)
    RelativeLayout llMasterTop;

    @BindView(R.id.chen)
    ImageView chen;
    @BindView(R.id.mai)
    ImageView mai;
    @BindView(R.id.view_master_chen)
    View viewMasterChen;
    @BindView(R.id.view_master_mai)
    View viewMasterMai;

    @Override
    protected int getLayoutId() {
        return R.layout.master_fragment;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

    }

    @OnClick({R.id.view_master_chen, R.id.view_master_mai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_master_chen:
                startActivity(MasterHomepageActivity.newIntent(view.getContext(),
                        Constant.MASTER_CHEN_ID));
                break;
            case R.id.view_master_mai:
                startActivity(MasterHomepageActivity.newIntent(view.getContext(),
                        Constant.MASTER_MAI_ID));

                break;
        }
    }
}
