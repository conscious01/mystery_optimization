package com.zgzx.metaphysics.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;

import butterknife.BindView;

/**
 * 语言设置
 */
public class LanguageSettingsActivity extends BaseActivity {

    @BindView(R.id.group_language) RadioGroup mGroupLanguage;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_language_settings;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 标题
        ActivityTitleHelper.setTitle(this, R.string.language);

        // 回显
        String language = LocalConfigStore.getInstance().getAcceptLanguage();
        mGroupLanguage.check(WebApiConstants.LANGUAGE_ZH_CN.equals(language) ?
                R.id.radio_simplified_chinese : R.id.radio_traditional_chinese);

        // 语言更换
        mGroupLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.radio_simplified_chinese:
                    AndroidUtil.alertLanguage(this, WebApiConstants.LANGUAGE_ZH_CN);
                    break;

                case R.id.radio_traditional_chinese:
                    AndroidUtil.alertLanguage(this, WebApiConstants.LANGUAGE_ZH_TW);
                    break;
            }

            // 重启页面
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

}
