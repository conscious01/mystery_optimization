package com.zgzx.metaphysics.utils.watchers;

import android.widget.TextView;

public interface IRule {

    boolean validation(CharSequence charSequence);

    TextView[] getObserved();

}
