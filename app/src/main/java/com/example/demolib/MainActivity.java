package com.example.demolib;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.demolib.utils.PhoneInfoUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView tv;
    @Inject
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DemoApplication.getApplicationComponent().inject(this);
        String string = mContext.getResources().getString(R.string.app_name);
        String deviceInfo = PhoneInfoUtils.getDeviceInfo(mContext);
        tv.setText(deviceInfo);

    }
}
