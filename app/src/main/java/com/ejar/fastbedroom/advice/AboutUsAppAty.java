package com.ejar.fastbedroom.advice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.advice.bean.UsAppBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyAboutAppBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.utils.TU;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/27.
 */

public class AboutUsAppAty extends BaseActivity<AtyAboutAppBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_about_app);
        initTitle();
    }

    private void initTitle() {
        setTitle("关于快到寝");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getUsTips()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<UsAppBean>(this, true, "信息获取中...") {
                    @Override
                    public void _doNext(UsAppBean usAppBean) {
                        if (usAppBean.getCode().equals("200")) {
                            bindingView.usApp.setText("" + usAppBean.getData().getAbout());
                        } else if (usAppBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(AboutUsAppAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT("网络繁忙，请稍后再试");
                        }
                    }
                });

    }
}
