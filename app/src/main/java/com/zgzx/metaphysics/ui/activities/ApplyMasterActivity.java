package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.ApplyMasterPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.core.ISingleRequestView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.TextWatcherAdapter;
import com.zgzx.metaphysics.utils.image.GlideEngine;
import com.zgzx.metaphysics.utils.watchers.EmptyRule;
import com.zgzx.metaphysics.utils.watchers.TextWatchers;

import java.util.List;
import java.util.stream.Collectors;

import and.fast.widget.image.add.AddImageLayout;
import and.fast.widget.image.add.OnAddClickListener;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请成为师傅
 */
public class ApplyMasterActivity extends BaseRequestActivity implements
        ISingleRequestView<Void>,
        OnAddClickListener {

    @BindView(R.id.but_submit) Button mButSubmit;
    @BindView(R.id.tv_length) TextView mTvLength;
    @BindView(R.id.et_content) EditText mEtContent;
    @BindView(R.id.et_mobile_number) EditText mEtMobileNumber;
    @BindView(R.id.add_image_view) AddImageLayout mAddImageView;

    private ApplyMasterPresenter mPresenter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_apply_master;
    }

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.apply_become_master);

        mTvLength.setText(String.format(getString(R.string.placeholder_word_length), 200));
        mAddImageView.setOnAddClickListener(this);
        mEtContent.addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable s) {
                mTvLength.setText(String.format(getString(R.string.placeholder_word_length), s.length()));
            }

        });

        // 手机号格式
        mEtMobileNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // 点击
        TextWatchers.add(mButSubmit, new EmptyRule(mEtContent, mEtMobileNumber));

        // 请求
        mPresenter = new ApplyMasterPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
    }


    @OnClick(R.id.but_submit)
    public void onViewClicked(View view) {
        if (!mAddImageView.obtainData().isEmpty()) {
            mPresenter.apply(
                    mEtContent.getText().toString(),
                    mEtMobileNumber.getText().toString(),
                    mAddImageView.obtainData()
            );

        } else {
            AppToast.showShort(getString(R.string.error_selected_image));
        }

    }


    @Override
    public void result() {
        startActivity(MainActivity.class);
        AppToast.showShort(getString(R.string.successful));
    }


    @Override
    public void add() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(3 - mAddImageView.obtainData().size())
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
}
