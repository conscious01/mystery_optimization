package com.zgzx.metaphysics.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FindMasterDetailController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.model.entity.MasterCommentEntity;
import com.zgzx.metaphysics.model.entity.MasterDetailEntityNew;
import com.zgzx.metaphysics.ui.adapters.MasterPhotoAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;
import com.zhy.view.flowlayout.TagFlowLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 师傅主页，编辑
 */
public class EditMasterHomepageActivity extends BaseRequestActivity implements FindMasterDetailController.View {

    @BindView(R.id.tv_more)
    View mTvMore;
    @BindView(R.id.tv_introduction)
    TextView mTvIntroduction;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.flow_specialty)
    TagFlowLayout mFlowSpecialty;

    private MasterPhotoAdapter mMasterPhotoAdapter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_edit_master_homepage;
    }

    @Override
    protected IStatusView createStatusView() {
        return new ToastRequestStatusView(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.my_homepage);

        // 师傅图片
        mMasterPhotoAdapter = new MasterPhotoAdapter();
        mRecyclerView.setAdapter(mMasterPhotoAdapter);
        mRecyclerView.addItemDecoration(new EvenItemDecoration(mRecyclerView, R.dimen.item_margin));


        FindMasterDetailController.Presenter mPresenter =
                new FindMasterDetailController.Presenter();
        mPresenter.setModelAndView(this);
//        mPresenter.getMasterDetail(LocalConfigStore.getInstance().getUser().getUid());

        // 逻辑
//        MasterEditHomepagePresenter presenter = new MasterEditHomepagePresenter();
//        presenter.setModelAndView(this);
//        getLifecycle().addObserver(presenter);
    }


    @OnClick({R.id.layout_photo_show, R.id.layout_introduction, R.id.layout_specialty,
            R.id.layout_my_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_photo_show:
                startActivity(EditMasterPhotoActivity.class);
                break;

            case R.id.layout_introduction:
                startActivityForResult(AlterContentActivity.newIntent(
                        this,
                        mTvIntroduction.getText().toString(),
                        AlterContentActivity.MASTER_INTRODUCTION),
                        AlterContentActivity.MASTER_INTRODUCTION
                );
                break;

            case R.id.layout_specialty:
                startActivity(new Intent(this, EditMasterSpecialtyActivity.class));
                break;

            case R.id.layout_my_service:
                startActivity(new Intent(this, MasterServiceSettingsActivity.class));
                break;
        }
    }

//    @Override
//    public void successful(MasterDetailEntity result) {
//
//    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == AlterContentActivity.MASTER_INTRODUCTION) {
                mTvIntroduction.setText(data.getStringExtra(Constants.REQ_RESULT));
            }
        }
    }


    @Override
    public void renderMasterDetail(MasterDetailEntityNew result) {
        // 师傅介绍
        mTvIntroduction.setText(result.getIntro());

        // 师傅照片
//        mMasterPhotoAdapter.setNewData(result.getPhotos());
        mTvMore.setVisibility(mMasterPhotoAdapter.getData().size() >= 3 ? View.VISIBLE :
                View.INVISIBLE);

        // 师傅特长
//        mFlowSpecialty.setAdapter(new FlowSpecialtyAdapter(StringUtil.convertStringArryaToList(result.getFields(), ",")));
    }

    @Override
    public void onGetComment(MasterCommentEntity commentEntity, int nowPage) {

    }
}
