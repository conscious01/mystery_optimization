package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FindMasterDetailController;
import com.zgzx.metaphysics.model.entity.MasterCommentEntity;
import com.zgzx.metaphysics.model.entity.MasterDetailEntityNew;
import com.zgzx.metaphysics.ui.adapters.FlowSpecialtyAdapter2;
import com.zgzx.metaphysics.ui.adapters.MasterCommentAdapter;
import com.zgzx.metaphysics.ui.adapters.MasterPhotoAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.StringUtil;
import com.zgzx.metaphysics.utils.image.GlideApp;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 师傅主页
 */
public class MasterHomepageActivity extends BaseRequestActivity implements
        BaseQuickAdapter.OnItemClickListener,
        FindMasterDetailController.View {


    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.tv_number_answers)
    TextView tvNumberAnswers;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    ImageView iv4;
    @BindView(R.id.iv5)
    ImageView iv5;
    @BindView(R.id.tv_master_name)
    TextView tvMasterName;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.iv_bg)
    ImageView ivBg;

    @BindView(R.id.flow_specialty)
    TagFlowLayout mFlowSpecialty;
    @BindView(R.id.rv_gallery)
    RecyclerView rvGallery;
    @BindView(R.id.tv_more_info)
    TextView tvMoreInfo;
    @BindView(R.id.tv_ask)
    TextView tvAsk;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.tv_seemore)
    TextView tvSeemore;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nested_scroll_view;


//     @BindView(R.id.chip_group)
//     ChipGroup mChipGroup;


    private MasterPhotoAdapter mPhotoAdapter;
    private MasterCommentAdapter mCommentAdapter;

    private FindMasterDetailController.Presenter mPresenter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_master_homepage;
    }


    public static Intent newIntent(Context context, int type) {
        return new Intent(context, MasterHomepageActivity.class)
                .putExtra("key", type);
    }

    public static Intent newIntent(Context context, int master_id, int uid) {
        return new Intent(context, MasterHomepageActivity.class)
                .putExtra("key", master_id).putExtra(Constants.EXT_AMOUNT, uid);
    }

    int master_id;
    int mPage = 1;
    int uid;
    boolean ifLoadingMore = true;

    @Override
    protected void initialize(Bundle savedInstanceState) {


        nested_scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY,
                                       int oldScrollX, int oldScrollY) {
                // 滚动到底
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (!ifLoadingMore) {
                        mPresenter.getMasterComment(mPage + 1, 10, uid,master_id);
                    }
                    ifLoadingMore = true;
                    System.out.println("滚动到底");
                }
            }
        });


        if (LocalConfigStore.getInstance().ifMaster()) {
            tvAsk.setVisibility(View.GONE);
        } else {
            tvAsk.setVisibility(View.VISIBLE);
        }


        //设置透明颜色
        layoutTitleContainer.setBackground(getDrawable(R.color.picture_color_transparent));
        master_id = getIntent().getIntExtra("key", 0);
        uid = getIntent().getIntExtra(Constants.EXT_AMOUNT, 0);
        //评价
        mCommentAdapter = new MasterCommentAdapter();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvComment.setLayoutManager(linearLayoutManager);
        rvComment.setAdapter(mCommentAdapter);
        mCommentAdapter.setOnItemClickListener(this);
        rvComment.addItemDecoration(new EvenItemDecoration(rvComment, R
                .dimen.heart_margin));

        // 师傅照片
        mPhotoAdapter = new MasterPhotoAdapter();

        rvGallery.setLayoutManager(new GridLayoutManager(this, 3));
        rvGallery.addItemDecoration(new EvenItemDecoration(rvGallery, R
                .dimen.item_margin));
        rvGallery.setAdapter(mPhotoAdapter);

        // 照片
//        mViewPager.setAdapter(new SimplePagerAdapter()
//                .put(getString(R.string.service), masterServiceRecycler)
//                .put(getString(R.string.photo), masterPhotoRecycler));

        // 逻辑
        mPresenter = new FindMasterDetailController.Presenter();
        mPresenter.setModelAndView(this);
        mPresenter.getMasterDetail(master_id, uid, LocalConfigStore.getInstance().getUser().getUid());

        mPresenter.getMasterComment(mPage, 10, uid,master_id);

    }


