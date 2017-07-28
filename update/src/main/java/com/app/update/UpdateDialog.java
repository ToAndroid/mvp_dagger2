package com.app.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by john on 2017/7/28.
 */

public class UpdateDialog {
    private Context mContext;
    private AlertDialog mNoticeDialog;
    private  View mRootVive;

    public UpdateDialog(Activity activity) {
        this.mContext = activity;
        creatDialog();
    }

    public void showDialog() {
        if (mNoticeDialog == null) return;
        mNoticeDialog.show();
        mNoticeDialog.getWindow().setContentView(mRootVive);
    }

    private void creatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.dialog);
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        mRootVive = inflater.inflate(R.layout.update_dialog, null);
        mNoticeDialog = builder.create();

        initView(mRootVive);
    }

    private void initView(View v) {

    }
}
