package com.example.demolib.ui.login;

import com.example.demolib.inject.PerActivity;
import com.example.demolib.inject.component.ApplicationComponent;

import dagger.Component;

/**
 * Created by john on 2017/7/24.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
