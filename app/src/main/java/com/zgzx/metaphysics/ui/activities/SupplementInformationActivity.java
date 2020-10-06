package com.zgzx.metaphysics.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.SupplementInformationPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.BirthPlaceDialog;
import com.zgzx.metaphysics.ui.dialogs.BirthDateDialog;
import com.zgzx.metaphysics.utils.ActivityNavigateManager;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.filters.LetterInputFilter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zgzx.metaphysics.network.WebApiConstants.SEX_MAN;
import static com.zgzx.metaphysics.network.WebApiConstants.SEX_WOMAN;


/**
 * 完善人个信息:
 * 1、所有信息都为必填
 * 2、昵称——对应用户昵称（在用户个人信息里有一个昵称）：汉字或字母或数字，可混搭，3个字节起，不支持空格和特殊符号
 * 3、出生时间：可选择农历或阳历 年月日时 如时辰不详，则可选未知
 * 4、出生地：可选择国内，海外 省市县；海外-系统自动加上时差
 * 5、必须所有需要输入的内容有输入信息，才能操作点击 【进入】按钮
 * <p>
 * 添加命书:
 */
public class SupplementInformationActivity extends BaseRequestActivity implements ISingleRequestView<UserDetailEntity> {

    private static final String EXT_TYPE = "TYPE";

    public static final int PERFECT_INFORMATION = 1, // 完善用户信息
            ADD_FATE_BOOK = 2; // 添加命书


    public static Intent newIntent(Context context, int type) {
        return new Intent(context, SupplementInformationActivity.class)
                .putExtra(EXT_TYPE, type);
    }

    @BindView(R.id.group_sex) RadioGroup mGroupSex;
    @BindView(R.id.et_nickname) EditText mEtNickname;
    @BindView(R.id.but_complete) TextView mButComplete;
    @BindView(R.id.iv_arrow_back) ImageView mIvArrowBack;
    @BindView(R.id.tv_birth_place) TextView mTvBirthPlace;
    @BindView(R.id.tv_date_birth) TextView mTvDateBirth;
    @BindView(R.id.layout_birth_place) View mLayoutBirthPlace;

    private int mType;
    private SupplementInformationPresenter mPresenter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_supplement_information;
    }

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 参数
        mType = getIntent().getIntExtra(EXT_TYPE, -1);

        // 标题
        StatusBarUtil.setTranslucentForImageView(this, 0, mIvArrowBack);

        // 限制只输入中文
        mEtNickname.setFilters(new InputFilter[]{new LetterInputFilter()});

        // 逻辑
        mPresenter = new SupplementInformationPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

        // 创建命书
        if (mType == ADD_FATE_BOOK) {
            mButComplete.setText(R.string.create);
            mLayoutBirthPlace.setVisibility(View.GONE);
            findViewById(R.id.view_divider).setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.iv_arrow_back, R.id.layout_birth_place, R.id.layout_date_birth, R.id.but_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow_back: // 返回
               finish();
                break;

            case R.id.layout_birth_place: // 出生地点
                BirthPlaceDialog.show(this, (country, province, city, area, v) -> {
                    mPresenter.setAddress(country, province, city, area);
                    mTvBirthPlace.setText(country + " " + province + " " + city + " " + area);
                });
                break;

            case R.id.layout_date_birth: // 出生日期
                BirthDateDialog.show(this, (time, timestamp, hour, calendarType) -> {
                    mPresenter.setTime(timestamp, hour, calendarType);
                    mTvDateBirth.setText(time);
                });
                break;

            case R.id.but_complete: // 完成, 提交
                submit();
                break;
        }
    }


    // 提交
    private void submit() {
        // 姓名
        String name = mEtNickname.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AppToast.showLong(getString(R.string.error_input_name));
            return;
        }

        // 性别
        int checkedRadioButtonId = mGroupSex.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            AppToast.showLong(getString(R.string.error_selected_sex));
            return;
        }

        // 出生地点
        String birthPlace = mTvBirthPlace.getText().toString();
        if (mLayoutBirthPlace.getVisibility() == View.VISIBLE && TextUtils.isEmpty(birthPlace)) {
            AppToast.showLong(getString(R.string.error_selected_birth_place));
            return;
        }

        // 出生日期
        String dateBirth = mTvDateBirth.getText().toString();
        if (TextUtils.isEmpty(dateBirth)) {
            AppToast.showLong(getString(R.string.error_selected_birth_date));
            return;
        }

        if (mType == ADD_FATE_BOOK) {
            // 创建命书
            mPresenter.create(
                    name,
                    checkedRadioButtonId == R.id.radio_woman ? SEX_WOMAN : SEX_MAN
            );

        } else if (mType == PERFECT_INFORMATION) {
            // 完善资料
//            mPresenter.submit(
//                    name,
//                    checkedRadioButtonId == R.id.radio_woman ? SEX_WOMAN : SEX_MAN
//            );
       }

    }


    @Override
    public void result(UserDetailEntity result) {
          finish(Activity.RESULT_OK);

//        if (mType == ADD_FATE_BOOK) {
//            finish(Activity.RESULT_OK);
//
//        } else if (mType == PERFECT_INFORMATION) {
//            // 完善资料
//            //LocalConfigStore.getInstance().saveUserInfo(result);
//            //ActivityNavigateManager.navigate(this, MainActivity.class);
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
