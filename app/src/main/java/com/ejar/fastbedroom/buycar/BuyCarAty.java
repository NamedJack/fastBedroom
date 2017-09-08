package com.ejar.fastbedroom.buycar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.databinding.AtyBuyCarBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2017/8/22.
 */

public class BuyCarAty extends BaseActivity<AtyBuyCarBinding>{

    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_buy_car);
        init();
    }

    private void init() {
//        bindingView
        setTitle("购物车");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {finish();});
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, R.layout.item_rv, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }
        };
        bindingView.buyCarRv.setAdapter(adapter);
        bindingView.buyCarRv.setLayoutManager(new LinearLayoutManager(this));
    }
}
