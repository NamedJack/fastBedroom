package com.ejar.fastbedroom.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyMessageWebBinding;
import com.ejar.fastbedroom.utils.TU;

/**
 * Created by json on 2017/9/29.
 */

public class MessageWebAty extends BaseActivity<AtyMessageWebBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_message_web);
        setTitle("消息");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {finish();});



        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");
        switch (tag) {
            case "banner":
                int id = intent.getIntExtra("webId", -1);
                String url = intent.getStringExtra("url");
                Log.e("msg", "" + url + id);
                getWebMessage(url + "?id=",id);

                break;
            case "list":
                int webId = intent.getIntExtra("webId", -1);
                getWebMessage("mng/post?id=", webId);
                break;
        }

    }

    private void getWebMessage(String loadUrl ,int webId) {
        String url = UrlConfig.baseUrl + loadUrl + webId;
        if (webId == -1) {
            TU.cT("没有消息哦~");
            return;
        } else {
            bindingView.messageWeb.getSettings().setUseWideViewPort(true);
            bindingView.messageWeb.getSettings().setLoadWithOverviewMode(true);
            bindingView.messageWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            bindingView.messageWeb.loadUrl(url);
            bindingView.messageWeb.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            }); }
    }
}
