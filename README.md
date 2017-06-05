# mvp+dagger2+rxandrod

### 环境搭建
创建config.grdle，统一三方库版本
***
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
***

    