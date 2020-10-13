package com.zgzx.metaphysics.ui.fragments;

import android.os.Bundle;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.OrderController;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.model.event.RefreshOrderEvent;
import com.zgzx.metaphysics.ui.activities.OrderCommentActivity;
import com.zgzx.metaphysics.ui.activities.OrderDetailActivity;
import com.zgzx.metaphysics.ui.activities.PayMethordMemberActivity;
import com.zgzx.metaphysics.ui.adapters.OrderAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.ui.dialogs.OrderCommonDialog;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import and.fast.statelayout.StateLayout;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class OrderMyFragment extends BaseRequestFragment implements
        OrderAdapter.OrderItem, OrderController.OrderCancel, OnRefreshLoadMoreListener {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    int mOrderType;
    OrderAdapter mOrderAdapter;
    OrderController.Presenter1 mPresenter;
    OrderController.PresenterCancel mPresenterCancel;

    @BindView(R.id.layout_list_state)
    StateLayout layoutListState;


    public static OrderMyFragment createInstance(int orderStatus) {
        Bundle args = new Bundle();
        args.putInt(Constants.KEY, orderStatus);
        OrderMyFragment fragment = new OrderMyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.order_my_fragment;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        Bundle arg = savedInstanceState == null ? getArguments() : savedInstanceState;
        mOrderType = arg.getInt(Constants.KEY, 0);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_divider));
        recyclerView.addItemDecoration(new EvenItemDecoration(recyclerView, R.dimen.dp_15));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        mOrderAdapter = new OrderAdapter();
        mOrderAdapter.setClick(this);
        recyclerView.setAdapter(mOrderAdapter);

        mPresenter = new OrderController.Presenter1();
        mPresenter.setStatus(mOrderType);
        mPresenter.setModelAndView(new PaginationView<>(recyclerView, smartRefreshLayout, this));
        getLifecycle().addObserver(mPresenter);


        mPresenterCancel = new OrderController.PresenterCancel();
        mPresenterCancel.setModelAndView(this);

        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshList(RefreshOrderEvent refreshOrderEvent) {
        mPresenter.requestRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mOrderAdapter != null) {
            mOrderAdapter.clear();
        }
    }

    @Override
    public void onOrderDetail(OrderResultEntity item) {
        OrderDetailActivity.start(getContext(), item);

    }

    @Override
    public void onCancelOrder(OrderResultEntity item) {
        OrderCommonDialog.show(getContext(), getString(R.string.warm_hint),
                getString(R.string.sure_to_cancel_order), new OrderCommonDialog.DialogClick() {
                    @Override
                    public void onLeftButton() {
                        OrderCommonDialog.disMiss();
                    }

                    @Override
                    public void onRightButton() {
                        OrderCommonDialog.disMiss();
                        Long time = new Date().getTime() / 1000;
                        Map<String, Object> map = new HashMap<>();
                        map.put("issue_id", item.getId());

                        map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
                        String str = HMacMD5Util.getMapToString(map);
                        String sign1 = null;
                        try {
                            sign1 = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        mPresenterCancel.cancel(item.getId(),
                                LocalConfigStore.getInstance().getAk(),
                                time + LocalConfigStore.getInstance().getTimestamp(), sign1);
                    }
                }, View.GONE);


    }

    @Override
    public void onGo2Pay(OrderResultEntity item) {
//        OrderPayActivity.start(getContext(), item, Constants.PAY_ALI);
        startActivity(PayMethordMemberActivity.newIntent(getContext(), item.getId(),
                Constants.TYPE_QUESTION_PAYING_QUERY));
    }

    @Override
    public void onGo2Comment(OrderResultEntity item) {
        //TODO 跳转评价
//        OrderCommentActivity.start();
        OrderCommentActivity.start(getContext(), item.getId());
    }


    @Override
    public void onCanceled() {
        mPresenter.requestRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestRefresh();
    }
}
