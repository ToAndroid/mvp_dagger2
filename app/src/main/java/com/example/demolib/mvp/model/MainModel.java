package com.example.demolib.mvp.model;

import com.example.demolib.App;
import com.example.demolib.MainActivity;
import com.example.demolib.api.MainApi;
import com.example.demolib.bean.BaseHttpFunc;
import com.example.demolib.bean.DataBean;
import com.example.demolib.components.exception.HttpServiceException;
import com.example.demolib.utils.Logger;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by jb on 2017/7/16.
 */

public class MainModel {
    @Inject
    MainApi mainApi;

    public MainModel() {
        App.getApplicationComponent().inject(this);
    }

    public void getDada() {
        mainApi.getData().observeOn(AndroidSchedulers.mainThread())
                .map(new BaseHttpFunc<List<DataBean>>())
                .subscribe(new Subscriber<List<DataBean>>() {
                    @Override
                    public void onCompleted() {
                        Logger.e("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpServiceException)
                            Logger.e("onError : " + ((HttpServiceException) e).toString());
                    }

                    @Override
                    public void onNext(List<DataBean> s) {
                        Gson gson = new Gson();
                        Logger.e(gson.toJson(s));
                    }
                });

    }
}
