package com.ejar.fastbedroom.personal.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyMoneyDetailBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.personal.bean.MoneyDetailBean;
import com.ejar.fastbedroom.personal.bean.TypeBean;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.utils.TimeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/30.
 * 收支明细
 */

public class MoneyDetailAty extends BaseActivity<AtyMoneyDetailBinding> {
    private ArrayList<TypeBean> monthList = new ArrayList<TypeBean>();
    private ArrayList<TypeBean> yearList = new ArrayList<TypeBean>();
    private int year, month;
    private List<MoneyDetailBean.DataBean> moneyDetailList = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;
    private int totalPage, currentPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_money_detail);
        initTitle();
        initAdapter();
        setListener();
        getMoneyInfo(currentPage);

    }

    private void initAdapter() {
        adapter = new MyRecyclerViewAdapter(this, R.layout.item_money, moneyDetailList) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                ImageView iv = holder.getView(R.id.img);
                int state = moneyDetailList.get(position).getState();

                if (state == 2 || state == 3 || state == 5 || state == 6) {
                    iv.setImageResource(R.drawable.img_bule);
                    holder.setText(R.id.money_state, "收入");
                    holder.setText(R.id.custom_money_number, "+ " + moneyDetailList.get(position).getGrade());
                } else {
                    holder.setText(R.id.money_state, "支出");
                    holder.setText(R.id.custom_money_number, "- " + moneyDetailList.get(position).getGrade());
                }
                holder.setText(R.id.money_custom_time, moneyDetailList.get(position).getEltime() + "");
            }
        };
        bindingView.detailRv.setLayoutManager(new LinearLayoutManager(this));
        bindingView.detailRv.setAdapter(adapter);
    }

    private void setListener() {
        bindingView.chooseYear.setOnClickListener(clickListener);
        bindingView.chooseMonth.setOnClickListener(clickListener);
        bindingView.detailSw.setEnableLoadmore(true);
        bindingView.detailSw.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                moneyDetailList.clear();
                getMoneyInfo(currentPage);
                bindingView.detailSw.finishRefresh();
            }
        });
        bindingView.detailSw.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(currentPage < totalPage){
                    currentPage++;
                    getMoneyInfo(currentPage);
                    bindingView.detailSw.finishLoadmore();
                }else {
                    bindingView.detailSw.finishLoadmore();
                }
            }
        });
    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.choose_year:
                showChooseYear();
                break;
            case R.id.choose_month:
                showChooseMonth();
                break;
        }
    };

    private void showChooseYear() {
        TimeUtils.alertBottomWheelOption(MoneyDetailAty.this, yearList, new TimeUtils.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                year = yearList.get(postion).getId();
                bindingView.chooseYear.setText(yearList.get(postion).getId() + "");
                moneyDetailList.clear();
                currentPage = 1;
                getMoneyInfo(currentPage);
            }
        });
    }

    private void showChooseMonth() {
        TimeUtils.alertBottomWheelOption(MoneyDetailAty.this, monthList, new TimeUtils.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                month = monthList.get(postion).getId();
                bindingView.chooseMonth.setText(monthList.get(postion).getName());
                bindingView.showTime.setText(monthList.get(postion).getName());
                moneyDetailList.clear();
                currentPage = 1;
                getMoneyInfo(currentPage);
            }
        });
    }


    private void initTitle() {
        setTitle("收支明细");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1; // 0~11
        bindingView.chooseYear.setText(year + "");
        bindingView.chooseMonth.setText(month + "月");
        bindingView.showTime.setText(month + "月");
        for (int i = 1; i <= 12; i++) {
            monthList.add(new TypeBean(i, i + "月"));
        }
        for (int i = 2016; i <= 2018; i++) {
            yearList.add(new TypeBean(i, i + "年"));
        }
    }

    public void getMoneyInfo(int page) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getMoneyDetail(APP.token, year, month, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<MoneyDetailBean>(this, true, "明细获取中...") {
                    @Override
                    public void _doNext(MoneyDetailBean moneyDetailBean) {
                        if (moneyDetailBean.getCode().equals("200")) {
//                            moneyDetailList.clear();
                            totalPage = moneyDetailBean.getTotalPage();
                            moneyDetailList.addAll(moneyDetailBean.getData());
                            if(moneyDetailBean.getData().size() == 0 && currentPage == 1){
                                bindingView.detailRv.setVisibility(View.GONE);
                                bindingView.moneyEmptyView.setVisibility(View.VISIBLE);
                            }else {
                                bindingView.detailRv.setVisibility(View.VISIBLE);
                                bindingView.moneyEmptyView.setVisibility(View.GONE);
                            }
                            adapter.notifyDataSetChanged();
                        } else if (moneyDetailBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            openNextActivity(LoginActivity.class);
                        }
                    }
                });
    }

//    private void setRvList() {
//        if(moneyDetailList.size() == 0){
//            bindingView.detailRv.setVisibility(View.GONE);
//            bindingView.moneyEmptyView.setVisibility(View.VISIBLE);
//            return;
//        }
//        adapter = new MyRecyclerViewAdapter(this, R.layout.item_money, moneyDetailList) {
//            @Override
//            public void convert(MyViewHolder holder, int position) {
//                ImageView iv = holder.getView(R.id.img);
//                int state = moneyDetailList.get(position).getState();
//
//                if (state == 2 || state == 3 || state == 5 || state == 6) {
//                    iv.setImageResource(R.drawable.img_bule);
//                    holder.setText(R.id.money_state, "收入");
//                    holder.setText(R.id.custom_money_number, "+ " + moneyDetailList.get(position).getGrade());
//                } else {
//                    holder.setText(R.id.money_state, "支出");
//                    holder.setText(R.id.custom_money_number, "- " + moneyDetailList.get(position).getGrade());
//                }
//                holder.setText(R.id.money_custom_time, moneyDetailList.get(position).getEltime() + "");
//            }
//        };
//        bindingView.detailRv.setLayoutManager(new LinearLayoutManager(this));
//        bindingView.detailRv.setAdapter(adapter);
//    }

}
