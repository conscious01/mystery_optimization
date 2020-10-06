package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.OrderController;
import com.zgzx.metaphysics.model.entity.QDetailEntity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.image.GlideApp;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderCommentActivity extends BaseRequestActivity implements OrderController.CommentView {
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.tv_q_content)
    TextView tvQContent;
    @BindView(R.id.rv_q)
    RecyclerView rvQ;

    @BindView(R.id.tv_answer_content)
    TextView tvAnswerContent;
    @BindView(R.id.ll_user_bottom)
    RelativeLayout llUserBottom;
    @BindView(R.id.tv_master_name)
    TextView tvMasterName;

    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;

    @Override
    protected int getContentLayoutId() {
        return R.layout.order_comment_activity;
    }

    QDetailEntity mData;

    public static void start(Context context, int orderID) {
        Intent intent = new Intent(context, OrderCommentActivity.class);
        intent.putExtra(Constants.EXT_TYPE, orderID);
        context.startActivity(intent);

    }

    float mRatting;
    int mOrderID;
    OrderController.PresenterComment mPresenter;

    @Override
    protected void initialize(Bundle savedInstanceState) {

        ActivityTitleHelper.setTitle(this, R.string.do_comment);
        Intent intent = getIntent();

        mOrderID = intent.getIntExtra(Constants.EXT_TYPE, 0);


        simpleRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                mRatting = rating;
                System.out.println("onRatingChanged-->" + rating);
                if (rating > 0) {
                    tvConfirm.setBackground(getResources().getDrawable(R.drawable.button_enable));
                }
            }
        });

        mPresenter = new OrderController.PresenterComment();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        mPresenter.getOrderDetail(mOrderID);
    }

    private void setQInfo() {

        tvQContent.setText(mData.getIssue_content());
        tvMasterName.setText(mData.getMaster_name());
        tvAnswerContent.setText(mData.getAnswer_content());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvQ.addItemDecoration(new EvenItemDecoration(rvQ, R.dimen.dp_8));
        rvQ.setAdapter(new PicAdapter(mData.getPhotos()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        if (mRatting > 0 && mData != null) {
            mPresenter.doComment(mData.getId(), (int) mRatting);
        }
    }

    @Override
    public void onCommment() {
        OrderCommentAfterActivity.start(this, mRatting);
        finish();
    }

    @Override
    public void onQ_DetailMaster(QDetailEntity data) {
        mData = data;
        setQInfo();
    }

    @Override
    public void onQ_DetailUser(QDetailEntity data) {
        mData = data;
        setQInfo();
    }

    class PicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public PicAdapter(@Nullable List<String> data) {
            super(R.layout.recycle_item_avater, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            GlideApp.with(helper.getView(R.id.iv_avatar)).load(item).into((ImageView) helper.getView(R.id.iv_avatar));
        }
    }
}
