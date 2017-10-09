package com.ejar.fastbedroom.advice;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.advice.bean.AboutUsBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyAboutUsBinding;
import com.ejar.fastbedroom.login.LoginActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/27.
 */

public class AboutUsAty extends BaseActivity<AtyAboutUsBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_about_us);
        initTitle();
        getAppVersionCode();
        getUsTelNumber();
        setListener();
    }

    private void setListener() {
        bindingView.updateUsApp.setOnClickListener(clickListener);
        bindingView.usAbout.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.update_us_app:
                break;
            case R.id.us_about:
                openNextActivity(AboutUsAppAty.class);
                break;
        }
    };

    private void getUsTelNumber() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getUsTel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<AboutUsBean>(this, false, "") {
                    @Override
                    public void _doNext(AboutUsBean aboutUsBean) {
                        Log.e("msg", "关于" + aboutUsBean.getCode());
                        if (aboutUsBean.getCode().equals("200")) {
                            bindingView.usTelNumber.setText("" + aboutUsBean.getData().getModeltel());
                        } else if (aboutUsBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(AboutUsAty.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void getAppVersionCode() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            int versioncode = pi.versionCode;
            String vsersionName = pi.versionName;
            bindingView.appNumber.setText("版本号 " + vsersionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initTitle() {
        setTitle("关于");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
