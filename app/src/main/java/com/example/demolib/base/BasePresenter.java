package com.example.demolib.base;

import android.support.annotation.NonNull;

/**
 * Created by john on 2017/7/24.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(@NonNull T t);

    void detachView();
}
