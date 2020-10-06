package com.zgzx.metaphysics.ui.activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.adapters.MyQuestionsAdapter;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.item.EvenItemDecoration;

import butterknife.BindView;

/**
 * 我的问题
 */
public class MyQuestionsActivity extends BaseActivity {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_my_questions;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F2EFEB"));

        ActivityTitleHelper.setTitle(this, R.string.my_questions);

        mRecyclerView.addItemDecoration(new EvenItemDecoration(mRecyclerView, R.dimen.heart_margin));
        mRecyclerView.setAdapter(new MyQuestionsAdapter());
    }

}
