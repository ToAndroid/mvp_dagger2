package com.example.demolib.bean;

import rx.functions.Func1;

/**
 * Created by john on 2017/6/6.
 */

public class BaseHttpFunc<T> implements Func1<BaseBean<T>, T> {
    @Override
    public T call(BaseBean<T> baseBean) {
        int status = baseBean.getStatus();
        String msg = baseBean.getMsg();
        if (status != 1000) {
            throw new HttpServiceException(status, msg);
        }
        return baseBean.getData();
    }

}
