package com.ejar.fastbedroom.mybook.frgment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ejar.fastbedroom.utils.AppManager;
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
import com.ejar.fastbedroom.fastmail.bean.PostMailBean;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mybook.bean.MailFinishOrderBean;
import com.ejar.fastbedroom.mybook.bean.MailPayOrderBean;
import com.ejar.fastbedroom.mybook.bean.OrderBean;
import com.ejar.fastbedroom.pay.PayAty;
import com.ejar.fastbedroom.utils.TU;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/29.
 * 快递领取
 */

public class MailFragment extends BaseFragment<FrgNotPaidBinding> {

    private List<MailPayOrderBean.DataBean> paidOrderList = new ArrayList<>();
    private List<OrderBean.DataBean> notAcceptOrderList = new ArrayList<>();
    private List<OrderBean.DataBean> notPayOrderList = new ArrayList<>();
    private List<MailFinishOrderBean.DataBean> finishOrderList = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;

    @Override
    public int setContent() {
        return R.layout.frg_not_paid;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String data = getArguments().getString("whichAty");
        switch (data) {
            case "未支付":
                initRvDataNoPay();
                break;
            case "未接单":
                initRvDataNoDeliver();
                break;
            case "已支付":
                initRvDatahavaPay();
                break;
            case "已完成":
                initRvDatafinish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
//        initRvDataNoPay();
    }


    /********************未支付********************************/

    /**
     * 未支付
     */
    private void initRvDataNoPay() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .getNotPayMailOrder(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<OrderBean>(getContext(), true, "订单获取中...") {
                    @Override
                    public void _doNext(OrderBean orderBean) {
                        Log.e("msg", "快递未支付" + orderBean.getCode());
                        if (orderBean.getCode().equals("200")) {
                            notPayOrderList.clear();
                            notPayOrderList.addAll(orderBean.getData());
                            setNotPaidRv(notPayOrderList);
                        } else if (orderBean.getCode().equals("201")) {
                            bindingView.rvGoodsInfo.setVisibility(View.GONE);
                            bindingView.emptyView.setVisibility(View.VISIBLE);
                        } else if (orderBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(orderBean.getMsg() + "");
                        }
                    }
                });
    }

