package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FortuneController;
import com.zgzx.metaphysics.model.entity.AddfortuneDataEntity;
import com.zgzx.metaphysics.model.entity.FortuneEntity;
import com.zgzx.metaphysics.model.event.PlayCompleteEvent;
import com.zgzx.metaphysics.rade_view.LineView;
import com.zgzx.metaphysics.ui.adapters.SimpleFragmentStatePagerAdapter;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.dialogs.AdVertiseMentDialog;
import com.zgzx.metaphysics.ui.dialogs.CurrencyDialog;
import com.zgzx.metaphysics.ui.fragments.FortuneFragment;
import com.zgzx.metaphysics.ui.fragments.FortuneFragment1;
import com.zgzx.metaphysics.ui.fragments.FortuneFragment2;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.image.GlideApp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 运程详请
 *
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/24
 * @Description: 运程
 */
public class FortuneDetailActivity extends BaseActivity implements FortuneController.View {

    private static final String POSTION = "POSTION";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    ViewPager mViewPage;
    @BindView(R.id.iv_avatar)
    ImageView mAvatarImage;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.sexImageView)
    ImageView sexImageView;

    @BindView(R.id.mLineView)
    LineView mLineView;
    @BindView(R.id.img_subject)
    ImageView mImgSubject;
    @BindView(R.id.img_add_fortune)
    ImageView mImgAddFortune;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private int pos;
    private FortuneController.Presenter mPresenter;
    private FortuneEntity mFortuneEntity;

    public static Intent newIntent(Context context, int pos) {
        return new Intent(context, FortuneDetailActivity.class).putExtra(POSTION, pos);
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_fortune_detail;
    }




    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, R.string.fortune_details);
        pos = getIntent().getIntExtra(POSTION, 0);

        mPresenter = new FortuneController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        mPresenter.get_trend_data();
        mPresenter.init((int) (new Date().getTime() / 1000));

        iniPersonalInfo();

        initFragment();
    }



    /**
     * 个人信息
     */
    private void iniPersonalInfo() {
        mTvTime.setText(LocalConfigStore.getInstance().getBirthTime());
        mTvName.setText(LocalConfigStore.getInstance().getUserName());
        LocalConfigStore.getInstance().getAvatar()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(avatar ->
                        GlideApp.with(mAvatarImage)
                                .load(avatar)
                                .avatar()
                                .into(mAvatarImage));
        switch (LocalConfigStore.getInstance().getSex()) {
            case 1:
                sexImageView.setBackgroundResource(R.drawable.icon_man);
                break;
            case 2:
                sexImageView.setBackgroundResource(R.drawable.icon_woman);
                break;
        }
    }

    private void initFragment() {
        String[] titles = getResources().getStringArray(R.array.fortune_time);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new SimpleFragmentStatePagerAdapter(getSupportFragmentManager())
                .add(FortuneFragment.instance(0), titles[0])
                .add(FortuneFragment1.instance(1), titles[1])
                .add(FortuneFragment2.instance(2), titles[2]));
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PlayCompleteEvent event) {
        if (event.getType() == 2) {
//            LocalConfigStore.getInstance().setJnType();
//            LocalConfigStore.getInstance().setJnType_7(true);
            FortuneEntity.TopicBean topicBean = mFortuneEntity.getTopic();
            CurrencyDialog.show(this, getResources().getString(R.string.fortune_toptic), 2,
                    topicBean.getDesc(), topicBean.getShort_desc(), topicBean.getCaution(), "",
                    "", "");
        } else if (event.getType() == 3) {
//            LocalConfigStore.getInstance().setJnType();
//            LocalConfigStore.getInstance().setJnType_8(true);
            String fortuneText1 =
                    getResources().getString(R.string.tv_lucky_wreck) + mFortuneEntity.getPosition().getAviodance();
            String fortuneText2 =
                    getResources().getString(R.string.tv_fortune) + mFortuneEntity.getPosition().getFinance();
            String fortuneText3 =
                    getResources().getString(R.string.tv_lucky_popularity) + mFortuneEntity.getPosition().getPeople();
            String fortuneText4 =
                    getResources().getString(R.string.tv_lucky_color) + mFortuneEntity.getColor();
            String fortuneText5 =
                    getResources().getString(R.string.tv_lucky_hospital_bed) + mFortuneEntity.getPosition().getSickness();
            String fortuneText6 =
                    getResources().getString(R.string.tv_lucky_number) + mFortuneEntity.getNumber();
            CurrencyDialog.show(this, getResources().getString(R.string.fortune_add_fortune), 3,
                    fortuneText1, fortuneText2, fortuneText3, fortuneText4, fortuneText5,
                    fortuneText6);
        }

    }

    @OnClick(value = {R.id.img_subject, R.id.img_add_fortune})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_subject:
                //              FortuneEntity.TopicBean topicBean = mFortuneEntity.getTopic();
//                CurrencyDialog.show(this, getResources().getString(R.string.fortune_toptic), 2,
//                        topicBean.getDesc(), topicBean.getShort_desc(), topicBean.getCaution(),
//                        "", "", "");
                AdVertiseMentDialog.show(FortuneDetailActivity.this, 0,
                        FortuneDetailActivity.this, 2);
//                if (LocalConfigStore.getInstance().getJnType() < 10) {
//                    if (LocalConfigStore.getInstance().getJnType7()) {
//                        FortuneEntity.TopicBean topicBean1 = mFortuneEntity.getTopic();
//                        CurrencyDialog.show(this,
//                                getResources().getString(R.string.fortune_toptic), 2,
//                                topicBean1.getDesc(), topicBean1.getShort_desc(),
//                                topicBean1.getCaution(), "", "", "");
//                    } else {
//                        AdVertiseMentDialog.show(FortuneDetailActivity.this, 0,
//                                FortuneDetailActivity.this, 2);
//                    }
//                } else {
//                    FortuneEntity.TopicBean topicBean2 = mFortuneEntity.getTopic();
//                    CurrencyDialog.show(this, getResources().getString(R.string.fortune_toptic),
//                            2, topicBean2.getDesc(), topicBean2.getShort_desc(),
//                            topicBean2.getCaution(), "", "", "");
//                }


                break;
            case R.id.img_add_fortune:
