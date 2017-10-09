package com.ejar.fastbedroom.advice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyAdviceBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.utils.TU;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/27.
 */

public class AdviceAty extends BaseActivity<AtyAdviceBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_advice);
        initTitle();
    }


    private void initTitle() {
        setTitle("意见反馈");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        bindingView.postAdvice.setOnClickListener(v -> {
            postAdvice();
        });
    }

    private void postAdvice() {
        String advice = bindingView.inputAdvice.getText().toString().trim();
        String phone = bindingView.inputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(advice)) {
            TU.cT("请输入意见");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            TU.cT("请输入您的电话号码");
            return;
        }
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .postAdvice(APP.token, advice, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, true, "意见提交中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("提交成功，感谢您宝贵的建议！");
                            bindingView.inputAdvice.setText("");
                            bindingView.inputPhone.setText("");
                        } else if (baseBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(AdviceAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(baseBean.getMsg() + "");
                        }

                    }
                });

    }
}
