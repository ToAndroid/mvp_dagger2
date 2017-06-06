package com.example.demolib;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.demolib.api.MainApi;
import com.example.demolib.bean.BaseHttpFunc;
import com.example.demolib.bean.DataBean;
import com.example.demolib.components.exception.HttpServiceException;
import com.example.demolib.utils.Logger;
import com.example.demolib.utils.PhoneInfoUtils;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView tv;
    @Inject
    Context mContext;

    @Inject
    MainApi mainApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DemoApplication.getApplicationComponent().inject(this);
        String string = mContext.getResources().getString(R.string.app_name);
        String deviceInfo = PhoneInfoUtils.getDeviceInfo(mContext);
        tv.setText(deviceInfo);
        mainApi.getData().observeOn(AndroidSchedulers.mainThread())
                .map(new BaseHttpFunc<List<DataBean>>())
                .subscribe(new Subscriber<List<DataBean>>() {
                    @Override
                    public void onCompleted() {
                        Logger.e("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof HttpServiceException)
                        Logger.e("onError : " + ((HttpServiceException)e).toString());
                    }

                    @Override
                    public void onNext(List<DataBean> s) {
                        Gson gson = new Gson();
                        Logger.e(gson.toJson(s));
                    }
                });


    }
}
