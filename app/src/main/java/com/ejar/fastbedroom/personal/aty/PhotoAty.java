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

import com.bumptech.glide.Glide;
import com.ejar.fastbedroom.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;

/**
 * Created by json on 2017/9/20.
 */

public class PhotoAty extends TakePhotoActivity {
    private TextView photoTake, photoPick, photoCancel;

    private Uri imageUri;
    private String imagePath;
    private TakePhoto takePhoto;

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

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.photo_take:
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
        Log.e("msg", msg );
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
            PhotoAty.this.finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, R.animator.aty_out);
    }
}
