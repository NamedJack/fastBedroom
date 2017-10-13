package com.ejar.fastbedroom.campusdynamics.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.fastbedroom.campusdynamics.bean.CampusHeadImg;
import com.ejar.fastbedroom.campusdynamics.bean.CampusMessageBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyCampusBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.utils.TU;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/10/9.
 * 校园动态aty
 */

public class CampusDynamicsAty extends BaseActivity<AtyCampusBinding> {
    private List<CampusMessageBean.DataBean.DynamicsBean> messageBeans = new ArrayList<>();
    private RecyclerView recycleView;
    private SmartRefreshLayout refreshLayout;
    private MyRecyclerViewAdapter adapter;
    private String imgUrl = "";
    private int currentPage = 1;
    private int totalPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_campus);
        initTitle();
        getData(currentPage);
    }

    private void getData(int page) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getCampusImg(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<CampusHeadImg>(this, false, "") {
                    @Override
                    public void _doNext(CampusHeadImg img) {
                        if (img.getCode().equals("200")) {
                            imgUrl = img.getData().getImage();
                        }
                        loadImg(imgUrl);
                    }
                });
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getCampusMessage(APP.token, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<CampusMessageBean>(this, false, "") {
                    @Override
                    public void _doNext(CampusMessageBean messageBean) {
                        if (messageBean.getCode().equals("200")) {
                            messageBeans.addAll(messageBean.getData().getDynamics());
                            totalPage = messageBean.getData().getPagetotle();
                            setRvList();
                        } else if (messageBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            openNextActivity(LoginActivity.class);
                            AppManager.removeAllAty();
                        } else {
                            TU.cT("" + messageBean.getMsg());
                        }
                    }
                });


    }

    private void loadImg(String imgUrl) {
        String url = UrlConfig.baseUrl + imgUrl;
        Glide.with(this).load(url).error(R.drawable.campus_head_img)
                .into(bindingView.campusHeadImg);
    }

    private void setRvList() {
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        recycleView = (RecyclerView) findViewById(R.id.campus_rv);
        LinearLayoutManager ll = new LinearLayoutManager(this);
        recycleView = (RecyclerView) findViewById(R.id.campus_rv);
        adapter = new MyRecyclerViewAdapter(this, R.layout.item_fast_rv, messageBeans) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                String time = messageBeans.get(position).getTime().split(" ")[0];
                String year = time.split("-")[0];
                String month = time.split("-")[1] + "月"
                        + time.split("-")[2] + "日";
                holder.setText(R.id.fast_month, month);
                holder.setText(R.id.fast_year, year);
                holder.setText(R.id.fast_title,  messageBeans.get(position).getTitle());
                holder.setText(R.id.fast_content, messageBeans.get(position).getSubtitle());
            }
        };

        recycleView.setLayoutManager(ll);
        recycleView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage < totalPage) {
                    currentPage++;
                    getData(currentPage);
                    adapter.notifyDataSetChanged();
                    refreshlayout.finishLoadmore(true);
                } else {
                    refreshlayout.finishLoadmore();
                }
            }
        });
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                Intent intent = new Intent(CampusDynamicsAty.this, CampusMessageAty.class);
                intent.putExtra("messageId", messageBeans.get(position).getId());
                startActivity(intent);
            }

            @Override
            public boolean onItemLonClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                return false;
            }
        });
    }

    private void initTitle() {
        setTitle("校园动态");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.campus_sm);
    }
}
