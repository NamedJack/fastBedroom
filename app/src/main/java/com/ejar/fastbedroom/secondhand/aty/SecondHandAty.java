package com.ejar.fastbedroom.secondhand.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.base.BaseActivity;

/**
 * Created by json on 2017/10/9.
 */

public class SecondHandAty extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_second_hand);
        initTitle();
    }

    private void initTitle() {
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {finish();});
        setTitle("二手市场 ");
    }
}
