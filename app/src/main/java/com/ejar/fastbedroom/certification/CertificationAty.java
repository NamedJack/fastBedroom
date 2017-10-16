package com.ejar.fastbedroom.certification;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyCertificationBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.personal.aty.PhotoAty;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.utils.TU;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by json on 2017/9/6.
 * 实名认证ATY
 */

public class CertificationAty extends BaseActivity<AtyCertificationBinding> {
    private String imagePathFirst = "", imagePathSecond = "";
    private File fileFirst, fileSecond;
    private boolean tag = false;//是否同意协议
    private int witchCard = -1; //1：学生证 2：身份证

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_certification);
        initTitle();
        setListener();
    }




    private void initTitle() {
        setTitle("实名认证");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });

    }

    private void setListener() {
        bindingView.userPostInfo.setOnClickListener(clickListener);
        bindingView.userIdCard.setOnClickListener(clickListener);
        bindingView.userStudentCard.setOnClickListener(clickListener);
        bindingView.userAgree.setOnClickListener(clickListener);
        bindingView.userCardPhoto.setOnClickListener(clickListener);
        bindingView.userCardPhotoBack.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = v -> {
        Drawable drawableLeft = getResources().getDrawable(R.drawable.icon_choose);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        Intent intent = null;
        switch (v.getId()) {
            case R.id.user_id_card: // 2：身份证
                setDefaulticon();
                witchCard = 2;
                bindingView.userIdCard.setCompoundDrawables(drawableLeft, null, null, null);
                break;
            case R.id.user_student_card://1：学生证
                setDefaulticon();
                witchCard = 1;
                bindingView.userStudentCard.setCompoundDrawables(drawableLeft, null, null, null);
                break;
            case R.id.user_agree:
                if (tag) {
                    bindingView.userAgree.setImageResource(R.drawable.icon_not_agree);
                    tag = false;
                } else {
                    bindingView.userAgree.setImageResource(R.drawable.icon_choose);
                    tag = true;
                }
                break;
            case R.id.user_post_info:
                checkUserInputInfo();
                break;
            case R.id.user_card_photo:
                intent = new Intent(CertificationAty.this, PhotoAty.class);
                startActivityForResult(intent, 1);
                this.overridePendingTransition(R.animator.aty_in, 0);
                break;
            case R.id.user_card_photo_back:
                intent = new Intent(CertificationAty.this, PhotoAty.class);
                startActivityForResult(intent, 2);
                this.overridePendingTransition(R.animator.aty_in, 0);
                break;
        }
    };





    /**
     * 检测用户信息
     */
    private void checkUserInputInfo() {
        String cardNo = bindingView.userCardNumber.getText().toString().trim();
        if (witchCard == -1) {
            TU.cT("请选择证件类型");
            return;
        }
        if (null == cardNo || TextUtils.isEmpty(cardNo)) {
            TU.cT("请输入证件号码");
            return;
        }

        if (TextUtils.isEmpty(imagePathFirst) && TextUtils.isEmpty(imagePathSecond)) {
            TU.cT("请选择证件照");
            return;
        }
        if (!tag) {
            TU.cT("未同意二手市场买卖协议");
            return;

        }
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", APP.token)
                .addFormDataPart("img_state", witchCard + "")
                .addFormDataPart("cardNumber", cardNo);

        if(TextUtils.isEmpty(imagePathFirst) && !TextUtils.isEmpty(imagePathSecond) ){
            fileSecond = new File(imagePathSecond);
            builder.addFormDataPart("fileSecond", fileSecond.getName(), RequestBody.create(MediaType.parse("image/*"), fileSecond));
        }
        if(!TextUtils.isEmpty(imagePathFirst) && TextUtils.isEmpty(imagePathSecond)){
            fileFirst = new File(imagePathFirst);
            builder.addFormDataPart("fileFirst", fileFirst.getName(), RequestBody.create(MediaType.parse("image/*"), fileFirst));
        }
        if(!TextUtils.isEmpty(imagePathFirst) && !TextUtils.isEmpty(imagePathSecond)){
            fileFirst = new File(imagePathFirst);
            fileSecond = new File(imagePathSecond);
            builder.addFormDataPart("fileFirst", fileFirst.getName(), RequestBody.create(MediaType.parse("image/*"), fileFirst));
            builder.addFormDataPart("fileSecond", fileSecond.getName(), RequestBody.create(MediaType.parse("image/*"), fileSecond));
        }

        RequestBody body = builder.build();
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .postCertification(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, true, "提交认证中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("提交审核成功");
                            CertificationAty.this.finish();
                        } else if (baseBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            Intent intent = new Intent(CertificationAty.this, LoginActivity.class);
                            startActivity(intent);
                            AppManager.removeAllAty();
                        } else {
                            TU.cT(baseBean.getMsg() + "");
                        }
                    }
                });

    }

    private void setDefaulticon() {
        Drawable drawableLeft = getResources().getDrawable(R.drawable.icon_default_choose);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        bindingView.userIdCard.setCompoundDrawables(drawableLeft, null, null, null);
        bindingView.userStudentCard.setCompoundDrawables(drawableLeft, null, null, null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                String path = data.getStringExtra("path");
                if (requestCode == 1) {
                    imagePathFirst = path;
                    Glide.with(this).load(imagePathFirst).centerCrop().into(bindingView.userCardPhoto);
                    break;
                } else if (requestCode == 2) {
                    imagePathSecond = path;
                    Glide.with(this).load(imagePathSecond).centerCrop().into(bindingView.userCardPhotoBack);
                }
        }
    }
}
