package com.app.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.io.File;

/**
 * Created by john on 2017/7/28.
 */

public class UpdateUtils {

    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() && networkInfo
                .getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isNetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    public static ApplicationInfo getApplicationInfo(Context context, String packageName, int flags) {
        try {
            if (context == null)
                return null;
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), flags);
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return null;
    }

    public static String getApkPath(Context context) {
        String packageName = context.getPackageName();
        if (packageName!=null&&packageName.contains(".")){
            packageName=packageName.substring(packageName.lastIndexOf(".")+1);
        }
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + packageName + File.separator + "apk";
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return  path;
    }

    public static String getApkName(Context context){
        return  context.getPackageName()+".apk";
    }

}
