package com.ejar.fastbedroom.forgotpwd.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.utils.TU;
import com.ejar.fastbedroom.Api.RegisterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyForgotPwdBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/27.
 */

public class ForgotPwdAty extends BaseActivity<AtyForgotPwdBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_forgot_pwd);
        initTitle();
        setListener();
    }

    private void setListener() {
        bindingView.forgotPwdGetCode.setOnClickListener(clickListener);
        bindingView.forgotPwdToNext.setOnClickListener(clickListener);
    }

    private void initTitle() {
        setTitle("忘记密码");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.forgot_pwd_get_code://获取验证码
                String tel = bindingView.forgotPwdName.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    TU.cT("请输入手机号码");
                    return;
                }
                getCode(tel);
                break;
            case R.id.forgot_pwd_to_next://验证验证码
                String tel1 = bindingView.forgotPwdName.getText().toString().trim();
                String code = bindingView.forgotPwdCode.getText().toString().trim();
                if (TextUtils.isEmpty(tel1)) {
                    TU.cT("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    TU.cT("请输入验证码");
                    return;
                }
                gotoNext(tel1, code);
                break;
        }
    };

    private void gotoNext(String tel, String code) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(RegisterApi.class)
                .provideCode(tel, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, true, "验证码验证中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            Intent intent = new Intent(ForgotPwdAty.this, ChangeUserPwdAty.class);
                            intent.putExtra("userTel", tel);
                            startActivity(intent);
                        } else {
                            TU.cT(baseBean.getMsg() + "");
                        }
                    }
                });
    }

    private void getCode(String tel) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(RegisterApi.class)
                .forgotPwd(tel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, true, "验证码获取中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
//                        if(baseBean.getCode().equals("200")){
                        TU.cT(baseBean.getMsg() + "");
//                        }
                    }
                });


    }
}
