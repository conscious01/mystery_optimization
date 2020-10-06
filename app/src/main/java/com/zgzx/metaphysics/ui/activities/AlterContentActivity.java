package com.zgzx.metaphysics.ui.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.AlterUserInfoPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.controller.views.impl.DialogRequestStatusView;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.watchers.EmptyRule;
import com.zgzx.metaphysics.utils.watchers.TextWatchers;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更改姓名、昵称、师傅简介
 */
public class AlterContentActivity extends BaseRequestActivity implements ICallback {

    private static final String EXT_CONTENT = "CONTENT",
            EXT_PAGE_TYPE = "PAGE_TYPE";

    public static final int NICKNAME = 1, // 昵称
            NAME = 2, // 姓名
            MASTER_INTRODUCTION = 3; // 师傅简介

    public static Intent newIntent(Context context, String content, int type) {
        return new Intent(context, AlterContentActivity.class)
                .putExtra(EXT_CONTENT, content)
                .putExtra(EXT_PAGE_TYPE, type);
    }


    @BindView(R.id.tv_action) TextView mTvAction;
    @BindView(R.id.et_content) EditText mEtContent;

    private AlterUserInfoPresenter mPresenter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_alter_content;
    }

    @Override
    protected IStatusView createStatusView() {
        return new DialogRequestStatusView(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 参数
        String content = getIntent().getStringExtra(EXT_CONTENT);
        int type = getIntent().getIntExtra(EXT_PAGE_TYPE, -1);

        // 逻辑
        mPresenter = new AlterUserInfoPresenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);

        // 页面显示
        if (type == NICKNAME) { // 修改昵称
            ActivityTitleHelper.setTitle(this, getString(R.string.alter_nickname), getString(R.string.save));
            mEtContent.setHint(R.string.hint_alter_nickname);
            mEtContent.setInputType(InputType.TYPE_CLASS_TEXT);
            mEtContent.setImeOptions(EditorInfo.IME_ACTION_DONE);
            mTvAction.setOnClickListener(v -> mPresenter.alterNickname(mEtContent.getText().toString()));

        } else if (type == NAME) { // 修改姓名
            ActivityTitleHelper.setTitle(this, getString(R.string.alter_name), getString(R.string.save));
            mEtContent.setHint(R.string.hint_alter_name);
            mEtContent.setInputType(InputType.TYPE_CLASS_TEXT);
            mEtContent.setImeOptions(EditorInfo.IME_ACTION_DONE);
            mTvAction.setOnClickListener(v -> mPresenter.alterName(mEtContent.getText().toString()));

        } else if (type == MASTER_INTRODUCTION) { // 修改师傅介绍
            ActivityTitleHelper.setTitle(this, getString(R.string.master_introduction), getString(R.string.save));
            mEtContent.setHint(R.string.hint_alter_master_introduction);
            mEtContent.setMaxLines(Constants.MASTER_INTRODUCTION_LENGTH);
            mTvAction.setOnClickListener(v -> mPresenter.alterMasterIntroduction(mEtContent.getText().toString()));
        }

        // 内容
        mEtContent.setText(content);
        TextWatchers.add(mTvAction, new EmptyRule(mEtContent));
    }


    @Override
    public void successful() {
        finish(Activity.RESULT_OK,
                new Intent()
                        .putExtra(Constants.REQ_RESULT, mEtContent.getText().toString())
        );
    }

}
