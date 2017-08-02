package com.example.demolib;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.app.update.ApkDownManager;
import com.example.demolib.api.MainApi;
import com.example.demolib.bean.DataBean;
import com.example.demolib.mvp.view.ItemFragment;
import com.example.demolib.mvp.view.ItemFragment.OnListFragmentInteractionListener;
import com.example.demolib.mvp.view.dummy.DummyContent;
import com.example.demolib.ui.login.LoginActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener {

    @BindView(R.id.root)
    RelativeLayout root;

    @Inject
    Context mContext;

    @Inject
    MainApi mainApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(Color.RED);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.getApplicationComponent().inject(this);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root,ItemFragment.newInstance(2)).commit();

//        LoginActivity.startLoginActivity(this);
        String url = "http://192.168.1.233/tp5/public/api/index/upload";
        ApkDownManager.getInstace().checkUpdate(url, this);
        UmengManager.onEvent(this, "main");
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    public void setStatusBarColor(int color) {
        if (color == 0)
            return;

        if (Build.VERSION.SDK_INT > 21) {
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        UmengManager.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UmengManager.onPause(this);
    }
}
