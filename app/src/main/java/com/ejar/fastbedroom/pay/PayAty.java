package com.ejar.fastbedroom.pay;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyPayBinding;
import com.ejar.fastbedroom.fastmail.bean.PostMailBean;
import com.ejar.fastbedroom.mystore.bean.DetailBuyBean;
import com.ejar.fastbedroom.mystore.bean.FastBuyBean;
import com.ejar.fastbedroom.pay.bean.PayResultZfb;
import com.ejar.fastbedroom.pay.bean.SignBean;
import com.ejar.fastbedroom.pay.bean.SignWxBean;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/13.
 */

public class PayAty extends BaseActivity<AtyPayBinding> {
    private PostMailBean.DataBean orderInfo;//快递
    private DetailBuyBean.DataBean detailInfo;//商品详情
    private FastBuyBean.DataBean singleInfo;//单个商品
    private List<String> codes = new ArrayList<>();
    private int payWay = -1;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI iwxapi;
    private String tag = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_pay);


        Intent intent = getIntent();
        tag = intent.getStringExtra("aty");
        Bundle bundle = new Bundle();
        initTitle();
        switch (tag) {
            case "detail"://商品详情界面购买
                Log.e("msg","detail");
                bundle = intent.getExtras();
                detailInfo = (DetailBuyBean.DataBean) bundle.getSerializable("pay_order");
                setDetailView(detailInfo.getTotalManey(), detailInfo.getSendPrice());//购物车支付界面
                bindingView.payOrderPay.setOnClickListener(clickListener1);
                break;
            case "mail"://
                Log.e("msg","mail");
                bundle = intent.getExtras();
                orderInfo = (PostMailBean.DataBean) bundle.getSerializable("orderInfo");
                setMailview();//快递支付界面
                break;
            case "single"://推荐商品 立即购买
                Log.e("msg","single");
                bundle = intent.getExtras();
                singleInfo = (FastBuyBean.DataBean) bundle.getSerializable("singleInfo");
                setDetailView(singleInfo.getTotalManey(), singleInfo.getSendPrice());
                bindingView.payOrderPay.setOnClickListener(clickListener2);
                break;
        }

    }

    /****************************Title***********************************/

    private void initTitle() {
        setTitle("填写订单");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        bindingView.payOrderZfb.setOnClickListener(clickListener);
        bindingView.payOrderWx.setOnClickListener(clickListener);
        bindingView.payOrderYe.setOnClickListener(clickListener);
    }

    /****************************快递界面***********************************/
    private void setMailview() {
        if (orderInfo == null) {
            return;
        }
        bindingView.payOrderPay.setOnClickListener(clickListener);
        bindingView.payOderTime.setVisibility(View.VISIBLE);
        bindingView.payOderTime.setText("" + orderInfo.getOrderinitial());
        bindingView.payOrderName.setText("收货人：" + orderInfo.getRevname());
        bindingView.payOrderPhone.setText("" + orderInfo.getRevtel());
        bindingView.payOrderAddress.setText("收货地址:" + orderInfo.getArea() + orderInfo.getAddress());
        bindingView.payOrderTips.setText("备注信息:" + orderInfo.getRemarks());
        bindingView.payOrderTotal.setText("￥ " + orderInfo.getPrice());
        for (int i = 0; i < orderInfo.getExpressorder().split(",").length; i++) {
            codes.add(orderInfo.getExpressorder().split(",")[i]);
        }
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, R.layout.item_goods_list, codes) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.mail_party, "" + orderInfo.getExpressname());
                holder.setText(R.id.mail_code, "取货码:" + codes.get(position));
            }
        };
        bindingView.payOrderRv.setLayoutManager(new LinearLayoutManager(PayAty.this));
        bindingView.payOrderRv.setAdapter(adapter);
    }


    View.OnClickListener clickListener = v -> {
        Drawable drawableRight = getResources().getDrawable(R.drawable.icon_pay_choose);
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        switch (v.getId()) {
            case R.id.pay_order_zfb:
                setDefaultDrawable();
                bindingView.payOrderZfb.setCompoundDrawables(null, null, drawableRight, null);
                payWay = 0x0001;
                break;
            case R.id.pay_order_wx:
                setDefaultDrawable();
                bindingView.payOrderWx.setCompoundDrawables(null, null, drawableRight, null);
                payWay = 0x0002;
                break;
            case R.id.pay_order_ye:
                setDefaultDrawable();
                bindingView.payOrderYe.setCompoundDrawables(null, null, drawableRight, null);
                payWay = 0x0003;
                break;
            case R.id.pay_order_pay://快递点击
                if (payWay == -1) {
                    TU.cT("请选择支付方式");
                } else if (payWay == 0x0001) {//支付宝
                    getOrderInfo(orderInfo.getOrderId());
                } else if (payWay == 0x0002) {//微信
                    paidByWx();
                } else if (payWay == 0x0003) {//余额
                    paidByYe(orderInfo.getOrderId());
                }
                break;

        }
    };

    /****************************自营超市***********************************/
    private void setDetailView(double totalMoney, double sendPrice) {
        String door = (String) SpUtils.get(this, "defaultAddrDoor", "");
        String name = (String) SpUtils.get(this, "defaultAddrName", "");
        String phone = (String) SpUtils.get(this, "defaultAddrTel", "");
        String area = (String) SpUtils.get(this, "defaultAddrArea", "");
        bindingView.payOderTime.setVisibility(View.GONE);

        bindingView.payOrderRv.setVisibility(View.GONE);
        bindingView.payOderTimeTop.setVisibility(View.GONE);
        bindingView.payOrderTips.setVisibility(View.GONE);
        bindingView.topView.setVisibility(View.GONE);
        bindingView.centerView.setVisibility(View.GONE);
        bindingView.payOrderName.setText("收货人：" + name);
        bindingView.payOrderPhone.setText("" + phone);
        bindingView.payOrderAddress.setText("收货地址:" + area + door);
        bindingView.payOrderTotal.setText("￥ " + totalMoney + "含运费(" + sendPrice + ")");
    }


    /**
     * 商品详情点击
     */
    View.OnClickListener clickListener1 = v -> {
        switch (v.getId()) {
            case R.id.pay_order_pay:
                if (payWay == -1) {
                    TU.cT("请选择支付方式");
                } else if (payWay == 0x0001) {//支付宝
                    getOrderInfo(detailInfo.getGoodsOrderNo());
                } else if (payWay == 0x0002) {//微信
//                    paidGoodsByWx();
                } else if (payWay == 0x0003) {//余额
//                    paidGoodsByYe(detailInfo.getGoodsOrderNo());
                }
                break;
        }
    };

    View.OnClickListener clickListener2 = v -> {
        switch (v.getId()) {
            case R.id.pay_order_pay:
                if (payWay == -1) {
                    TU.cT("请选择支付方式");
                } else if (payWay == 0x0001) {//支付宝
//                    paidGoodsByZfb();
                    getOrderInfo(singleInfo.getGoodsOrderNo());
                } else if (payWay == 0x0002) {//微信
//                    paidGoodsByWx();
                } else if (payWay == 0x0003) {//余额
//                    paidGoodsByYe(detailInfo.getGoodsOrderNo());
                }
                break;
        }
    };


