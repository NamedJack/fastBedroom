package com.ejar.fastbedroom.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.fastbedroom.R;

/**
 * Created by json on 2017/8/22.
 */

public class MessageFrg extends BaseFragment {

    public int setContent() {
        return R.layout.frg_message;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
    }
}
