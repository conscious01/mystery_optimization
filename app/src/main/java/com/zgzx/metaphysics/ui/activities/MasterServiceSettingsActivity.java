package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.MasterServiceSettingController;
import com.zgzx.metaphysics.model.entity.MasterServiceSettingEntity;
import com.zgzx.metaphysics.ui.adapters.MasterServiceSettingsAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 师傅服务设置
 */
public class MasterServiceSettingsActivity extends BaseRequestActivity implements MasterServiceSettingController.View {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private MasterServiceSettingsAdapter mAdapter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_master_service_settings;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.service_settings, R.string.edit);

        mAdapter = new MasterServiceSettingsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        MasterServiceSettingController.Presenter presenter = new MasterServiceSettingController.Presenter();
        presenter.setModelAndView(this);
        getLifecycle().addObserver(presenter);
    }


    @Override
    public void result(MasterServiceSettingEntity result) {
        mAdapter.setNewData(result.getItems());
    }

}
