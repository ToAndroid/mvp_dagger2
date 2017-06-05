package com.example.demolib.component;

import android.content.Context;

import com.example.demolib.DemoApplication;
import com.example.demolib.MainActivity;
import com.example.demolib.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by john on 2017/6/5.
 */
@Singleton
@Component(dependencies = AppModule.class)
public interface AppComponent {
    void inject(DemoApplication application);

    void inject(MainActivity activity);

    Context getContext();
}
