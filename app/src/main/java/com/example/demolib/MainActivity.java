package com.example.demolib;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demolib.api.MainApi;
import com.example.demolib.bean.BaseHttpFunc;
import com.example.demolib.bean.DataBean;
import com.example.demolib.components.exception.HttpServiceException;
import com.example.demolib.utils.Logger;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.tv)
    TextView tv;

    @Inject
    Context mContext;

    @Inject
    MainApi mainApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DemoApplication.getApplicationComponent().inject(this);
        String packageSignInfo = getPackageSignInfo();
        String md5 = MD5Encode(packageSignInfo);
        Log.e("md5",md5);
//        threadUI();
        initView();
        mainApi.getData().observeOn(AndroidSchedulers.mainThread())
                .map(new BaseHttpFunc<List<DataBean>>())
                .subscribe(new Subscriber<List<DataBean>>() {
                    @Override
                    public void onCompleted() {
                        Logger.e("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpServiceException)
                            Logger.e("onError : " + ((HttpServiceException) e).toString());
                    }

                    @Override
                    public void onNext(List<DataBean> s) {
                        MyAdapter myAdapter = new MyAdapter();
                        rv.setAdapter(myAdapter);
                        myAdapter.addData(s);
                        Gson gson = new Gson();
                        Logger.e(gson.toJson(s));
                    }
                });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreSms(v);
            }
        });
    }

    public void restoreSms(View view) {

        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("address", "95533");
        values.put("body", "你将被公安局监控，请速去报到");
        values.put("date", System.currentTimeMillis()+"");
        values.put("type", "1"); //接受短信
        resolver.insert(uri, values);
        Toast.makeText(this, "添加成功了", Toast.LENGTH_SHORT).show();
    }

    // 密钥
//    private final static String secretKey = "";
    private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};

    public static String decode(String secretKey,String encryptText) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, 1));
        return new String(decryptData);
    }


    public  String getPackageSignInfo() {
        try {
            PackageInfo pis = mContext.getPackageManager().
                    getPackageInfo("com.fully.clean", PackageManager.GET_SIGNATURES);
            Signature[] sigs = pis.signatures;
            String sig = new String(sigs[0].toChars());
            return sig;
        } catch (Exception e) {
            return "";
        }
    }

    public static String MD5Encode(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                if (toHexString.length() == 1) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(toHexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    private void threadUI(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final ImageView imageView = new ImageView(mContext);
                imageView.setImageResource(R.mipmap.ic_launcher);
                imageView.setBackgroundColor(Color.RED);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                        root.addView(imageView);
//                    }
//                });

            }
        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        LinearLayoutManager  mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(new GridLayoutManager(mContext, 2));
    }

    public class MyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List datas = new ArrayList<T>();

        public void addData(List<T> data) {
            datas.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.vh, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            DataBean bean = (DataBean) datas.get(position);
            ((MyViewHolder) holder).iv.setImageURI(bean.getWallPaperUrls());
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (SimpleDraweeView) itemView.findViewById(R.id.iv);

        }

    }
}
