package com.ejar.fastbedroom.buycar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ejar.baseframe.base.aty.BaseActivity;
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
    private List<BuyCarBean.DataBean> beans = new ArrayList<>();
    //购物车结算/删除列表
    private List<BuyCarBean.DataBean> paidList = new ArrayList<>();
    List<DeleteGoodsBean> deleteGoods = new ArrayList<>();

    private BuyCarAdapter myAdapter;
    private RelativeLayout zfb, wx, yu_e;
    private View notDataView;
    private boolean tag = false;//结算界面是否显示
    private TextView userAddr;
    private Button changeAddr;
    private int defaultAddrId = -1;
    private ImageView imgWx, imgZfb, imgYu_e, goodsChose, goodsAdd, goodsCut;
    private boolean isSave = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_buy_car);
        init();
        initAdapter();
        getBuyCar();
    }


    private void initData(List<BuyCarBean.DataBean> netList) {
        beans.addAll(netList);
        if(beans.size() > 0){
            myAdapter.setNewData(beans);
            bindingView.viewPaidInfo.setVisibility(View.VISIBLE);
        }else {
            notDataView = getLayoutInflater().inflate(R.layout.empty_view,
                    (ViewGroup) bindingView.buyCarRv.getParent(), false);
            myAdapter.setEmptyView(notDataView);
        }
    }

    private void initAdapter() {
        myAdapter = new BuyCarAdapter(R.layout.item_rv, beans, this);
//        myAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);

        myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView buyNumber = (TextView) adapter.getViewByPosition(bindingView.buyCarRv, position, R.id.goods_number);
                CheckBox checkBox = (CheckBox) adapter.getViewByPosition(bindingView.buyCarRv, position, R.id.goods_isChoose);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        beans.get(position).setChecked(isChecked);
                        if (isChecked) {
                            paidList.add(beans.get(position));
                        } else {
                            paidList.remove(beans.get(position));
                        }
                        Log.e("msg", "zongjia" + isChecked);
                        getTotalMoney();
                    }
                });
                int number = beans.get(position).getNumber();
                switch (view.getId()) {
                    case R.id.goods_add_number:
                        if (number > beans.get(position).getStock()) {
                            TU.cT("已达该商品最大库存");
                            break;
                        }
                        number++;
                        buyNumber.setText(number + "");
                        beans.get(position).setNumber(number);
                        getTotalMoney();
                        break;
                    case R.id.goods_cut_number:
                        if (number > 1) {
                            number--;
                            buyNumber.setText(number + "");
                            beans.get(position).setNumber(number);
                        } else {//最少为1个
                            number = 1;
                            buyNumber.setText(number + "");
                            beans.get(position).setNumber(number);
                        }
                        getTotalMoney();
                        break;
                }
            }
        });
        bindingView.buyCarRv.setLayoutManager(new LinearLayoutManager(this));
        bindingView.buyCarRv.setAdapter(myAdapter);
    }

    private void init() {
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
                if (beans.size() > 0) {
                    if (isChecked) {
                        for (int i = 0; i < beans.size(); i++) {
                            beans.get(i).setChecked(true);
                        }
                        paidList.addAll(beans);
                    } else {
                        for (int i = 0; i < beans.size(); i++) {
                            beans.get(i).setChecked(false);
                        }
                        paidList.removeAll(beans);
                    }

                    myAdapter.setNewData(beans);
                }
            }
        });
    }

    //List<BuyCarBean.DataBean> moneyList
    private void getTotalMoney() {
        Log.e("msg", "paidSize" + paidList.size());
        BigDecimal totalMoney = new BigDecimal(0d);
        for (int i = 0; i < paidList.size(); i++) {
            BigDecimal price = new BigDecimal(paidList.get(i).getShopPrice());
            BigDecimal number = new BigDecimal(paidList.get(i).getNumber());
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
                if (!tag) {
                    tag = true;
                    bindingView.showPaidView.setVisibility(View.VISIBLE);
                    bindingView.showPaidView.setAnimation(show);
                } else {
                    tag = false;
                    bindingView.showPaidView.setVisibility(View.GONE);
                }
                break;
            case R.id.paid_by_weixin:
                setDefaultDrawable();
                imgWx.setImageDrawable(getResources().getDrawable(R.drawable.img_weixin));
                payMoney(beans);
                break;
            case R.id.paid_by_zhifubao:
                setDefaultDrawable();
                imgZfb.setImageDrawable(getResources().getDrawable(R.drawable.img_zhifubao));
                payMoney(beans);
                break;
            case R.id.paid_by_last_money:
                setDefaultDrawable();
                imgYu_e.setImageDrawable(getResources().getDrawable(R.drawable.img_last_money_default));
                payMoney(beans);
                break;
            case R.id.choose_get_goods_addr:
                openNextActivity(UserAddrAty.class);
                break;
            case R.id.go_to_delete:
                deleteGoods();
                break;
        }
    };

    /**
     * 删除购物车的商品
     */
    private void deleteGoods() {
        for (int i = 0; i < paidList.size(); i++) {
            DeleteGoodsBean goodsBean = new DeleteGoodsBean();
            goodsBean.setId(paidList.get(i).getId());
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
                            if (buyCarBean.getData().size() > 0) {
                                initData(buyCarBean.getData());
                            }
                        }
                    }
                });
    }

    private void payMoney(List<BuyCarBean.DataBean> buyList) {
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
                        Log.e("msg", payOrder.getMsg() + "  " + payOrder.getCode());
                        if (payOrder.getCode().equals("200")) {

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
