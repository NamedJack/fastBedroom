package com.ejar.fastbedroom.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.fastbedroom.utils.TU;
import com.ejar.fastbedroom.Api.StoreApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.bean.PayOrder;
import com.ejar.fastbedroom.buycar.bean.PostGoodsBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyConfirmBuyGoodsBinding;
import com.ejar.fastbedroom.mystore.bean.ConfirmBuyGoodsDetail;
import com.ejar.fastbedroom.mystore.bean.FastBuyBean;
import com.ejar.fastbedroom.mystore.bean.RowBean;
import com.ejar.fastbedroom.pay.PayAty;
import com.ejar.fastbedroom.useraddr.UserAddrAty;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/21.
 */

public class ConfirmBuyGoodsAty extends BaseActivity<AtyConfirmBuyGoodsBinding> {
    private String userDoor, userTel, userName, userArea;
    private int userAreaId, goodsNumber;
    private String money;
    private MyRecyclerViewAdapter adapter;
    private List<ConfirmBuyGoodsDetail> detailGoodList = new ArrayList<>();
    private ConfirmBuyGoodsDetail detailGood;
    private int goodsAty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_confirm_buy_goods);
        initTitle();
        setListener();
        Intent intent = getIntent();
        goodsAty = intent.getIntExtra("goodAty", -1);
        if (goodsAty == 1) {
            Bundle bundle = new Bundle();
            bundle = intent.getExtras();
            detailGood = (ConfirmBuyGoodsDetail) bundle.getSerializable("goodsDetail");
            goodsNumber = detailGood.getNumber();
            detailGoodList.add(detailGood);
            initData(detailGoodList);
            getTotalMoney(detailGoodList);
        } else if (goodsAty == 2) {
            List<RowBean> post = new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    post.addAll(DataSupport.where("bookNumber > ?", "0")
                            .find(RowBean.class));
                    for (int i = 0; i < post.size(); i++) {
                        ConfirmBuyGoodsDetail goodsDetail = new ConfirmBuyGoodsDetail();
                        goodsDetail.setImg(post.get(i).getImg());
                        goodsDetail.setPrice(post.get(i).getShopPrice());
                        goodsDetail.setNumber(post.get(i).getBookNumber());
                        goodsDetail.setId(post.get(i).getGoodsId());
                        goodsDetail.setName(post.get(i).getName());
                        detailGoodList.add(goodsDetail);
                    }
                    handler.sendEmptyMessage(1);
                }
            }).start();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData(detailGoodList);
                    getTotalMoney(detailGoodList);
                    break;
            }
        }
    };


    private void getTotalMoney(List<ConfirmBuyGoodsDetail> list) {

        BigDecimal allMoeney = new BigDecimal(0d);

        for (int i = 0; i < list.size(); i++) {
            BigDecimal singlePrice = new BigDecimal(list.get(i).getPrice());
            allMoeney = allMoeney.add(singlePrice.multiply(new BigDecimal(list.get(i).getNumber())));
        }
        money = allMoeney.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        bindingView.goodsDetailTotalMoney.setText("合计：" + money + "元");
    }


    @Override
    protected void onResume() {
        super.onResume();
        userTel = (String) SpUtils.get(this, "defaultAddrTel", "");
        userArea = (String) SpUtils.get(this, "defaultAddrArea", "");
        userName = (String) SpUtils.get(this, "defaultAddrName", "");
        userDoor = (String) SpUtils.get(this, "defaultAddrDoor", "");
        userAreaId = (int) SpUtils.get(this, "defaultUserAddrId", -1);

        bindingView.userAddre.setText(userArea + userDoor);
        bindingView.userTel.setText(userTel);
        if (!TextUtils.isEmpty(userName) || userName.equals("")) {
            bindingView.userName.setText(userName + " 先生");
        }

    }

    private void initData(List<ConfirmBuyGoodsDetail> list) {

        bindingView.confirmGoodsList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, R.layout.item_confirm_buy_goods, list) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.confirm_goods_name, list.get(position).getName());
                holder.setText(R.id.confirm_goods_number, "X " + list.get(position).getNumber());
                holder.setText(R.id.confirm_goods_price, "￥" + list.get(position).getPrice());
                ImageView iv = holder.getView(R.id.confirm_goods_img);
                Glide.with(ConfirmBuyGoodsAty.this).load(UrlConfig.baseUrl + list.get(position).getImg())
                        .error(R.drawable.defult_img).into(iv);
            }
        };
        bindingView.confirmGoodsList.setAdapter(adapter);

    }

    private void setListener() {
        bindingView.topUserAddress.setOnClickListener(clickListener);
        bindingView.postOrder.setOnClickListener(clickListener);
    }

    private void initTitle() {
        setTitle("提交订单");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
    }


    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.top_user_address:
                openNextActivity(UserAddrAty.class);
                break;
            case R.id.post_order:
                if (detailGoodList.size() <= 0) {
                    TU.cT("没有商品不能提交订单");
                    return;
                }
                if (goodsAty == 1) {//单个商品提交
                    toPostOrderAndPay();
                } else if (goodsAty == 2) {//多个商品提交
                    DataSupport.deleteAll(RowBean.class);
                    toPostOrderCar();
                }
                break;
        }
    };

    private void toPostOrderCar() {
        Gson gson = new Gson();
        List<PostGoodsBean> postList = new ArrayList<>();
        for (int i = 0; i < detailGoodList.size(); i++) {
            PostGoodsBean post = new PostGoodsBean();
            post.setGoodsid(detailGoodList.get(i).getId());
            post.setNumber(detailGoodList.get(i).getNumber());
            postList.add(post);
        }
        String goodsJson = gson.toJson(postList);

        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .multiGoodsOrder(APP.token, goodsJson, money, userAreaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<PayOrder>(this, true, "订单生成中...") {
                    @Override
                    public void _doNext(PayOrder payOrder) {
                        if (payOrder.getCode().equals("200")) {
                            Intent intent = new Intent(ConfirmBuyGoodsAty.this, PayAty.class);
                            intent.putExtra("aty", "buyCar");
                            Bundle itemBundle = new Bundle();
                            itemBundle.putSerializable("buyCar", payOrder.getData());
                            intent.putExtras(itemBundle);
                            startActivity(intent);
                        } else {
//                            TU.cT("" + payOrder.getMsg());
                        }
                    }
                });

    }


    /**
     * 生成订单号。跳转到支付界面
     */
    private void toPostOrderAndPay() {
//        Log.e("msg",goodsId + " " +goodsPrice);
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .singleBuy(APP.token, detailGood.getId(), goodsNumber, userAreaId, money + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<FastBuyBean>(this, true, "订单生成中，请稍后...") {
                    @Override
                    public void _doNext(FastBuyBean fastBuyBean) {
                        if (fastBuyBean.getCode().equals("200")) {
                            Intent intent = new Intent(ConfirmBuyGoodsAty.this, PayAty.class);
                            intent.putExtra("aty", "single");
                            Bundle itemBundle = new Bundle();
                            itemBundle.putSerializable("singleInfo", fastBuyBean.getData());
                            intent.putExtras(itemBundle);
                            startActivity(intent);
                        } else {
                            TU.cT(fastBuyBean.getMsg() + " ");
                        }
                    }
                });
    }

}
