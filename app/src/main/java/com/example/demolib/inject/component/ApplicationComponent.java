package com.example.demolib.inject.component;

import android.content.Context;

import com.example.demolib.App;
import com.example.demolib.MainActivity;
import com.example.demolib.api.MainApi;
import com.example.demolib.inject.module.ApiModule;
import com.example.demolib.inject.module.ApplicationModule;
import com.example.demolib.mvp.model.MainModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by john on 2017/6/5.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(App application);

    void inject(MainActivity activity);

    void inject(MainModel mainModel);

    Context getContext();

    MainApi getMainApi();

}
