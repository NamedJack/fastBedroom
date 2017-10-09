package com.ejar.fastbedroom.personal.aty;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.fastbedroom.Api.HomeAtyApi;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyPersoninfoBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.personal.bean.LogoutBean;
import com.ejar.fastbedroom.personal.bean.UserInfoBean;
import com.ejar.fastbedroom.register.aty.ChooseSchoolAty;
import com.ejar.fastbedroom.utils.TU;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by json on 2017/8/24.
 */

public class PersonInfoAty extends BaseActivity<AtyPersoninfoBinding> {

    private AlertDialog.Builder builder;
    private UserInfoBean.DataBean userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_personinfo);
        initTitle();
        setListener();
        getPersoninfo(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPersoninfo(true);
    }

    private void getPersoninfo(boolean isLoadImg) {
//        mDialog = NetDialog.createDialog(this, "获取个人信息中...");
        NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                .getPersonInfo(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<UserInfoBean>(this, true, "个人信息获取中..") {
                    @Override
                    public void _doNext(UserInfoBean userInfoBean) {
                        if (userInfoBean.getCode().equals("200")) {
                            userInfo = userInfoBean.getData();
                            bindingView.changePersonName.setText(userInfo.getName());
                            bindingView.changePersonSex.setText(userInfo.getSex() > 1 ? "女" : "男");
                            bindingView.changePersonNumber.setText(userInfo.getTel());
                            bindingView.changePersonSchool.setText(userInfo.getAgent().getSchoolname());
                            if (!isLoadImg) {
                                Glide.with(PersonInfoAty.this).load(UrlConfig.baseUrl + userInfo.getImg())
                                        .error(R.drawable.image_user_head).into(bindingView.personImgChange);
                            }

                        } else if (userInfoBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(PersonInfoAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(userInfoBean.getMsg());
                        }
                    }
                });
    }

    private void setListener() {
        setNavigationOnClickListener(v -> {
            finish();
        });
        bindingView.personImgChange.setOnClickListener(clickListener);
        bindingView.changePersonName.setOnClickListener(clickListener);
        bindingView.logoutAccount.setOnClickListener(clickListener);
        bindingView.changePersonSchool.setOnClickListener(clickListener);
        bindingView.changePersonNumber.setOnClickListener(clickListener);
        bindingView.changePersonPwd.setOnClickListener(clickListener);
    }


    private void initTitle() {
        setTitle("个人中心");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
    }

    View.OnClickListener clickListener = v -> {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.logout_account:
                    showLogoutDialog();
                    break;
                case R.id.person_img_change:
                    intent = new Intent(PersonInfoAty.this, PhotoAty.class);
                    startActivityForResult(intent,1);
                    PersonInfoAty.this.overridePendingTransition(R.animator.aty_in,0);
                    break;
                case R.id.change_person_name:
                    intent = new Intent(PersonInfoAty.this, ChangeMessageAty.class);
                    intent.putExtra("item", 1);
                    startActivity(intent);
                    break;
                case R.id.change_person_school:
                    openNextActivity(ChooseSchoolAty.class);
                    break;
                case R.id.change_person_number:
                    intent = new Intent(PersonInfoAty.this, ChangeMessageAty.class);
                    intent.putExtra("item", 2);
                    startActivity(intent);
                    break;
                case R.id.change_person_pwd:
                    intent = new Intent(PersonInfoAty.this, ChangeMessageAty.class);
                    intent.putExtra("item", 3);
                    startActivity(intent);
                    break;
            }
    };

    private void showLogoutDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出登录?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoLogout(dialog);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    /**
     * 退出登录
     * @param dialog
     */
    private void gotoLogout(DialogInterface dialog) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                .logout(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<LogoutBean>(this, true, "退出登录中...") {
                    @Override
                    public void _doNext(LogoutBean logoutBean) {
                        if (logoutBean.getCode().equals("200")) {
                            SpUtils.put(APP.getInstance(), "token", "");
                            AppManager.removeAllAty();
                            Intent intent = new Intent(PersonInfoAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else if (logoutBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(PersonInfoAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT("" + logoutBean.getMsg());
                        }
                    }
                });
    }


    Map map = new HashMap();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:

                String path = data.getStringExtra("path");
                Glide.with(PersonInfoAty.this).load(path)
                        .error(R.drawable.image_user_head).into(bindingView.personImgChange);
                File file = new File(path);
                map.put("token", APP.token);
                // 创建 RequestBody，用于封装构建RequestBody
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part  和后端约定好Key，这里的partName是用image
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                        .changUserImg(map, body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyBaseObserver<BaseBean>(this,true,"图片上传中...") {
                            @Override
                            public void _doNext(BaseBean baseBean) {
                            }
                        });
        }

    }

}
