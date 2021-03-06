package com.ejar.fastbedroom.mystore;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.StoreApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyMyStoreBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mystore.banner.GlideImageLoader;
import com.ejar.fastbedroom.mystore.bean.BannerBean;
import com.ejar.fastbedroom.mystore.bean.ConfirmBuyGoodsDetail;
import com.ejar.fastbedroom.mystore.bean.RecommendBean;
import com.ejar.fastbedroom.mystore.bean.StoreAllBean;
import com.ejar.fastbedroom.mystore.bean.TabCenterBean;
import com.ejar.fastbedroom.useraddr.UserAddrAty;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/28.
 */

public class StoreActivity extends BaseActivity<AtyMyStoreBinding> {
    private String TAG = "StoreActivity";

    private List<BannerBean.DataBean> list = new ArrayList<>();
    private Dialog dialog;
    private List<String> bannerImg = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;
    private Banner banner;
    private List<RecommendBean.RowsBean> recommendBeanList = new ArrayList<>();
    private List<TabCenterBean.DataBean> tabList = new ArrayList<>();

    private String userAddress, userDoor;
    private int userArea;
    private boolean flag = false;
    private int goodsId;
    private String goodsPrice;
    private SmartRefreshLayout refreshLayout;
    private int currentPage = 1;
    private int totalPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_my_store);
        initTitle();
        allRequest(currentPage);
    }


    private void initCenterTab(List<TabCenterBean.DataBean> data) {
        if(data == null){
            return;
        }
        LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        bindingView.myStoreRv.setLayoutManager(ll);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, R.layout.item_mystore_tab, data) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.tab_Dial_Tv, data.get(position).getName());
                ImageView iv = holder.getView(R.id.tab_Dial_Img);
                Glide.with(StoreActivity.this).load(UrlConfig.baseUrl + data.get(position).getImage())
                        .error(R.drawable.defult_img).into(iv);
            }

        };
        bindingView.myStoreRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("lifeUser", data.get(position));
                openNextActivity(StoreSecondAty.class, bundle);
            }

            @Override
            public boolean onItemLonClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                return false;
            }
        });

    }


    /**
     * 推荐商品列表
     *
     * @param rows
     */
    private List<RecommendBean.RowsBean> rows = new ArrayList<>();
    private void initRecommendRv() {
        if(rows == null || rows.size() == 0){
            bindingView.storeRecommendRv.setVisibility(View.GONE);
            bindingView.storeRecommendEmpty.setVisibility(View.VISIBLE);
            return;
        }

        bindingView.storeRecommendRv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MyRecyclerViewAdapter(this, R.layout.aa, rows) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                ImageView imageView = (ImageView) holder.getView(R.id.recommend_goods_img);
                Glide.with(StoreActivity.this).load(UrlConfig.baseUrl + rows.get(position).getImg())
                        .error(R.drawable.defult_img).into(imageView);
                holder.setText(R.id.recommend_goods_title, rows.get(position).getName());
                holder.setText(R.id.recommend_goods_describe, rows.get(position).getSummary());
                holder.setText(R.id.recommend_goods_price, "¥" + rows.get(position).getShopPrice() + "元/" +
                        rows.get(position).getUnit());
                //立即购买
                holder.setOnClickListener(R.id.recommend_goods_fast_buy, v -> {
                    ConfirmBuyGoodsDetail detailGood = new ConfirmBuyGoodsDetail();
                    detailGood.setId(rows.get(position).getId());
                    detailGood.setImg(rows.get(position).getImg());
                    detailGood.setName(rows.get(position).getName());
                    detailGood.setNumber(1);
                    detailGood.setPrice(rows.get(position).getShopPrice());
                    Intent intent = new Intent(StoreActivity.this, ConfirmBuyGoodsAty.class);
                    intent.putExtra("goodAty",1);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goodsDetail", detailGood);
                    intent.putExtras(bundle);
                    startActivity(intent);
                });
            }
        };
        bindingView.storeRecommendRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                //点击item跳转商品详情
                Intent intent = new Intent(StoreActivity.this, GoodsDetailAty.class);
                intent.putExtra("goodsAty","recommend");
                Bundle bundle = new Bundle();
                bundle.putSerializable("goodsId", rows.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLonClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                return false;
            }
        });
    }


    private void initBanner(List<BannerBean.DataBean> data) {
        if(data == null){return;}
        banner = (Banner) findViewById(R.id.my_store_banner);
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

    private void initTitle() {
        setTitle("网上超市");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.store_sm);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(currentPage < totalPage){
                    currentPage++;
                    allRequest(currentPage);
                }else {
                    refreshlayout.finishLoadmore();
                }
            }
        });

    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.goods_detail_choose_addr:
                openNextActivity(UserAddrAty.class);
                break;
