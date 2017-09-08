package com.ejar.fastbedroom.register.aty;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyRegisterBinding;
import com.ejar.fastbedroom.register.bean.CodeBean;
import com.ejar.fastbedroom.register.bean.RegisterCodeBean;
import com.ejar.fastbedroom.register.model.RegisterApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/15.
 */

public class RegisterAty extends BaseActivity<AtyRegisterBinding> {
    private String registerName;
    private String registerCode;
    private Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_register);
        init();
    }

    private void init() {
        setTitle("注册");
        setNavigationOnClickListener(v -> {finish();});
        bindingView.goToNext.setOnClickListener(clickListener);
        bindingView.getRegisterCode.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.go_to_next:
                checkRegisterInfo();
//                openNextActivity(RegisterInfoAty.class);
                break;
            case R.id.get_register_code:
                getRegisterCode();
                break;
            default:
                break;
        }
    };

    private void getRegisterCode() {
        registerName = bindingView.registerName.getText().toString().trim();
        if(TextUtils.isEmpty(registerName)){
            TU.cT("请输入注册手机号码");
            return;
        }
        dialog = NetDialog.createDialog(this,"验证码获取中...");
        NetRequest.getInstance(UrlConfig.baseUrl).create(RegisterApi.class)
                .getRegisterCode(registerName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterCodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterCodeBean registerCodeBean) {
                        NetDialog.closeDialog(dialog);
                        if(registerCodeBean.getCode().equals("200")){
//                            timer.start();
                            TU.cT(""+ registerCodeBean.getMsg());
                        }else if(registerCodeBean.getCode().equals("202")){
                            TU.cT(""+ registerCodeBean.getMsg());
                        }else if(registerCodeBean.getCode().equals("203")){
                            TU.cT(""+ registerCodeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetDialog.closeDialog(dialog);
                    }

                    @Override
                    public void onComplete() {
                        NetDialog.closeDialog(dialog);
                    }
                });

    }

    private void checkRegisterInfo() {
        registerCode = bindingView.registerCode.getText().toString().trim();
        registerName = bindingView.registerName.getText().toString().trim();

        if(TextUtils.isEmpty(registerName)){
            TU.cT("请输入注册手机号码");
            return;
        }
        if(TextUtils.isEmpty(registerCode)){
            TU.cT("请输入验证码");
            return;
        }
        dialog = NetDialog.createDialog(this,"");
        NetRequest.getInstance(UrlConfig.baseUrl).create(RegisterApi.class)
                .provingTel(registerName, registerCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CodeBean codeBean) {
                        NetDialog.closeDialog(dialog);
                        if(codeBean.getCode().equals("200")){
                            SpUtils.put(APP.getInstance(),"userName", registerName);
                            openNextActivity(RegisterInfoAty.class);
                        }else if(codeBean.getCode().equals("204")){
                            TU.cT("" + codeBean.getMsg());
                        }else {
                            TU.cT("" + codeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetDialog.closeDialog(dialog);
                    }

                    @Override
                    public void onComplete() {
                        NetDialog.closeDialog(dialog);
                    }
                });
    }

//    CountDownTimer timer = new CountDownTimer(31000, 1000) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            bindingView.getRegisterCode.setText(millisUntilFinished/1000+"s");
//            bindingView.getRegisterCode.setClickable(false);
//        }
//
//        @Override
//        public void onFinish() {
//            bindingView.registerName.setClickable(true);
//        }
//    };
//
//    @Override
//    protected void onDestroy() {
//        if(timer != null){
//            timer.cancel();
//        }
//        super.onDestroy();
//    }
}
