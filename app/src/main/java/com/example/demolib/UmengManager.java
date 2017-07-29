package com.example.demolib;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * Created by john on 2017/7/29.
 */

public class UmengManager {

    public static void init(Context context) {

        // 普通统计场景
        try {
            MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
            // 禁止调试
            MobclickAgent.setDebugMode(false);
            // 传输加密
            MobclickAgent.enableEncrypt(true);
            // 禁止默认的页面统计方式，这样将不会再自动统计Activity
            MobclickAgent.openActivityDurationTrack(false);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    public static void onPause(Context context) {
        MobclickAgent.onPause(context);
        onPageEnd(context.getClass().getSimpleName());
    }

    public static void onResume(Context context) {
        MobclickAgent.onResume(context);
        onPageStart(context.getClass().getSimpleName());

    }

    public static void onPageStart(String tag) {
        MobclickAgent.onPageStart(tag);
    }

    public static void onPageEnd(String tag) {
        MobclickAgent.onPageEnd(tag);
    }

    public static void onEvent(Context context, String tag) {
        MobclickAgent.onEvent(context, tag);
    }


    public static void onEvent(Context context, String tag, Map<String, String> map) {
        MobclickAgent.onEvent(context, tag, map);
    }


}
