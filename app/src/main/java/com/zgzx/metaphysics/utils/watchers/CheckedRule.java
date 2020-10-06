package com.zgzx.metaphysics.utils.watchers;

import android.widget.CheckBox;
import android.widget.TextView;

public class CheckedRule extends SingleRule {

    CheckBox[] mObserved;

    public CheckedRule(CheckBox... observed) {
        super(observed);
        this.mObserved = observed;
    }

    @Override
    public boolean validation(CharSequence charSequence) {
        // 最后检验不以此处为准
        return false;
    }


    @Override
    public TextView[] getObserved() {
        return mObserved;
    }

}
