package com.zgzx.metaphysics.utils.filters;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;

/**
 * 小数点后几位
 */
public class NumberDecimalFilter implements InputFilter {

    private int mDigits;

    public NumberDecimalFilter(int digits) {
        mDigits = digits;
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        // 删除等特殊字符，直接返回
        if ("".equals(source.toString())) {
            return null;
        }

        String dValue = dest.toString();
        if (TextUtils.isEmpty(dValue)) {
            return source;
        }

        String[] splitArray = dValue.split("\\.");
        if (splitArray.length > 1) {
            int cursorIndex = dValue.indexOf(".");

            if (dEnd > cursorIndex) {
                String dotValue = splitArray[1];
                int diff = dotValue.length() + 1 - mDigits;
                if (diff > 0) {
                    return "";
                }
            }
        }

        return source;
    }
}