package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.utils.NumberUtil;

public class PayMethodDialog extends BottomPopupView {
    private float balance;
    private int payType;
    private String account;
    private TextView mTvBalance, mTvAmount,mTvPayType;
    private View.OnClickListener listener;

    public static void show(Context context,float balance, String account,int payType,View.OnClickListener listener) {
        new XPopup.Builder(context)
                .enableDrag(true)
                .isDestroyOnDismiss(true)

                .asCustom(new PayMethodDialog(context, balance, account, payType,listener))
                .show();
    }

    public PayMethodDialog(@NonNull Context context, float balance, String account,int payType,View.OnClickListener listener) {
        super(context);
        this.account = account;
        this.balance = balance;
        this.listener=listener;
        this.payType=payType;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_pay_kmz_layout;
    }

    @Override
    protected void onCreate() {
        mTvBalance = findViewById(R.id.tv_balance);
        mTvAmount = findViewById(R.id.tv_account);
        mTvPayType=findViewById(R.id.textViewPayType);
        mTvBalance.setText("¥"+NumberUtil.format(balance));


       if (account.length()==11){
           mTvAmount.setText(account.substring(0,3)+"****"+account.substring(7,account.length()));
       }else {
           mTvAmount.setText(account.substring(0,2)+"****"+account.substring(5,account.length()));
       }
       if (payType==0){
           mTvPayType.setText(getResources().getText(R.string.default_currency_name));
       }else if (payType==1){
           mTvPayType.setText(getResources().getText(R.string.alipay));
       }else if (payType==2){
           mTvPayType.setText(getResources().getText(R.string.we_chat));
       }
        findViewById(R.id.nowPayBtn).setOnClickListener(listener);
        findViewById(R.id.dialog_iv_close).setOnClickListener(v -> dismiss());
    }
    public void dissDialog(){
        dismiss();
    }

}
