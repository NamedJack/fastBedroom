package com.ejar.fastbedroom.mybook.frgment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.AllOrderInfoApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.FrgNotPaidBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mybook.bean.StoreOrderBean;
import com.ejar.fastbedroom.pay.PayAty;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.utils.TU;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/29.
 * 自营超市
 */

public class MyStoreFragment extends BaseFragment<FrgNotPaidBinding> {
    private MyRecyclerViewAdapter adapter;
    private int currentPage = 1;
    private int totalPage;
    private List<StoreOrderBean.DateBean.DataBean> list = new ArrayList<>();


    @Override
    public int setContent() {
        return R.layout.frg_not_paid;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String data = getArguments().getString("whichAty");
        initAdapter(data);
        switch (data) {
            case "未支付":
                getOrderList(1, currentPage);
                break;
            case "未接单":
                getOrderList(2, currentPage);
                break;
            case "已支付":
                getOrderList(3, currentPage);
                break;
            case "已完成":
                getOrderList(4, currentPage);
                break;
            default:
                break;
        }
        setLoadMore(data);
    }

    private void initAdapter(String data) {
        list.clear();
        adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_order_of_money, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.get_order_time, list.get(position).getXdTime() + "");
                holder.setText(R.id.get_order_got_name,
                        "收货人:" + list.get(position).getAddress().getReceivename());
                holder.setText(R.id.get_order_got_phone,
                        "" + list.get(position).getAddress().getReceivetel());
                holder.setText(R.id.get_order_number,
                        "订单编号：" + list.get(position).getGoodsOrderNo());
                holder.setText(R.id.get_order_send_addr,
                        "收货地址：" + list.get(position).getAddress().getReceivesite());
                holder.setText(R.id.get_order_money,
                        "￥ " + list.get(position).getTotalMoney());
                switch (data) {
                    case "未支付":
                        holder.setText(R.id.get_order_send_name, "接单人: 暂未接单");
                        holder.setOnClickListener(R.id.get_order_cancel, v -> {
                            canCelPaidOrder(list.get(position).getId(), position);
                        });
                        holder.setOnClickListener(R.id.get_order_pay, v -> {
                            payNotPayOrder(list.get(position));
                        });
                        break;
                    case "未接单":
                        holder.setText(R.id.get_order_send_name, "接单人: 暂未接单");
                        Button payBtn = holder.getView(R.id.get_order_cancel);
                        payBtn.setVisibility(View.INVISIBLE);
                        Button cancelBtn = holder.getView(R.id.get_order_pay);
                        cancelBtn.setText("取消订单");
                        holder.setOnClickListener(R.id.get_order_pay, v -> {
                            cancelPaidOrder(list.get(position).getId(), position);
                        });
                        break;
                    case "已支付":
                        holder.setText(R.id.get_order_send_name, "接单人: " + list.get(position).getCurierName());
                        Button confirmBtn = holder.getView(R.id.get_order_cancel);
                        confirmBtn.setVisibility(View.INVISIBLE);
                        holder.setText(R.id.get_order_pay, "确认收货");
                        holder.setOnClickListener(R.id.get_order_pay, v -> {
                            canfirmGotGoods(list.get(position).getId());
                        });
                        break;
                    case "已完成":
                        holder.setText(R.id.get_order_send_name, "接单人: " + list.get(position).getCurierName());
                        Button finishBtn = holder.getView(R.id.get_order_cancel);
                        finishBtn.setVisibility(View.INVISIBLE);
                        holder.setText(R.id.get_order_pay, "已完成");
                        break;
                }

            }
        };

        bindingView.rvGoodsInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        bindingView.rvGoodsInfo.setAdapter(adapter);
    }


    private void setLoadMore(String data) {
        bindingView.frgSm.setEnableLoadmore(true);
        bindingView.frgSm.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage < totalPage) {
                    currentPage++;
                    switch (data) {
                        case "未支付":
                            getOrderList(1, currentPage);
                            break;
                        case "未接单":
                            getOrderList(2, currentPage);
                            break;
                        case "已支付":
                            getOrderList(3, currentPage);
                            break;
                        case "已完成":
                            getOrderList(4, currentPage);
                            break;
                    }
                    bindingView.frgSm.finishLoadmore(true);
                } else {
                    bindingView.frgSm.finishLoadmore();
                }

            }
        });
        bindingView.frgSm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                refreshlayout.finishRefresh(2000);
            }
        });
    }

    private void getOrderList(int state, int pageNo) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .getUserOrder(APP.token, state, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<StoreOrderBean>(getContext(), true, "订单获取中...") {
                    @Override
                    public void _doNext(StoreOrderBean orderBean) {
                        if (orderBean.getCode().equals("200")) {
                            bindingView.rvGoodsInfo.setVisibility(View.VISIBLE);
                            bindingView.emptyView.setVisibility(View.GONE);
                            totalPage = orderBean.getDate().getPagetotle();
                            list.addAll(orderBean.getDate().getData());
//                            Log.e("msg", list.size() + "daxao" + orderBean.getDate().getData().size());
                            adapter.notifyDataSetChanged();
                            if (currentPage == 1 && orderBean.getDate().getData().size() == 0) {
//                                TU.cT("没有该类订单");
                                bindingView.rvGoodsInfo.setVisibility(View.GONE);
                                bindingView.emptyView.setVisibility(View.VISIBLE);
                            }
                        } else if (orderBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            AppManager.removeAllAty();
                        }
                    }
                });
    }


    /***************************未支付*************************************/

    //取消已经支付的订单
    private void canCelPaidOrder(int goodsOrderId, int position) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .storeCancelOder(APP.token, goodsOrderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(getContext(), true, "取消中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("取消成功");
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                        } else if (baseBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT("" + baseBean.getMsg());
                        }
                    }
                });
    }

    /**
     * 支付订单
     *
     * @param oderInfo
     */
    private void payNotPayOrder(StoreOrderBean.DateBean.DataBean oderInfo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("storeOrder", oderInfo);
        Intent intent = new Intent(getActivity(), PayAty.class);
        intent.putExtra("aty", "storeOrder");
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /***************************未接单*************************************/
    private void cancelPaidOrder(int id, int position) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .storeCancelOder(APP.token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(getContext(), true, "取消中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                        } else if (baseBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                            AppManager.removeAllAty();
                        } else {
                            TU.cT("" + baseBean.getMsg());
                        }
                    }
                });
    }

    /***************************待收货************************************/


    //确认收货
    private void canfirmGotGoods(int goodsOrderId) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .storeConfirmOrder(APP.token, goodsOrderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(getContext(), true, "确认中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("确认成功");
                            getOrderList(3, currentPage);
                        } else if (baseBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(baseBean.getMsg() + "");
                        }
                    }
                });
    }


    /*************************已完成*************************************/

}
