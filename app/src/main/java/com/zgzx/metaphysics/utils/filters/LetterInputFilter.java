package com.zgzx.metaphysics.utils.filters;

import android.text.InputFilter;
import android.text.Spanned;


/**
 * 非字母过滤, 仅支持英文字母及中文
 */
public class LetterInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        for (int i = start; i < end; i++) {
            if (!Character.isLetter(source.charAt(i))) {
                return "";
            }
        }

        return null;
    }

}