//    @OnClick(value = {R.id.iv_arrow_back, R.id.but_pay})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_arrow_back:
//                onBackPressed();
//                break;
//
//            case R.id.but_pay:
//                //startActivity(new Intent(this, StateActivity.class));
//                PaymentDialog.show(this);
//                break;
//        }
//    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        MasterServiceItemEntity item = mCommentAdapter.getItem(position);
//        mCommentAdapter.selected(position); // 选择
//        mPresenter.selectedService(item); // 选择服务
//        mLayoutPay.setVisibility(View.VISIBLE); // 显示支付
    }

    MasterDetailEntityNew masterDetailEntityNew;

    @Override
    public void renderMasterDetail(MasterDetailEntityNew detail) {
        masterDetailEntityNew = detail;
        if (detail.getAvatar() != null ) {
            GlideApp.with(ivBg.getContext())
                    .load(detail.getAvatar())
                    .miniThumb()
                    .into(ivBg);
        }
        tvNumberAnswers.setText(detail.getAnswer_num() + "");
        setPoints(detail.getScore());

        tvMasterName.setText(detail.getMaster_name());
        tvYear.setText(getString(R.string.carrior_year) + detail.getCareer_years() + getString(R.string.year));

        tvIntro.setText(detail.getIntro());


        setFlowSpecialty(StringUtil.convertStringToList(detail.getFields(), ","));

        mPhotoAdapter.setNewData(detail.getPhotos());

        System.out.println("renderMasterDetail");
//        mTvName.setText(detail.getMaster_name());
//        mTvScore.setText(String.valueOf(detail.getScore()));
//        mTvNumberAnswers.setText("已解答：" + detail.getAnswer_num());
//        mTvIntroduction.setText(detail.getIntro());
//        mRatingBar.setRating(detail.getScore());
//
//        // 头像
//
    }

    @Override
    public void onGetComment(MasterCommentEntity commentEntity, int nowPage) {
        ifLoadingMore = false;
        mPage = nowPage;
//        mCommentAdapter.setNewData(commentEntity.getItems());
        mCommentAdapter.addData(commentEntity.getItems());
    }

    private void setPoints(int points) {
        if (points == 1) {
            GlideApp.with(iv1.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv1);
        } else if (points == 2) {
            GlideApp.with(iv2.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv2);
            GlideApp.with(iv1.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv1);

        } else if (points == 3) {
            GlideApp.with(iv1.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv1);
            GlideApp.with(iv2.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv2);
            GlideApp.with(iv3.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv3);

        } else if (points == 4) {
            GlideApp.with(iv1.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv1);
            GlideApp.with(iv2.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv2);
            GlideApp.with(iv3.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv3);
            GlideApp.with(iv4.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv4);

        } else if (points == 5) {
            GlideApp.with(iv1.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv1);
            GlideApp.with(iv2.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv2);
            GlideApp.with(iv3.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv3);
            GlideApp.with(iv4.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv4);
            GlideApp.with(iv5.getContext())
                    .load(R.drawable.dashi_dszy_star)
                    .miniThumb()
                    .into(iv5);

        }
    }


    private void setFlowSpecialty(List<String> list) {
        if ((list == null) || (list.size() == 0)) {
            return;
        }

        mFlowSpecialty.setAdapter(new FlowSpecialtyAdapter2(list));
//        for (String string : list) { // 特长
//            Chip chip = (Chip) LayoutInflater.from(this)
//                    .inflate(R.layout.include_chip_label, mChipGroup, false);
//            chip.setText(string);
//            mChipGroup.addView(chip);
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_more_info, R.id.tv_ask, R.id.tv_seemore, R.id.iv_arrow_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow_back:
                finish();
                break;


            case R.id.tv_more_info:

                if (masterDetailEntityNew != null) {
                    String fcURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                            "/inside_pages" +
                            "/mien?language=" + LocalConfigStore.getInstance().getAcceptLanguage() +
                            "&master_id=" + master_id;
                    startActivity(WebViewActivity.newIntent(this, fcURL));
                }


                break;
            case R.id.tv_ask:
                if (masterDetailEntityNew != null) {
                    if (LocalConfigStore.getInstance().isLogin()) {
                        Intent intent = new Intent(this, AskQuestionsActivity.class);
                        intent.putExtra(Constants.EXT_TYPE, masterDetailEntityNew);
                        startActivity(intent);
                    } else {

                        startActivity(new Intent(this, LoginActivity.class));
                    }

                }

                break;
            case R.id.tv_seemore:
                break;
        }
    }
}
