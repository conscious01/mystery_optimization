package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.LoginController;
import com.zgzx.metaphysics.controller.presenters.AlterUserInfoPresenter;
import com.zgzx.metaphysics.controller.presenters.SupplementInformationPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.event.UpdateFortuneEvent;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.BirthDateDialog;
import com.zgzx.metaphysics.ui.dialogs.BirthPlaceDialog;
import com.zgzx.metaphysics.ui.dialogs.ModifyNameDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.image.GlideApp;
import com.zgzx.metaphysics.utils.image.GlideEngine;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息:
 * 1、性别 、 姓名 、 设置后不可以更改
 * 2、出生日期 一年内只能改一次，但时辰可以随时更改
 * 3、昵称、出生地 和 居住地 可以随时更改 无限制
 */
public class PersonalInformationActivity extends BaseRequestActivity implements ICallback,
        ISingleRequestView<UserDetailEntity>, ModifyNameDialog.OnDialogClickListener, LoginController.View {

    public static Intent newIntent(Context context, UserDetailEntity entity, int type) {
        return new Intent(context, PersonalInformationActivity.class)
                .putExtra(Constants.EXT_PARCELABLE, entity).putExtra(Constants.EXT_TYPE, type);
    }

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_date_birth)
    TextView mTvDateBirth;
    @BindView(R.id.tv_birth_place)
    TextView mTvBirthPlace;
    @BindView(R.id.tv_live_area)
    TextView mTvLiveArea;
    @BindView(R.id.tv_user_id)
    TextView mTvUserId;
    @BindView(R.id.layout_sex)
    LinearLayout layout_sex;
    @BindView(R.id.layout_sex_select)
    LinearLayout layout_sex_select;
    @BindView(R.id.group_sex)
    RadioGroup group_sex;
    @BindView(R.id.save_info)
    Button save_info;
    @BindView(R.id.sex_img)
    ImageView sex_img;


    private AlterUserInfoPresenter mPresenter;
    private int type;
    private int hourTime, sex = 0;
    private String mNickName, birthTime, diZiTime, path, birth_area;
    private SupplementInformationPresenter mSupplementInformationPresenter;
    private LoginController.Presenter mLoginPresenter;

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_personal_information;
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {

        type = getIntent().getIntExtra(Constants.EXT_TYPE, -1);
        mSupplementInformationPresenter = new SupplementInformationPresenter();
        mSupplementInformationPresenter.setModelAndView(this);
        getLifecycle().addObserver(mSupplementInformationPresenter);

        // 请求
        mPresenter = new AlterUserInfoPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        mLoginPresenter = new LoginController.Presenter();
        mLoginPresenter.setModelAndView(this);
        getLifecycle().addObserver(mLoginPresenter);
        switch (type) {
            case 0:
                ActivityTitleHelper.setTitle_GoneImg(this, R.string.perfect_information_1);
                layout_sex.setVisibility(View.GONE);
                layout_sex_select.setVisibility(View.VISIBLE);
                save_info.setVisibility(View.VISIBLE);
                sex_img.setVisibility(View.VISIBLE);
                save_info.setText(getResources().getString(R.string.start_main_1));
                // 逻辑
                GlideApp.with(mIvAvatar)
                        .load(R.drawable.ic_default_avatar)
                        .avatar()
                        .into(mIvAvatar);
                group_sex.setOnCheckedChangeListener((group, checkedId) -> {
                    switch (checkedId) {
                        case R.id.radio_man:
                            sex = 1;
                            break;
                        case R.id.radio_woman:
                            sex = 2;
                            break;
                    }
                });

                break;
            case 1:
                ActivityTitleHelper.setTitle(this, R.string.personal_information);
                save_info.setVisibility(View.GONE);
                UserDetailEntity entity = getIntent().getParcelableExtra(Constants.EXT_PARCELABLE);
                renderUserDetail(entity);
                layout_sex.setVisibility(View.VISIBLE);
                layout_sex_select.setVisibility(View.GONE);
                sex_img.setVisibility(View.GONE);

                break;
        }

    }


    private void renderUserDetail(UserDetailEntity entity) {
        if (entity != null) {
            mNickName = entity.getNickname();
            // 用户姓名
            mTvName.setText(entity.getRealname());

            // 用户昵称
            mTvNickname.setText(entity.getNickname());
            sex = entity.getSex();
            // 性别
            mTvSex.setText(entity.getSex() == WebApiConstants.SEX_WOMAN ? R.string.woman : R.string.man);

            // 出生日期
            mTvDateBirth.setText(DateUtils.getTime(entity.getBirth_day(), DateUtils.PATTERN_2));
            birth_area = entity.getBirth_area();
            // 出生地点
            mTvBirthPlace.setText(entity.getBirth_area());

            // 居住地点
            mTvLiveArea.setText(entity.getLive_area());

            // 用户ID
            mTvUserId.setText(String.valueOf(entity.getUid()));

            // 用户头像
            GlideApp.with(mIvAvatar)
                    .load(entity.getAvatar())
                    .avatar()
                    .into(mIvAvatar);
        }
    }


    @OnClick({R.id.layout_avatar, R.id.layout_nickname,
            R.id.save_info,
            R.id.layout_date_birth, R.id.layout_birth_place, R.id.layout_live_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_avatar:
                selectorPicture();
                break;
            case R.id.save_info:
                if (sex == 0) {
                    AppToast.showShort(getResources().getString(R.string.error_selected_sex));
                    return;
                }
                if (TextUtils.isEmpty(mTvDateBirth.getText().toString())) {
                    AppToast.showShort(getResources().getString(R.string.error_selected_birth_date));
                    return;
                }

                if (TextUtils.isEmpty(mNickName)) {
                    AppToast.showShort(getResources().getString(R.string.hint_alter_nickname));
                    return;
                }
                if (TextUtils.isEmpty(mTvBirthPlace.getText().toString())) {
                    AppToast.showShort(getResources().getString(R.string.error_selected_birth_place));
                    return;
                }
                mSupplementInformationPresenter.submit(birthTime, path, mNickName, sex);
                break;
            case R.id.layout_nickname: // 昵称
                ModifyNameDialog.show(PersonalInformationActivity.this, this, mNickName);
                break;
            case R.id.layout_date_birth:
                String[] branchArray = getResources().getStringArray(R.array.terrestrial_branch_string_array_1);
                // 出生日期
                BirthDateDialog.show(this, (time, timestamp, hour, calendarType) -> {
                    switch (calendarType) {
                        case 1://农历
                            mTvDateBirth.setText(time);
                            birthTime = time;
                            if (type == 0) {
                                mSupplementInformationPresenter.setTime(timestamp, hour, calendarType);
                            } else if (type == 1) {
                                mPresenter.setTime(timestamp, hour, calendarType, birthTime, mNickName, sex, birth_area);
                            }
                            break;
                        case 2://阳历
                            switch (hour) {
                                case 0:
                                    hourTime = 23;
                                    diZiTime = branchArray[0];
                                    break;
                                case 1:
                                    hourTime = 1;
                                    diZiTime = branchArray[1];
                                    break;
                                case 2:
                                    hourTime = 3;
                                    diZiTime = branchArray[2];
                                    break;
                                case 3:
                                    hourTime = 5;
                                    diZiTime = branchArray[3];
                                    break;
                                case 4:
                                    hourTime = 7;
                                    diZiTime = branchArray[4];
                                    break;
                                case 5:
                                    hourTime = 9;
                                    diZiTime = branchArray[5];
                                    break;
                                case 6:
                                    hourTime = 11;
                                    diZiTime = branchArray[6];
                                    break;
                                case 7:
                                    hourTime = 13;
                                    diZiTime = branchArray[7];
                                    break;
                                case 8:
                                    hourTime = 15;
                                    diZiTime = branchArray[8];
                                    break;
                                case 9:
                                    hourTime = 17;
                                    diZiTime = branchArray[9];
                                    break;
                                case 10:
                                    hourTime = 19;
                                    diZiTime = branchArray[10];
                                    break;
                                case 11:
                                    hourTime = 21;
                                    diZiTime = branchArray[11];
                                    break;
                            }

                            mTvDateBirth.setText(time);
                            birthTime = time.substring(0, time.length() - 11) + " " + diZiTime;
                            if (type == 0) {
                                mSupplementInformationPresenter.setTime(timestamp, hourTime, calendarType);
                            } else if (type == 1) {
                                mPresenter.setTime(timestamp, hourTime, calendarType, birthTime, mNickName, sex, birth_area);
                            }

                            break;
                    }

                });
                break;

            case R.id.layout_birth_place: // 出生地点

                BirthPlaceDialog.show(this, (country, province, city, area, v) -> {

                    mTvBirthPlace.setText(country + " " + province + " " + city + " " + area);
                    switch (type) {
                        case 0:
                            mSupplementInformationPresenter.setAddress(country, province, city, area);
                            break;
                        case 1:
                            mPresenter.alterBirthArea(country, province, city, area);
                            break;
                    }

                });


                break;

            case R.id.layout_live_area: // 居住地点
                BirthPlaceDialog.show(this, (country, province, city, area, v) -> {
                    mTvLiveArea.setText(country + " " + province + " " + city + " " + area);
                    mPresenter.alterLiveArea(country, province, city, area);
                });
                break;
        }
    }

    private void selectorPicture() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .isCompress(true)
                .compressQuality(50)
                .isPreviewVideo(false) // 预览视频
                .isPreviewImage(false) // 预览图片
                .forResult(new OnResultCallbackListener<LocalMedia>() {

                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // 上传图片
                        LocalMedia localMedia = result.get(0);
                        path = localMedia.getCompressPath() != null ? localMedia.getCompressPath()
                                : (localMedia.getRealPath() != null)
                                ? localMedia.getRealPath() : localMedia.getAndroidQToPath();
                        if (type == 1) {
                            mPresenter.alterAvatar(path);
                        }


                        // 显示
                        try {

                            GlideApp.with(mIvAvatar)
                                    .load(localMedia.getCompressPath())
                                    .avatar()
                                    .into(mIvAvatar);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }

                })
        ;
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && data != null) {
//            if (requestCode == AlterContentActivity.NICKNAME) {
//                mTvNickname.setText(data.getStringExtra(Constants.REQ_RESULT));
//
//            } else if (requestCode == AlterContentActivity.NAME) {
//                mTvName.setText(data.getStringExtra(Constants.REQ_RESULT));
//            }
//        }
//    }


    @Override
    public void result(UserDetailEntity result) {
        //   LocalConfigStore.getInstance().saveUserInfo(result);
        mLoginPresenter.login(result.getPhone_code(), result.getPhone(), LocalConfigStore.getInstance().getPassword());

    }


    @Override
    public void successful() {
        AppToast.showLong(getString(R.string.successful));
        EventBus.getDefault().post(new UpdateFortuneEvent());
    }


    @Override
    public void onDialogClick(String name, View v) {
        mTvNickname.setText(name);
        switch (type) {
            case 0:
                mNickName = name;
                break;
            case 1:
                mPresenter.alterName(name);
                break;
        }

    }

    @Override
    public void loginOk(UserDetailEntity userDetailEntity) {
        LocalConfigStore.getInstance().saveUserInfo(userDetailEntity);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
