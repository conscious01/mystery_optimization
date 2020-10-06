package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.util.Consumer;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.utils.NumberUtil;
import com.zgzx.metaphysics.utils.TextWatcherAdapter;
import com.zgzx.metaphysics.widgets.VerificationCodeEditText;

/**
 * 支付
 */
public class PayPasswordDialog extends CenterPopupView {

    private int mMessageRes;
    private float mAmount, mBalance;
    private Consumer<String> mConsumer;

    private EditText mEtPassword;
    private TextView mTvMessage, mTvAmount, mTvBalance, mTvError;

    public PayPasswordDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_pay_password;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        mEtPassword = findViewById(R.id.et_password);
        mTvAmount = findViewById(R.id.dialog_tv_amount);
        mTvMessage = findViewById(R.id.dialog_tv_message);
        mTvBalance = findViewById(R.id.dialog_tv_balance_tips);
        mTvError = findViewById(R.id.dialog_tv_error);

        findViewById(R.id.dialog_iv_close).setOnClickListener(v -> dismiss());

        mEtPassword.addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() == 6) {
                   // dismiss();
                    mConsumer.accept(mEtPassword.getText().toString());
                }

            }
        });

        // 消息
        mTvMessage.setText(mMessageRes);

        // 价格
        Resources resources = getContext().getResources();
        mTvAmount.setText(NumberUtil.format(mAmount) + " " + resources.getString(R.string.default_currency_name));

        // 余额
        if (mBalance < mAmount) {
            mTvBalance.setTextColor(resources.getColor(R.color.colorAccent));
            mTvBalance.setText(String.format(resources.getString(R.string.placeholder_insufficient_balance), NumberUtil.format(mBalance)));

        } else {

            mTvBalance.setText(String.format(resources.getString(R.string.placeholder_remaining_balance), NumberUtil.format(mBalance)));
        }
    }

    @Override
    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? (int) (XPopupUtils.getWindowWidth(getContext()) * 0.86f)
                : popupInfo.maxWidth;
    }

    public PayPasswordDialog setMessage(@StringRes int messageRes) {
        this.mMessageRes = messageRes;
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        mTvError.setText(errorMessage);
        mTvError.setVisibility(View.VISIBLE);
    }

    public PayPasswordDialog setAmount(float amount) {
        mAmount = amount;
        return this;
    }

    public PayPasswordDialog setBalance(float balance) {
        mBalance = balance;
        return this;
    }

    public PayPasswordDialog setListener(Consumer<String> consumer) {
        this.mConsumer = consumer;
        return this;
    }

}
