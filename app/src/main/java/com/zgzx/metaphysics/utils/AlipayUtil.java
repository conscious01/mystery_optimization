package com.zgzx.metaphysics.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AlipayUtil {
    //拦截特定支付标识
    public static boolean isAlipay(String url) {
        if (url.startsWith("alipays:") || url.startsWith("alipay")) {
            return true;
        }

        return false;
    }

    public static void goAlipays(Activity activity, String url) {
        //判断是否安装支付宝
        if (checkAliPayInstalled(activity)) {
            goUrl(activity, url);
        } else {
            //安装下载支付宝
//            updateAlipayDialog(activity);
        }
    }

    //直接隐式调用
    private static void goUrl(Activity activity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    //判断是否安装支付宝app
    private static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }


}
