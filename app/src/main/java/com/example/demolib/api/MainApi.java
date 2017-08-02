package com.example.demolib.api;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.demolib.bean.BaseResponse;
import com.example.demolib.bean.DataBean;

import java.util.List;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by john on 2017/6/6.
 */

public class MainApi extends BaseApi<MainService> {
    public MainApi(OkHttpClient mOkHttpClient, Context context) {
        super(mOkHttpClient, context);
    }

    @Nullable
    @Override
    protected Class<MainService> getServiceClass() {
        return MainService.class;
    }

    @Nullable
    @Override
    protected String getBaseUrl() {
        return "http://192.168.1.233/tp5/public/api/";
    }


    public Observable<BaseResponse<List<DataBean>>> getData() {
        return mMainService.getData().subscribeOn(Schedulers.io());
    }

    public Observable<BaseResponse<String>> login(String username, String md5) {
        return mMainService.login(username, md5);
    }
}
