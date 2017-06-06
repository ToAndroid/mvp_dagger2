package com.example.demolib;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demolib.api.MainApi;
import com.example.demolib.bean.BaseHttpFunc;
import com.example.demolib.bean.DataBean;
import com.example.demolib.components.exception.HttpServiceException;
import com.example.demolib.mvp.component.DaggerApplicationComponent;
import com.example.demolib.mvp.module.ApiModule;
import com.example.demolib.utils.Logger;
import com.example.demolib.utils.PhoneInfoUtils;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
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


    }

    private void initView() {
//        LinearLayoutManager  mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
