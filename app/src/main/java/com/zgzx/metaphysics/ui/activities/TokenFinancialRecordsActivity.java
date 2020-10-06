package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.adapters.FinancialRecordsAdapter;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 财务记录
 */
@Deprecated
public class TokenFinancialRecordsActivity extends BaseActivity {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_token_financial_records;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.financial_records);

        mRecyclerView.setAdapter(new FinancialRecordsAdapter());
    }

}
