package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.zgzx.metaphysics.R;

public class CurrencyDialog extends CenterPopupView {

    public static void show(Context context, String title, int type, String message, String mSubDesc, String mCaution, String mFortuneText1, String mFortuneText2, String mFortuneText3) {
        new XPopup.Builder(context)
                .dismissOnTouchOutside(false)
                .asCustom(new CurrencyDialog(context, title, type, message, mSubDesc, mCaution, mFortuneText1, mFortuneText2, mFortuneText3))
                .show();
    }

    private String title;
    private String message;
    private String mSubDesc;
    private String mCaution;
    private String mFortuneText1;
    private String mFortuneText2;
    private String mFortuneText3;
    private int type;//1:代表通用只显示内容，2：代表运势课题。3：代表增运

    public CurrencyDialog(@NonNull Context context, String title, int type, String message, String mSubDesc,
                          String mCaution, String mFortuneText1, String mFortuneText2, String mFortuneText3) {
        super(context);
        this.title = title;
        this.message = message;
        this.mCaution = mCaution;
        this.mSubDesc = mSubDesc;
        this.type = type;
        this.mFortuneText1 = mFortuneText1;
        this.mFortuneText2 = mFortuneText2;
        this.mFortuneText3 = mFortuneText3;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_currency_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvTitle = findViewById(R.id.title);
        tvTitle.setText(title);
        TextView tvMessage = findViewById(R.id.content);
        LinearLayout topicLayout = findViewById(R.id.topicLayout);
        LinearLayout addFortuneLayout = findViewById(R.id.addFortuneLayout);
        TextView content_desc = findViewById(R.id.content_desc);
        TextView tv_sub_desc = findViewById(R.id.tv_sub_desc);
        TextView tv_caution = findViewById(R.id.tv_caution);

        TextView tv_fortune_1 = findViewById(R.id.tv_fortune_1);
        TextView tv_fortune_2 = findViewById(R.id.tv_fortune_2);
        TextView tv_fortune_3 = findViewById(R.id.tv_fortune_3);
        TextView tv_fortune_4 = findViewById(R.id.tv_fortune_4);
        TextView tv_fortune_5 = findViewById(R.id.tv_fortune_5);
        TextView tv_fortune_6 = findViewById(R.id.tv_fortune_6);
        switch (type) {
            case 1:
                topicLayout.setVisibility(GONE);
                tvMessage.setVisibility(VISIBLE);
                addFortuneLayout.setVisibility(GONE);
                tvMessage.setText(message);

                break;
            case 2:
                topicLayout.setVisibility(VISIBLE);
                tvMessage.setVisibility(GONE);
                addFortuneLayout.setVisibility(GONE);
                content_desc.setText(message);
                tv_sub_desc.setText(mSubDesc);
                tv_caution.setText(mCaution);
                break;
            case 3:
                topicLayout.setVisibility(GONE);
                tvMessage.setVisibility(GONE);
                addFortuneLayout.setVisibility(VISIBLE);
                tv_fortune_1.setText(message);
                tv_fortune_2.setText(mSubDesc);
                tv_fortune_3.setText(mCaution);
                tv_fortune_4.setText(mFortuneText1);
                tv_fortune_5.setText(mFortuneText2);
                tv_fortune_6.setText(mFortuneText3);
                break;
        }


        // 关闭
        findViewById(R.id.close).setOnClickListener(v -> dismiss());
    }

}
