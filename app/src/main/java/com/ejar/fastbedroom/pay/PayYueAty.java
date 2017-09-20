package com.ejar.fastbedroom.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyYuEPayBinding;
import com.ejar.fastbedroom.fastmail.bean.PostMailBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/14.
 */

public class PayYueAty extends BaseActivity<AtyYuEPayBinding> {
    private PostMailBean.DataBean orderInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_yu_e_pay);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        orderInfo = (PostMailBean.DataBean) bundle.getSerializable("orderId");
        initTitle();
        setListener();
    }

    private void setListener() {
        bindingView.yuEPay.setOnClickListener(v -> {
            if (TextUtils.isEmpty(orderInfo.getOrderId()) || orderInfo.getOrderId().equals("")) {
                return;
            } else {
                NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                        .payByYu_e(APP.token, orderInfo.getId())
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
                                } else {
                                    bindingView.imgPayTitle.setImageResource(R.drawable.img_paid_lose);
                                    bindingView.tvPay.setText("支付失败");
                                    bindingView.yuEPay.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });

    }

    private void initTitle() {
        setTitle("余额支付");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });

        bindingView.yuePayMoney.setText("￥ " + orderInfo.getPrice());
        bindingView.paidOrderNumber.setText("" + orderInfo.getOrderId());
        bindingView.paidOderAddress.setText("收货地址：" + orderInfo.getArea() + orderInfo.getAddress());
    }
}
