package com.zgzx.metaphysics.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.github.penfeizhou.animation.apng.APNGAssetLoader;
import com.github.penfeizhou.animation.apng.APNGDrawable;
import com.jaeger.library.StatusBarUtil;
import com.lxj.xpopup.core.BasePopupView;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.SupplementInformationPresenter;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.ui.dialogs.BirthDateDialog;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AppToast;



import butterknife.BindView;
import butterknife.OnClick;

import static com.zgzx.metaphysics.network.WebApiConstants.SEX_MAN;
import static com.zgzx.metaphysics.network.WebApiConstants.SEX_WOMAN;

public class CreateFateBookActivity extends BaseRequestActivity implements ISingleRequestView<UserDetailEntity> {
    @BindView(R.id.group_sex)
    RadioGroup group_sex;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_date)
    TextView edit_date;
    @BindView(R.id.create_layout)
    LinearLayout create_layout;
    @BindView(R.id.kmImgView_create)
    ImageView kmImgView_create;

    @BindView(R.id.kmImgView_btn)
    ImageView kmImgView_btn;
    @BindView(R.id.iv_arrow_back)
    ImageView iv_arrow_back;
    private int sex, hourTime;
    private static final String KM_IMAGE_PATH = "assets://icon_create_bg.png";
    private SupplementInformationPresenter mPresenter;
    private BasePopupView basePopupView;

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, CreateFateBookActivity.class)
                .putExtra(Constants.TYPE, type);
    }

    @Override
    protected IStatusView createStatusView() {
        return new ToastRequestStatusView(this);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_create_book_layout;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

        ActivityTitleHelper.setTitle(this, R.string.tv_title);

        iv_arrow_back.setOnClickListener(v -> onBackPressed());

        mPresenter = new SupplementInformationPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        kmImgView_create.setVisibility(View.VISIBLE);
        APNGAssetLoader assetLoader = new APNGAssetLoader(this, "icon_create_bg.png");
// 创建 Drawable
        APNGDrawable apngDrawable = new APNGDrawable(assetLoader);
        apngDrawable.setLoopLimit(1);
        apngDrawable.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationStart(Drawable drawable) {
                super.onAnimationStart(drawable);
            }

            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                kmImgView_create.setVisibility(View.GONE);
                create_layout.setVisibility(View.VISIBLE);

            }
        });

// 设置后自动播放
        kmImgView_create.setImageDrawable(apngDrawable);
    }


    @OnClick({R.id.mCreateBtn, R.id.edit_date, R.id.iv_arrow_back_layout, R.id.kmImgView_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.edit_date:
                // 出生日期
                BirthDateDialog.show(this, (time, timestamp, hour, calendarType) -> {
                    switch (calendarType) {
                        case 1://农历
                            edit_date.setText(time);
                            mPresenter.setTime(timestamp, hour, calendarType);
                            break;
                        case 2://阳历
                            switch (hour) {
                                case 0:
                                    hourTime = 23;
                                    break;
                                case 1:
                                    hourTime = 1;
                                    break;
                                case 2:
                                    hourTime = 3;
                                    break;
                                case 3:
                                    hourTime = 5;
                                    break;
                                case 4:
                                    hourTime = 7;
                                    break;
                                case 5:
                                    hourTime = 9;
                                    break;
                                case 6:
                                    hourTime = 11;
                                    break;
                                case 7:
                                    hourTime = 13;
                                    break;
                                case 8:
                                    hourTime = 15;
                                    break;
                                case 9:
                                    hourTime = 17;
                                    break;
                                case 10:
                                    hourTime = 19;
                                    break;
                                case 11:
                                    hourTime = 21;
                                    break;
                            }
                            edit_date.setText(time);
                            mPresenter.setTime(timestamp, hourTime, calendarType);
                            break;
                    }

                });
                break;
            case R.id.mCreateBtn:
                createFateBook();
                break;
            case R.id.iv_arrow_back_layout:
                onBackPressed();
                break;
        }
    }

    private void createFateBook() {
        //  kmImgView_btn.setVisibility(View.VISIBLE);
        // 姓名
        String name = edit_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AppToast.showLong(getString(R.string.error_input_name));
            return;
        }

        // 性别
        int checkedRadioButtonId = group_sex.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            AppToast.showLong(getString(R.string.error_selected_sex));
            return;
        }
        // 出生日期
        String dateBirth = edit_date.getText().toString();
        if (TextUtils.isEmpty(dateBirth)) {
            AppToast.showLong(getString(R.string.error_selected_birth_date));
            return;
        }


        // 创建命书
        mPresenter.create(
                name,
                checkedRadioButtonId == R.id.radio_woman ? SEX_WOMAN : SEX_MAN
        );


    }

    @Override
    public void result(UserDetailEntity result) {
        finish(Activity.RESULT_OK);
//        kmImgView_btn.setVisibility(View.VISIBLE);
//        ApngLoaderStart.loadImage(ApngImageUtils.Scheme.ASSETS.wrap("icon_btn.png"), kmImgView_btn,new ApngImageLoadingListener(new ApngPlayListener() {
//            @Override
//            public void onAnimationStart(ApngDrawable drawable) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(ApngDrawable drawable) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(ApngDrawable drawable) {
//                drawable.stop();
//                kmImgView_btn.setVisibility(View.GONE);
//                finish(Activity.RESULT_OK);
//
//            }
//        }));
    }


}
