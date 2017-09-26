package com.ejar.fastbedroom.mybook.frgment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.Api.AllOrderInfoApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.FrgNotPaidBinding;
import com.ejar.fastbedroom.mybook.bean.StoreOrderBean;
import com.ejar.fastbedroom.pay.PayAty;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/29.
 * 自营超市
 */

public class MyStoreFragment extends BaseFragment<FrgNotPaidBinding> {
    @Override
    public int setContent() {
        return R.layout.frg_not_paid;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String data = getArguments().getString("whichAty");
//        Log.e("msg", "aaa"+data);
        switch (data) {
            case "未支付":
                getOrderList(1);
//                initRvDataNoPay();
                break;
            case "未接单":
                getOrderList(2);
                break;
            case "已支付":
                getOrderList(3);
                break;
            case "已完成":
                getOrderList(4);
//                initRvDatafinish();
                break;
            default:
                break;

        }
    }

    private void getOrderList(int state) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .getUserOrder(APP.token, state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<StoreOrderBean>(getContext(), true, "订单获取中...") {
                    @Override
                    public void _doNext(StoreOrderBean orderBean) {
                        switch (state) {
                            case 1:
                                initRvDataNoPay(orderBean.getDate().getData());
                                break;
                            case 2:
                                initRvDataNoDeliver(orderBean.getDate().getData());
                                break;
                            case 3:
                                initRvDatahavaPay(orderBean.getDate().getData());
                                break;
                            default:
                                break;
                        }
                    }
                });
    }


    /***************************未支付*************************************/
    private void initRvDataNoPay(List<StoreOrderBean.DateBean.DataBean> list) {
        Log.e("msg", list.size() + "未支付");
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_order_of_money, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.get_order_send_name, "接单人: 暂未接单");
                holder.setText(R.id.get_order_time, list.get(position).getXdTime() + "");
                holder.setText(R.id.get_order_got_name, "收货人:" + list.get(position).getAddress().getReceivename());
                holder.setText(R.id.get_order_got_phone, "" + list.get(position).getAddress().getReceivetel());
                holder.setText(R.id.get_order_number, "订单编号：" + list.get(position).getGoodsOrderNo());
                holder.setText(R.id.get_order_send_addr, "收货地址：" + list.get(position).getAddress()
                        .getDpareaname() + list.get(position).getAddress().getReceivesite());
                holder.setText(R.id.get_order_money, "￥ " + list.get(position).getTotalMoney());
                holder.setOnClickListener(R.id.get_order_cancel, v -> {
                    canCelPaidOrder(list.get(position).getId());
                });
                holder.setOnClickListener(R.id.get_order_pay, v -> {
                    payNotPayOrder(list.get(position));
                });
            }
        };
        bindingView.rvGoodsInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        bindingView.rvGoodsInfo.setAdapter(adapter);
    }

    //取消已经支付的订单
    private void canCelPaidOrder(int goodsOrderId) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .storeCancelOder(APP.token, goodsOrderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(getContext(), true, "取消中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("取消成功");
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
    private void initRvDataNoDeliver(List<StoreOrderBean.DateBean.DataBean> list) {
        Log.e("msg", list.size() + "未接单");
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_order_of_money, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.get_order_send_name, "接单人: 暂未接单");
                holder.setText(R.id.get_order_time, list.get(position).getXdTime() + "");
                holder.setText(R.id.get_order_got_name, "收货人:" + list.get(position).getAddress().getReceivename());
                holder.setText(R.id.get_order_got_phone, "" + list.get(position).getAddress().getReceivetel());
                holder.setText(R.id.get_order_number, "订单编号：" + list.get(position).getGoodsOrderNo());
                holder.setText(R.id.get_order_send_addr, "收货地址：" + list.get(position).getAddress()
                        .getDpareaname() + list.get(position).getAddress().getReceivesite());
                holder.setText(R.id.get_order_money, "￥ " + list.get(position).getTotalMoney());
                Button payBtn = holder.getView(R.id.get_order_cancel);
                payBtn.setVisibility(View.INVISIBLE);
                Button cancelBtn = holder.getView(R.id.get_order_pay);
                cancelBtn.setVisibility(View.INVISIBLE);
            }
        };
        bindingView.rvGoodsInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        bindingView.rvGoodsInfo.setAdapter(adapter);
    }

    /***************************待收货************************************/
    private void initRvDatahavaPay(List<StoreOrderBean.DateBean.DataBean> list) {
        Log.e("msg", list.size() + "代收货");
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_order_of_money, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.get_order_send_name, "接单人: " + list.get(position).getCurierName());
                holder.setText(R.id.get_order_time, list.get(position).getXdTime() + "");
                holder.setText(R.id.get_order_got_name, "收货人:" + list.get(position).getAddress().getReceivename());
                holder.setText(R.id.get_order_got_phone, "" + list.get(position).getAddress().getReceivetel());
                holder.setText(R.id.get_order_number, "订单编号：" + list.get(position).getGoodsOrderNo());
                holder.setText(R.id.get_order_send_addr, "收货地址：" + list.get(position).getAddress()
                        .getDpareaname() + list.get(position).getAddress().getReceivesite());
                holder.setText(R.id.get_order_money, "￥ " + list.get(position).getTotalMoney());
                Button payBtn = holder.getView(R.id.get_order_cancel);
                payBtn.setVisibility(View.INVISIBLE);
                holder.setText(R.id.get_order_pay, "确认收货");
                holder.setOnClickListener(R.id.get_order_pay, v -> {
                    canfirmGotGoods(list.get(position).getId());
                });
            }
        };
        bindingView.rvGoodsInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        bindingView.rvGoodsInfo.setAdapter(adapter);
    }

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
                        } else {
                            TU.cT(baseBean.getMsg() + "");
                        }
                    }
                });
    }
}
