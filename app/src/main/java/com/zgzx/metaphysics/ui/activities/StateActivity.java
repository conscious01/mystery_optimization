package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 状态页面
 */
public class StateActivity extends BaseActivity {

    public static final int QUESTION = 1, // 提问
            PAY = 2; // 支付

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, StateActivity.class)
                .putExtra("TYPE", type);
    }


    @BindView(R.id.tv_state) TextView mTvState;
    @BindView(R.id.tv_additional) TextView mTvAdditional;
    @BindView(R.id.but_positive) Button mButPositive;
    @BindView(R.id.but_negative) Button mButNegative;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_state;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra("TYPE", -1);

        if (type == QUESTION){
            mTvState.setText(R.string.asked_successfully);
            mTvAdditional.setText(R.string.question_additional);
            mButPositive.setText(R.string.my_questions_answer);
            mButNegative.setText(R.string.back_find_page);

            mButNegative.setOnClickListener(v -> startActivity(MainActivity.class)); // 返回发现页面
            mButPositive.setOnClickListener(v -> // 我的问答
                    startActivity(new Intent(this, MyQuestionsActivity.class)));

        } else if (type == PAY){
            mTvAdditional.setVisibility(View.GONE);
            mTvState.setText(R.string.payment_successful);
            mButPositive.setText(R.string.now_ask_questions);
            mButNegative.setText(R.string.later);

            mButNegative.setOnClickListener(v -> onBackPressed()); // 返回师傅页面
            mButPositive.setOnClickListener(v -> // 提问
                    startActivity(new Intent(this, AskQuestionsActivity.class)));
        }
    }


    @OnClick(R.id.iv_arrow_back)
    public void onViewClicked(View view) {
        onBackPressed();
    }

}
