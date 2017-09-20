package com.ejar.fastbedroom.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.TextView;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.Api.StoreApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.bean.PostGoodsBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyGoodsDetailBinding;
import com.ejar.fastbedroom.mystore.banner.GlideImageLoader;
import com.ejar.fastbedroom.mystore.bean.DetailBuyBean;
import com.ejar.fastbedroom.mystore.bean.GoodsDetailBean;
import com.ejar.fastbedroom.mystore.bean.RecommendBean;
import com.ejar.fastbedroom.mystore.bean.RowBean;
import com.ejar.fastbedroom.pay.PayAty;
import com.ejar.fastbedroom.useraddr.UserAddrAty;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/15.
 */

public class GoodsDetailAty extends BaseActivity<AtyGoodsDetailBinding> {
    private RecommendBean.RowsBean rowsBean;//推荐商品
    private RowBean rowBean;//超市商品
    private Banner banner;
    private int goodsBuyNumber;
    private List<String> bannerImg = new ArrayList<>();
    private BigDecimal totalMoney = new BigDecimal(0d);
    private Button btnConfirm, btnChooseAddr;
    private TextView addrTv;
    private int addressId;
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_goods_detail);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        String witchAty = intent.getStringExtra("goodsAty");
        switch (witchAty){
            case "store":
                rowBean = (RowBean) bundle.getSerializable("goods");
                initTitle(rowBean.getName());
                getDetail(rowBean.getGoodsId());
                break;
            case "recommend":
                rowsBean = (RecommendBean.RowsBean) bundle.getSerializable("goodsId");
                initTitle(rowsBean.getName());
                getDetail(rowsBean.getId());
                break;
        }
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String addr = (String) SpUtils.get(this, "defaultAddrArea", "");
        String door = (String) SpUtils.get(this, "defaultAddrDoor", "");
        addressId = (int) SpUtils.get(this, "defaultUserAddrId", 0);
        if (!TextUtils.isEmpty(addr) || !TextUtils.isEmpty(door)) {
            addrTv.setText(addr + door + " ");
        }
    }

    private void setListener() {
        bindingView.goodsDetailCut.setOnClickListener(clickListener);
        bindingView.goodsDetailAdd.setOnClickListener(clickListener);
        bindingView.goodsDetailBuy.setOnClickListener(clickListener);
        bindingView.goodsDetailAddCar.setOnClickListener(clickListener);
    }

    private void getDetail(int goodsId) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .getGoodsDetail(APP.token, goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<GoodsDetailBean>(this, true, "商品详情获取中...") {
                    @Override
                    public void _doNext(GoodsDetailBean goodsDetail) {
                        if (goodsDetail.getCode().equals("200")) {
                            init(goodsDetail);
                        } else {
                            TU.cT("获取失败,请稍后再试");
                        }
                    }
                });
    }

    private void init(GoodsDetailBean goodsDetail) {
        Log.e("msg", goodsDetail.toString());
        bindingView.allLlView.setVisibility(View.VISIBLE);
        bindingView.goodsDeatailName.setText(goodsDetail.getGood().getName() + " ");
        bindingView.goodsDetailDescription.setText(goodsDetail.getGood().getSummary() + " ");
        bindingView.goodsDetailPrice.setText(goodsDetail.getGood().getShopPrice() + "元/"
                + goodsDetail.getGood().getUnit());
        bindingView.goodsDetailNumber.setText("0");
        initBanner(goodsDetail.getImgs());

        bindingView.goodsDetailWeb.getSettings().setUseWideViewPort(true);
        bindingView.goodsDetailWeb.getSettings().setLoadWithOverviewMode(true);
        bindingView.goodsDetailWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        bindingView.goodsDetailWeb.loadDataWithBaseURL(
                UrlConfig.baseBannerUrl, goodsDetail.getGood().getContent(), "text/html", "utf-8", null);
    }

    private void initBanner(List<GoodsDetailBean.ImgsBean> data) {
        banner = (Banner) findViewById(R.id.goods_detail_banner);
        //设置图片加载器
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        for (int i = 0; i < data.size(); i++) {
            bannerImg.add(UrlConfig.baseUrl + data.get(i).getImg());
        }

        banner.setImages(bannerImg);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                TU.cT(position + "");
            }
        });
    }

    private void initTitle(String title) {
        setTitle("" + title);
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        addrTv = (TextView) findViewById(R.id.goods_detail_send_address);
        btnChooseAddr = (Button) findViewById(R.id.goods_detail_choose_addr);
        btnConfirm = (Button) findViewById(R.id.goods_detail_to_order);
        btnChooseAddr.setOnClickListener(clickListener);
        btnConfirm.setOnClickListener(clickListener);
    }


    View.OnClickListener clickListener = v -> {
        goodsBuyNumber = Integer.parseInt(bindingView.goodsDetailNumber.getText().toString().trim());
        BigDecimal singlePrice = new BigDecimal(rowsBean.getShopPrice());
        switch (v.getId()) {
            case R.id.goods_detail_add:
                goodsBuyNumber++;
                if (goodsBuyNumber > rowsBean.getStock()) {
                    TU.cT("已达最大库存");
                    break;
                }
                bindingView.goodsDetailNumber.setText(goodsBuyNumber + "");

                totalMoney = totalMoney.add(singlePrice);
                bindingView.goodsDetailTotalMoney.setText(
                        "合计：" + totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "元"
                );
                break;
            case R.id.goods_detail_cut:
                goodsBuyNumber--;
                if (goodsBuyNumber < 0) {
                    break;
                }
                bindingView.goodsDetailNumber.setText(goodsBuyNumber + "");
//                BigDecimal singlePrice1 = new BigDecimal(rowsBean.getShopPrice());
                totalMoney = totalMoney.subtract(singlePrice);
                bindingView.goodsDetailTotalMoney.setText(
                        "合计：" + totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "元"
                );
                break;
            case R.id.goods_detail_add_car:
                postToMyCar();
                break;
            case R.id.goods_detail_buy:
                if (!flag) {
                    bindingView.goodsDetailAddrView.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    bindingView.goodsDetailAddrView.setVisibility(View.GONE);
                    flag = false;
                }
                break;
            case R.id.goods_detail_choose_addr://选择地址
                openNextActivity(UserAddrAty.class);
                break;
            case R.id.goods_detail_to_order://去支付界面
                if (addrTv.getText().toString().trim().equals("") || null == addrTv.getText().toString().trim()) {
                    TU.cT("请选择地址");
                    break;
                }
                toGetOrderNumber();
                break;
        }
    };

    /**
     * 去生成订单号 跳转到支付界面
     */
    private void toGetOrderNumber() {
        int goodsNum = Integer.parseInt(bindingView.goodsDetailNumber.getText().toString().trim());
        PostGoodsBean bean = new PostGoodsBean();
        bean.setGoodsid(rowsBean.getId());
        if (goodsNum == 0) {
            TU.cT("商品数量至少为1");
            return;
        }
        bean.setNumber(goodsNum);
        Gson gson = new Gson();
        String s = "[" + gson.toJson(bean) + "]";
        String money = totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .detailBuy(APP.token, rowsBean.getId(), goodsNum, addressId, money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<DetailBuyBean>(this, true, "订单生成中，请稍候...") {
                    @Override
                    public void _doNext(DetailBuyBean detailBuyBean) {
                        if (detailBuyBean.getCode().equals("200")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pay_order", detailBuyBean.getData());
                            Intent intent = new Intent(GoodsDetailAty.this, PayAty.class);
                            intent.putExtra("aty", "detail");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            TU.cT(detailBuyBean.getMsg() + " " + detailBuyBean.getCode());
                        }
                    }
                });
    }

    /**
     * 添加到购物车
     */
    private void postToMyCar() {
        PostGoodsBean bean = new PostGoodsBean();
        bean.setGoodsid(rowsBean.getId());
        bean.setNumber(Integer.parseInt(bindingView.goodsDetailNumber.getText().toString().trim()));
        Gson gson = new Gson();
        String s = "[" + gson.toJson(bean) + "]";
        Log.e("msg", s + "gouwch");
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .goodsPost(APP.token, s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, true, "购物车提交中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        Log.e("msg", "tijiao" + s + " " + baseBean.getCode() + baseBean.getMsg());
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("购物车提交成功");
                        } else {
                            TU.cT(baseBean.getMsg() + " ");
                        }
                    }
                });
    }
}
