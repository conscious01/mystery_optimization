package com.zgzx.metaphysics.utils.watchers;

import android.widget.TextView;

public abstract class SingleRule implements IRule {

    private TextView[] mObserved;

    public SingleRule(TextView... observed) {
        this.mObserved = observed;
    }

    @Override
    public TextView[] getObserved() {
        return mObserved;
    }

}