package com.ejar.fastbedroom.deliver;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.HomeAtyApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.campusdynamics.aty.CampusDynamicsAty;
import com.ejar.fastbedroom.campusdynamics.aty.CampusMessageAty;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.FrgFastBinding;
import com.ejar.fastbedroom.fastmail.FastMailAty;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mystore.StoreActivity;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.utils.TU;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/22.
 * 首页 快到寝 frg
 */

public class FastFrg extends BaseFragment<FrgFastBinding> {

    private Dialog dialog;
    private List<RvBean.DataBean> rvList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_aty_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_home_right:
                Intent intent = new Intent(getActivity(), CampusDynamicsAty.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public int setContent() {
        return R.layout.frg_fast;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getData();

    }

    private void setAutoRv(List<AutoMessageBean.DataBean> beans) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        AutoPollAdapter adapter = new AutoPollAdapter(getContext(), beans);
        adapter.setOnItemClickListener(new AutoPollAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = beans.get(position % beans.size()).getId();
                Intent intent = new Intent(getActivity(), CampusMessageAty.class);
                intent.putExtra("messageId", id);
                startActivity(intent);
            }
        });
        bindingView.autoRv.setLayoutManager(manager);
        bindingView.autoRv.setAdapter(adapter);
        bindingView.autoRv.start();
    }

    private void getData() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                .getRvList(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<RvBean>(getContext(), true, "消息获取中...") {
                    @Override
                    public void _doNext(RvBean rvBean) {
                        if (rvBean.getCode().equals("200")) {
                            rvList.addAll(rvBean.getData());
                            setView();
                        } else if (rvBean.getCode().equals("201")) {
                            TU.cT("登录失效,请重新登录");
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            AppManager.removeAllAty();
                        }
                    }
                });
        NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                .getAutoMessage(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<AutoMessageBean>(getContext(), true, "消息获取中...") {
                    @Override
                    public void _doNext(AutoMessageBean messageBean) {
                        if (messageBean.getCode().equals("200")) {
                            setAutoRv(messageBean.getData());
                        }
                    }
                });
//
    }

    /**
     * 设置列表
     */
    private void setView() {
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getActivity(), R.layout.item_message_rv, rvList) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                String time = rvList.get(position).getPostTime().split(" ")[0];
                String year = time.split("-")[0];
                String month = time.split("-")[1] + "月"
                        + time.split("-")[2] + "日";
                holder.setText(R.id.message_month, month);
                holder.setText(R.id.message_year, year);
                holder.setText(R.id.message_title, rvList.get(position).getPostTitle());
                holder.setText(R.id.message_content, rvList.get(position).getPostContent());
            }


        };
        bindingView.fastRv.setAdapter(adapter);
        bindingView.fastRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initView() {
        bindingView.fastRlShop.setOnClickListener(clickListener);
        bindingView.fastRlSecondHand.setOnClickListener(clickListener);
        bindingView.fastRlDeliver.setOnClickListener(clickListener);
        bindingView.fastRlOutBuy.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = v -> {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.fast_rl_shop: //自营超市
                intent = new Intent(getActivity(), StoreActivity.class);
                startActivity(intent);
                break;
            case R.id.fast_rl_second_hand://二手市场
                break;
            case R.id.fast_rl_deliver://快递领取
                intent = new Intent(getActivity(), FastMailAty.class);
                startActivity(intent);
                break;
            case R.id.fast_rl_out_buy://外卖配送
                break;
            default:
                break;
        }
//        startActivity(intent);
    };


}
