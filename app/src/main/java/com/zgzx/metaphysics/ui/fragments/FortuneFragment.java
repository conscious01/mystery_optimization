package com.zgzx.metaphysics.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FortuneController;
import com.zgzx.metaphysics.model.entity.AddfortuneDataEntity;
import com.zgzx.metaphysics.model.entity.FortuneEntity;
import com.zgzx.metaphysics.model.entity.FortuneGradeEntity;
import com.zgzx.metaphysics.rade_view.CircleProgress;
import com.zgzx.metaphysics.ui.activities.WebViewActivity;
import com.zgzx.metaphysics.ui.adapters.FortuneDeatailAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.utils.LunarCalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 运势
 */
public class FortuneFragment extends BaseRequestFragment implements FortuneController.View {
    private static final String EXT_POS = "POS";

    @BindView(R.id.fortune_recycle_grade)
    RecyclerView mRecycleFortuneGrade;
    @BindView(R.id.tv_time_ten)
    TextView tv_time_ten;

    @BindView(R.id.tv_time_ge)
    TextView tv_time_ge;
    @BindView(R.id.tv_date)
    TextView tv_date;//阳历
    @BindView(R.id.tv_lunarCalendar)
    TextView tv_lunarCalendar;//农历

    @BindView(R.id.circle_progress_bar)
    CircleProgress mCircleProgressBar;
    @BindView(R.id.tvAddFortune)
    TextView mAddFortune;//增运
    @BindView(R.id.tv_fortune_evaluate)
    TextView mFortuneEvaluate;//运势总评
    private FortuneController.Presenter mPresenter;
    private Calendar calendar;
    private int pos;
    private final static int[] COLORS = new int[]{Color.parseColor("#FFDD88"), Color.parseColor(
            "#FF7F2A")};

    public static Fragment instance(int pos) {
        FortuneFragment fragment = new FortuneFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(EXT_POS, pos);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fortune_detail_layout_1;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 请求
        mPresenter = new FortuneController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        pos = getArguments().getInt(EXT_POS, -1);
        calendar = Calendar.getInstance();
        mPresenter.init((int) (new Date().getTime() / 1000));

        // 当前日历，时间
        initCalendar();
        mAddFortune.setText(getResources().getString(R.string.tv_add_fortune));
        mAddFortune.setOnClickListener(view -> {
                    //红包接服
                    // TODO 已接红包次数先传递0
                    String hbjfURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                            "/red_packet/red_packet?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&degree=0" + "&status_bar_height=" + 24;

                    startActivity(WebViewActivity.newIntent(getActivity(), hbjfURL));
                }
        );

    }

    private void initCalendar() {
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "年" + month + "月";
        tv_date.setText(date);
        if (day > 9) {
            String dayString = String.valueOf(day);
            tv_time_ten.setText(dayString.substring(0, 1));
            tv_time_ge.setText(dayString.substring(1, dayString.length()));
        } else {
            tv_time_ten.setText("0");
            tv_time_ge.setText(day + "");
        }
        tv_lunarCalendar.setText(LunarCalendarUtil.get(getResources(), calendar));
    }


    @Override
    public void renderFortune(List<FortuneEntity.FortuneBean.Bean> data) {
        String[] array = getResources().getStringArray(R.array.fortune_stick);

        List<FortuneGradeEntity> fortuneGradeEntityList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            FortuneGradeEntity entity = new FortuneGradeEntity(data.get(i).getScore(), array[i]);
            fortuneGradeEntityList.add(entity);
        }

        mRecycleFortuneGrade.setAdapter(new FortuneDeatailAdapter(fortuneGradeEntityList,
                getActivity()));
    }

    @Override
    public void renderFortuneAll(FortuneEntity data) {

    }


    @Override
    public void renderPersonalFortune(int score, String fortune) {
        mCircleProgressBar.setGradientColors(COLORS);
        mCircleProgressBar.setValue((float) score);
        mFortuneEvaluate.setText(fortune);

    }

    @Override
    public void renderMoudleImg(List<String> imgs) {

    }

    @Override
    public void renderTrend(List<String> xDateValue, List<String> yTipValue,
                            List<Integer> xKeyValue, List<Float> yNumberValue) {

    }

    @Override
    public void onAddFourtune(AddfortuneDataEntity addfortuneDataEntity) {

    }


    @Override
    public void onAnewRequestNetwork() {
        super.onAnewRequestNetwork();
        // mPresenter.anewRequest();
    }


}
