package com.ejar.fastbedroom.buycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.Api.StoreApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.bean.BuyCarBean;
import com.ejar.fastbedroom.buycar.bean.DeleteGoodsBean;
import com.ejar.fastbedroom.buycar.bean.PayOrder;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyBuyCarBinding;
import com.ejar.fastbedroom.pay.PayYueAty;
import com.ejar.fastbedroom.pay.bean.YuEBean;
import com.ejar.fastbedroom.useraddr.UserAddrAty;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/22.
 * 购物车aty
 */

public class BuyCarAty extends BaseActivity<AtyBuyCarBinding> {
    private String money = "";
    //购物车列表
    private List<BuyCarBean.DataBean> allCarList = new ArrayList<>();
    //购物车结算/删除列表
    private List<BuyCarBean.DataBean> chooseList = new ArrayList<>();
    List<DeleteGoodsBean> deleteGoods = new ArrayList<>();

    private MyRecyclerViewAdapter adapter;
    private RelativeLayout zfb, wx, yu_e;
    private boolean tag = false;//结算界面是否显示
    private TextView userAddr;
    private Button changeAddr;
    private int defaultAddrId = -1;
    private ImageView imgWx, imgZfb, imgYu_e, goodsChose, goodsAdd, goodsCut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_buy_car);
        initTitle();
        getBuyCar();
    }

    private void initData(List<BuyCarBean.DataBean> netList) {
        if (netList.size() > 0) {
            allCarList.addAll(netList);
            initAdapter(netList);
            bindingView.viewPaidInfo.setVisibility(View.VISIBLE);
        }else {
            TU.cT("购物车没有商品哦，赶紧去商城添加吧~~");
        }
    }

    private void initAdapter(List<BuyCarBean.DataBean> carList) {
        adapter = new MyRecyclerViewAdapter(this, R.layout.item_rv, carList) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.goods_title, carList.get(position).getName());
                holder.setText(R.id.goods_content, carList.get(position).getSummary());
                holder.setText(R.id.goods_price, carList.get(position).getShopPrice() + "元/"
                        + carList.get(position).getUnit());
                holder.setText(R.id.goods_number, carList.get(position).getNumber() + "");
                ImageView iv = holder.getView(R.id.goods_img);
                Glide.with(BuyCarAty.this).load(UrlConfig.baseUrl + carList.get(position).getImg())
                        .error(R.drawable.defult_img).into(iv);
                CheckBox checkBox = holder.getView(R.id.goods_isChoose);
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(carList.get(position).isChecked());
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        carList.get(position).setChecked(isChecked);
                        if (isChecked) {
                            chooseList.add(carList.get(position));
                        } else {
                            chooseList.remove(carList.get(position));
                            if (chooseList.size() == 0) {//单个商品挨个取消后 把全选设为false
                                bindingView.checkedAll.setChecked(false);
                            }
                        }
                        getTotalMoney(chooseList);
                    }
                });

                View.OnClickListener itemClick = v -> {
                    TextView goodsNumber = holder.getView(R.id.goods_number);
                    int number = Integer.parseInt(goodsNumber.getText().toString().trim());
                    switch (v.getId()) {
                        case R.id.goods_add_number:
                            if (number > carList.get(position).getStock()) {
                                TU.cT("已达该商品最大库存");
                                break;
                            }
                            number++;
                            goodsNumber.setText(number + "");
                            carList.get(position).setNumber(number);
                            getTotalMoney(chooseList);
                            break;
                        case R.id.goods_cut_number:
                            if (number > 1) {
                                number--;
                                goodsNumber.setText(number + "");
                                carList.get(position).setNumber(number);
                            } else {//最少为1个
                                number = 1;
                                goodsNumber.setText(number + "");
                                carList.get(position).setNumber(number);
                            }
                            getTotalMoney(chooseList);
                            break;
                        case R.id.goods_isChoose:
                            break;
                    }
                };
                holder.setOnClickListener(R.id.goods_add_number, itemClick);
                holder.setOnClickListener(R.id.goods_cut_number, itemClick);
            }
        };
        bindingView.buyCarRv.setLayoutManager(new LinearLayoutManager(this));
        bindingView.buyCarRv.setAdapter(adapter);
    }

    private void initTitle() {
        setTitle("购物车");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        bindingView.goToPay.setOnClickListener(clickListener);
        bindingView.goToDelete.setOnClickListener(clickListener);
        zfb = (RelativeLayout) findViewById(R.id.paid_by_zhifubao);
        wx = (RelativeLayout) findViewById(R.id.paid_by_weixin);
        yu_e = (RelativeLayout) findViewById(R.id.paid_by_last_money);
        userAddr = (TextView) findViewById(R.id.user_send_address);
        changeAddr = (Button) findViewById(R.id.choose_get_goods_addr);
        imgWx = (ImageView) findViewById(R.id.img_weixin);
        imgZfb = (ImageView) findViewById(R.id.img_zhifubao);
        imgYu_e = (ImageView) findViewById(R.id.img_last_money);


        zfb.setOnClickListener(clickListener);
        wx.setOnClickListener(clickListener);
        yu_e.setOnClickListener(clickListener);
        changeAddr.setOnClickListener(clickListener);

        bindingView.checkedAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (allCarList.size() > 0) {
                    if (isChecked) {
                        for (int i = 0; i < allCarList.size(); i++) {
                            allCarList.get(i).setChecked(true);
                        }
                    } else {
                        for (int i = 0; i < allCarList.size(); i++) {
                            allCarList.get(i).setChecked(false);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getTotalMoney(List<BuyCarBean.DataBean> moneyList) {
        BigDecimal totalMoney = new BigDecimal(0d);
        for (int i = 0; i < moneyList.size(); i++) {
            BigDecimal price = new BigDecimal(moneyList.get(i).getShopPrice());
            BigDecimal number = new BigDecimal(moneyList.get(i).getNumber());
            totalMoney = totalMoney.add(price.multiply(number));
        }
        money = totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        showTotalMoney(totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    private void showTotalMoney(String money) {

        bindingView.buyCarTotalMoney.setText("合计: " + money + " 元");
    }


    View.OnClickListener clickListener = v -> {
        Animation show = new AlphaAnimation(0, 1);
        show.setDuration(1000);
        switch (v.getId()) {
            case R.id.go_to_pay://支付界面
                if (chooseList.size() == 0) {
                    TU.cT("未选择商品");
                    return;
                }
                if (!tag) {
                    tag = true;
                    bindingView.showPaidView.setVisibility(View.VISIBLE);
                    bindingView.showPaidView.setAnimation(show);
                } else {
                    tag = false;
                    bindingView.showPaidView.setVisibility(View.GONE);
                }
                break;
            case R.id.paid_by_weixin://微信支付
                setDefaultDrawable();
                imgWx.setImageDrawable(getResources().getDrawable(R.drawable.img_weixin));
                payMoney(chooseList, 0);
                break;
            case R.id.paid_by_zhifubao://支付宝支付
                setDefaultDrawable();
                imgZfb.setImageDrawable(getResources().getDrawable(R.drawable.img_zhifubao));
                payMoney(chooseList, 1);
                break;
            case R.id.paid_by_last_money://余额支付
                setDefaultDrawable();
                imgYu_e.setImageDrawable(getResources().getDrawable(R.drawable.img_last_money_default));
                payMoney(chooseList, 2);
                break;
            case R.id.choose_get_goods_addr:
                openNextActivity(UserAddrAty.class);
                break;
            case R.id.go_to_delete:
                if (chooseList.size() == 0) {
                    TU.cT("未选择商品");
                    return;
                }
                deleteGoods();
                break;
        }
    };

    /**
     * 删除购物车的商品
     */
    private void deleteGoods() {
        for (int i = 0; i < chooseList.size(); i++) {
            DeleteGoodsBean goodsBean = new DeleteGoodsBean();
            goodsBean.setId(chooseList.get(i).getCartId());
            deleteGoods.add(goodsBean);
        }
        Gson gson = new Gson();
        String deleteJson = gson.toJson(deleteGoods);
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .deleteGoods(APP.token, deleteJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, true, "删除中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("删除成功");
                            getBuyCar();
                        } else {
                            TU.cT("" + baseBean.getMsg());
                        }
                    }
                });
    }

    private void setDefaultDrawable() {
        imgWx.setImageDrawable(getResources().getDrawable(R.drawable.img_weixin_defaulte));
        imgYu_e.setImageDrawable(getResources().getDrawable(R.drawable.img_last_money));
        imgZfb.setImageDrawable(getResources().getDrawable(R.drawable.img_zhifubao_default));
    }


    /**
     * 获取网络购物车
     */
    public void getBuyCar() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .getBuyCar(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BuyCarBean>(this, true, "") {
                    @Override
                    public void _doNext(BuyCarBean buyCarBean) {
                        if (buyCarBean.getCode().equals("200")) {
                            initData(buyCarBean.getData());
                        }
                    }
                });
    }

    private void payMoney(List<BuyCarBean.DataBean> buyList, int witchBtn) {
        String addr = userAddr.getText().toString().trim();
        if (addr.equals("") || addr == null) {
            TU.cT("请选择你的收货地址");
            return;
        }
        Gson gson = new Gson();
        String buyGoods = gson.toJson(buyList);
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .payGoods(APP.token, money + "", buyGoods, addr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<PayOrder>(this, true, "订单生成中...") {
                    @Override
                    public void _doNext(PayOrder payOrder) {
                        if (payOrder.getCode().equals("200")) {
                            switch (witchBtn) {
                                case 0://微信
                                    break;
                                case 1://支付宝
                                    break;
                                case 2://余额
                                    Intent intent = new Intent(BuyCarAty.this, PayYueAty.class);
                                    YuEBean yuEBean = new YuEBean();
                                    yuEBean.setArea(payOrder.getData().getDpareaname());
                                    yuEBean.setDoor(payOrder.getData().getAddress());
                                    yuEBean.setTotalMoney(payOrder.getData().getTotalManey() +"");
                                    yuEBean.setOrderId(payOrder.getData().getGoodsOrderNo());
                                    yuEBean.setTag(-2);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("orderId", yuEBean);
                                    intent.putExtras(bundle);
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultAddrId = (int) SpUtils.get(this, "defaultAddrId", -1);
        String defaultAddr = (String) SpUtils.get(this, "defaultAddr", "");
        if (defaultAddrId == -1) {
            return;
        }
        userAddr.setText(defaultAddr + "");
    }

}
