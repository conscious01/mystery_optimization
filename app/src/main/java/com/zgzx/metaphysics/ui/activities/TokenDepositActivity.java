package com.zgzx.metaphysics.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充币
 */
@Deprecated
public class TokenDepositActivity extends BaseActivity {

    @BindView(R.id.iv_token) ImageView mIvToken;
    @BindView(R.id.tv_token_name) TextView mTvTokenName;
    @BindView(R.id.iv_rq_code) ImageView mIvRqCode;
    @BindView(R.id.tv_address) TextView mTvAddress;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_token_deposit;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);

        ActivityTitleHelper.setTitle(this, R.string.deposit_token);

        // 信息
        mIvToken.setBackgroundColor(Color.RED);
        mIvRqCode.setBackgroundColor(Color.BLUE);
        mTvTokenName.setText("USDT");
        mTvAddress.setText("0x24602722816b6cad0e143ce9fabf31f6026ec6226026ec6226026ec622");
    }

    @OnClick({R.id.but_save, R.id.but_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_save:
                // 保存图片
                break;

            case R.id.but_copy:
                AndroidUtil.copy(mTvAddress);
                break;
        }
    }

}
