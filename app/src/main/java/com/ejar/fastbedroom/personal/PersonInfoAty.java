package com.ejar.fastbedroom.personal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ejar.baseframe.base.aty.AppManager;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.net.NetWork;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.camera.CameraActivity;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyPersoninfoBinding;
import com.ejar.fastbedroom.home.HomeAtyApi;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.personal.adapter.MyListAdaper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/24.
 */

public class PersonInfoAty extends BaseActivity<AtyPersoninfoBinding> {

    private static final int TACK_PICTURE = 0x0001;//拍照
    private static final int TACK_SD_IMG = 0x0002;//相册获取
    private static final int CROP_TACK_PICTURE = 0x0003;//拍照裁剪
    private static final int CROP_TACK_SD_IMG = 0x0004;//相册获取裁剪
    private AlertDialog.Builder builder;
    private List<String> methods = new ArrayList<>();
    private AlertDialog dialog;
    private Dialog mDialog;//网络请求时dialog

    private Uri imageUri;
    private String sdPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_personinfo);
        initData();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_center, menu);
        setMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.save_info:
                        showSaveinfoDialog();
                        break;
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 点击保存按钮
     */
    private void showSaveinfoDialog() {
        TU.cT("baocun");
    }

    private void initData() {
        methods.add("从相册中获取");
        methods.add("拍照");
        methods.add("取消");

        sdPath = Environment.getExternalStorageDirectory().getPath();
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String picPath = sdPath + "/DCIM/Camera/" + timeStamp + "_" + (int) Math.floor(Math.random() * 10000 + 10000) + ".png";
        imageUri = Uri.fromFile(new File(picPath));
    }

    private void init() {
        setTitle("个人中心");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        bindingView.changePersonImg.setOnClickListener(v -> {
            showChooseDialog();
        });
        bindingView.logoutAccount.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.logout_account:
                    showLogoutDialog();
                    break;
            }
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
    }

    private void gotoLogout(DialogInterface dialog) {
        mDialog = NetDialog.createDialog(this,"退出中...");
        NetWork.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                .logout(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LogoutBean logoutBean) {
                        NetDialog.closeDialog(mDialog);
                        dialog.dismiss();
                        if(logoutBean.getCode().equals("200")){
                            SpUtils.put(APP.getInstance(),"token","");
                            AppManager.removeAllAty();
                            Intent intent = new Intent(PersonInfoAty.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            TU.cT("" + logoutBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetDialog.closeDialog(mDialog);
                        dialog.dismiss();
                        TU.cT("退出失败");
                    }

                    @Override
                    public void onComplete() {
                        NetDialog.closeDialog(mDialog);
                        dialog.dismiss();
                        TU.cT("退出成功");
                    }
                });
    }

    /**
     * 更换头像图片
     */
    private void showChooseDialog() {
        builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.choose_dialog, null, false);
        ListView lv = (ListView) view.findViewById(R.id.choose_method);
        MyListAdaper adaper = new MyListAdaper(methods, this);
        lv.setAdapter(adaper);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        openMethod(0);
                        break;
                    case 1:
                        openMethod(1);
                        break;
                    case 2:
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.create();
        dialog = builder.show();
    }

    /**
     * 0：相册
     * 1：拍照
     * @param i
     */
    public void openMethod(int i) {
        Log.e("msg",i + "");
        Intent intent = null;
        switch (i) {
            case 0://   相册获取
                Log.e("msg", "相册");
                intent = new Intent(this, CameraActivity.class);
                startActivityForResult(intent, TACK_SD_IMG);
                break;
            case 1://拍照
                Log.e("msg", "拍照");
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TACK_PICTURE);
                break;
            default:
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TACK_PICTURE:
                cropImageUri(imageUri, 800, 800, CROP_TACK_PICTURE);
                break;
            case TACK_SD_IMG:
                cropImageUri(data.getData(), 800, 800, CROP_TACK_SD_IMG);
                break;
            case CROP_TACK_PICTURE:
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    bindingView.personImg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case CROP_TACK_SD_IMG:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //是否裁剪
        intent.putExtra("crop", "true");
        //设置xy的裁剪比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置输出的宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //是否缩放
        intent.putExtra("scale", false);
        //输入图片的Uri，指定以后，可以在这个uri获得图片
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //是否返回图片数据，可以不用，直接用uri就可以了
        intent.putExtra("return-data", false);
        //设置输入图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //是否关闭面部识别
        intent.putExtra("noFaceDetection", true); // no face detection
        //启动
        startActivityForResult(intent, requestCode);
    }



}
