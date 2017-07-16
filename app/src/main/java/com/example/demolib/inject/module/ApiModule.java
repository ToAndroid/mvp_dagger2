package com.example.demolib.inject.module;

import android.content.Context;

import com.example.demolib.api.MainApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by john on 2017/6/6.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public MainApi provideMainApi(OkHttpClient client, Context context) {
        return new MainApi(client, context);
    }
}
