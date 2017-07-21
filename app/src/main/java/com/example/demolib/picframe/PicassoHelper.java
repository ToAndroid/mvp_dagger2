package com.example.demolib.picframe;

/**
 * Created by john on 2017/7/21.
 */

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * -dontwarn com.squareup.okhttp.**
 */
public class PicassoHelper {

    public static  void setPicasso(Context context, String url, ImageView imageView){
        Picasso.with(context).load(url).into(imageView);
    }
}
