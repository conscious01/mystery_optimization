package com.zgzx.metaphysics.utils.image;

import android.text.TextUtils;


public class ImageURL {

    public static String splice(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";

        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;

        } else if (url.startsWith("/")) {
//            return BuildConfig.DOMAIN + url;
            return "";

        } else {
//            return BuildConfig.DOMAIN + "/" + url;
            return "";
        }
    }

}
