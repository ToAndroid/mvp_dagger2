package com.example.demolib.ui.login;

import android.support.annotation.NonNull;
import android.text.AndroidCharacter;

import com.example.demolib.api.MainApi;
import com.example.demolib.bean.BaseHttpFunc;
import com.example.demolib.bean.BaseResponse;
import com.example.demolib.bean.LoginData;
import com.example.demolib.inject.PerActivity;
import com.example.demolib.utils.SecurityUtil;
import com.example.demolib.utils.ToastUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by john on 2017/7/24.
 */
@PerActivity
public class LoginPresenter implements LoginContract.Presenter {
    private MainApi mApi;
    private LoginContract.View mView;
    private Subscription mSubscription;

    @Inject
    public LoginPresenter(MainApi api) {
        this.mApi = api;
    }

    @Override
    public void attachView(@NonNull LoginContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mView = null;
    }

    public void login(String username, String pwd) {
        mSubscription = mApi.login(username, SecurityUtil.getMD5(pwd))
                .map(new BaseHttpFunc<String>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(String data) {
                        ToastUtil.showToast(data);
                    }
                });
    }
}
