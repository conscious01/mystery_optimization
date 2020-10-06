package com.zgzx.metaphysics.network.interceptor;


import com.blankj.utilcode.util.LogUtils;
import com.zgzx.metaphysics.BuildConfig;
import com.zgzx.metaphysics.utils.encrypt.XXTEA;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;

public class EncryptParameterInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder();

        encrypt(originalRequest, requestBuilder);
        return chain.proceed(requestBuilder.build());
    }

    private void encrypt(Request request, Request.Builder requestBuilder) throws IOException {
        if ("POST".equalsIgnoreCase(request.method())) {

            RequestBody body = request.body();
            if (body instanceof FormBody) {
                return;
            }

            // 开发环境不加密参数
            if (BuildConfig.DEBUG) {
                return;
            }

            try {

                // 获取body里的内容
                Field byteStringField = body.getClass().getDeclaredField("val$content");
                byteStringField.setAccessible(true);

                ByteString byteString = null;
                Object o = byteStringField.get(body);
                if (o instanceof ByteString) {
                    byteString = (ByteString) o;
                }

                // 将body里的内容加密
                if (byteString != null) {

                    // TODO XXTEA公钥
                    String encryptData = XXTEA.encryptToBase64String(byteString.utf8(), "123456");
                    LogUtils.d("origin: %s, encrypt: %s", byteString.utf8(), encryptData);

                    if (encryptData != null) {
                        requestBuilder.post(new FormBody.Builder().add("req", encryptData).build()).build();
                    }
                }

            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }
    }

}
