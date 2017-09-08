package com.ejar.fastbedroom.fastmail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.databinding.AtyFastMailBinding;

/**
 * Created by json on 2017/8/28.
 * 快递领取
 *
 */

public class FastMailAty extends BaseActivity<AtyFastMailBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_fast_mail);
        initTitle();
    }

    private void initTitle() {
        setTitle("自营超市");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {finish();});
    }
}
