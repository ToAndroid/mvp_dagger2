package com.example.demolib.api;


import com.example.demolib.base.BasePresenter;
import com.example.demolib.bean.BaseResponse;
import com.example.demolib.bean.DataBean;
import com.example.demolib.bean.LoginData;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by john on 2017/6/6.
 */

public interface MainService {
    @GET("index/getpic")
    Observable<BaseResponse<List<DataBean>>> getData();

    @FormUrlEncoded
    @POST("index/login")
    Observable<BaseResponse<String>> login(@Field("username") String username,
                                           @Field("password") String md5);
}
