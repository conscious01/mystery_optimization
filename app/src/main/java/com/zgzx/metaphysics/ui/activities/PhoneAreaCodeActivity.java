package com.zgzx.metaphysics.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.CountryCodePresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.model.entity.AreaCodeEntity;
import com.zgzx.metaphysics.ui.adapters.AreaCodeAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.TextWatcherAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手机区号
 */
public class PhoneAreaCodeActivity extends BaseRequestActivity
        implements ISingleRequestView<List<AreaCodeEntity>>,
        BaseQuickAdapter.OnItemClickListener {

    public static Intent newIntent(Context context) {
        return new Intent(context, PhoneAreaCodeActivity.class);
    }


    @BindView(R.id.et_search) EditText mEtSearch;
    @BindView(R.id.tv_cancel) TextView mTvCancel;
    @BindView(R.id.layout_container) ViewGroup mLayoutContainer;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private AreaCodeAdapter mAdapter;

    @Override
    protected int getTitleLayoutRes() {
        return R.layout.include_area_code_title_bar;
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.include_recycler_view;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 添加状态栏高度
        AndroidUtil.addStatusBarHeightPadding(mLayoutContainer);

        // 列表
        mAdapter = new AreaCodeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        // 请求
        CountryCodePresenter presenter = new CountryCodePresenter();
        presenter.setModelAndView(this);
        getLifecycle().addObserver(presenter);

        // 搜索
        mEtSearch.addTextChangedListener(new TextWatcherAdapter(){

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.search(editable);
            }

        });
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked(){
        onBackPressed();
    }

    @Override
    public void result(List<AreaCodeEntity> result) {
        mAdapter.setNewData(result);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AreaCodeEntity item = mAdapter.getItem(position);
        finish(Activity.RESULT_OK, new Intent().putExtra(Constants.EXTRA_AREA_CODE, item.getCode()));
    }

}
