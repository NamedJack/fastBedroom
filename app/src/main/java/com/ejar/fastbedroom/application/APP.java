package com.ejar.fastbedroom.application;

import android.app.Application;
import android.content.Context;

import com.ejar.baseframe.utils.toast.TU;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by json on 2017/8/15.
 */

public class APP extends LitePalApplication {
    public static Context instance;
    public static String token;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LitePal.initialize(this);
        TU.register(this);
    }

    public static Context getInstance(){
        return instance;
    }

}
