package com.ejar.fastbedroom.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.camera.CameraActivity;
import com.ejar.fastbedroom.home.HomeActivity;
import com.ejar.fastbedroom.login.LoginActivity;

/**
 * Created by json on 2017/8/14.
 */

public class SplashActivity extends AppCompatActivity {
//    public static String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        initView();
    }

    private void initView() {
        ImageView imageView = (ImageView) findViewById(R.id.splash_iv);
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
               String token = (String) SpUtils.get(APP.getInstance(), "token", "");
                if (TextUtils.isEmpty(token)) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                    startActivity(new Intent(SplashActivity.this, CameraActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();
            }
        }, 1000);
    }
}
