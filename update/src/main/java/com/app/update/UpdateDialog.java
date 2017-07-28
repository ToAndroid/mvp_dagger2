package com.app.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by john on 2017/7/28.
 */

public class UpdateDialog {

    /**
     * 下载的升级包软件版本号
     */
    public static final String UPLOAD_VERSIONCODE = "UPLOADVERSIONCODE";
    /**
     * 升级包的主要信息
     */
    public static final String UPLOAD_DESC = "desc";
    /**
     * 升级包的名字
     */
    public static final String UPLOAD_VNAME = "name";
    /**
     * 升级包的大小
     */
    public static final String UPLOAD_FSIZE = "size";
    /**
     * 升级包的时间
     */
    public static final String UPLOAD_PDATE = "time";

    public static final String UPLOAD_STATUS = "status";
    private Context mContext;
    private AlertDialog mNoticeDialog;
    private View mRootVive;
    private TextView mUpdateContent;

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
    }

    public void setOnClickListener(View.OnClickListener l) {
        mRootVive.findViewById(R.id.update_id_ok).setOnClickListener(l);
    }

    private void localVersionInfo() {
        String desc = UpdatePrefUtils.getString(mContext, UPLOAD_DESC, mContext.getString(R.string.loading));
        String vname = UpdatePrefUtils.getString(mContext, UPLOAD_VNAME, mContext.getString(R.string.loading));
        String fsize = UpdatePrefUtils.getString(mContext, UPLOAD_FSIZE, mContext.getString(R.string.loading));
        String pdate = UpdatePrefUtils.getString(mContext, UPLOAD_PDATE, mContext.getString(R.string.loading));
        showtext(vname, fsize, pdate, desc);

    }

    private boolean remoteVersionInfo(String json) {
        if (TextUtils.isEmpty(json)) return false;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (!jsonObject.has(UPLOAD_STATUS)) {
                return false;
            } else {
                String status = jsonObject.getString(UPLOAD_STATUS);
                if (!TextUtils.equals("200", status)) {
                    return false;
                }
            }


            String desc = "";
            String vname = "";
            String fsize = "";
            String pdate = "";
            if (jsonObject.has(UPLOAD_DESC)) {
                desc = jsonObject.getString(UPLOAD_DESC);
            }

            if (jsonObject.has(UPLOAD_FSIZE)) {
                fsize = jsonObject.getString(UPLOAD_FSIZE);
            }

            if (jsonObject.has(UPLOAD_PDATE)) {
                pdate = jsonObject.getString(UPLOAD_PDATE);
            }

            if (jsonObject.has(UPLOAD_VNAME)) {
                vname = jsonObject.getString(UPLOAD_VNAME);
            }
            showtext(vname, fsize, pdate, desc);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }


    }

    private void showtext(String vname, String fsize, String pdate, String desc) {

        if (!"".equals(desc)) {
            desc = "\n" + desc;
        }
        if (!"".equals(pdate)) {
            pdate = "\n更新时间  ：" + pdate;
        }
        if (!"".equals(fsize)) {
            fsize = "\n文件大小  ： " + fsize;
        }
        if (!"".equals(vname)) {
            vname = "\n版本号  ： " + vname;
        }
        if (mUpdateContent != null) {
            mUpdateContent.setText(vname + fsize + pdate + desc);
        }
    }

    public void finish() {
        if (mNoticeDialog != null) {
            mNoticeDialog.dismiss();
        }
    }


}