//    private void paidGoodsByYe(String goodsOrderNo) {
//
//    }
//
//    private void paidGoodsByWx() {
//
//    }

//    private void paidGoodsByZfb() {
//        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
//                .getZfbSign(APP.token, detailInfo.getGoodsOrderNo())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MyBaseObserver<StoreZfbOrderSign>(this, true, "") {
//                    @Override
//                    public void _doNext(StoreZfbOrderSign storeZfbOrderSign) {
//                        if (storeZfbOrderSign.getCode().equals("200")) {
//                            Runnable authRunnable = new Runnable() {
//                                @Override
//                                public void run() {
//                                    PayTask alipay = new PayTask(PayAty.this);
//                                    Map<String, String> result = alipay.payV2(storeZfbOrderSign.getData().getSignatureorder(), true);
//                                    Message msg = new Message();
//                                    msg.what = SDK_PAY_FLAG;
//                                    msg.obj = result;
//                                    handler.sendMessage(msg);
//                                }
//                            };
//                            Thread authThread = new Thread(authRunnable);
//                            authThread.start();
//                        }
//                    }
//                });
//    }


    /**
     * 订单号去获取支付宝签名
     */
    private void getOrderInfo(String orderId) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getZfbSign(APP.token, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<SignBean>(this, true, "") {
                    @Override
                    public void _doNext(SignBean signBean) {
                        if (signBean.getCode().equals("200")) {
                            Runnable authRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(PayAty.this);
                                    Map<String, String> result = alipay.payV2(signBean.getData().getSignatureorder(), true);
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    handler.sendMessage(msg);
                                }
                            };
                            Thread authThread = new Thread(authRunnable);
                            authThread.start();

                        }
                    }
                });
    }

    /**
     * 跳转余额支付
     *
     * @param orderId
     */
    private void paidByYe(String orderId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderId", orderInfo);
        openNextActivity(PayYueAty.class, bundle);

    }

    private void paidByWx() {
        //获取微信签名
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class).getWxSign(APP.token, orderInfo.getOrderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<SignWxBean>(this, false, "") {
                    @Override
                    public void _doNext(SignWxBean signWxBean) {
                        if (signWxBean.getCode().equals("200")) {
                            gotoWxPay(signWxBean);
                        }
                    }
                });
    }

    private void gotoWxPay(SignWxBean signWxBean) {
        iwxapi = WXAPIFactory.createWXAPI(this, signWxBean.getData().getSign().getAppid(), true);
        iwxapi.registerApp(signWxBean.getData().getSign().getAppid());

        if (iwxapi != null) {
            PayReq req = new PayReq();
            req.appId = signWxBean.getData().getSign().getAppid();
            req.partnerId = signWxBean.getData().getSign().getPartnerid();
            req.prepayId = signWxBean.getData().getSign().getPrepayid();
            req.nonceStr = signWxBean.getData().getSign().getNoncestr();
            req.timeStamp = signWxBean.getData().getSign().getTimestamp();
            req.packageValue = signWxBean.getData().getSign().getPackageX();
            req.sign = signWxBean.getData().getSign().getSign();
            req.extData = "app data";
            Log.e("msg", "weixin");
            iwxapi.sendReq(req);
        }
    }


    private void setDefaultDrawable() {
        Drawable drawableRight = getResources().getDrawable(R.drawable.icon_pay_normail);
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        bindingView.payOrderZfb.setCompoundDrawables(null, null, drawableRight, null);
        bindingView.payOrderWx.setCompoundDrawables(null, null, drawableRight, null);
        bindingView.payOrderYe.setCompoundDrawables(null, null, drawableRight, null);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResultZfb payResult = new PayResultZfb((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        TU.cT("支付成功");
                        PayAty.this.finish();
//                        Toast.makeText(PayAty.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        TU.cT("支付失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

}