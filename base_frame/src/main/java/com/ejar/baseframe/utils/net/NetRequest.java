package com.ejar.baseframe.utils.net;

import android.text.TextUtils;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by json on 2017/8/14.
 */

public class NetWork {
    private volatile static NetWork netWork;
//    public  String baseUrl ;
    private Retrofit retrofit;
//    private OkHttpClient.Builder builder;

    private NetWork(String baseUrl) {
        Gson gson = new Gson();
//        builder = HttpClient.getInstance().getBuilder();
        if (TextUtils.isEmpty(baseUrl)) throw new RuntimeException("baseUrl not initial");
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HttpClient.getInstance().getBuilder().build())
                .build();
    }

    public static NetWork getInstance(String baseUrl) {
        if (netWork == null) {
            synchronized (NetWork.class) {
                if (netWork == null) {
                    netWork = new NetWork(baseUrl);
                }
            }
        }
        return netWork;
    }

    public <T> T create(Class<T> tClass) {
        return retrofit.create(tClass);
    }

}
