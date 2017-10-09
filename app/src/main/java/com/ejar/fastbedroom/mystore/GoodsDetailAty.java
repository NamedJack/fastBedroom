package com.ejar.fastbedroom.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.StoreApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.bean.PostGoodsBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyGoodsDetailBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mystore.banner.GlideImageLoader;
import com.ejar.fastbedroom.mystore.bean.ConfirmBuyGoodsDetail;
import com.ejar.fastbedroom.mystore.bean.GoodsDetailBean;
import com.ejar.fastbedroom.mystore.bean.RecommendBean;
import com.ejar.fastbedroom.mystore.bean.RowBean;
import com.ejar.fastbedroom.utils.TU;
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
    private int addressId;
    private String witchAty;
    private ConfirmBuyGoodsDetail goodsDetail ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_goods_detail);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        goodsDetail = new ConfirmBuyGoodsDetail();
        witchAty = intent.getStringExtra("goodsAty");
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
                        } else if (goodsDetail.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(GoodsDetailAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT("获取失败,请稍后再试");
                        }
                    }
                });
    }

    private void init(GoodsDetailBean goodsDetail) {
//        Log.e("msg", goodsDetail.toString());
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
    }


    View.OnClickListener clickListener = v -> {
        switch (witchAty){
            case "store"://商店商品的监听
                goodsBuyNumber = Integer.parseInt(bindingView.goodsDetailNumber.getText().toString().trim());
                BigDecimal singlePrice = new BigDecimal(rowBean.getShopPrice());
                switch (v.getId()) {
                    case R.id.goods_detail_add:
                        goodsBuyNumber++;
                        if (goodsBuyNumber > rowBean.getStock()) {
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
                        totalMoney = totalMoney.subtract(singlePrice);
                        bindingView.goodsDetailTotalMoney.setText(
                                "合计：" + totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "元"
                        );
                        break;
                    case R.id.goods_detail_add_car:
                        postToMyCar(rowBean.getGoodsId());
                        break;
                    case R.id.goods_detail_buy:
                        int goodsNum = Integer.parseInt(bindingView.goodsDetailNumber.getText().toString().trim());
                        if (goodsNum == 0) {
                            TU.cT("商品数量至少为1");
                            return;
                        }
                        Bundle bundle = new Bundle();
                        goodsDetail.setNumber(goodsNum);
                        goodsDetail.setImg(rowBean.getImg());
                        goodsDetail.setName(rowBean.getName());
                        goodsDetail.setPrice(rowBean.getShopPrice());
                        goodsDetail.setId(rowBean.getGoodsId());
                        bundle.putSerializable("goodsDetail",goodsDetail);
                        Intent intent = new Intent(GoodsDetailAty.this, ConfirmBuyGoodsAty.class);
                        intent.putExtra("goodAty",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
                break;
            case "recommend"://推荐商品的监听
                goodsBuyNumber = Integer.parseInt(bindingView.goodsDetailNumber.getText().toString().trim());
                BigDecimal singlePrice1 = new BigDecimal(rowsBean.getShopPrice());
                switch (v.getId()) {
                    case R.id.goods_detail_add:
                        goodsBuyNumber++;
                        if (goodsBuyNumber > rowsBean.getStock()) {
                            TU.cT("已达最大库存");
                            break;
                        }
                        bindingView.goodsDetailNumber.setText(goodsBuyNumber + "");

                        totalMoney = totalMoney.add(singlePrice1);
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
                        totalMoney = totalMoney.subtract(singlePrice1);
                        bindingView.goodsDetailTotalMoney.setText(
                                "合计：" + totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "元"
                        );
                        break;
                    case R.id.goods_detail_add_car:
                        postToMyCar(rowsBean.getId());
                        break;
                    case R.id.goods_detail_buy:
                        int goodsNum = Integer.parseInt(bindingView.goodsDetailNumber.getText().toString().trim());
                        if (goodsNum == 0) {
                            TU.cT("商品数量至少为1");
                            return;
                        }
                        Bundle bundle = new Bundle();
                        goodsDetail.setNumber(goodsNum);
                        goodsDetail.setImg(rowsBean.getImg());
                        goodsDetail.setName(rowsBean.getName());
                        goodsDetail.setPrice(rowsBean.getShopPrice());
                        goodsDetail.setId(rowsBean.getId());
                        bundle.putSerializable("goodsDetail",goodsDetail);
                        Intent intent = new Intent(GoodsDetailAty.this, ConfirmBuyGoodsAty.class);
                        intent.putExtra("goodAty",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
                break;
        }

    };


    /**
     * 添加到购物车
     */
    private void postToMyCar(int  goodsId) {
        PostGoodsBean bean = new PostGoodsBean();
        bean.setGoodsid(goodsId);
        bean.setNumber(Integer.parseInt(bindingView.goodsDetailNumber.getText().toString().trim()));
        Gson gson = new Gson();
        String s = "[" + gson.toJson(bean) + "]";
//        Log.e("msg", s + "gouwch");
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
