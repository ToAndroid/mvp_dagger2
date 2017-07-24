package com.example.demolib.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.demolib.App;
import com.example.demolib.R;
import com.example.demolib.updata.UploadNet;
import com.example.demolib.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.textInputUserName)
    TextInputLayout textInputUserName;
    @BindView(R.id.etPassWord)
    EditText etPassWord;
    @BindView(R.id.textInputPassword)
    TextInputLayout textInputPassword;

    @Inject
    LoginPresenter mLoginPresenter;

    public static void startLoginActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolBar(toolbar);
        setTitle("登录");
        etUserName.addTextChangedListener(new MTextWatcher(textInputUserName));
        etPassWord.addTextChangedListener(new MTextWatcher(textInputPassword));

    }

    private void initInject() {
        DaggerLoginComponent.builder()
                .applicationComponent(App.getApplicationComponent())
                .build()
                .inject(this);
        mLoginPresenter.attachView(this);
    }

    public void initToolBar(Toolbar mToolBar) {
        if (null != mToolBar) {
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    class MTextWatcher implements TextWatcher {

        TextInputLayout textInputLayout;

        public MTextWatcher(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            textInputLayout.setErrorEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.login) {
            doLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doLogin() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                String s = UploadNet.get("http://blog.csdn.net/u012527802/article/details/51153071");
                String downUlr = "http://app.cet4free.cn/voicedata/puregirls.zip";
                UploadNet.downFile(downUlr, getApplication().getFilesDir().getPath(), "puregirls.zip", new UploadNet.DownFileListenter() {
                    @Override
                    public void onStart() {
                        Logger.e("onStart");
                    }

                    @Override
                    public void onProgress(long total, long progress) {
                        Logger.e("onProgress : " + progress + " / " + total);
                    }

                    @Override
                    public void onFinish() {
                        Logger.e("onFinish");
                    }

                    @Override
                    public void onDownError() {
                        Logger.e("onDownError");
                    }
                });
            }
        });
        thread.start();


        String username = etUserName.getText().toString().trim();
        String pwd = etPassWord.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) return;
//        mLoginPresenter.login(username,pwd);
    }

}