//                String fortuneText1 =
//                        getResources().getString(R.string.tv_lucky_wreck) + mFortuneEntity.getPosition().getAviodance();
//                String fortuneText2 =
//                        getResources().getString(R.string.tv_fortune) + mFortuneEntity.getPosition().getFinance();
//                String fortuneText3 =
//                        getResources().getString(R.string.tv_lucky_popularity) + mFortuneEntity.getPosition().getPeople();
//                String fortuneText4 =
//                        getResources().getString(R.string.tv_lucky_color) + mFortuneEntity.getColor();
//                String fortuneText5 =
//                        getResources().getString(R.string.tv_lucky_hospital_bed) + mFortuneEntity.getPosition().getSickness();
//                String fortuneText6 =
//                        getResources().getString(R.string.tv_lucky_number) + mFortuneEntity.getNumber();
//                CurrencyDialog.show(this, getResources().getString(R.string.fortune_add_fortune),
//                        3, fortuneText1, fortuneText2, fortuneText3, fortuneText4, fortuneText5,
//                        fortuneText6);
                AdVertiseMentDialog.show(FortuneDetailActivity.this, 0,
                        FortuneDetailActivity.this, 3);
//                if (LocalConfigStore.getInstance().getJnType() < 10) {
//                    if (LocalConfigStore.getInstance().getJnType8()) {
//                        String fortuneText12 =
//                                getResources().getString(R.string.tv_lucky_wreck) + mFortuneEntity.getPosition().getAviodance();
//                        String fortuneText22 =
//                                getResources().getString(R.string.tv_fortune) + mFortuneEntity.getPosition().getFinance();
//                        String fortuneText32 =
//                                getResources().getString(R.string.tv_lucky_popularity) + mFortuneEntity.getPosition().getPeople();
//                        String fortuneText42 =
//                                getResources().getString(R.string.tv_lucky_color) + mFortuneEntity.getColor();
//                        String fortuneText52 =
//                                getResources().getString(R.string.tv_lucky_hospital_bed) + mFortuneEntity.getPosition().getSickness();
//                        String fortuneText62 =
//                                getResources().getString(R.string.tv_lucky_number) + mFortuneEntity.getNumber();
//                        CurrencyDialog.show(this,
//                                getResources().getString(R.string.fortune_add_fortune), 3,
//                                fortuneText12, fortuneText22, fortuneText32, fortuneText42,
//                                fortuneText52, fortuneText62);
//                    } else {
//                        AdVertiseMentDialog.show(FortuneDetailActivity.this, 0,
//                                FortuneDetailActivity.this, 3);
//
//                    }
//                } else {
//                    String fortuneText11 =
//                            getResources().getString(R.string.tv_lucky_wreck) + mFortuneEntity.getPosition().getAviodance();
//                    String fortuneText21 =
//                            getResources().getString(R.string.tv_fortune) + mFortuneEntity.getPosition().getFinance();
//                    String fortuneText31 =
//                            getResources().getString(R.string.tv_lucky_popularity) + mFortuneEntity.getPosition().getPeople();
//                    String fortuneText41 =
//                            getResources().getString(R.string.tv_lucky_color) + mFortuneEntity.getColor();
//                    String fortuneText51 =
//                            getResources().getString(R.string.tv_lucky_hospital_bed) + mFortuneEntity.getPosition().getSickness();
//                    String fortuneText61 =
//                            getResources().getString(R.string.tv_lucky_number) + mFortuneEntity.getNumber();
//                    CurrencyDialog.show(this,
//                            getResources().getString(R.string.fortune_add_fortune), 3,
//                            fortuneText11, fortuneText21, fortuneText31, fortuneText41,
//                            fortuneText51, fortuneText61);
//                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);

    }

    @Override
    public void renderFortune(List<FortuneEntity.FortuneBean.Bean> data) {

    }

    @Override
    public void renderFortuneAll(FortuneEntity data) {
        mFortuneEntity = data;
    }

    @Override
    public void renderPersonalFortune(int score, String fortune) {

    }

    @Override
    public void renderPersonalFortune(FortuneEntity.GeneralCommentBean generalCommentBean) {

    }

    @Override
    public void renderMoudleImg(List<String> imgs) {
        GlideApp.with(mImgSubject)
                .load(imgs.get(0))
                .into(mImgSubject);

        GlideApp.with(mImgAddFortune)
                .load(imgs.get(1))
                .into(mImgAddFortune);
    }

    @Override
    public void renderTrend(List<String> xDateValue, List<String> yTipValue,
                            List<Integer> xKeyValue, List<Float> yNumberValue) {

        mLineView.setYValues(yNumberValue);
        mLineView.setXValues(xDateValue, yTipValue, xKeyValue);


    }

    @Override
    public void onAddFourtune(AddfortuneDataEntity addfortuneDataEntity) {
        System.out.println("addfortuneDataEntity");
//        if (addfortuneDataEntity != null) {
//            P.putInt(Constants.add_fortune_times, addfortuneDataEntity.getAdd_fortune_times());
//            EventBus.getDefault().post(new OnGetFourtuneEvent(addfortuneDataEntity));
////            EventBus.getDefault().post(new OnGetFourtuneEvent(new AddfortuneDataEntity(4,5,3,2)));
//
//        }

    }

    @Override
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {

    }
}
