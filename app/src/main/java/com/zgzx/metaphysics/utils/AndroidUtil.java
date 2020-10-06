package com.zgzx.metaphysics.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.mondo.logger.Logger;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.network.WebApiConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observer;

import me.jessyan.autosize.utils.ScreenUtils;


public class AndroidUtil {

    public static void passwordVisibility(CheckBox checkBox, EditText editText) {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editText.setTransformationMethod(isChecked
                    ? HideReturnsTransformationMethod.getInstance()
                    : PasswordTransformationMethod.getInstance());
            editText.setSelection(editText.getText().toString().length());
        });
    }


    public static void addStatusBarHeightPadding(View view) {
        int statusBarHeight = ScreenUtils.getStatusBarHeight();
        int paddingTop = view.getPaddingTop();
        view.setPadding(
                view.getPaddingLeft(),
                paddingTop + statusBarHeight,
                view.getPaddingRight(),
                view.getPaddingBottom()
        );
    }

    public static void addStatusBarHeightMargin(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = layoutParams.topMargin + ScreenUtils.getStatusBarHeight();
        view.setLayoutParams(layoutParams);
    }


    public static void copy(TextView textView) {
        Context context = textView.getContext();
        copy(context, textView.getText().toString());
    }


    public static void copy(Context context, String string) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", string);
        cm.setPrimaryClip(mClipData);
    }

    public static String paste(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            return clipData.getItemAt(0).getText().toString();
        }

        return null;
    }

    // 更换语言
    public static void alertLanguage(Context context, String targetLanguage) {
        Locale locale = (WebApiConstants.LANGUAGE_ZH_CN.equals(targetLanguage))
                ? Locale.SIMPLIFIED_CHINESE // 简体
                : Locale.TRADITIONAL_CHINESE // 繁体
//                "zh-jp".equalsIgnoreCase(targetLanguage)
//                ? Locale.JAPANESE
//                : Locale.SIMPLIFIED_CHINESE
                ;

        // 刷新
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);

        // 本地
        LocalConfigStore.getInstance().updateAcceptLanguage(targetLanguage);
    }


    // 保存图片
    @SuppressLint("NewApi")
    public static void saveBitmap(ViewGroup view) {
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "诸葛在线分享";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        File file = new File(appDir, System.currentTimeMillis() + ".jpg");
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < view.getChildCount(); i++) {
            h += view.getChildAt(i).getHeight();
        }

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(view.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(view.getContext().getResources().getColor(R.color.colorAccent));
        view.draw(canvas);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

            Context context = view.getContext();
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
            AppToast.showShort(context.getResources().getString(R.string.successful));
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(e, e.getMessage());
        }
    }

    public static void getViewBitmap(View v, Context context) {
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "诸葛在线分享";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        File file = new File(appDir, System.currentTimeMillis() + ".jpg");
        if (null == v) {
            return;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }

        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        //保存到相册
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, file.getName(), "分享图片");
        //广播通知刷新图库


        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
        AppToast.showShort(context.getResources().getString(R.string.successful));
    }


    /**
     * 将view转化为bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] split(String string, char ch) {
        ArrayList<String> stringList = new ArrayList<String>();
        char chars[] = string.toCharArray();
        int nextStart = 0;
        for (int w = 0; w < chars.length; w++) {
            if (ch == chars[w]) {
                stringList.add(new String(chars, nextStart, w - nextStart));
                nextStart = w + 1;
                if (nextStart ==
                        chars.length) {    //当最后一位是分割符的话，就再添加一个空的字符串到分割数组中去
                    stringList.add("");
                }
            }
        }
        if (nextStart <
                chars.length) {    //如果最后一位不是分隔符的话，就将最后一个分割符到最后一个字符中间的左右字符串作为一个字符串添加到分割数组中去
            stringList.add(new String(chars, nextStart,
                    chars.length - 1 - nextStart + 1));
        }

        for (int i = 0; i < stringList.size(); i++) {
            if (TextUtils.isEmpty(stringList.get(i))){
                stringList.remove(i);
            }
        }
        return stringList.toArray(new String[stringList.size()]);
    }


}
