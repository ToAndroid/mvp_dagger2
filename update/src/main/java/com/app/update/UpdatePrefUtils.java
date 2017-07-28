package com.app.update;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference封装
 *
 * @author Kevin
 */
public class UpdatePrefUtils {
    public static final String PREF_NAME = "version_config";
    private static SharedPreferences mSp;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
        }
        return mSp;
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        return getSharedPreferences(ctx).getBoolean(key, defaultValue);
    }


    public static int getInt(Context context, String key, int defaultValue) {
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    public static void setInt(Context ctx, String key, int value) {

        getSharedPreferences(ctx).edit().putInt(key, value).commit();
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }


    public static String getString(Context ctx, String key, String defaultValue) {
        return getSharedPreferences(ctx).getString(key, defaultValue);
    }


    public static void setLong(Context ctx, String key, long value) {
        getSharedPreferences(ctx).edit().putLong(key, value).commit();
    }

    public static void setString(Context ctx, String key, String value) {

        getSharedPreferences(ctx).edit().putString(key, value).commit();
    }


    public static void clearSP(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


}