//            case R.id.goods_detail_to_order:
//                if (TextUtils.isEmpty(showAddr.getText().toString().trim())) {
//                    TU.cT("请选择收货地址");
//                    return;
//                }
//                fastBuyGetOrder();
//                break;
        }
    };

//    /**
//     * 生成订单号。跳转到支付界面
//     */
//    private void fastBuyGetOrder() {
//        Log.e("msg",goodsId + " " +goodsPrice);
//        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
//                .singleBuy(APP.token, goodsId, 1, userArea, goodsPrice +"")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MyBaseObserver<FastBuyBean>(this, true, "订单生成中，请稍后...") {
//                    @Override
//                    public void _doNext(FastBuyBean fastBuyBean) {
//                        if (fastBuyBean.getCode().equals("200")) {
//                            Intent intent = new Intent(StoreActivity.this, PayAty.class);
//                            intent.putExtra("aty", "single");
//                            Bundle itemBundle = new Bundle();
//                            itemBundle.putSerializable("singleInfo", fastBuyBean.getData());
//                            intent.putExtras(itemBundle);
//                            startActivity(intent);
//
//                        } else {
//                            TU.cT(fastBuyBean.getMsg() + " ");
//                        }
//                    }
//                });
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        userAddress = (String) SpUtils.get(this, "defaultAddrArea", "");
//        userDoor = (String) SpUtils.get(this, "defaultAddrDoor", "");
//        showAddr.setText(userAddress + userDoor + " ");
//        userArea = (int) SpUtils.get(this, "defaultUserAddrId", -1);
//    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void allRequest(int currentPage) {

        StoreApi service = NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class);

        Observable<BannerBean> banner = service.getBanner(APP.token).subscribeOn(Schedulers.io());
        Observable<TabCenterBean> center = service.getRvTab(APP.token).subscribeOn(Schedulers.io());
        Observable<RecommendBean> recommend = service.getRecommend(APP.token,currentPage).subscribeOn(Schedulers.io());

        Observable.zip(banner, center, recommend, new Function3<BannerBean, TabCenterBean, RecommendBean, Object>() {
            @Override
            public StoreAllBean apply(BannerBean bannerBean, TabCenterBean tabCenterBean, RecommendBean recommendBean) throws Exception {
                StoreAllBean allBean = new StoreAllBean();
                allBean.setBanner(bannerBean);
                allBean.setRecommend(recommendBean);
                allBean.setTabCenter(tabCenterBean);
                return allBean;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<Object>(this, true, "") {
                    @Override
                    public void _doNext(Object o) {
                        StoreAllBean allBean = (StoreAllBean) o;
                        if (o == null) {
                            return;
                        }
                        if(allBean.getRecommend().getCode().equals(UrlConfig.logoutCodeTwo)){
                            AppManager.removeAllAty();
                            Intent intent = new Intent(StoreActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        totalPage =  allBean.getRecommend().getPagetatol();
                        initBanner(allBean.getBanner().getData());
                        rows.addAll(allBean.getRecommend().getRows());
                        initRecommendRv();
                        initCenterTab(allBean.getTabCenter().getData());
                    }
                });
    }

}
