package com.ejar.fastbedroom.certification;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.personal.aty.PhotoAty;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.utils.TU;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by json on 2017/9/6.
 * 实名认证ATY
 */

public class CertificationAty extends TakePhotoActivity {
    private Uri imageUri;
    private String imagePath;
    private List<String> imagePaths = new ArrayList<>();
    private HashMap<String, String> imgs = new HashMap<>();
    private TakePhoto takePhoto;
    private File file, fileBack;

    private TextView userIdCard, userStudentCard, addPhoto, addPhotoBack;
    private ImageView userCardPhoto, userCardPhotoBack, userAgree, backIcon;
    private Button userPostInfo;
    private EditText userCardNo;


    private boolean tag = false;//是否同意协议
    private int witchCard = -1; //1：学生证 2：身份证
    private AlertDialog.Builder builder;
    private List<String> methods = new ArrayList<>();
    private AlertDialog dialog;
    private Map map = new HashMap();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_certification);
        findId();
        initTitle();
        setListener();
        initPhotoSet();
    }

    private void initPhotoSet() {
        //图片路径
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + SystemClock.currentThreadTimeMillis() + ".jpg");
        if (file.exists()) file.delete();
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);

        //图片压缩
        CompressConfig config = new CompressConfig.Builder()
                .setMaxPixel(500)
                .enableReserveRaw(true)
                .create();

        takePhoto = getTakePhoto();
        takePhoto.onEnableCompress(config, false);
    }

    private void findId() {
        backIcon = (ImageView) findViewById(R.id.take_photo_back_icon);
        userPostInfo = (Button) findViewById(R.id.user_post_info);
        userIdCard = (TextView) findViewById(R.id.user_id_card);
        userStudentCard = (TextView) findViewById(R.id.user_student_card);
        userAgree = (ImageView) findViewById(R.id.user_agree);
        addPhoto = (TextView) findViewById(R.id.photo_add);
        addPhotoBack = (TextView) findViewById(R.id.photo_add_back);
        userCardPhoto = (ImageView) findViewById(R.id.user_card_photo);
        userCardPhotoBack = (ImageView) findViewById(R.id.user_card_photo_back);
        userCardNo = (EditText) findViewById(R.id.user_card_number);

    }

    private void initTitle() {
        methods.add("从相册中获取");
        methods.add("拍照");
        methods.add("取消");
    }

    private void setListener() {
        backIcon.setOnClickListener(clickListener);
        userPostInfo.setOnClickListener(clickListener);
        userIdCard.setOnClickListener(clickListener);
        userStudentCard.setOnClickListener(clickListener);
        userAgree.setOnClickListener(clickListener);
        userCardPhoto.setOnClickListener(clickListener);
        userCardPhotoBack.setOnClickListener(clickListener);
        addPhotoBack.setOnClickListener(clickListener);
        addPhoto.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = v -> {
        Drawable drawableLeft = getResources().getDrawable(R.drawable.icon_choose);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        Intent intent = null;
        switch (v.getId()) {
            case R.id.user_id_card: // 2：身份证
                setDefaulticon();
                witchCard = 2;
                userIdCard.setCompoundDrawables(drawableLeft, null, null, null);
                break;
            case R.id.user_student_card://1：学生证
                setDefaulticon();
                witchCard = 1;
                userStudentCard.setCompoundDrawables(drawableLeft, null, null, null);
                break;
            case R.id.user_agree:
                if (tag) {
                    userAgree.setImageResource(R.drawable.icon_not_agree);
                    tag = false;
                } else {
                    userAgree.setImageResource(R.drawable.icon_choose);
                    tag = true;
                }
                break;
            case R.id.user_post_info:
                checkUserInputInfo();
                break;
            case R.id.user_card_photo:
                if (imgs.containsKey("img")) {
                    imgs.remove("img");
                    userCardPhoto.setImageResource(R.drawable.img_post);
                }


                break;
            case R.id.user_card_photo_back:
//                showChooseDialog();
                if (imgs.containsKey("imgBack")) {
                    imgs.remove("imgBack");
                    userCardPhotoBack.setImageResource(R.drawable.img_post);
                }


                break;
            case R.id.take_photo_back_icon:
                CertificationAty.this.finish();
                break;
            case R.id.photo_add:
                if (imgs.containsKey("img")) {
                    imgs.remove("img");
                }
                intent = new Intent(CertificationAty.this, PhotoAty.class);
                startActivityForResult(intent, 1);
                this.overridePendingTransition(R.animator.aty_in, 0);
                break;
            case R.id.photo_add_back:
                if (imgs.containsKey("imgBack")) {
                    imgs.remove("imgBack");
                }
                intent = new Intent(CertificationAty.this, PhotoAty.class);
                startActivityForResult(intent, 2);
                this.overridePendingTransition(R.animator.aty_in, 0);
                break;
        }
    };


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Log.e("msg", msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        imagePath = result.getImage().getCompressPath();
//        Log.e("mag", "图片路径" + imagePath);
        if (!TextUtils.isEmpty(imagePath)) {
            Intent intent = new Intent();
            intent.putExtra("path", imagePath);
            setResult(RESULT_OK, intent);
            Glide.with(this).load(imagePath).centerCrop().into(userCardPhoto);
        }
    }

    /**
     * 检测用户信息
     */
    private void checkUserInputInfo() {
        String cardNo = userCardNo.getText().toString().trim();
        if (witchCard == -1) {
            TU.cT("请选择证件类型");
            return;
        }
        if (null == cardNo || TextUtils.isEmpty(cardNo)) {
            TU.cT("请输入证件号码");
            return;
        }

        if (TextUtils.isEmpty(imagePath)) {
            TU.cT("请选择证件照");
            return;
        }
        if (!tag) {
            TU.cT("未同意二手市场买卖协议");
            return;

        }

        map.put("token", APP.token);
        map.put("img_state", witchCard);
        map.put("cardNumber", cardNo);
        List<MultipartBody.Part> fileList = new ArrayList<>();
        if (imgs.containsKey("img")) {
            file = new File(imgs.get("img"));
            // 创建 RequestBody，用于封装构建RequestBody
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            fileList.add(body);
        }
        if (imgs.containsKey("imgBack")) {
            fileBack = new File(imgs.get("imgBack"));
            RequestBody requestFileBack = RequestBody.create(MediaType.parse("multipart/form-data"), fileBack);
            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            MultipartBody.Part bodyBack =
                    MultipartBody.Part.createFormData("file", fileBack.getName(), requestFileBack);
            fileList.add(bodyBack);
        }


//        // 创建 RequestBody，用于封装构建RequestBody
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

//        RequestBody requestFileBack = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
//        MultipartBody.Part bodyBack =
//                MultipartBody.Part.createFormData("file", file.getName(), requestFileBack);
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .postCertification(map, fileList)
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
        userIdCard.setCompoundDrawables(drawableLeft, null, null, null);
        userStudentCard.setCompoundDrawables(drawableLeft, null, null, null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                String path = data.getStringExtra("path");
                imagePath = path;
                if (requestCode == 1) {
                    imgs.put("img", imagePath);
                    Glide.with(this).load(path).centerCrop().into(userCardPhoto);
                    break;
                } else if (requestCode == 2) {
                    imgs.put("imgBack", imagePath);
                    Glide.with(this).load(path).centerCrop().into(userCardPhotoBack);
                }

        }
    }
}
