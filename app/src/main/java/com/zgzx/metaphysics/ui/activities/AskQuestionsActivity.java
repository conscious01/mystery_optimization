package com.zgzx.metaphysics.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.ApplyQuestionController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.model.entity.MasterDetailEntityNew;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.BirthDateDialog;
import com.zgzx.metaphysics.ui.dialogs.BirthPlaceDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.ViewUtils;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;
import com.zgzx.metaphysics.utils.image.GlideEngine;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import and.fast.widget.image.add.AddImageLayout;
import and.fast.widget.image.add.OnAddClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提问题
 */
public class AskQuestionsActivity extends BaseRequestActivity implements OnAddClickListener,
        ApplyQuestionController.View {

    @BindView(R.id.tv_action)
    TextView mTvAction;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.et_question_content)
    EditText mEtQuestionContent;
    @BindView(R.id.et_question_title)
    EditText mEtQuestionTitle;
    @BindView(R.id.add_image_view)
    AddImageLayout mAddImageView;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_birth_place)
    TextView mTvBirthPlace;
    @BindView(R.id.tv_date_birth)
    TextView mTvDateBirth;
    @BindView(R.id.group_sex)
    RadioGroup mGroupSex;
    @BindView(R.id.group_target)
    RadioGroup mGroupTarget;
    @BindView(R.id.tv_question_type)
    TextView mTvQuestionType;
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.radio_self)
    RadioButton radioSelf;
    @BindView(R.id.radio_other)
    RadioButton radioOther;
    @BindView(R.id.tv_see_example)
    TextView tvSeeExample;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_ask_now)
    TextView tvAskNow;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_born)
    TextView tvBorn;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_q_detail)
    TextView tvQDetail;

    private ApplyQuestionController.ApplyQuestionPresenter mPresenter;



    @Override
    public int getContentLayoutId() {
        return R.layout.activity_ask_questions;
    }

    MasterDetailEntityNew masterDetailEntityNew;

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {


        ViewUtils.textViewDifferentColor(getString(R.string.ur_name),"*",tvName, Color.parseColor("#9A3E3E"));
        ViewUtils.textViewDifferentColor(getString(R.string.ur_gender),"*",tvGender, Color.parseColor("#9A3E3E"));
        ViewUtils.textViewDifferentColor(getString(R.string.q_boren_location),"*",tvBorn, Color.parseColor("#9A3E3E"));
        ViewUtils.textViewDifferentColor(getString(R.string.q_bore_location),"*",tvLocation, Color.parseColor("#9A3E3E"));
        ViewUtils.textViewDifferentColor(getString(R.string.question_detail),"*",tvQDetail, Color.parseColor("#9A3E3E"));


        ActivityTitleHelper.setTitle(this, R.string.ask_a_question);

        Intent intent = getIntent();
        if (intent != null) {
            masterDetailEntityNew =
                    (MasterDetailEntityNew) intent.getSerializableExtra(Constants.EXT_TYPE);
        }

        // 滑动到最底部
//        KeyboardUtils.registerSoftInputChangedListener(this, height -> {
//            if (height > 0 && (mEtQuestionTitle.isFocused() || mEtQuestionContent.isFocused())) {
//                mScrollView.fullScroll(View.FOCUS_DOWN);
//            }
//        });

        // 添加照片
        mAddImageView.setOnAddClickListener(this);

        // 显示
        mGroupTarget.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_self:
//                    renderSelfDetail();
                    break;

                case R.id.radio_other:
//                    renderOther();
                    break;
            }
        });

        mGroupTarget.check(R.id.radio_self);
        mTvAction.setEnabled(true);
        mTvQuestionType.setText("婚姻情感");
        if (masterDetailEntityNew.getVip_discount()>0) {
            tvAmount.setText("￥" + masterDetailEntityNew.getPrice() );
            tvDiscount.setText(getString(R.string.member_1)+((int)(masterDetailEntityNew.getVip_discount()*10))+getString(R.string.discount));

        }else {
            tvAmount.setText("￥" + masterDetailEntityNew.getPrice());

        }

        mPresenter = new ApplyQuestionController.ApplyQuestionPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

    }

    // 我的信息
    private void renderSelfDetail() {
        mEtName.setText("张三");
        mTvSex.setText("男");
        mTvDateBirth.setText("农历 2020-01-01 00:00-00:59");
        mTvBirthPlace.setText("中国 广东省 深圳市");
        mGroupSex.setVisibility(View.GONE);
        mTvSex.setVisibility(View.VISIBLE);
    }

    // 其他人，清空信息
    private void renderOther() {
        mEtName.setText(null);
        mTvSex.setText(null);
        mTvDateBirth.setText(null);
        mTvBirthPlace.setText(null);
        mTvSex.setVisibility(View.GONE);
        mGroupSex.setVisibility(View.VISIBLE);
    }


