package com.ejar.fastbedroom.mybook.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.databinding.FrgNotPaidBinding;

/**
 * Created by json on 2017/8/29.
 * 外卖配送
 */

public class OutSentFragment extends BaseFragment<FrgNotPaidBinding> {
    @Override
    public int setContent() {
        return R.layout.frg_not_paid;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindingView.rvGoodsInfo.setVisibility(View.GONE);
        bindingView.emptyView.setVisibility(View.VISIBLE);
    }
}
