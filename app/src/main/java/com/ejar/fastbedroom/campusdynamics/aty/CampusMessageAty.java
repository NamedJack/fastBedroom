package com.ejar.fastbedroom.campusdynamics.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.fastbedroom.campusdynamics.bean.DetailMessageBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyCampusMessageBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.utils.TU;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by json on 2017/10/10.
 */

public class CampusMessageAty extends BaseActivity<AtyCampusMessageBinding> {
    private String webUrl = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_campus_message);
        initTitle();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        int messageId = intent.getIntExtra("messageId", -1);
        if(messageId == -1){
            bindingView.campusMessageWeb.setVisibility(View.GONE);
            bindingView.emptyTv.setVisibility(View.VISIBLE);
            return;
        }

        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getCampusDetailMessage(APP.token, messageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<DetailMessageBean>(this, true, "消息获取中...") {
                    @Override
                    public void _doNext(DetailMessageBean messageBean) {
                       if(messageBean.getCode().equals("200")){
                           webUrl = messageBean.getOkurl();
                           loadview(webUrl);
                       }else if(messageBean.getCode().equals(UrlConfig.logoutCodeOne)){
                           openNextActivity(LoginActivity.class);
                           AppManager.removeAllAty();
                       }else {
                           TU.cT("" + messageBean.getMsg());
                       }
                    }
                });


    }

    private void loadview(String webUrl) {
        bindingView.campusMessageWeb.getSettings().setUseWideViewPort(true);
        bindingView.campusMessageWeb.getSettings().setLoadWithOverviewMode(true);
        bindingView.campusMessageWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String url = UrlConfig.baseUrl + webUrl;
        bindingView.campusMessageWeb.loadUrl(url);
        bindingView.campusMessageWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initTitle() {
        setTitle("校园动态");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {finish();});
    }
}
