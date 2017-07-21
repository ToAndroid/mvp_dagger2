package com.example.demolib.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.demolib.picframe.PicassoHelper;

/**
 * Created by john on 2017/7/21.
 */

public class NetImageView extends AppCompatImageView {


    public NetImageView(Context context) {
        super(context);
        init();
    }

    public NetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NetImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    private void init(){
        setScaleType(ScaleType.CENTER_CROP);
    }

    public void setNetUrl(String url) {
        if (TextUtils.isEmpty(url)) return;

        PicassoHelper.setPicasso(getContext(), url, this);
    }
}
