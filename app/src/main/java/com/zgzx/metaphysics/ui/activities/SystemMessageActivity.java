package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.core.IPaginationPresenter;
import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.core.SimplePaginationPresenter;
import com.zgzx.metaphysics.controller.presenters.SystemMessagePresenter;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.model.entity.SystemMessageEntity;
import com.zgzx.metaphysics.ui.adapters.SystemMessageAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * 系统消息, 通知 列表
 */
public class SystemMessageActivity extends BaseRequestActivity implements
        OnRefreshLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    private static final String EXT_TYPE = "TYPE";

    public static final int SYSTEM_MSG_TYPE = 1, SYSTEM_NOTICE_TYPE = 2;

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, SystemMessageActivity.class)
                .putExtra(EXT_TYPE, type);
    }

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout) SmartRefreshLayout mSmartRefreshLayout;

    private SystemMessagePresenter mPresenter;

    @Override
    protected int getTitleLayoutRes() {
        return R.layout.include_activity_title;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.include_smart_refresh_recycler_view;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra(EXT_TYPE, -1);

        if (type == SYSTEM_MSG_TYPE){
            ActivityTitleHelper.setTitle(this, R.string.system_message);

        } else {
            ActivityTitleHelper.setTitle(this, R.string.system_notice);
        }

        AndroidUtil.addStatusBarHeightMargin(findViewById(R.id.layout_title_container));

        // 列表
        SystemMessageAdapter adapter = new SystemMessageAdapter();
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new EvenItemDecoration(mRecyclerView, R.dimen.item_msg_margin));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        // 逻辑
        mPresenter = new SystemMessagePresenter(type);
        mPresenter.setModelAndView(new PaginationView<>(mRecyclerView, mSmartRefreshLayout, this));
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SystemMessageEntity item = (SystemMessageEntity) adapter.getItem(position);
        startActivity(WebViewActivity.newIntent(this, item.getUrl()));
    }

}
