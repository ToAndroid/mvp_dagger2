package com.example.demolib.mvp.component;

import android.content.Context;

import com.example.demolib.DemoApplication;
import com.example.demolib.MainActivity;
import com.example.demolib.api.MainApi;
import com.example.demolib.mvp.module.ApiModule;
import com.example.demolib.mvp.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by john on 2017/6/5.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(DemoApplication application);

    void inject(MainActivity activity);

    Context getContext();

    MainApi getMainApi();

}