//    @OnClick(R.id.tv_action)
//    public void onViewClicked(View v) {
//        startActivity(StateActivity.newIntent(v.getContext(), StateActivity.QUESTION));
//    }


    @Override
    public void add() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(Constants.QUESTION_PHOTO_SIZE - mAddImageView.obtainData().size())
                .isCompress(true)
                .isPreviewVideo(false)
                .forResult(new OnResultCallbackListener<LocalMedia>() {

                    @Override
                    public void onResult(List<LocalMedia> result) {
                        mAddImageView.addPath(result.stream().map(LocalMedia::getRealPath).collect(Collectors.toList()));
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }

                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.unregisterSoftInputChangedListener(getWindow());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    int mtimestamp, mHour, mcalendarType;

    @OnClick({R.id.tv_see_example, R.id.tv_notice, R.id.tv_date_birth, R.id.tv_birth_place,
            R.id.tv_ask_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_see_example:
                String slURL = LocalConfigStore.getInstance().getH5_Base() + "/pages/master" +
                        "/sample_graph";
                startActivity(WebViewActivity.newIntent(this, slURL));
                break;
            case R.id.tv_notice:
                String ywxzURL = LocalConfigStore.getInstance().getH5_Base() + "/pages/master" +
                        "/operation";
                startActivity(WebViewActivity.newIntent(this, ywxzURL));
                break;

            case R.id.tv_date_birth:
                BirthDateDialog.show(this, (time, timestamp, hour, calendarType) -> {
                    mTvDateBirth.setText(time);
                    mtimestamp = timestamp;
                    mHour = DateUtils.getRealHour(hour,calendarType);
                    mcalendarType = calendarType;

                });
                break;
            case R.id.tv_birth_place:
                BirthPlaceDialog.show(this, (country, province, city, area, v) -> {
                    mTvBirthPlace.setText(country + " " + province + " " + city + " " + area);
                });
                break;

            case R.id.tv_ask_now:

                onClickAskQuestion();
                break;
        }
    }

    private void onClickAskQuestion() {


//        if (true) {
//            mPresenter.testUploadImages(mAddImageView.obtainData()
//            );
//            return;
//        }


        if (mEtName.getText().toString().isEmpty()) {
            ToastUtils.showShort(R.string.error_no_name);
            return;
        } else if (rbMale.isChecked() == false && rbFemale.isChecked() == false) {
            ToastUtils.showShort(R.string.error_no_gender);
            return;
        } else if (mTvDateBirth.getText().toString().isEmpty()) {
            ToastUtils.showShort(R.string.error_no_birthday);
            return;
        } else if (mTvBirthPlace.getText().toString().isEmpty()) {
            ToastUtils.showShort(R.string.error_no_birth_location);
            return;
        } else if (mEtQuestionContent.getText().toString().isEmpty()) {
            ToastUtils.showShort(R.string.error_no_question_detail);
            return;
        } else if (!cb.isChecked()) {
            ToastUtils.showShort(R.string.error_no_check_notice);
            return;
        } else {
            String nickName = mEtName.getText().toString();
            int gender;
            if (rbMale.isChecked()) {
                gender = 1;
            } else {
                gender = 2;
            }
            int master_id = masterDetailEntityNew.getId();
            int birth_day = mtimestamp;
            int birth_hour = mHour;
            int calendar_type = mcalendarType;
            String birth_area = mTvBirthPlace.getText().toString().trim();
            String content = mEtQuestionContent.getText().toString();
            if (!mAddImageView.obtainData().isEmpty()) {
                mPresenter.applyWithImage(nickName, gender, master_id, birth_day, birth_hour,
                        calendar_type, birth_area, content, mAddImageView.obtainData(),this
                ,LocalConfigStore.getInstance().getAk());

            } else {
                Long time = new Date().getTime() / 1000;
                Map<String, Object> map = new HashMap<>();
                map.put("nickname", nickName);
                map.put("sex", gender);
                map.put("master_id", master_id);
                map.put("birth_day", birth_day);
                map.put("birth_hour", birth_hour);
                map.put("calendar_type", calendar_type);
                map.put("birth_area", birth_area);
                map.put("content", content);

                map.put("timestamp", time + LocalConfigStore.getInstance().getTimestamp());
                String str = HMacMD5Util.getMapToString(map);
                String sign1 = null;
                try {
                    sign1 = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                mPresenter.apply(nickName, gender, master_id, birth_day, birth_hour,
                        calendar_type, birth_area, content,LocalConfigStore.getInstance().getAk(),time + LocalConfigStore.getInstance().getTimestamp(),sign1);
            }
        }
    }


    @Override
    public void onApplyResult(OrderResultEntity detail) {
        OrderPayActivity.start(this, detail, Constants.PAY_ALI);
        finish();
    }
}
