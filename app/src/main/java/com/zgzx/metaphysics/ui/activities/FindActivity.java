package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FindController;
import com.zgzx.metaphysics.model.entity.ArticleListEntity;
import com.zgzx.metaphysics.ui.adapters.FindAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 发现文章列表
 */
public class FindActivity extends BaseRequestActivity implements FindController.View, OnRefreshLoadMoreListener {
    @BindView(R.id.mFindRecycleView)
    RecyclerView mFindRecycleView;
    @BindView(R.id.mFindSmartRefresh)
    SmartRefreshLayout mFindSmartRefresh;
    private FindController.Presenter mPresenter;
    private int cate_id;
    private int page = 1;
    private int total;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_find_layout;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FindActivity.class);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.nav_find);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        mFindRecycleView.addItemDecoration(divider);
       // cate_id = getIntent().getIntExtra(Constants.EXT_ID, -1);

        mPresenter = new FindController.Presenter();
        mPresenter.setModelAndView(this);
        mFindSmartRefresh.setOnRefreshLoadMoreListener(this);
        getLifecycle().addObserver(mPresenter);
        mPresenter.getArticleList(-1, page, 10);
    }

    @Override
    public void renderArticleList(List<ArticleListEntity.ItemsBean> data, int total) {
        FindAdapter findAdapter = new FindAdapter(data);
        findAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url =
                        ((ArticleListEntity.ItemsBean) adapter.getData().get(position)).getLink_url();
                startActivity(WebViewActivity.newIntent(FindActivity.this, url));
            }
        });
        mFindRecycleView.setAdapter(findAdapter);
        this.total = total;
        mFindSmartRefresh.finishLoadMore();
        mFindSmartRefresh.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        if (page <= total/10) {
            mPresenter.getArticleList(-1, page, 10);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFindSmartRefresh.finishLoadMore();
                }
            }, 2000);

        }

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.getArticleList(-1, page, 10);
    }
}
