package com.example.demolib.ui.login;

import com.example.demolib.base.BasePresenter;
import com.example.demolib.base.BaseView;

/**
 * Created by john on 2017/7/24.
 */

public interface LoginContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        void login(String username, String pwd);
    }

}
