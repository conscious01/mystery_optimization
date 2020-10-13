package com.zgzx.metaphysics.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * h5页面掉起微信、支付宝 支付
 */
public class MyWebViewCLient extends WebViewClient {

    Activity mActivity;
    WebView mWebView;


    public MyWebViewCLient(Activity activity, WebView webView) {
        this.mActivity = activity;
        this.mWebView = webView;

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Build.VERSION.SDK_INT < 26 && mWebView != null) {
            if (url.startsWith("weixin://wap/pay?")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mActivity.startActivity(intent);
            } else if (AlipayUtil.isAlipay(url)) {
                AlipayUtil.goAlipays(mActivity, url);
            } else {
                mWebView.loadUrl(url);
            }
            return true;
        } else {
            if (url.startsWith("weixin://wap/pay?")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mActivity.startActivity(intent);
                return true;
            } else if (AlipayUtil.isAlipay(url)) {
                AlipayUtil.goAlipays(mActivity, url);
                return true;
            }
        }

        return false;
    }
}
