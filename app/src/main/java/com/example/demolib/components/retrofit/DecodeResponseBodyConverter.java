package com.example.demolib.components.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by john on 2017/6/23.
 */

class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    public DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String data = value.string();
        String key = "627746bcfe761bdeab1093147cd86e78";
        try {
            String encode = DesUtils.decode(key, data);
            return adapter.fromJson(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
