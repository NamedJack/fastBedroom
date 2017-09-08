package com.ejar.baseframe.utils.net;

import okhttp3.OkHttpClient;

/**
 * Created by json on 2017/8/15.
 */

public class HttpClient {
    private static HttpClient instance;
    private static OkHttpClient.Builder builder;

    public HttpClient() {
        builder = new OkHttpClient.Builder();
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

}
