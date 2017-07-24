package com.example.demolib.api;

import android.content.Context;

import com.example.demolib.bean.BaseResponse;
import com.example.demolib.bean.DataBean;
import com.example.demolib.bean.LoginData;
import com.example.demolib.components.retrofit.DecodeConverterFactory;
import com.example.demolib.components.retrofit.FastJsonConverterFactory;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by john on 2017/6/6.
 */

public class MainApi {
    static final String BASE_URL = "http://192.168.1.195/tp5/public/api/";
    private Context mContext;
    private MainService mMainService;

    public MainApi(OkHttpClient mOkHttpClient, Context context) {

        mContext = context;
        Retrofit retrofit =
                new Retrofit.Builder()
                        .addConverterFactory(DecodeConverterFactory.create())
                        .client(mOkHttpClient)
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
        mMainService = retrofit.create(MainService.class);
    }

    public Observable<BaseResponse<List<DataBean>>> getData() {
        return mMainService.getData().subscribeOn(Schedulers.io());
    }

    public Observable<BaseResponse<String>> login(String username, String md5) {
        return mMainService.login(username, md5);
    }
}
