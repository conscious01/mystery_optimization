package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.MsgController;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.model.entity.MessageEntity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;

import and.fast.statelayout.StateLayout;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zgzx.metaphysics.utils.DateUtils.PATTERN_2;

public class MyMessageActivity extends BaseRequestActivity implements OnRefreshLoadMoreListener {
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;


    MsgController.Presenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.layout_list_state)
    StateLayout layoutListState;

    @Override
    protected int getContentLayoutId() {
        return R.layout.my_msg_activity;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

        ActivityTitleHelper.setTitle(this, getString(R.string.my_message));

        DividerItemDecoration divider = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        recyclerView.addItemDecoration(new EvenItemDecoration(recyclerView, R.dimen.dp_15));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        MsgAdapter msgAdapter = new MsgAdapter();
        recyclerView.setAdapter(msgAdapter);

        mPresenter = new MsgController.Presenter();
        mPresenter.setModelAndView(new PaginationView<>(recyclerView, smartRefreshLayout, this));
        getLifecycle().addObserver(mPresenter);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestRefresh();
    }

    class MsgAdapter extends BaseQuickAdapter<MessageEntity.ItemsBean, BaseViewHolder> {
        public MsgAdapter() {
            super(R.layout.recycle_item_message);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, MessageEntity.ItemsBean item) {
            helper.setText(R.id.tv_title, item.getTitle());
            helper.setText(R.id.tv_time, DateUtils.getTime(item.getCreate_time(), PATTERN_2));
            helper.getView(R.id.ll_item).setOnClickListener(v -> {

                if (!item.getUrl().isEmpty()) {
                    String url =
                            item.getUrl() + "?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&orderNumber="+item.getId();
                    startActivity(WebViewActivity.newIntent(helper.getView(R.id.ll_item).getContext(), item.getUrl(),item.getJump_type(),item.getIssue_id()));
                }



            });
        }
    }

}
