package com.example.demolib.inject.module;

import android.content.Context;

import com.example.demolib.App;
import com.example.demolib.components.okhttp.CookieInterceptor;
import com.example.demolib.components.okhttp.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by john on 2017/6/6.
 */
@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(App context) {
        this.mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }

    @Singleton
    @Provides
    @Named("api")
    public OkHttpClient provideApiOkhttpClient(CookieInterceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkhttpClient(@Named("api") OkHttpClient okHttpClient) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        builder.interceptors().clear();
        return builder.build();

    }

    @Provides
    @Singleton
    public CookieInterceptor provideCookieInterceptor() {
        return new CookieInterceptor();
    }


}
