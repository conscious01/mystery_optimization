package com.zgzx.metaphysics.utils.watchers;

import android.text.TextUtils;
import android.widget.TextView;

public class EmptyRule extends SingleRule {

    public EmptyRule(TextView... editTexts) {
        super(editTexts);
    }

    @Override
    public boolean validation(CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence);
    }

}
