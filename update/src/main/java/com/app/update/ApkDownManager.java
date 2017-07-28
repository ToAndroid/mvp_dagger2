package com.app.update;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by john on 2017/7/28.
 */

public class ApkDownManager {
    private final static String UPLOAD_VERSIONCODE = "upload_versioncode";
    private Context mContext;

    private ApkDownManager() {
    }

    private static final ApkDownManager instace = new ApkDownManager();

    public static ApkDownManager getInstace() {
        return instace;
    }

    public void checkUpdate(String url,Activity activity, boolean isWifiUpdate) {
        mContext = activity.getApplicationContext();
        //本都存储的升级号与当前软件的版本号3种情况：
        //a:大于,说明本地已有下载好的升级包，用户没有安装
        //b:等于,说明当前版本相同，需要进行网络检查是否有更新版本
        //c:小于，说明当前存储错误，需要进行网络检查是否有更新版本
        int currentCode = UpdatePrefUtils.getInt(mContext, UPLOAD_VERSIONCODE, 0);
        int versionCode = UpdateUtils.getVersionCode(mContext);
        if (currentCode > versionCode) {  //情况a
            File apk_file = new File(UpdateUtils.getApkPath(mContext));
            if (apk_file.exists()) {//当前路径下有升级版本包给出升级弹框
                // 显示提示对话框
                showNoticeDialog(activity);

            } else {//当前路径下没有升级升级包，重新网络下载
                checkUpdateNet(url,activity);
            }
        } else { //情况b,c;
            checkUpdateNet(url,activity);
        }
    }

    private void checkUpdateNet(final String url, final Activity activity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String l=url+"?version=" +UpdateUtils.getVersionCode(mContext);
                final String s = UploadNet.get(l);
                if (!TextUtils.isEmpty(s)){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UpdateDialog dialog = new UpdateDialog(activity);
                            dialog.showRemoteDialog(s);
                        }
                    });
                }
            }
        }).start();

    }


    private void showNoticeDialog(Activity activity) {
        UpdateDialog dialog = new UpdateDialog(activity);
        dialog.showLocalDialog();

    }


}
