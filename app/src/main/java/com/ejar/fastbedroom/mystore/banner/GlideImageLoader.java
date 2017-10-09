package com.ejar.fastbedroom.mystore.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by json on 2017/8/30.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(APP.getInstance()).load(path).error(R.drawable.img_stroe_banner).into(imageView);
    }
}
