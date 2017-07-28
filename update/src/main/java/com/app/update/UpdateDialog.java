package com.app.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by john on 2017/7/28.
 */

public class UpdateDialog implements UploadNet.DownFileListenter {

    /**
     * 下载的升级包软件版本号
     */
    public static final String UPLOAD_VERSIONCODE = "UPLOADVERSIONCODE";
    /**
     * 升级包的主要信息
     */
    public static final String UPLOAD_CONTENT = "content";
    public static final String UPLOAD_URL = "url";


    public static final String UPLOAD_STATUS = "status";
    private Context mContext;
    private AlertDialog mNoticeDialog;
    private View mRootVive;
    private TextView mUpdateContent;
    private ProgressBar mPb;

    private String downAPKurl;

    public UpdateDialog(Activity activity) {
        this.mContext = activity;
        creatDialog();
    }

    public void showLocalDialog() {
        if (mNoticeDialog == null) return;
        localVersionInfo();
        mNoticeDialog.show();
        mNoticeDialog.getWindow().setContentView(mRootVive);
    }

    public void showRemoteDialog(String version) {
        if (mNoticeDialog == null) return;
        boolean b = remoteVersionInfo(version);
        if (!b) {
            return;
        }
        mNoticeDialog.show();
        mNoticeDialog.getWindow().setContentView(mRootVive);
    }


    private void creatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.dialog);
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        mRootVive = inflater.inflate(R.layout.update_dialog, null);
        mNoticeDialog = builder.create();

        initView();
    }

    private void initView() {
        mUpdateContent = (TextView) mRootVive.findViewById(R.id.update_content);


        mRootVive.findViewById(R.id.update_id_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setOnClickListener(null);
    }

    public void setOnClickListener(View.OnClickListener l) {
        mRootVive.findViewById(R.id.update_id_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(downAPKurl)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mNoticeDialog!=null){
                                mNoticeDialog.setCancelable(false);
                            }
                            UploadNet.downFile(downAPKurl, mContext.getFilesDir().getPath(), mContext.getPackageName() + ".apk", UpdateDialog.this);
                        }
                    }).start();

                    v.setVisibility(View.GONE);
                }
            }
        });
    }

    private void localVersionInfo() {
        String desc = UpdatePrefUtils.getString(mContext, UPLOAD_CONTENT, mContext.getString(R.string.loading));


    }

    private boolean remoteVersionInfo(String json) {
        if (TextUtils.isEmpty(json)) return false;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (!jsonObject.has(UPLOAD_STATUS)) {
                return false;
            }

            String status = jsonObject.getString(UPLOAD_STATUS);
            if (TextUtils.equals(status, "300")) {//wifi更新
                if (!UpdateUtils.isWifiAvailable(mContext)) {
                    return false;
                }
            } else if (TextUtils.equals(status, "100")) {//强制更新
                if (mNoticeDialog != null) {
                    mNoticeDialog.setCancelable(false);
                }
                mRootVive.findViewById(R.id.update_id_cancel).setVisibility(View.GONE);

            } else if (!TextUtils.equals(status, "200")) {//全网更新
                return false;
            }
            if (jsonObject.has(UPLOAD_URL)) {
                downAPKurl = jsonObject.getString(UPLOAD_URL);
            }
            if (jsonObject.has(UPLOAD_CONTENT)) {
                if (mUpdateContent != null) {
                    mUpdateContent.setText(jsonObject.getString(UPLOAD_CONTENT));
                }
            }

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }


    }


    public void finish() {
        if (mNoticeDialog != null) {
            mNoticeDialog.dismiss();
        }
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onProgress(final long total, final long progress) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mPb == null && mRootVive != null) {
                    mPb = (ProgressBar) mRootVive.findViewById(R.id.pb);
                    mPb.setVisibility(View.VISIBLE);
                    mPb.setMax((int) total);
                }
                mPb.setProgress((int) progress);
            }
        });
        SystemClock.sleep(1);

    }

    @Override
    public void onFinish(final String savePath) {

        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mPb != null) {
                    mPb.setVisibility(View.GONE);
                }
                installApk(savePath);
            }
        });


    }

    @Override
    public void onDownError() {

    }

    /**
     * 安装APK文件
     */
    private void installApk(String savePath) {
        final File apkfile = new File(savePath);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        mContext.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
