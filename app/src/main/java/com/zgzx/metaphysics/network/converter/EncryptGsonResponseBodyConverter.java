package com.zgzx.metaphysics.network.converter;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.zgzx.metaphysics.BuildConfig;
import com.zgzx.metaphysics.utils.encrypt.XXTEA;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class EncryptGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    EncryptGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader;
        String string = value.string();

        LogUtils.d(string);

        // TODO 加解密
//        if (BuildConfig.DEBUG) {
            jsonReader = gson.newJsonReader(new StringReader(string));

//        } else {
//            jsonReader = decrypt(string);
//        }

        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }


    private JsonReader decrypt(String string) {
        // TODO XXTEA 解密
        String decryptData = XXTEA.decryptBase64StringToString(string, "123456");

        if (decryptData == null) {
            throw new JsonIOException("解密失败 \n\n " + string);
        }

        return gson.newJsonReader(new StringReader(decryptData));
    }

}
