package com.ejar.fastbedroom.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyYuEPayBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.pay.bean.YuEBean;
import com.ejar.fastbedroom.utils.TU;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/14.
 */

public class PayYueAty extends BaseActivity<AtyYuEPayBinding> {
    private YuEBean yuEBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_yu_e_pay);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        yuEBean = (YuEBean) bundle.getSerializable("orderId");
        initTitle();
        setListener();
//        Log.e("msg", yuEBean.getId() + "dingdaha" + yuEBean.getTotalMoney());
    }

    private void setListener() {

        bindingView.yuEPay.setOnClickListener(v -> {
//            if (yuEBean.getId() == 0) {
//                return;
//            } else {
            if (yuEBean.getTag() == -1) {//订单号付款
                NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                        .payByYu_e(APP.token, yuEBean.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyBaseObserver<BaseBean>(this, true, "订单支付中") {
                            @Override
                            public void _doNext(BaseBean baseBean) {
                                if (baseBean.getCode().equals("200")) {
                                    bindingView.imgPayTitle.setImageResource(R.drawable.img_paid_sucess);
                                    bindingView.tvPay.setText("支付成功");
                                    bindingView.yuEPay.setVisibility(View.GONE);
                                } else if (baseBean.getCode().equals("201")) {
                                    bindingView.imgPayTitle.setImageResource(R.drawable.img_paid_lose);
                                    bindingView.tvPay.setText("支付失败");
                                    bindingView.yuEPay.setVisibility(View.GONE);
                                    TU.cT("订单已经支付");
                                } else if (baseBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                                    AppManager.removeAllAty();
                                    Intent intent = new Intent(PayYueAty.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    bindingView.imgPayTitle.setImageResource(R.drawable.img_paid_lose);
                                    bindingView.tvPay.setText("支付失败");
                                    bindingView.yuEPay.setVisibility(View.GONE);
                                    TU.cT(baseBean.getMsg() + "");
                                }
                            }
                        });
            } else if (yuEBean.getTag() == -2) {//订单编号
                NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                        .goodsPaidByYue(APP.token, yuEBean.getOrderId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyBaseObserver<BaseBean>(this, true, "订单支付中...") {
                            @Override
                            public void _doNext(BaseBean baseBean) {
                                if (baseBean.getCode().equals("200")) {
                                    bindingView.imgPayTitle.setImageResource(R.drawable.img_paid_sucess);
                                    bindingView.tvPay.setText("支付成功");
                                    bindingView.yuEPay.setVisibility(View.GONE);
                                } else if (baseBean.getCode().equals("201")) {
                                    bindingView.imgPayTitle.setImageResource(R.drawable.img_paid_lose);
                                    bindingView.tvPay.setText("支付失败");
                                    bindingView.yuEPay.setVisibility(View.GONE);
                                    TU.cT("订单已经支付");
                                } else if (baseBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                                    AppManager.removeAllAty();
                                    Intent intent = new Intent(PayYueAty.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    bindingView.imgPayTitle.setImageResource(R.drawable.img_paid_lose);
                                    bindingView.tvPay.setText("支付失败");
                                    bindingView.yuEPay.setVisibility(View.GONE);
                                    TU.cT(baseBean.getMsg() + "");
                                }
                            }
                        });
//                }




            }
        });

    }

    private void initTitle() {
        setTitle("余额支付");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });

        bindingView.yuePayMoney.setText("￥ " + yuEBean.getTotalMoney()
                + "含运费(" + yuEBean.getSendPrice() + ") 元");
        bindingView.paidOrderNumber.setText("" + yuEBean.getOrderId());
        bindingView.paidOderAddress.setText("收货地址：" + yuEBean.getArea() + yuEBean.getDoor());
    }
}
