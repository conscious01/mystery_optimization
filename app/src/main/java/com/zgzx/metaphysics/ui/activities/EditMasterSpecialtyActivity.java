package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.EditMasterSpecialtyController;
import com.zgzx.metaphysics.model.entity.MasterServiceTypeEntity;
import com.zgzx.metaphysics.ui.adapters.FlowSpecialtyAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


import butterknife.BindView;


/**
 * 编辑师傅特长,擅长
 */
public class EditMasterSpecialtyActivity extends BaseRequestActivity implements
        TagFlowLayout.OnTagClickListener,
        EditMasterSpecialtyController.View {

    @BindView(R.id.selected_flow_specialty) FlowLayout mSelectedFlowSpecialty;
    @BindView(R.id.all_flow_specialty) TagFlowLayout mAllFlowSpecialty;

    private EditMasterSpecialtyController.Presenter mPresenter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_edit_master_specialty;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, "我的擅长", "编辑");


        mAllFlowSpecialty.setOnTagClickListener(this);

        // ...
        mPresenter = new EditMasterSpecialtyController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        mPresenter.add(position);
        return true;
    }


    @Override
    public void renderUser(List<MasterServiceTypeEntity> list) {
        mSelectedFlowSpecialty.removeAllViews();

        for (MasterServiceTypeEntity entity : list) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(ResourcesCompat.getFont(this, R.font.font_family));
            textView.setTextSize(14);
            textView.setText(entity.getName());
            textView.setBackgroundResource(R.drawable.ic_label_bg);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setOnClickListener(v -> mPresenter.delete(entity)); // 删除

            mSelectedFlowSpecialty.addView(textView);
        }
    }

    @Override
    public void renderAll(List<MasterServiceTypeEntity> list) {
        mAllFlowSpecialty.setAdapter(new FlowSpecialtyAdapter(
                list,
                R.drawable.selector_specialty
        ));
    }

    @Override
    public void renderSelected(int ...position) {
        //mAllFlowSpecialty.getAdapter().setSelectedList(position);
    }

}