    private void setNotPaidRv(List<OrderBean.DataBean> list) {

        adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_order_of_money, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.get_order_send_name, "接单人:" + "暂未接单");
                holder.setText(R.id.get_order_time, "" + list.get(position).getOrderinitial());
                holder.setText(R.id.get_order_got_name, "收货人:" + list.get(position).getRevname());
                holder.setText(R.id.get_order_got_phone, "" + list.get(position).getRevtel());
                holder.setText(R.id.get_order_number, "快递公司:" + list.get(position).getExpressname());
                holder.setText(R.id.get_order_money, "￥:" + list.get(position).getPrice());
                holder.setText(R.id.get_order_send_addr,"收货地址:" + list.get(position).getArea()
                                        + list.get(position).getAddress());
                TextView mailNumber = holder.getView(R.id.get_mail_number);
                mailNumber.setVisibility(View.VISIBLE);
                mailNumber.setText("编码:" +  list.get(position).getExpressorder());
                holder.setOnClickListener(R.id.get_order_pay, v -> {
                    toPayOrder(list.get(position));
                });
                holder.setOnClickListener(R.id.get_order_cancel, v -> {
                    toCancelOrder(list.get(position).getId());
                });
            }

        };
        bindingView.rvGoodsInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.rvGoodsInfo.setAdapter(adapter);
    }


    /**
     * 取消未支付订单
     *
     * @param orderId
     */
    private void toCancelOrder(int orderId) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .userCancelNotPaidOrder(orderId, APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(getContext(), true, "订单取消中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("取消成功");
                            initRvDataNoPay();
                        } else if (baseBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(baseBean.getMsg());
                        }
                    }
                });
    }

    /**
     * 支付订单
     *
     * @param orderBean
     */
    private void toPayOrder(OrderBean.DataBean orderBean) {
        PostMailBean.DataBean mail = new PostMailBean.DataBean();
        mail.setOrderinitial(orderBean.getOrderinitial());
        mail.setRevname(orderBean.getRevname());
        mail.setRevtel(orderBean.getRevtel());
        mail.setArea(orderBean.getArea());
        mail.setAddress(orderBean.getAddress());
        mail.setRemarks(orderBean.getRemarks());
        mail.setPrice(orderBean.getPrice());
        mail.setExpressname(orderBean.getExpressname());
        mail.setExpressorder(orderBean.getExpressorder());
        mail.setOrderId(orderBean.getOrderId());
        mail.setId(orderBean.getId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderInfo", mail);
        Intent intent =  new Intent(getActivity(), PayAty.class);
        intent.putExtra("aty","mail");
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /********************未接单********************************/

    /**
     * 未接单
     */
    private void initRvDataNoDeliver() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .getNotSendOrder(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<OrderBean>(getActivity(), true, "订单获取中...") {
                    @Override
                    public void _doNext(OrderBean orderBean) {
                        if (orderBean.getCode().equals("200")) {
                            notAcceptOrderList.clear();
                            notAcceptOrderList.addAll(orderBean.getData());
                            setNotAcceptRv(notAcceptOrderList);
                        } else if (orderBean.getCode().equals("201")) {
                            bindingView.rvGoodsInfo.setVisibility(View.GONE);
                            bindingView.emptyView.setVisibility(View.VISIBLE);
                        } else if (orderBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(orderBean.getMsg() + "");
                        }
                    }
                });
    }

    private void setNotAcceptRv(List<OrderBean.DataBean> list) {
        adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_order_of_money, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.get_order_send_name, "接单人:" + "暂未接单");
                holder.setText(R.id.get_order_time, "" + list.get(position).getOrderTime());
                holder.setText(R.id.get_order_got_name, "收货人:" + list.get(position).getRevname());
                holder.setText(R.id.get_order_got_phone, "" + list.get(position).getRevtel());
                holder.setText(R.id.get_order_number, "订单编号:" + list.get(position).getOrderId());
                holder.setText(R.id.get_order_money, "￥:" + list.get(position).getPrice());
                TextView cancelBtn = holder.getView(R.id.get_order_cancel);
                cancelBtn.setVisibility(View.INVISIBLE);
                holder.setText(R.id.get_order_pay, "取消订单");
                holder.setOnClickListener(R.id.get_order_pay, v -> {
                    toCancelPaidOrder(position, list.get(position).getId());
                });
            }

        };
        bindingView.rvGoodsInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.rvGoodsInfo.setAdapter(adapter);
    }

    private void toCancelPaidOrder(int position, int id) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .userCancelPaidOrder(id, APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(getContext(), true, "订单取消中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("取消成功");
                            initRvDataNoDeliver();
//                            notAcceptOrderList.remove(position);
//                            adapter.notifyItemChanged(position);
//                            adapter.notifyItemRangeChanged(0, notAcceptOrderList.size());
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


    /********************已支付********************************/

    private void initRvDatahavaPay() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .getUserHaveOrder(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<MailPayOrderBean>(getActivity(), true, "订单获取中...") {
                    @Override
                    public void _doNext(MailPayOrderBean orderBean) {
                        if (orderBean.getCode().equals("200")) {
                            paidOrderList.clear();
                            paidOrderList.addAll(orderBean.getData());
                            setPaidRv(paidOrderList);
                        } else if (orderBean.getCode().equals("201")) {
                            bindingView.rvGoodsInfo.setVisibility(View.GONE);
                            bindingView.emptyView.setVisibility(View.VISIBLE);
                        } else if (orderBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(orderBean.getMsg() + "");
                        }
                    }
                });

    }

    /**
     * 已支付订单列表
     *
     * @param list
     */
    private void setPaidRv(List<MailPayOrderBean.DataBean> list) {
//        Log.e("msg", "已支付fff" + list.size());
        adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_order_of_money, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.get_order_send_name, "接单人:" + list.get(position).getUser().getName());
                holder.setText(R.id.get_order_time, "" + list.get(position).getOrderTime());
                holder.setText(R.id.get_order_got_name, "收货人:" + list.get(position).getRevname());
                holder.setText(R.id.get_order_got_phone, "" + list.get(position).getRevtel());
                holder.setText(R.id.get_order_number, "订单编号:" + list.get(position).getOrderId());
                holder.setText(R.id.get_order_money, "￥:" + list.get(position).getPrice());
                TextView cancelBtn = holder.getView(R.id.get_order_cancel);
                cancelBtn.setVisibility(View.INVISIBLE);
                holder.setText(R.id.get_order_pay, "确认收货");
                holder.setOnClickListener(R.id.get_order_pay, v -> {
                    toConfirmGetGoods(position, list.get(position).getId());
                });
            }

        };
        bindingView.rvGoodsInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.rvGoodsInfo.setAdapter(adapter);
    }

    /**
     * 确认收货
     *
     * @param position
     * @param orderId
     */
    private void toConfirmGetGoods(int position, int orderId) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .userConfirmOrder(orderId, APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(getContext(), true, "确认收货中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("确认收货成功");
                            initRvDatahavaPay();
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

    /********************已完成********************************/
    private void initRvDatafinish() {

        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .mailFinishOrder(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<MailFinishOrderBean>(getActivity(), true, "订单获取中...") {
                    @Override
                    public void _doNext(MailFinishOrderBean orderBean) {
                        if (orderBean.getCode().equals("200")) {
                            finishOrderList.clear();
                            finishOrderList.addAll(orderBean.getData());
                            setFinishRv(finishOrderList);
                        } else if (orderBean.getCode().equals("201")) {
                            bindingView.rvGoodsInfo.setVisibility(View.GONE);
                            bindingView.emptyView.setVisibility(View.VISIBLE);
                        } else if (orderBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(orderBean.getMsg() + "");
                        }
                    }
                });
    }

    private void setFinishRv(List<MailFinishOrderBean.DataBean> list) {
        adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_order_of_money, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.get_order_send_name, "接单人:" + list.get(position).getUser().getName());
                holder.setText(R.id.get_order_time, "" + list.get(position).getOrderTime());
                holder.setText(R.id.get_order_got_name, "收货人:" + list.get(position).getRevname());
                holder.setText(R.id.get_order_got_phone, "" + list.get(position).getRevtel());
                holder.setText(R.id.get_order_number, "订单编号:" + list.get(position).getOrderId());
                holder.setText(R.id.get_order_money, "￥:" + list.get(position).getPrice());
                TextView cancelBtn = holder.getView(R.id.get_order_cancel);
                cancelBtn.setVisibility(View.INVISIBLE);
                holder.setText(R.id.get_order_pay, "已完成");
//                holder.setOnClickListener(R.id.get_order_pay, v -> {
//                    toConfirmGetGoods(position, list.get(position).getId());
//                });
            }
        };
        bindingView.rvGoodsInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.rvGoodsInfo.setAdapter(adapter);
    }
}
