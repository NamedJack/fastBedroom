package com.ejar.fastbedroom.forgotpwd.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.fastbedroom.utils.TU;
import com.ejar.fastbedroom.Api.RegisterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyChangeUserPwdBinding;
import com.ejar.fastbedroom.login.LoginActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/27.
 */

public class ChangeUserPwdAty extends BaseActivity<AtyChangeUserPwdBinding> {
    private String tel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_change_user_pwd);
        Intent intent = getIntent();
        tel = intent.getStringExtra("userTel");
        bindingView.changePwdTel.setText(tel + "");
        initTitle();
        setListener();
    }

    private void setListener() {
        bindingView.confirmChangePwd.setOnClickListener(v -> {
            String pwd = bindingView.changePwd.getText().toString().trim();
            String pwdAgain = bindingView.changePwdAgain.getText().toString().trim();
            if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
                TU.cT("请输入并且确认密码");
                return;
            }
            if (!pwd.equals(pwdAgain)) {
                TU.cT("两次输入的密码不一致");
                return;
            }
            NetRequest.getInstance(UrlConfig.baseUrl).create(RegisterApi.class)
                    .confirmChangePwd(tel, pwd, pwdAgain)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MyBaseObserver<BaseBean>(this, true, "提交中...") {
                        @Override
                        public void _doNext(BaseBean  baseBean) {
                            if(baseBean.getCode().equals("200")){
                                TU.cT("修改成功");
                                SpUtils.put(APP.getInstance(), "userName", tel);
                                AppManager.removeAllAty();
                                openNextActivity(LoginActivity.class);
                            }else {
                                TU.cT("" + baseBean.getMsg());
                            }
                        }
                    });
        });
    }

    private void initTitle() {
        setTitle("修改密码");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
