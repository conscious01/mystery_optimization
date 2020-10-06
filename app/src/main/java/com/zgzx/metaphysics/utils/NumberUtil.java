package com.zgzx.metaphysics.utils;

import java.text.DecimalFormat;
import java.util.Locale;

public class NumberUtil {

    public static String format(double number) {
//        return new DecimalFormat("#######0.00##").format(number);
        return String.format(Locale.CHINA, "%.02f", number);
    }
    public static String format1(double number) {
//        return new DecimalFormat("#######0.00##").format(number);
        return String.valueOf(new Double(number).intValue());
    }
}
