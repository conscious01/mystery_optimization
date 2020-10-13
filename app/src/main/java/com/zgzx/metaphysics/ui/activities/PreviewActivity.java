package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;

import butterknife.ButterKnife;

public class PreviewActivity extends BaseActivity {



    @Override
    protected int getContentLayoutId() {
        return R.layout.previews;
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
