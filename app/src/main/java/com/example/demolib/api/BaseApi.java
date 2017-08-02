package com.example.demolib.api;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.demolib.components.retrofit.DecodeConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by john on 2017/7/29.
 */

public abstract class BaseApi<T> {
    private Context mContext;
    protected T mMainService;

    public BaseApi(OkHttpClient mOkHttpClient, Context context) {
        mContext = context;
        Retrofit retrofit =
                new Retrofit.Builder()
                        .addConverterFactory(DecodeConverterFactory.create())
                        .client(mOkHttpClient)
                        .baseUrl(getBaseUrl())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
        mMainService = retrofit.create(getServiceClass());


    }

    @Nullable
    protected abstract Class<T> getServiceClass();

    @Nullable
    protected abstract String getBaseUrl();

}
