package com.ejar.fastbedroom.personal.aty;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.fastbedroom.Api.HomeAtyApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyChangeMessageBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.personal.bean.ResultBean;
import com.ejar.fastbedroom.utils.TU;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/25.
 * 修改个人电话  昵称等aty
 */

public class ChangeMessageAty extends BaseActivity<AtyChangeMessageBinding> {
    private int code;
    private Dialog mDialog ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_change_message);
        setNavigationOnClickListener(v -> {finish();});
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        Intent intent = getIntent();
        code = intent.getIntExtra("item", -1);
        initData();
        setListener();
    }

    private void setListener() {
        bindingView.confirContent.setOnClickListener(v -> {
            String messageOne = bindingView.changedContentFirst.getText().toString().trim();
            String messageTwo = bindingView.changedContentSecond.getText().toString().trim();
            if(TextUtils.isEmpty(messageOne)){
                TU.cT("请输入修改内容");
                return;
            }
            if(View.VISIBLE == bindingView.changedContentSecond.getVisibility() &&
                    TextUtils.isEmpty( messageTwo )){
                TU.cT("请输入修改内容");
                return;
            }
            commitChangeInfo(messageOne,messageTwo);
        });
    }

    private void commitChangeInfo(String messageOne, String messageTwo) {
        mDialog = NetDialog.createDialog(this, "信息获取中,请稍候...");
        if(code == 1){
            NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                    .changedName(messageOne, APP.token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultBean>() {
                        @Override
                        public void accept(ResultBean resultBean) throws Exception {
                            NetDialog.closeDialog(mDialog);
                            if(resultBean.getCode().equals("200")){
                                TU.cT("修改成功");
                                ChangeMessageAty.this.finish();
                            } else if (resultBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                                AppManager.removeAllAty();
                                Intent intent = new Intent(ChangeMessageAty.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                bindingView.changeViewTips.setText(resultBean.getMsg());
                            }
                        }
                    });

        }else if(code == 2){
            NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                    .provingPhone(messageOne, messageTwo, APP.token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultBean>() {
                        @Override
                        public void accept(ResultBean resultBean) throws Exception {
                            NetDialog.closeDialog(mDialog);
                            if(resultBean.getCode().equals("200")){
                                bindingView.changeViewTips.setText("请输入新的电话号码");
                                bindingView.changedContentFirst.setHint("请输入新的电话号码");
                                bindingView.changedContentSecond.setVisibility(View.GONE);
                                code = 4;
                            } else if (resultBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                                AppManager.removeAllAty();
                                Intent intent = new Intent(ChangeMessageAty.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                bindingView.changeViewTips.setText(resultBean.getMsg());
                            }
                        }
                    });

        }else if(code == 3){
            NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                    .provingPwd(messageOne, APP.token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultBean>() {
                        @Override
                        public void accept(ResultBean resultBean) throws Exception {
                            NetDialog.closeDialog(mDialog);
                            if(resultBean.getCode().equals("200")){
                                bindingView.changeViewTips.setText("请输入新的密码");
                                bindingView.changedContentSecond.setVisibility(View.VISIBLE);
                                bindingView.changedContentFirst.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                bindingView.changedContentSecond.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                code = 5;
                            } else if (resultBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                                AppManager.removeAllAty();
                                Intent intent = new Intent(ChangeMessageAty.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                bindingView.changeViewTips.setText(resultBean.getMsg());
                            }
                        }
                    });

        }else if(code == 4){//修改电话
            NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                    .changePhone(messageOne, APP.token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultBean>() {
                        @Override
                        public void accept(ResultBean resultBean) throws Exception {
                            NetDialog.closeDialog(mDialog);
                            if(resultBean.getCode().equals("200")){
                                TU.cT("修改成功");
                                ChangeMessageAty.this.finish();
                            } else if (resultBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                                AppManager.removeAllAty();
                                Intent intent = new Intent(ChangeMessageAty.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                bindingView.changeViewTips.setText(resultBean.getMsg());
                            }
                        }
                    });
        }else if(code == 5){//修改密码
            if(!messageOne.equals(messageTwo)){
                NetDialog.closeDialog(mDialog);
                bindingView.changeViewTips.setText("两次输入密码不一致");
                return; }
            NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                    .changePwd(messageOne, messageTwo, APP.token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultBean>() {
                        @Override
                        public void accept(ResultBean resultBean) throws Exception {
                            NetDialog.closeDialog(mDialog);
                            if(resultBean.getCode().equals("200")){
                                TU.cT("修改成功,请用新密码重新登录");
                                AppManager.removeAllAty();
                                Intent intent = new Intent(ChangeMessageAty.this, LoginActivity.class);
                                startActivity(intent);
                            } else if (resultBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                                AppManager.removeAllAty();
                                Intent intent = new Intent(ChangeMessageAty.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                bindingView.changeViewTips.setText(resultBean.getMsg());
                            }
                        }
                    });
        }

    }

    private void initData() {
        switch (code){
            case -1 ://昵称
                setTitle("修改信息");
                break;
            case 1:
                setTitle("修改昵称");
                break;
            case 2://验证电话
                setTitle("修改电话");
                bindingView.changeViewTips.setVisibility(View.VISIBLE);
                bindingView.changeViewTips.setText("Tips: 修改电话需要验证原手机号码和密码");
                bindingView.changedContentSecond.setVisibility(View.VISIBLE);
                bindingView.changedContentSecond.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                bindingView.changedContentFirst.setHint("请输入原电话号码");
                bindingView.changedContentFirst.setInputType(InputType.TYPE_CLASS_PHONE);
                InputFilter[] filters = {new InputFilter.LengthFilter(11)};
                bindingView.changedContentFirst.setFilters(filters);
                bindingView.changedContentSecond.setHint("请输入原始密码");
                break;
            case 3://验证密码
                setTitle("修改密码");
                bindingView.changeViewTips.setVisibility(View.VISIBLE);
                bindingView.changeViewTips.setText("Tips: 请输入原密码");
                break;
                default:break;
        }
    }



}
