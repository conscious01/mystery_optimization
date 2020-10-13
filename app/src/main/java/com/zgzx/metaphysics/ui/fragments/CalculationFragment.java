package com.zgzx.metaphysics.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.blankj.utilcode.util.LogUtils;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.MyWebViewCLient;
import com.zgzx.metaphysics.utils.StringUtil;
import com.zgzx.metaphysics.widgets.JavaScriptinterface;

import butterknife.BindView;

public class CalculationFragment extends BaseRequestFragment {
    @BindView(R.id.web_view)
    WebView web_view;
    private static final String TAG = "CalculationFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calculation_layout;
    }

    @Override
    protected void initStatusBar(Activity activity, boolean initialDisplay) {
        super.initStatusBar(activity, initialDisplay);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 状态栏
        AndroidUtil.addStatusBarHeightPadding(findViewById(R.id.layout_title_container));
        ActivityTitleHelper.setTitle_GoneImg(getActivity(), R.string.nav_calculation);
        String language = LocalConfigStore.getInstance().getAcceptLanguage();
        initSetting(web_view);
        setJavaInterFace();

//        String localBase = "http://192.168.10.167:8080";
        String localBase = LocalConfigStore.getInstance().getH5_Base();

        String cesuanURL = localBase + "/pages/calculate" +
                "/calculate?language=" + language;
        web_view.loadUrl(cesuanURL);

        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (StringUtil.checkHasChinese(title)) {
                    super.onReceivedTitle(view, title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    String url =
                            "javascript:sendTokenBySignature(\"" + LocalConfigStore.getInstance().getUserToken() + "\",\"" + DateUtils.getTimestamp() + "\",\"" + LocalConfigStore.getInstance().getAk() + "\",\"" + LocalConfigStore.getInstance().getKey() + "\")";
                    LogUtils.e(url);
                    web_view.loadUrl(url);
                }
            }
        });

        web_view.setWebViewClient(new MyWebViewCLient(getActivity(), web_view));


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
        webSettings.setAppCachePath(getActivity().getDir("appCache", 0).getPath());
        webSettings.setAllowFileAccess(true);
        webSettings.setSavePassword(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(2);
        }

    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void setJavaInterFace() {
        web_view.addJavascriptInterface(new JavaScriptinterface(getActivity())
                , "android");
    }


}
