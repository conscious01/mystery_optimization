//package com.zgzx.metaphysics.ui.activities;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager.widget.ViewPager;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.zgzx.metaphysics.R;
//import com.zgzx.metaphysics.controller.FindMasterDetailController;
//import com.zgzx.metaphysics.model.entity.FindMasterDetailEntity;
//import com.zgzx.metaphysics.model.entity.MasterPhotoEntity;
//import com.zgzx.metaphysics.model.entity.MasterServiceItemEntity;
//import com.zgzx.metaphysics.model.entity.MasterServiceTypeEntity;
//import com.zgzx.metaphysics.ui.adapters.FlowSpecialtyAdapter;
//import com.zgzx.metaphysics.ui.adapters.MasterPhotoAdapter;
//import com.zgzx.metaphysics.ui.adapters.MasterServiceAdapter;
//import com.zgzx.metaphysics.ui.adapters.SimplePagerAdapter;
//import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
//import com.zgzx.metaphysics.ui.dialogs.PaymentDialog;
//import com.zgzx.metaphysics.utils.NumberUtil;
//import com.zgzx.metaphysics.utils.image.GlideApp;
//import com.zgzx.metaphysics.utils.item.EvenItemDecoration;
//import com.zhy.view.flowlayout.TagFlowLayout;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * 师傅主页
// */
//public class MasterHomepageActivity extends BaseRequestActivity implements
//        BaseQuickAdapter.OnItemClickListener,
//        FindMasterDetailController.View {
//
//    @BindView(R.id.layout_pay) View mLayoutPay;
//    @BindView(R.id.tv_name) TextView mTvName;
//    @BindView(R.id.rating_bar) RatingBar mRatingBar;
//    @BindView(R.id.tv_score) TextView mTvScore;
//    @BindView(R.id.iv_avatar) ImageView mIvAvatar;
//    @BindView(R.id.tv_number_answers) TextView mTvNumberAnswers;
//    @BindView(R.id.tv_introduction) TextView mTvIntroduction;
//    @BindView(R.id.view_pager) ViewPager mViewPager;
////    @BindView(R.id.chip_group) ChipGroup mChipGroup;
//
//    @BindView(R.id.iv_service) ImageView mIvService;
//    @BindView(R.id.tv_price) TextView mTvPrice;
//    @BindView(R.id.tv_question_type) TextView mTvQuestionType;
//    @BindView(R.id.flow_specialty) TagFlowLayout mFlowSpecialty;
//
//    private MasterPhotoAdapter mPhotoAdapter;
//    private MasterServiceAdapter mServiceAdapter;
//
//    private FindMasterDetailController.Presenter mPresenter;
//
//    @Override
//    public int getContentLayoutId() {
//        return R.layout.activity_master_homepage;
//    }
//
//    @Override
//    protected void initialize(Bundle savedInstanceState) {
//        // 师傅服务
//        mServiceAdapter = new MasterServiceAdapter();
//        RecyclerView masterServiceRecycler = new RecyclerView(this);
//        masterServiceRecycler.setLayoutManager(new LinearLayoutManager(this));
//        masterServiceRecycler.setAdapter(mServiceAdapter);
//        mServiceAdapter.setOnItemClickListener(this);
//        masterServiceRecycler.addItemDecoration(new EvenItemDecoration(masterServiceRecycler, R.dimen.heart_margin));
//
//        // 师傅照片
//        mPhotoAdapter = new MasterPhotoAdapter();
//        RecyclerView masterPhotoRecycler = new RecyclerView(this);
//        masterPhotoRecycler.setLayoutManager(new GridLayoutManager(this, 3));
//        masterPhotoRecycler.addItemDecoration(new EvenItemDecoration(masterPhotoRecycler, R.dimen.item_margin));
//        masterPhotoRecycler.setAdapter(mPhotoAdapter);
//
//        // 照片
//        mViewPager.setAdapter(new SimplePagerAdapter()
//                .put(getString(R.string.service), masterServiceRecycler)
//                .put(getString(R.string.photo), masterPhotoRecycler));
//
//        // 逻辑
//        mPresenter = new FindMasterDetailController.Presenter();
//        mPresenter.setModelAndView(this);
//        getLifecycle().addObserver(mPresenter);
//    }
//
//
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
//
//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        MasterServiceItemEntity item = mServiceAdapter.getItem(position);
//        mServiceAdapter.selected(position); // 选择
//        mPresenter.selectedService(item); // 选择服务
//        mLayoutPay.setVisibility(View.VISIBLE); // 显示支付
//    }
//
//    @Override
//    public void renderMasterDetail(FindMasterDetailEntity detail) {
//        mTvName.setText(detail.getMaster_name());
//        mTvScore.setText(String.valueOf(detail.getScore()));
//        mTvNumberAnswers.setText("已解答：" + detail.getAnswer_num());
//        mTvIntroduction.setText(detail.getIntro());
//        mRatingBar.setRating(detail.getScore());
//
//        // 头像
//        GlideApp.with(mIvAvatar)
//                .load(detail.getPhotos())
//                .miniThumb()
//                .into(mIvAvatar);
//    }
//
//    @Override
//    public void renderServices(List<MasterServiceItemEntity> entities) {
//        mServiceAdapter.setNewData(entities);
//    }
//
//    @Override
//    public void renderSelectedService(MasterServiceItemEntity item) {
//        mTvPrice.setText("¥ " + NumberUtil.format(item.getPrice()));
//        mTvQuestionType.setText(item.getName());
//        mIvService.setBackgroundColor(Color.RED);
//    }
//
//    @Override
//    public void renderPhotos(List<MasterPhotoEntity> photos) {
//        mPhotoAdapter.setNewData(photos);
//    }
//
//    @Override
//    public void renderSpecialty(List<MasterServiceTypeEntity> list) {
//        // 师傅特长
//        mFlowSpecialty.setAdapter(new FlowSpecialtyAdapter(list));
////        for (String string : list) { // 特长
////            Chip chip = (Chip) LayoutInflater.from(this)
////                    .inflate(R.layout.include_chip_label, mChipGroup, false);
////            chip.setText(string);
////            mChipGroup.addView(chip);
////        }
//    }
//
//}
