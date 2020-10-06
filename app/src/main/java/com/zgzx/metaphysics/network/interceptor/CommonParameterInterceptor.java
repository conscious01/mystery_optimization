package com.zgzx.metaphysics.network.interceptor;


import com.blankj.utilcode.util.DeviceUtils;
import com.zgzx.metaphysics.BuildConfig;
import com.zgzx.metaphysics.LocalConfigStore;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公共参数
 */
public class CommonParameterInterceptor implements Interceptor {

    private String mUniqueDeviceId, // 设备唯一ID
            mBrand, // 品牌
            mModel; // 型号


    public CommonParameterInterceptor() {
        mModel = DeviceUtils.getModel();
        mBrand = android.os.Build.BRAND;
        mUniqueDeviceId = DeviceUtils.getUniqueDeviceId();
    }


    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = addHeaders(originalRequest);
        return chain.proceed(requestBuilder.build());
    }


    private Request.Builder addHeaders(Request request) {
        Request.Builder requestHeader = request.newBuilder()
                .header("os", "1") // 客户端平台，1-安卓，2-IOS
                .header("device-id", mUniqueDeviceId) // 设备唯一ID
                .header("brand", mBrand) // 品牌
                .header("model", mModel) // 型号
                // 中文简体zh-CN，中文繁体zh-TW，英文en-US
                .header("lang", LocalConfigStore.getInstance().getAcceptLanguage())
                .header("version-name", BuildConfig.VERSION_NAME)
                .header("version-code", String.valueOf(BuildConfig.VERSION_CODE));


        // 登录 token
        String token = LocalConfigStore.getInstance().getUserToken();
        if (token != null && token.length() > 0) {
            requestHeader.header("token", token);
        }

        return requestHeader;
    }

}

