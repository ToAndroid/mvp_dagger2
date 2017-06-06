package com.example.demolib.api;


import com.example.demolib.bean.BaseBean;
import com.example.demolib.bean.DataBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by john on 2017/6/6.
 */

public interface MainService {
    @GET("index/getpic1")
    Observable<BaseBean<List<DataBean>>> getData();
}
