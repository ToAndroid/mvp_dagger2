# mvp+dagger2+rxandrod

### 环境搭建
    创建config.grdle，统一三方库版本
    ***
    ext {
        android = [
                applicationId    : "com.demolib",
                compileSdkVersion: 25,
                buildToolsVersion: "25.0.2",
                minSdkVersion    : 14,
                targetSdkVersion : 25,
                versionCode      : 1,
                versionName      : "1.0.0"
        ]
    
        dependencies = [
                //view
                "appcompat-v7"        : "com.android.support:appcompat-v7:25.3.1",
                "constraint-layout"   : "com.android.support.constraint:constraint-layout:1.0.2",
                "recyclerview"        : "com.android.support:recyclerview-v7:23.1.0",
    
                //retrofit
                "retrofit"            : "com.squareup.retrofit2:retrofit:2.1.0",
                "adapter-rxjava"      : "com.squareup.retrofit2:adapter-rxjava:2.1.0",
                "converter-gson"      : "com.squareup.retrofit2:converter-gson:2.1.0",
    
                // Reactive Programming RxJava
                "rxandroid"           : "io.reactivex:rxandroid:1.1.0",
                "rxjava"              : "io.reactivex:rxjava:1.1.0",
                "rxbinding"           : "com.jakewharton.rxbinding:rxbinding:0.3.0",
    
                //dagger2
                "dagger"              : "com.google.dagger:dagger:2.0.2",
                "dagger-compiler"     : "com.google.dagger:dagger-compiler:2.0.2",
                "javax.annotation-api": "javax.annotation:javax.annotation-api:1.2",
    
                // ButterKnife DI
                "butterknife"         : "com.jakewharton:butterknife:8.4.0",
                "butterknife-compiler": 'com.jakewharton:butterknife-compiler:8.4.0'
    
    
    
    
        ]
    }
    ***
    