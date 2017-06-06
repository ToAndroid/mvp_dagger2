package com.example.demolib;

import android.app.Application;

import com.example.demolib.mvp.component.ApplicationComponent;
import com.example.demolib.mvp.component.DaggerApplicationComponent;
import com.example.demolib.mvp.module.ApplicationModule;


/**
 * Created by john on 2017/6/5.
 */

public class DemoApplication extends Application {
    private static ApplicationComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return sAppComponent;
    }
}
