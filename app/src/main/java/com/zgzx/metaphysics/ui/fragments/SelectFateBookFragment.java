package com.zgzx.metaphysics.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectFateBookFragment extends BaseRequestActivity {
    @BindView(R.id.mImgBook_onselef)
    ImageView mImgBook_onselef;
    @BindView(R.id.mImgBook_love)
    ImageView mImgBook_love;
    @BindView(R.id.mImgBook_case)
    ImageView mImgBook_case;
    @BindView(R.id.mImgBook_balance)
    ImageView mImgBook_balance;
    @BindView(R.id.mImgBook_home)
    ImageView mImgBook_home;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_select_fate_book_layout;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

    }

    @OnClick(value = {R.id.mImgBook_onselef, R.id.mImgBook_love, R.id.mImgBook_case,R.id.mImgBook_balance_layout,R.id.mImgBook_home})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.mImgBook_onselef:
                break;
            case R.id.mImgBook_love:
                break;
            case R.id.mImgBook_case:
                break;
            case R.id.mImgBook_balance_layout:
                break;
            case R.id.mImgBook_home:
                break;

        }
    }
}
