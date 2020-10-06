package com.zgzx.metaphysics.ui.dialogs;

import android.app.Activity;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.event.SplashEvent;
import com.zgzx.metaphysics.ui.activities.WebViewActivity;
import com.zgzx.metaphysics.utils.SpannableStringHelper;

import org.greenrobot.eventbus.EventBus;

public class AgreementDialog extends CenterPopupView {
    private Context mContext;
    private Activity mActivity;

    public static void show(Context context, Activity activity) {
        new XPopup.Builder(context)
                .isDestroyOnDismiss(false)
                .enableShowWhenAppBackground(false)
                .asCustom(new AgreementDialog(context, activity))
                .show();
    }

    public AgreementDialog(@NonNull Context context, Activity activity) {
        super(context);
        mContext = context;
        mActivity = activity;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_simple;
    }

    @Override
    protected void onCreate() {
        TextView tvTitle = findViewById(R.id.dialog_tv_title);
        tvTitle.setText(getResources().getString(R.string.tv_agreement_1));
        TextView tvMessage = findViewById(R.id.dialog_tv_message);
        String language = LocalConfigStore.getInstance().getAcceptLanguage();
        String serviceURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                "/privacy_and_agreement/agreement?" +
                "language=" + language;
        String privateURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                "/privacy_and_agreement/privacy?" +
                "language=" + language;

        tvMessage.setText(
                SpannableStringHelper.newBuilder(getResources().getString(R.string.tv_agreement_2))
                        .append(getResources().getString(R.string.register_protocol))
                        .foregroundColor(getResources().getColor(R.color.color_80993c))
                        .click(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mActivity.startActivity(WebViewActivity.newIntent(mActivity, serviceURL));
                            }
                        })
                        .append(getResources().getString(R.string.with))
                        .append(getResources().getString(R.string.register_privacy_policy))
                        .foregroundColor(getResources().getColor(R.color.color_80993c))

                        .click(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mActivity.startActivity(WebViewActivity.newIntent(mActivity, privateURL));
                            }
                        })
                        .append(getResources().getString(R.string.tv_agreement))
                        .build()
        );
        tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
        findViewById(R.id.dialog_tv_negative).setOnClickListener(v -> {
            dialog.dismiss();
            EventBus.getDefault().post(new SplashEvent(0));
        });
        findViewById(R.id.dialog_tv_positive).setOnClickListener(v -> {
            dialog.dismiss();
            EventBus.getDefault().post(new SplashEvent(1));
        });

    }
}
