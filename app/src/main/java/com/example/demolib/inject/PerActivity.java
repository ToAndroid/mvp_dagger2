package com.example.demolib.inject;

import java.lang.annotation.Retention;


import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by john on 2017/7/24.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
