package com.zgzx.metaphysics.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.event.RefreshFourtuneEvent;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.MyWebViewCLient;
import com.zgzx.metaphysics.utils.StringUtil;
import com.zgzx.metaphysics.widgets.JavaScriptinterface;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;

/**
 * 网页界面
 */
public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewActivity";

    public static Intent newIntent(Context context, String url) {
        return new Intent(context, WebViewActivity.class).putExtra(Constants.EXTRA_URL, url);
    }


    public static Intent newIntent(Context context, String url, int type) {
        return new Intent(context, WebViewActivity.class).putExtra(Constants.EXTRA_URL, url).putExtra(Constants.KEY, type);
    }


    public static Intent newIntent(Context context, String url, int jumpType, int orderID) {
        return new Intent(context, WebViewActivity.class).putExtra(Constants.EXTRA_URL, url).putExtra(Constants.KEY, jumpType).putExtra(Constants.REQ_RESULT, orderID);
    }

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.tv_title)
    TextView mTitleTextView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_arrow_back)
    AppCompatImageView mArrowBackImage;

    @BindView(R.id.layout_title_container)
    View titleView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_web_view;
    }

    int jumpType, orderID;
    int type;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        mArrowBackImage.setOnClickListener(v -> onBackPressed());

        jumpType = getIntent().getIntExtra(Constants.KEY, 0);
        orderID = getIntent().getIntExtra(Constants.REQ_RESULT, 0);
        type = getIntent().getIntExtra(Constants.KEY, 0);
        if (type == Constants.WEB_VIEW_TYPE_ZGJM) {
            titleView.setVisibility(View.GONE);
            StatusBarUtil.setColor(this, Color.parseColor("#140B37"), 0);
        } else if (type == Constants.WEB_VIEW_TYPE_YJZB) {
            StatusBarUtil.setColor(this, Color.parseColor("#121212"), 0);
            titleView.setBackgroundColor(Color.parseColor("#121212"));
            mTitleTextView.setTextColor(Color.parseColor("#E8D5BA"));
            //todo 替换图标
//            mArrowBackImage.setBackgroundDrawable(getDrawable(R.drawable.button_enable));
        }

        initSetting(mWebView);

        LogUtils.aTag("webview url-->" + getIntent().getStringExtra(Constants.EXTRA_URL));
        mWebView.loadUrl(getIntent().getStringExtra(Constants.EXTRA_URL));

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mTitleTextView != null) {
                    if (StringUtil.checkHasChinese(title)) {
                        mTitleTextView.setText(title);
                    }

                }
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

        mWebView.setWebViewClient(new MyWebViewCLient(this,mWebView));



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new RefreshFourtuneEvent());

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
        setJavaInterFace();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(2);
        }


    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void setJavaInterFace() {
        if (jumpType == 0 && orderID == 0) {
            mWebView.addJavascriptInterface(new JavaScriptinterface(WebViewActivity.this),
                    "android");

        } else {
            mWebView.addJavascriptInterface(new JavaScriptinterface(WebViewActivity.this,
                    jumpType, orderID), "android");

        }

    }


}
