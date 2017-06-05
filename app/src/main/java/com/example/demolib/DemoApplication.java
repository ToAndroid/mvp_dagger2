package com.example.demolib;

import android.app.Application;
import com.example.demolib.component.AppComponent;
import com.example.demolib.component.DaggerAppComponent;
import com.example.demolib.module.AppModule;

/**
 * Created by john on 2017/6/5.
 */

public class DemoApplication extends Application {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
