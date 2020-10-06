package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通知详情
 */
@Deprecated
public class NoticeDetailActivity extends BaseActivity {

    private static final String EXT_TITLE = "TITLE",
            EXT_DATE = "DATE",
            EXT_CONTENT = "CONTENT";

    public static Intent newIntent(Context context, String title, String content) {
        return new Intent(context, NoticeDetailActivity.class)
                .putExtra(EXT_TITLE, title)
                .putExtra(EXT_CONTENT, content);
    }


    @BindView(R.id.tv_date) TextView mTvDate;
    @BindView(R.id.tv_notice_title) TextView mTvNoticeTitle;
    @BindView(R.id.tv_content) TextView mTvContent;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // TODO 未国际化
        ActivityTitleHelper.setTitle(this, "公告详情");

        mTvDate.setText("2020-06-20");
        mTvNoticeTitle.setText(getIntent().getStringExtra(EXT_TITLE));
        mTvContent.setText(getIntent().getStringExtra(EXT_CONTENT));
    }

}
