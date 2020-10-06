package com.zgzx.metaphysics.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.MasterServiceListPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.controller.views.impl.LayoutRequestStatusView;
import com.zgzx.metaphysics.controller.views.impl.PaginationView;
import com.zgzx.metaphysics.model.entity.MasterServiceEntity;
import com.zgzx.metaphysics.model.entity.MasterServiceTypeEntity;
import com.zgzx.metaphysics.ui.activities.MasterHomepageActivity;
import com.zgzx.metaphysics.ui.adapters.MasterAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;

import java.util.List;

import and.fast.statelayout.StateLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 发现
 */
public class FindFragment extends BaseRequestFragment implements
        BaseQuickAdapter.OnItemClickListener,
        ISingleRequestView<List<MasterServiceTypeEntity>>,
        RadioGroup.OnCheckedChangeListener,
        OnRefreshLoadMoreListener {

    //    @BindView(R.id.radio_price) RadioButton mRadioPrice;
//    @BindView(R.id.radio_group) RadioGroup mRadioGroup;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.layout_list_state)
    StateLayout mListStateLayout;
//    @BindView(R.id.group_sort) RadioGroup mGroupSort;

    private MasterServiceListPresenter mListPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

//        // 列表
        MasterAdapter adapter = new MasterAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        adapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.addItemDecoration(new EvenItemDecoration(mRecyclerView, R.dimen
                .dp_12));
//
//        // 师傅服务类型
//        MasterServiceTypePresenter typePresenter = new MasterServiceTypePresenter();
//        typePresenter.setModelAndView(this);
//        getLifecycle().addObserver(typePresenter);
//
//        // 列表请求
        mListPresenter = new MasterServiceListPresenter();
        PaginationView<MasterServiceEntity> paginationView = new PaginationView<>(mRecyclerView,
                mRefreshLayout, new LayoutRequestStatusView(mListStateLayout));
        paginationView.setEmptyLayoutResId(R.layout.view_state_layout_empty);
        mListPresenter.setModelAndView(paginationView);
        getLifecycle().addObserver(mListPresenter);

//
//        // 排序
//        mGroupSort.check(R.id.radio_default);
//        mGroupSort.setOnCheckedChangeListener((group, checkedId) -> {
//            switch (checkedId) {
//                case R.id.radio_default: // 默认排序
//                    mListPresenter.setSortParams(MASTER_SORT_DEFAULT);
//                    break;
//
//                case R.id.radio_score: // 评分
//                    mListPresenter.setSortParams(MASTER_SORT_SCORE);
//                    break;
//
//                case R.id.radio_answers: // 解答数量
//                    mListPresenter.setSortParams(MASTER_SORT_ANSWER);
//                    break;
//
//                //case R.id.radio_price: // 价格
//                    //mListPresenter.setSortParams(MASTER_SORT_PRICE);
//                 //   break;
//            }
//        });
//
//        // 价格
//        mRadioPrice.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            Logger.i("is checked %s", isChecked);
//
//            if (!isChecked) { // 未选择
//                Drawable drawable = getResources().getDrawable(R.drawable.ic_filter);
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
//                .getIntrinsicHeight());
//
//                buttonView.setSelected(false);
//                buttonView.setCompoundDrawables(
//                        null,
//                        null,
//                        drawable,
//                        null
//                );
//            }
//        });
//
//        // 价格
//        mRadioPrice.setOnClickListener(v -> {
//            Logger.i("is click %s", v.isSelected());
//
//            if (mRadioPrice.isChecked()) {
//                Drawable drawable = getResources().getDrawable(v.isSelected() ? R.drawable
//                .ic_filter_down : R.drawable.ic_filter_up);
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
//                .getIntrinsicHeight());
//
//                mRadioPrice.setCompoundDrawables(
//                        null,
//                        null,
//                        drawable,
//                        null
//                );
//
//                mListPresenter.setSortPrice(v.isSelected());
//                v.setSelected(!v.isSelected());
//            }
//        });
    }

    @Override
    public void result(List<MasterServiceTypeEntity> result) {
//        mRadioGroup.removeAllViews();
//
//        for (MasterServiceTypeEntity entity : result) {
//            RadioButton button = (RadioButton) LayoutInflater.from(getContext())
//                    .inflate(R.layout.include_radio_button_label, mRadioGroup, false);
//            button.setText(entity.getName());
//            button.setId(ViewCompat.generateViewId());
//            button.setTag(entity.getId()); // 设置类型Id
//            mRadioGroup.addView(button);
//        }
//
//        // 最后条目添加距离
//        if (mRadioGroup.getChildCount() > 0) {
//            View child = mRadioGroup.getChildAt(mRadioGroup.getChildCount() - 1);
//            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) child
//            .getLayoutParams();
//            layoutParams.setMargins(
//                    layoutParams.leftMargin,
//                    layoutParams.topMargin,
//                    layoutParams.rightMargin + getResources().getDimensionPixelOffset(R.dimen
//                    .heart_margin),
//                    layoutParams.bottomMargin
//            );
//
//            child.setLayoutParams(layoutParams);
//        }
//
//        mRadioGroup.setOnCheckedChangeListener(this);
//        mRadioGroup.check(mRadioGroup.getChildAt(0).getId());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(MasterHomepageActivity.newIntent(view.getContext(),
                ((MasterServiceEntity) adapter.getData().get(position)).getId()));
        // TODO 师傅主页
    }

    // TODO 消息
//    @OnClick(R.id.iv_msg)
//    public void onViewClicked(View view) {
//        startActivity(new Intent(view.getContext(), MyQuestionsActivity.class));
//    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        mListPresenter.setParams((int) group.findViewById(checkedId).getTag());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

        mListPresenter.requestLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mListPresenter.requestRefresh();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtil.setLightMode(getActivity());
        }
    }

}
