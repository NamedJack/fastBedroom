package com.ejar.fastbedroom.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.net.NetWork;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.baseframe.widget.CustomDialog;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.camera.CameraActivity;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyLoginBinding;
import com.ejar.fastbedroom.home.HomeActivity;
import com.ejar.fastbedroom.register.aty.RegisterAty;
import com.ejar.fastbedroom.splash.SplashActivity;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by json on 2017/8/14.
 */

public class LoginActivity extends BaseActivity<AtyLoginBinding> {
    private String userName;
    private String userPwd;
    private Dialog dialog = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        init();
    }

    private void init() {
        setTitle("登 录");
        setNavigationOnClickListener(v -> {
            finish();
        });
        String userName = (String) SpUtils.get(APP.getInstance(), "userName", "");
        if (!TextUtils.isEmpty(userName)) {
            bindingView.userName.setText(userName);
        }
        bindingView.btnLogin.setOnClickListener(clickListener);
        bindingView.doRegister.setOnClickListener(clickListener);
        bindingView.doForgotPwd.setOnClickListener(clickListener);
    }


    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.do_register:
                openNextActivity(RegisterAty.class);
                break;
            case R.id.do_forgot_pwd:
//                openNextActivity(CameraActivity.class);
                break;
            case R.id.btn_login:
                checkinputDologin();
                break;
            default:
                break;
        }
    };

    private void checkinputDologin() {
        userName = bindingView.userName.getText().toString().trim();
        userPwd = bindingView.userPwd.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            TU.cT("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(userPwd)) {
            TU.cT("请输入密码");
            return;
        }
        dialog = NetDialog.createDialog(this,"登录中...");
        NetWork.getInstance(UrlConfig.baseUrl).create(LoginApi.class)
                .postLogin(userName, userPwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
//                    Dialog dialog = null;
                    @Override
                    public void onSubscribe(Disposable d) {
//                        dialog =  NetDialog.createDialog(APP.getInstance(),"登录中...");
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        NetDialog.closeDialog(dialog);
                        if (userBean.getCode().equals("202")) {
                            //帐号不存在
                            TU.cT("该帐号不存在");
                        } else if (userBean.getCode().equals("203")) {
                            TU.cT("密码有误");
                            //密码错误
                        } else if (userBean.getCode().equals("200")) {
                            SpUtils.put(APP.getInstance(), "token", userBean.getToken());
                            SpUtils.put(APP.getInstance(), "userName", userName);
                            APP.token = userBean.getToken();
                            openNextActivity(HomeActivity.class);
                            LoginActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetDialog.closeDialog(dialog);
                        Log.e("msg", "错误" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        NetDialog.closeDialog(dialog);
                    }
                });
    }


}
