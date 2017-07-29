package com.example.demolib;

import android.app.Application;

import com.example.demolib.inject.component.ApplicationComponent;
import com.example.demolib.inject.component.DaggerApplicationComponent;
import com.example.demolib.inject.module.ApiModule;
import com.example.demolib.inject.module.ApplicationModule;
import com.example.demolib.utils.ToastUtil;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

import javax.inject.Inject;

import okhttp3.OkHttpClient;


/**
 * Created by john on 2017/6/5.
 */

public class App extends Application {

    private static ApplicationComponent sAppComponent;

    @Inject
    OkHttpClient mOkHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.register(this);
        sAppComponent = DaggerApplicationComponent.builder()
                .apiModule(new ApiModule())
                .applicationModule(new ApplicationModule(this)).build();
        sAppComponent.inject(this);
        UmengManager.init(getApplicationContext());
    }

    public static ApplicationComponent getApplicationComponent() {
        return sAppComponent;
    }

}
