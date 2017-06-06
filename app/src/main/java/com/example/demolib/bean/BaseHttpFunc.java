package com.example.demolib.bean;

import com.example.demolib.components.exception.HttpServiceException;

import rx.functions.Func1;

/**
 * Created by john on 2017/6/6.
 */

public class BaseHttpFunc<T> implements Func1<BaseResponse<T>, T> {
    @Override
    public T call(BaseResponse<T> baseBean) {
        int status = baseBean.getStatus();
        String msg = baseBean.getMsg();
        if (status != 10000) {
            throw new HttpServiceException(status, msg);
        }
        return baseBean.getData();
    }

}
