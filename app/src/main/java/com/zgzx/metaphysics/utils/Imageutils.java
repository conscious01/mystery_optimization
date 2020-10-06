package com.zgzx.metaphysics.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Imageutils {
    /**
     * 将资源文件转换成Drawabled对象然后再进行转换
     */
    public static Bitmap getBitmapViaDrawable(Context context, int drawableID) {
        Drawable db = context.getResources().getDrawable(drawableID);
        BitmapDrawable drawable = (BitmapDrawable) db;

        Bitmap bitmap = drawable.getBitmap();
        return bitmap;
    }
}
