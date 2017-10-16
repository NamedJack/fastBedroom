package com.ejar.fastbedroom.personal.aty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ejar.fastbedroom.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * Created by json on 2017/9/20.
 */

public class PhotoAty extends TakePhotoActivity {
    private TextView photoTake, photoPick, photoCancel;

    private Uri imageUri;
    private String imagePath;
    private TakePhoto takePhoto;
    private CropOptions.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_photo);
        findId();
        initPhotoSet();
    }

    private void findId() {
        photoTake = (TextView) findViewById(R.id.photo_take);
        photoPick = (TextView) findViewById(R.id.photo_pick);
        photoCancel = (TextView) findViewById(R.id.photo_cancel);

        photoTake.setOnClickListener(clickListener);
        photoPick.setOnClickListener(clickListener);
        photoCancel.setOnClickListener(clickListener);
    }
    //图片压缩
    CompressConfig config = new CompressConfig.Builder()
            .setMaxPixel(500)
            .enableReserveRaw(true)
            .create();
    private void initPhotoSet() {
        //图片裁剪
        builder = new CropOptions.Builder();
        builder.setWithOwnCrop(false);

        TakePhotoOptions.Builder b = new TakePhotoOptions.Builder();
        b.setWithOwnGallery(true);
        b.setCorrectImage(true);//纠正拍照的照片旋转角度


        //图片路径
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + SystemClock.currentThreadTimeMillis() + ".jpg");
        if (file.exists()) file.delete();
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);
//        imageUri = FileProvider.getUriForFile(this, "com.jph.takephoto.fileprovider", file);


        takePhoto = getTakePhoto();
        takePhoto.onEnableCompress(config, false);
        takePhoto.setTakePhotoOptions(b.create());
    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.photo_take:
//                Log.e("msg", "7.0" + imageUri);
                takePhoto.onPickFromCapture(imageUri);
                break;
            case R.id.photo_pick:
                takePhoto.onPickMultiple(1);
                break;
            case R.id.photo_cancel:
                PhotoAty.this.finish();
                break;
            default:
                break;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PhotoAty.this.finish();
        return true;
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Log.e("msg","失败" + msg );
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        imagePath = result.getImage().getCompressPath();
//        Log.e("msg", "图片路径" + imagePath);
        if (!TextUtils.isEmpty(imagePath)) {
            Intent intent = new Intent();
            intent.putExtra("path", imagePath);
            setResult(RESULT_OK, intent);
            PhotoAty.this.finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, R.animator.aty_out);
    }
}
