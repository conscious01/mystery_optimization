package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FeedBackController;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.image.GlideEngine;

import java.util.List;
import java.util.stream.Collectors;

import and.fast.widget.image.add.AddImageLayout;
import and.fast.widget.image.add.OnAddClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseRequestActivity implements OnAddClickListener,
        FeedBackController.View {
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.et_refused_content)
    EditText etRefusedContent;
    @BindView(R.id.tv_input_number)
    TextView tvInputNumber;
    @BindView(R.id.ll_master_answer)
    RelativeLayout llMasterAnswer;
    @BindView(R.id.add_image_view)
    AddImageLayout addImageView;
    @BindView(R.id.tv_done)
    TextView tvDone;

    @Override
    protected int getContentLayoutId() {
        return R.layout.feed_back_activity;
    }

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    FeedBackController.FeedBackPresenter mPresenter;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.feed_back);

        mPresenter = new FeedBackController.FeedBackPresenter();
        mPresenter.setModelAndView(this);

        etRefusedContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvInputNumber.setText(s.toString().length() + "/140");
            }
        });
        // 添加照片
        addImageView.setOnAddClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv_done)
    public void onViewClicked() {
        if (!etRefusedContent.getText().toString().isEmpty()) {

            if (!addImageView.obtainData().isEmpty()) {
                mPresenter.applyWithImage(etRefusedContent.getText().toString(),
                        addImageView.obtainData());

            } else {
                mPresenter.apply(etRefusedContent.getText().toString());
            }

        } else {
            ToastUtils.showShort(R.string.pls_input_content);
        }
    }

    @Override
    public void add() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(Constants.QUESTION_PHOTO_SIZE - addImageView.obtainData().size())
                .isCompress(true)
                .isPreviewVideo(false)
                .forResult(new OnResultCallbackListener<LocalMedia>() {

                    @Override
                    public void onResult(List<LocalMedia> result) {
                        addImageView.addPath(result.stream().map(LocalMedia::getRealPath).collect(Collectors.toList()));
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }

                });
    }

    @Override
    public void onApplyResult() {
        ToastUtils.showShort(getString(R.string.successful));
        finish();
    }
}
