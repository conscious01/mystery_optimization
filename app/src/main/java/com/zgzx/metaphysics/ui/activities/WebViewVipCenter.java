package com.zgzx.metaphysics.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.StringUtil;
import com.zgzx.metaphysics.widgets.JavaScriptinterface;

import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;

public class WebViewVipCenter extends BaseActivity {

    public static Intent newIntent(Context context, int type, String url) {
        return new Intent(context, WebViewVipCenter.class).putExtra(Constants.EXTRA_URL, url).putExtra(Constants.EXT_TYPE, type);
    }

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_arrow_back)
    AppCompatImageView mArrowBackImage;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private int type;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_web_view_vip_center;
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        mArrowBackImage.setOnClickListener(v -> onBackPressed());
        initSetting(mWebView);
        type = getIntent().getIntExtra(Constants.EXT_TYPE, -1);

        if (type == 1) {//会员
//             tv_title.setVisibility(View.GONE);
//             mArrowBackImage.setVisibility(View.GONE);
        } else if (type == 2) {//周公
            tv_title.setVisibility(View.GONE);
            mArrowBackImage.setVisibility(View.GONE);
        }
        mWebView.loadUrl(getIntent().getStringExtra(Constants.EXTRA_URL));

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

                if (StringUtil.checkHasChinese(title)) {
                    super.onReceivedTitle(view, title);                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar != null) {
                    mProgressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(View.GONE);
                        String url =
                                "javascript:sendTokenBySignature(\"" + LocalConfigStore.getInstance().getUserToken() + "\",\"" + DateUtils.getTimestamp() + "\",\"" + LocalConfigStore.getInstance().getAk() + "\",\"" + LocalConfigStore.getInstance().getKey() + "\")";

                        LogUtils.i("url--> " + url);
                        mWebView.loadUrl(url);
                    }
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Build.VERSION.SDK_INT < 26 && mWebView != null) {
                    mWebView.loadUrl(url);
                    return true;
                }

                return false;
            }
        });
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setTextZoom(100);
        webSettings.setAppCacheMaxSize(10 * 1024 * 1024);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getDir("appCache", 0).getPath());
        webSettings.setAllowFileAccess(true);
        webSettings.setSavePassword(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(2);
        }
        setJavaInterFace();

    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void setJavaInterFace() {
        mWebView.addJavascriptInterface(new JavaScriptinterface(WebViewVipCenter.this),
                "android");
    }

}
