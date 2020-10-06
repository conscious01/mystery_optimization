package com.zgzx.metaphysics.utils;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * Date: 2019/9/19
 * Desc: 富文本字体工具
 */
public class SpannableStringHelper {

    public static SpannableStringHelper newBuilder(CharSequence text) {
        return new SpannableStringHelper(text);
    }

    private int start, end;
    private SpannableStringBuilder style;

    private SpannableStringHelper(CharSequence text) {
        style = new SpannableStringBuilder(text);
        this.start = 0;
        this.end = text.length();
    }

    public SpannableStringHelper append(CharSequence text) {
        this.start = style.length();
        style.append(text);
        return range(start);
    }

    public SpannableStringHelper range(int start) {
        return this.range(start, style.length());
    }

    public SpannableStringHelper range(int start, int end) {
        this.start = start;
        this.end = end;
        return this;
    }

    public SpannableStringHelper striketrough(){
        style.setSpan(new StrikethroughSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper backgroundColor(@ColorInt int color) {
        style.setSpan(new BackgroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper foregroundColor(@ColorInt int color) {
        style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return this;
    }

    public SpannableStringHelper click(View.OnClickListener clickable) {
        return click(this.start, this.end, clickable);
    }

    public SpannableStringHelper click(int start, int end, View.OnClickListener clickable) {
        style.setSpan(new ClickableSpan() {

            @Override
            public void onClick(@NonNull View widget) {
                clickable.onClick(widget);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return this;
    }

    // 百分比
    public SpannableStringHelper size(float proportion) {
        return size(proportion, start, end);
    }

    public SpannableStringHelper size(float proportion, int start, int end) {
        style.setSpan(new RelativeSizeSpan(proportion), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper backgroundColor(@ColorInt int color, int start, int end) {
        style.setSpan(new BackgroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper foregroundColor(@ColorInt int color, int start, int end) {
        style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper bold() {
        return bold(start, end);
    }

    public SpannableStringHelper bold(int start, int end) {
        style.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper drawable(Drawable drawable) {
        return drawable(drawable, start, end);
    }

    public SpannableStringHelper drawable(Drawable drawable, int start, int end) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        style.setSpan(new ImageSpan(drawable), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringBuilder build() {
        return this.style;
    }

}
