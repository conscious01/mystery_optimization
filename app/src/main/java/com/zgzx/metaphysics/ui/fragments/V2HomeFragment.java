package com.zgzx.metaphysics.ui.fragments;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mondo.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.HomeController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.model.entity.ArticleListEntity;
import com.zgzx.metaphysics.model.entity.BannerEntity;
import com.zgzx.metaphysics.model.entity.CalendarDayEntity;
import com.zgzx.metaphysics.model.entity.CalendarHourEntity;
import com.zgzx.metaphysics.model.entity.DailyQuestionEntity;
import com.zgzx.metaphysics.model.entity.FortuneEntity;
import com.zgzx.metaphysics.model.entity.FortuneGradeEntity;
import com.zgzx.metaphysics.model.entity.FortuneListImageEntity;
import com.zgzx.metaphysics.model.entity.GanEntity;
import com.zgzx.metaphysics.model.entity.HomeFouncationEntity;
import com.zgzx.metaphysics.model.event.PlayCompleteEvent;
import com.zgzx.metaphysics.model.event.SupplementInformationEvent;
import com.zgzx.metaphysics.ui.activities.DailyBlessActivity;
import com.zgzx.metaphysics.ui.activities.FindActivity;
import com.zgzx.metaphysics.ui.activities.FortuneDetailActivity;
import com.zgzx.metaphysics.ui.activities.LoginActivity;
import com.zgzx.metaphysics.ui.activities.MainActivity;
import com.zgzx.metaphysics.ui.activities.WebViewActivity;
import com.zgzx.metaphysics.ui.adapters.CalendarDayAdapter;
import com.zgzx.metaphysics.ui.adapters.FortuneBagAdapter;
import com.zgzx.metaphysics.ui.adapters.FortuneGradeAdapter;
import com.zgzx.metaphysics.ui.adapters.GanAdapter;
import com.zgzx.metaphysics.ui.adapters.HomeAskQuestionAdapter;
import com.zgzx.metaphysics.ui.adapters.HomeBannerAdapter;
import com.zgzx.metaphysics.ui.adapters.HomeBannerToolsAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.ui.dialogs.AdVertiseMentDialog;
import com.zgzx.metaphysics.ui.dialogs.CurrencyDialog;
import com.zgzx.metaphysics.ui.dialogs.ShareDialog;
import com.zgzx.metaphysics.ui.view.WaveProgressView;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.LunarCalendarUtil;
import com.zgzx.metaphysics.utils.image.GlideApp;
import com.zgzx.metaphysics.widgets.ArcView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/23
 * @Description: 主页 (版本2)
 */
