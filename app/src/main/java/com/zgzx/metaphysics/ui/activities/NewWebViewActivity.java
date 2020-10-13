package com.zgzx.metaphysics.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.MyWebViewCLient;
import com.zgzx.metaphysics.utils.StringUtil;

import butterknife.BindView;

public class NewWebViewActivity extends BaseActivity {

    public static Intent newIntent(Context context, String url) {
        return new Intent(context, NewWebViewActivity.class).putExtra(Constants.EXTRA_URL, url);
    }

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_arrow_back)
    ImageView mArrowBackImage;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_new_web_layout;
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this,255);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        mArrowBackImage.setOnClickListener(v -> onBackPressed());

        initSetting(mWebView);

        mWebView.loadUrl(getIntent().getStringExtra(Constants.EXTRA_URL));

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (StringUtil.checkHasChinese(title)) {
                    super.onReceivedTitle(view, title);
                }

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar != null) {
                    mProgressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
        mWebView.addJavascriptInterface(new JsInteration(), "android");
        mWebView.setWebViewClient(new MyWebViewCLient(this,mWebView));

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
    }
    public class JsInteration {

        @JavascriptInterface
        public void back() {

        }
    }
}