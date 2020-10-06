package com.zgzx.metaphysics.utils.watchers;

import android.text.Editable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.collection.ArrayMap;

import com.mondo.logger.Logger;

import java.util.Collection;
import java.util.Map;

public class TextWatchers {

    public static TextWatchers add(IRule... rules) {
        return new TextWatchers(rules);
    }

    public static void add(TextView targetView, IRule... rules) {
        add(rules).setWatcherCallback(targetView::setEnabled);
    }


    private OnWatcherCallback mCallback;
    private Map<TextView, Boolean> mObservedMap;

    private boolean mPreWatchResult = false;

    private TextWatchers(IRule... rules) {
        mObservedMap = new ArrayMap<>();

        for (IRule rule : rules) {
            TextView[] observed = rule.getObserved();

            for (TextView textView : observed) {

                mObservedMap.put(textView, rule.validation(textView.getText()));

                if (textView instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) textView;
                    checkBox.setOnCheckedChangeListener(new CheckedChangeListener(rule));

                } else {
                    textView.addTextChangedListener(new TextViewTextWatcher(rule, textView));
                }
            }
        }

        Logger.i(mObservedMap.toString());
    }


    private void watchResult(boolean result, TextView textView) {

        mObservedMap.put(textView, result);

        Collection<Boolean> values = mObservedMap.values();

        if (mCallback != null) {
            boolean watchResult = !values.contains(Boolean.FALSE);

            if (watchResult != mPreWatchResult) {
                mPreWatchResult = watchResult;
                mCallback.callback(watchResult);
            }
        }
    }


    public void setWatcherCallback(OnWatcherCallback callback) {
        this.mCallback = callback;
    }



    private class CheckedChangeListener implements CheckBox.OnCheckedChangeListener {

        private final IRule mRule;

        CheckedChangeListener(IRule rule) {
            this.mRule = rule;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            watchResult(isChecked, buttonView);
        }
    }


    private class TextViewTextWatcher implements android.text.TextWatcher {

        private final IRule mRule;
        private final TextView mTextView;

        TextViewTextWatcher(IRule rule, TextView textView) {
            this.mRule = rule;
            this.mTextView = textView;
        }

        @Override
        public void afterTextChanged(Editable s) {
            watchResult(this.mRule.validation(s), mTextView);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }



    public interface OnWatcherCallback {

        void callback(boolean enable);

    }

}