public class V2HomeFragment extends BaseRequestFragment implements HomeController.View,
        BaseQuickAdapter.OnItemClickListener, ShareDialog.OnDialogClickListener {

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.banner_tools)
    Banner mBannerTools;
    @BindView(R.id.arc_view)
    ArcView mArcView;
    @BindView(R.id.text_view1)
    TextView mTextView1;
    @BindView(R.id.text_view2)
    TextView mTextView2;
    @BindView(R.id.view_flipper)
    ViewFlipper mViewFlipper;
    @BindView(R.id.recycle_time_ji)
    RecyclerView mRecycleTimeJi;
    @BindView(R.id.recycle_time_yi)
    RecyclerView mRecycleTimeYi;
    @BindView(R.id.recycle_day_ji)
    RecyclerView mRecycleDayJi;
    @BindView(R.id.recycle_day_yi)
    RecyclerView mRecycleDayYi;
    @BindView(R.id.recycle_ask)
    RecyclerView mRecycleAsk;
    @BindView(R.id.recycle_gan)
    RecyclerView recycle_gan;

    @BindView(R.id.recycle_fortune_grade)
    RecyclerView recycle_fortune_grade;
    @BindView(R.id.recycle_fortune_bag)
    RecyclerView recycle_fortune_bag;

    @BindView(R.id.layout_title_container)
    ViewGroup mLayoutTitleContainer;

    @BindView(R.id.homepage_top_title)
    TextView homepage_top_title;

    @BindView(R.id.tv_hour_yj)
    TextView tv_hour_yj;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.home_fate_book_img)
    RoundedImageView home_fate_book_img;//命书
    @BindView(R.id.home_master_img)
    RoundedImageView home_master_img;//师傅
    @BindView(R.id.home_pray_img)
    RoundedImageView home_pray_img;//祈福
    @BindView(R.id.home_oneiromancy_img)
    RoundedImageView home_oneiromancy_img;//解梦
    @BindView(R.id.home_practise_divination_img)
    RoundedImageView home_practise_divination_img;//易经占卜

    @BindView(R.id.start_fate_book)
    ImageView start_fate_book;
    @BindView(R.id.start_km)
    ImageView start_km;

    @BindView(R.id.tv_today_chong_tips)
    TextView tv_today_chong_tips;//冲
    @BindView(R.id.tv_today_sha_tips)
    TextView tv_today_sha_tips;//煞
    @BindView(R.id.tv_joy_god)
    TextView tv_joy_god;//喜神
    @BindView(R.id.tv_bless_god)
    TextView tv_bless_god;//福神
    @BindView(R.id.tv_money_god)
    TextView tv_money_god;//财神

    @BindView(R.id.tv_yj_start)
    TextView tv_yj_start;
    @BindView(R.id.tv_yj_end)
    TextView tv_yj_end;

    @BindView(R.id.tv_fortune)
    TextView tv_fortune;//财运位
    @BindView(R.id.tv_lucky_color)
    TextView tv_lucky_color;//幸运色
    @BindView(R.id.tv_lucky_number)
    TextView tv_lucky_number;//幸运数
    @BindView(R.id.tv_fortune_tips)
    TextView tv_fortune_tips;//幸运提示
    @BindView(R.id.mWaveProgressView)
    WaveProgressView mWaveProgressView;

    private HomeController.Presenter mPresenter;
    private static final int FLAG_ONE = 0X0001;
    private int index = 1;
    private Calendar calendar;
    private String yi, ji;
    private GanAdapter ganAdapter;
    private List<CalendarHourEntity> calendarHourBeanList;
    private String[] fortuneArrayList;

    private List<FortuneEntity.FortuneBean.Bean> fortuneEntityList;
    private int progress, mPosition;
    private int maxProgress;

    private Animator animator;
    private Animator animator1;
    private List<BannerEntity> bannerEntityList;
    private Handler mFortuneHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress++;
            switch (msg.what) {
                case FLAG_ONE:
                    if (progress <= maxProgress) {

                        mWaveProgressView.setCurrent(progress, progress + "");
                        sendEmptyMessageDelayed(FLAG_ONE, 10);
                    } else {
                        return;
                    }
                    break;
            }
        }
    };


    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected IStatusView createRequestView() {
        return new ToastRequestStatusView(getActivity());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_v2;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        AndroidUtil.addStatusBarHeightPadding(mLayoutTitleContainer);
        calendar = Calendar.getInstance();
        // 当前日历，时间
        initCalendar();
        // 初始化列表
        mRecycleTimeJi.setNestedScrollingEnabled(false);
        mRecycleTimeYi.setNestedScrollingEnabled(false);
        mRecycleDayJi.setNestedScrollingEnabled(false);
        mRecycleDayYi.setNestedScrollingEnabled(false);


        // 请求
        mPresenter = new HomeController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

        mPresenter.getDailyQuestion(index);
        mPresenter.fortune();
        initCalendarHourDiZi();
        // 广告图
        initBanner();


        // 滚动颜色
        initHeaderBar();


        animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.home_page_animation);
        animator.setTarget(start_fate_book);
        animator.start();
        animator1 = AnimatorInflater.loadAnimator(getActivity(), R.animator.home_page_animation);
        animator1.setTarget(start_km);
        animator1.start();

    }


    private void initCalendarHourDiZi() {
        String[] branchArray =
                getResources().getStringArray(R.array.terrestrial_branch_string_array);
        List<GanEntity> ganEntityList = new ArrayList<>();
        for (int i = 0; i < branchArray.length; i++) {
            GanEntity ganEntity = new GanEntity(false, branchArray[i]);
            ganEntityList.add(ganEntity);
        }
        ganAdapter = new GanAdapter(ganEntityList);
        recycle_gan.setAdapter(ganAdapter);
        ganAdapter.setOnItemClickListener(this);
    }


    private void initHeaderBar() {
        mNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    int top = mLayoutTitleContainer.getTop();
                    int color = scrollY > top ? Color.WHITE : Color.TRANSPARENT;

                    Drawable background = mLayoutTitleContainer.getBackground();

                    if (background instanceof ColorDrawable) {
                        // 避免过渡绘制背景颜色
                        if (((ColorDrawable) background).getColor() != color) {
                            //  mLayoutTitleContainer.setBackgroundColor(color);
                            Logger.i("home fragment color change");
                        }

                    } else {
                        //  mLayoutTitleContainer.setBackgroundColor(color);
                    }

                });
    }


    private void initCalendar() {
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "年" + month + "月" + day + "日";
        String date1 = LunarCalendarUtil.get(getResources(), Calendar.getInstance());
        String date2 = LunarCalendarUtil.getInstance().getFestival(year, month, day);

        homepage_top_title.setText(date + "   " + date1 + "  " + date2);
    }


    private void initBanner() {
        mBanner.addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getContext()))
                .addOnPageChangeListener(new OnPageChangeListener() {

                    @Override
                    public void onPageScrolled(int position, float positionOffset,
                                               int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        HomeBannerAdapter adapter = (HomeBannerAdapter) mBanner.getAdapter();
                        BannerEntity bean = adapter.getData(position);

                        Glide.with(getContext())
                                .asBitmap()
                                .load(bean.getImage())
                                .into(new CustomTarget<Bitmap>() {

                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource,
                                                                @Nullable Transition<?
                                                                        super Bitmap> transition) {
                                        Palette palette = Palette.from(resource).generate();
                                        mArcView.setBackgroundColor(palette.getDominantColor(Color.TRANSPARENT));
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }

                                });
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


    }


    @OnClick(value = {R.id.fortune_layout_1, R.id.tv_share_daily_ask,
            R.id.fate_book_bg, R.id.km_bg, R.id.home_pray_img,
            R.id.home_oneiromancy_img, R.id.home_practise_divination_img, R.id.tv_share_fortune,
            R.id.tv_share_today_yj, R.id.tv_share_time_yj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fortune_layout_1:
                if (LocalConfigStore.getInstance().isLogin()) {
                    startActivity(FortuneDetailActivity.newIntent(getActivity(), 0));

                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }
                break;
            case R.id.tv_share_daily_ask:
                index = index + 1;
                mPresenter.getDailyQuestion(index);
                break;
            case R.id.fate_book_bg://命书
                ((MainActivity) getActivity()).mBottomNavigation.setSelectedItemId(R.id.nav_book);
                break;
            case R.id.km_bg://师傅
                ((MainActivity) getActivity()).mBottomNavigation.setSelectedItemId(R.id.nav_find);
                break;
            case R.id.home_pray_img://祈福
                if (LocalConfigStore.getInstance().isLogin()) {
                    startActivity(new Intent(getActivity(), DailyBlessActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.home_oneiromancy_img://解梦
                String zgjmURL = LocalConfigStore.getInstance().getH5_Base() + "/pages/zhou_gong" +
                        "/oneiromancy?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&status_bar_height=" + 24;

                startActivity(WebViewActivity.newIntent(getContext(), zgjmURL));
                break;
            case R.id.home_practise_divination_img://易经占卜
                String yjzbURL = "LocalConfigStore.getInstance().getH5_Base()" + "/pages/divination" +
                        "/divination?language=" + LocalConfigStore.getInstance().getAcceptLanguage();
                startActivity(WebViewActivity.newIntent(getActivity(), yjzbURL));
//
                break;
            case R.id.tv_share_fortune://分享运势
                ShareDialog.show(getActivity(), this,
                        getResources().getString(R.string.today_fortune),
                        AndroidUtil.view2Bitmap(findViewById(R.id.fortune_layout_1)));

                break;
            case R.id.tv_share_today_yj://分享今日宜忌
                ShareDialog.show(getActivity(), this,
                        getResources().getString(R.string.tv_daily_yj),
                        AndroidUtil.view2Bitmap(findViewById(R.id.today_yj_layout)));
                break;
            case R.id.tv_share_time_yj://分享时辰宜忌
                ShareDialog.show(getActivity(), this,
                        getResources().getString(R.string.tv_time_yj),
                        AndroidUtil.view2Bitmap(findViewById(R.id.time_yj_layout)));
                break;
        }
    }


    @Override
    public void renderBanner(List<BannerEntity> data) {
        HomeBannerAdapter adapter = new HomeBannerAdapter(data);
        mBanner.setAdapter(adapter);

        bannerEntityList = data;
        if (data != null && !data.isEmpty()) {
            Glide.with(getContext())
                    .asBitmap()
                    .load(data.get(0).getImage())
                    .into(new CustomTarget<Bitmap>() {

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource,
                                                    @Nullable Transition<? super Bitmap> transition) {
                            Palette palette = Palette.from(resource).generate();
                            mArcView.setBackgroundColor(palette.getDominantColor(Color.TRANSPARENT));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                    });
        }
        adapter.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                if (bannerEntityList.size() > 0) {
                    BannerEntity entity = bannerEntityList.get(position);
                    if (entity.getNavite_page_name().equals("zhougongjiemeng")) {//周工解梦
                        String zgjmURL = LocalConfigStore.getInstance().getH5_Base() + "/pages/zhou_gong" +
                                "/oneiromancy?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&status_bar_height=" + 24;

                        startActivity(WebViewActivity.newIntent(getContext(), zgjmURL));

                    } else if ("yijingzhanbu".equals(entity.getNavite_page_name())) {//易经占卜
                        String yjzbURL = LocalConfigStore.getInstance().getH5_Base() + "/pages/divination" +
                                "/divination?language=" + LocalConfigStore.getInstance().getAcceptLanguage();
                        startActivity(WebViewActivity.newIntent(getActivity(), yjzbURL));

                    } else if ("zengyunhongbao".equals(entity.getNavite_page_name())) {//增运红包
                        String hbjfURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                                "/red_packet/red_packet?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&degree=0" + "&status_bar_height=" + 24;

                        startActivity(WebViewActivity.newIntent(getActivity(), hbjfURL));
                    } else if ("mingshu".equals(entity.getNavite_page_name())) {//命书
                        ((MainActivity) getActivity()).mBottomNavigation.setSelectedItemId(R.id.nav_book);
                    } else if ("kongmingzuoguan".equals(entity.getNavite_page_name())) {//孔明做馆
                        ((MainActivity) getActivity()).mBottomNavigation.setSelectedItemId(R.id.nav_find);
                    } else if ("meiriqifu".equals(entity.getNavite_page_name())) {//每日祈福
                        startActivity(new Intent(getActivity(), DailyBlessActivity.class));
                    } else if (TextUtils.isEmpty(entity.getNavite_page_name())) {
                        startActivity(WebViewActivity.newIntent(getActivity(), entity.getLink_url()));
                    }
                }
            }
        });
    }

    @Override
    public void renderNomalFouncation(List<HomeFouncationEntity.Icon1Bean> data) {
//        GlideApp.with(getContext())
//                .load(data.get(0).getIcon())
//                .into(home_fate_book_img);
//        GlideApp.with(getContext())
//                .load(data.get(1).getIcon())
//                .into(home_master_img);
    }

    @Override
    public void renderToolsFouncation(List<HomeFouncationEntity.Icon2Bean> data) {
//        GlideApp.with(getContext())
//                .load(data.get(0).getIcon())
//                .into(home_pray_img);
//        GlideApp.with(getContext())
//                .load(data.get(1).getIcon())
//                .into(home_oneiromancy_img);
//        GlideApp.with(getContext())
//                .load(data.get(2).getIcon())
//                .into(home_practise_divination_img);
    }

    @Override
    public void renderFouncationBanner(List<HomeFouncationEntity.BannerBean> data) {
        mBannerTools.setAdapter(new HomeBannerToolsAdapter(data));
    }

    @Override
    public void renderCalendarDay(CalendarDayEntity data) {
        tv_today_chong_tips.setText(getResources().getString(R.string.tv_chong) + " " + data.getChong());//冲
        tv_today_sha_tips.setText(getResources().getString(R.string.tv_sha) + " " + data.getSha());//煞
        tv_joy_god.setText(getResources().getString(R.string.tv_joy_god) + " " + data.getXishen());//喜神
        tv_bless_god.setText(getResources().getString(R.string.tv_bless_god) + " " + data.getFushen());//福神
        tv_money_god.setText(getResources().getString(R.string.tv_money_god) + " " + data.getCaishen());//财神
        mRecycleDayYi.setAdapter(new CalendarDayAdapter(data.getYi()));
        mRecycleDayJi.setAdapter(new CalendarDayAdapter(data.getJi()));
    }

    @Override
    public void rendercalendarHour(List<CalendarHourEntity> data) {

        calendarHourBeanList = data;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 1 && hour < 3) {
            setHourYJ(data, 1);
            ganAdapter.selected(1);
        } else if (hour >= 3 && hour < 5) {
            setHourYJ(data, 2);
            ganAdapter.selected(2);
        } else if (hour >= 5 && hour < 7) {
            setHourYJ(data, 3);
            ganAdapter.selected(3);
        } else if (hour >= 7 && hour < 9) {
            setHourYJ(data, 4);
            ganAdapter.selected(4);

        } else if (hour >= 9 && hour < 11) {
            setHourYJ(data, 5);
            ganAdapter.selected(5);
        } else if (hour >= 11 && hour < 13) {
            setHourYJ(data, 6);
            ganAdapter.selected(6);
        } else if (hour >= 13 && hour < 15) {
            setHourYJ(data, 7);
            ganAdapter.selected(7);
        } else if (hour >= 15 && hour < 17) {
            setHourYJ(data, 8);
            ganAdapter.selected(8);
        } else if (hour >= 17 && hour < 19) {
            setHourYJ(data, 9);
            ganAdapter.selected(9);
        } else if (hour >= 19 && hour < 21) {
            setHourYJ(data, 10);
            ganAdapter.selected(10);
        } else if (hour >= 21 && hour < 23) {
            setHourYJ(data, 11);
            ganAdapter.selected(11);
        } else {
            setHourYJ(data, 0);
            ganAdapter.selected(0);
        }

    }

    /**
     * 设置时辰宜忌
     *
     * @param data
     * @param i
     */
    private void setHourYJ(List<CalendarHourEntity> data, int i) {
        yi = data.get(i).getYi();
        ji = data.get(i).getJi();
        tv_yj_start.setText(data.get(i).getStart_hour());
        tv_yj_end.setText(data.get(i).getEnd_hour());
        tv_hour_yj.setText(data.get(i).getGan_zhi());
        mRecycleTimeJi.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecycleTimeYi.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecycleTimeYi.setAdapter(new CalendarDayAdapter(Arrays.asList(AndroidUtil.split(yi,
                " ".charAt(0)))));
        mRecycleTimeJi.setAdapter(new CalendarDayAdapter(Arrays.asList(AndroidUtil.split(ji,
                " ".charAt(0)))));
    }

    @Override
    public void renderFortune(List<FortuneEntity.FortuneBean.Bean> data) {

        fortuneEntityList = data;
        fortuneArrayList = getResources().getStringArray(R.array.fortune_stick);
        List<FortuneGradeEntity> fortuneGradeEntityList = new ArrayList<>();
        List<FortuneListImageEntity> fortuneListImageEntityList = new ArrayList<>();
        for (int i = 0; i < fortuneArrayList.length; i++) {
            FortuneGradeEntity entity = new FortuneGradeEntity(data.get(i).getScore(),
                    fortuneArrayList[i]);
            fortuneGradeEntityList.add(entity);

            FortuneListImageEntity fortuneListImageEntity = new FortuneListImageEntity(fortuneArrayList[i], data.get(i).getIcon());
            fortuneListImageEntityList.add(fortuneListImageEntity);
        }
        recycle_fortune_grade.setAdapter(new FortuneGradeAdapter(fortuneGradeEntityList,
                getActivity()));

        FortuneBagAdapter fortuneBagAdapter =
                new FortuneBagAdapter(fortuneListImageEntityList);
        recycle_fortune_bag.setAdapter(fortuneBagAdapter);
        fortuneBagAdapter.setOnItemClickListener(this);
    }

    @Override
    public void renderFortuneTip(FortuneEntity data) {
        maxProgress = data.getGeneral_comment().getAverage();
        tv_fortune.setText(getResources().getText(R.string.tv_fortune) + data.getPosition().getFinance());//财运位
        tv_lucky_color.setText(getResources().getText(R.string.tv_lucky_color) + data.getColor());//幸运色
        tv_lucky_number.setText(getResources().getText(R.string.tv_lucky_number) + data.getNumber());//幸运数
        tv_fortune_tips.setText(data.getGeneral_comment().getTips());//幸运提示
        mFortuneHandler.sendEmptyMessageDelayed(FLAG_ONE, 10);


    }

    @Override
    public void renderAskQuestion(List<DailyQuestionEntity> dataBeanList) {
        HomeAskQuestionAdapter homeAskQuestionAdapter = new HomeAskQuestionAdapter(dataBeanList);

        homeAskQuestionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (LocalConfigStore.getInstance().isLogin()) {

                    startActivity(WebViewActivity.newIntent(getContext(),
                            ((DailyQuestionEntity) (adapter.getData().get(position))).getDetail_url() + "&language=" + LocalConfigStore.getInstance().getAcceptLanguage()));

                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }

            }
        });


        mRecycleAsk.setAdapter(homeAskQuestionAdapter);

    }

    @Override
    public void renderNotice(List<ArticleListEntity.ItemsBean> data) {
        mViewFlipper.removeAllViews();

        for (ArticleListEntity.ItemsBean notice : data) {
            TextView textView = new TextView(mViewFlipper.getContext());
            textView.setMaxLines(1);
            textView.setTextSize(16);
            textView.setText(notice.getTitle());
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextColor(Color.parseColor("#787C81"));

            textView.setOnClickListener(view -> startActivity(FindActivity.newIntent(getContext())));
            mViewFlipper.addView(textView);
        }

        mViewFlipper.startFlipping();
    }


    @Override
    public void onAnewRequestNetwork() {
        mPresenter.anewRequest();
    }

    @Subscribe
    public void onEvent(PlayCompleteEvent event) {
        if (event.getType() == 1) {
            switch (event.getPos())
            {
                case 0:
                    LocalConfigStore.getInstance().setJnType_1(true);
                    break;
                case 1:
                    LocalConfigStore.getInstance().setJnType_2(true);
                    break;
                case 2:
                    LocalConfigStore.getInstance().setJnType_3(true);
                    break;
                case 3:
                    LocalConfigStore.getInstance().setJnType_4(true);
                    break;
                case 4:
                    LocalConfigStore.getInstance().setJnType_5(true);
                    break;
                case 5:
                    LocalConfigStore.getInstance().setJnType_6(true);
                    break;
                case 6:
                    LocalConfigStore.getInstance().setJnType_7(true);
                    break;

            }
            CurrencyDialog.show(getActivity(), fortuneArrayList[event.getPos()], 1,
                    fortuneEntityList.get(event.getPos()).getNotes(), "", "", "", "", "");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.HOME_REQUEST_CODE_FORYUNE && resultCode == Activity.RESULT_OK) {
            startActivity(FortuneDetailActivity.newIntent(getActivity(), 2));
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof GanAdapter) {
            ganAdapter.selected(position);
            setHourYJ(calendarHourBeanList, position);
        } else if (adapter instanceof FortuneBagAdapter) {

            if (LocalConfigStore.getInstance().getJnType()<6){
                switch (position)
                {
                    case 0:
                        if (LocalConfigStore.getInstance().getJnType1()){
                            CurrencyDialog.show(getActivity(), fortuneArrayList[position], 1,
                                    fortuneEntityList.get(position).getNotes(), "", "", "", "", "");

                        }else {
                            AdVertiseMentDialog.show(getActivity(), position, getActivity(), 1);
                        }

                        break;
                    case 1:
                        if (LocalConfigStore.getInstance().getJnType2()){
                            CurrencyDialog.show(getActivity(), fortuneArrayList[position], 1,
                                    fortuneEntityList.get(position).getNotes(), "", "", "", "", "");

                        }else {
                            AdVertiseMentDialog.show(getActivity(), position, getActivity(), 1);
                        }
                        break;
                    case 2:
                        if (LocalConfigStore.getInstance().getJnType3()){
                            CurrencyDialog.show(getActivity(), fortuneArrayList[position], 1,
                                    fortuneEntityList.get(position).getNotes(), "", "", "", "", "");

                        }else {
                            AdVertiseMentDialog.show(getActivity(), position, getActivity(), 1);
                        }
                        break;
                    case 3:
                        if (LocalConfigStore.getInstance().getJnType4()){
                            CurrencyDialog.show(getActivity(), fortuneArrayList[position], 1,
                                    fortuneEntityList.get(position).getNotes(), "", "", "", "", "");

                        }else {
                            AdVertiseMentDialog.show(getActivity(), position, getActivity(), 1);
                        }
                        break;
                    case 4:
                        if (LocalConfigStore.getInstance().getJnType5()){
                            CurrencyDialog.show(getActivity(), fortuneArrayList[position], 1,
                                    fortuneEntityList.get(position).getNotes(), "", "", "", "", "");

                        }else {
                            AdVertiseMentDialog.show(getActivity(), position, getActivity(), 1);
                        }
                        break;
                    case 5:
                        if (LocalConfigStore.getInstance().getJnType6()){
                            CurrencyDialog.show(getActivity(), fortuneArrayList[position], 1,
                                    fortuneEntityList.get(position).getNotes(), "", "", "", "", "");

                        }else {
                            AdVertiseMentDialog.show(getActivity(), position, getActivity(), 1);
                        }
                        break;
                }

            }else {
                CurrencyDialog.show(getActivity(), fortuneArrayList[position], 1,
                        fortuneEntityList.get(position).getNotes(), "", "", "", "", "");
            }

        }


    }


    @AfterPermissionGranted(Constants.RQC_WRITE_EXTERNAL_STORAGE)
    public void saveImageLocal(View view) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            AndroidUtil.getViewBitmap(view, getActivity());
        } else {
            EasyPermissions.requestPermissions(getActivity(),
                    getResources().getString(R.string.permiss_write_storage),
                    Constants.RQC_WRITE_EXTERNAL_STORAGE, perms);
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            AppToast.showShort(getResources().getString(R.string.successful));
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            AppToast.showShort(getResources().getString(R.string.tv_failed));
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            AppToast.showShort(getResources().getString(R.string.cancel));
        }
    };

    @Override
    public void onDialogClick(int i, View v) {
        UMImage image = new UMImage(mContext, AndroidUtil.view2Bitmap(v));

        switch (i) {
            case 0:

                if (!AndroidUtil.isWeixinAvilible(getContext())) {
                    AppToast.showShort("请先安装微信");
                    return;
                }
                new ShareAction(getActivity())
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withMedia(image)
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case 1:

                new ShareAction(getActivity())
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .withMedia(image)
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case 2:
                saveImageLocal(v);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        animator.end();
        animator1.end();
    }


}
