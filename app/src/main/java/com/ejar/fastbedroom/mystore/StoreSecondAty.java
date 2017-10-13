package com.ejar.fastbedroom.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.StoreApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.fastbedroom.buycar.BuyCarAty;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyStoreSecondBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mystore.banner.GlideImageLoader;
import com.ejar.fastbedroom.mystore.bean.BannerBean;
import com.ejar.fastbedroom.mystore.bean.RowBean;
import com.ejar.fastbedroom.mystore.bean.SecondStoreRvBean;
import com.ejar.fastbedroom.mystore.bean.SecondStoreTab;
import com.ejar.fastbedroom.mystore.bean.TabCenterBean;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.utils.TU;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.litepal.crud.DataSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/31.
 * 点击自营超市 tab 进入二级aty
 */

public class StoreSecondAty extends BaseActivity<AtyStoreSecondBinding> {
    private Bundle bundle;
    private TabCenterBean.DataBean dataBean;
    private List<RowBean> goodsList = new ArrayList<>();
    private List<RowBean> requestList = new ArrayList<>();
    private List<BannerBean.DataBean> list = new ArrayList<>();
    private List<SecondStoreTab> centerTabs = new ArrayList<>();//banner下面的标题条目集合
    private MyRecyclerViewAdapter adapter;
    private Banner banner;
    private List<String> bannerImg = new ArrayList<>();
    private BigDecimal totalMoney = new BigDecimal(0d);
    private int goodsPid;

    private RecyclerView rv;
    private SmartRefreshLayout refreshLayout;
    private int currentPage = 1;
    private int totalItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_store_second);
        initTile();
        netBannerRequest();
        getGoodsTab();
        initRvList(currentPage, dataBean.getId() + "");
    }

    /***
     * 商品列表
     * @param pageNo
     * @param pid
     * @return
     */
    private List<RowBean> initRvList(int pageNo, String pid) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .getUseRvList(APP.token, "" + pid, pageNo + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<SecondStoreRvBean>(this, true, "") {
                    @Override
                    public void _doNext(SecondStoreRvBean secondStoreRvBean) {
                        if (secondStoreRvBean.getCode().equals("200")) {
                            goodsList.addAll(secondStoreRvBean.getRows());
                            totalItem = secondStoreRvBean.getTotal();//总条数
                            setDataList();
                        } else if (secondStoreRvBean.getCode().equals("201")) {
                            AppManager.removeAllAty();
                            openNextActivity(LoginActivity.class);
                        } else if (secondStoreRvBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(StoreSecondAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(secondStoreRvBean.getMsg() + "");
                        }
                    }
                });
        return requestList;
    }

    private void setDataList() {
        if (goodsList.size() == 0) {
            rv.setVisibility(View.GONE);
            bindingView.secondStoreEmpty.setVisibility(View.VISIBLE);
            return;
        }//如果先显示了没有数量的界面，刷新后需要显示有数量的界面需要重新设置显示与隐藏
        rv.setVisibility(View.VISIBLE);
        bindingView.secondStoreEmpty.setVisibility(View.GONE);
        LinearLayoutManager ll = new LinearLayoutManager(this);
        adapter = new MyRecyclerViewAdapter(this, R.layout.item_rv, goodsList) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.goods_title, goodsList.get(position).getName() + "");
                holder.setText(R.id.goods_content, goodsList.get(position).getSummary() + "");
                holder.setText(R.id.goods_price, goodsList.get(position).getShopPrice() + "元/"
                        + goodsList.get(position).getUnit());
                holder.setText(R.id.goods_number, "0");
                ImageView iv = holder.getView(R.id.goods_img);
//                ImageView anim = holder.getView(R.id.img_add_number);
                Glide.with(StoreSecondAty.this).load(UrlConfig.baseUrl + goodsList.get(position).getImg())
                        .error(R.drawable.defult_img).into(iv);
                View.OnClickListener itemClick = v -> {
                    TextView tv = holder.getView(R.id.goods_number);
                    int number = Integer.parseInt(tv.getText().toString().trim());
                    switch (v.getId()) {
                        case R.id.goods_add_number:
                            number++;
                            if (number > goodsList.get(position).getStock()) {
                                TU.cT("库存不足");
                                break;
                            }
                            tv.setText(number + "");
                            goodsList.get(position).setBookNumber(number);
                            //动画
//                            AddCartAnimation.AddToCart(anim, bindingView.myStoreFlButton,
//                                    StoreSecondAty.this, bindingView.myStoreRl, 0.5);
                            totalMoney = totalMoney.add(new BigDecimal(goodsList.get(position).getShopPrice()));
                            showMoney(totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                            break;
                        case R.id.goods_cut_number:
                            number--;
                            if (number < 0) {
                                tv.setText(0 + "");
                                goodsList.get(position).setBookNumber(0);
                                break;
                            }
                            tv.setText(number + "");
                            goodsList.get(position).setBookNumber(number);
                            totalMoney = totalMoney.subtract(new BigDecimal(goodsList.get(position).getShopPrice()));
                            showMoney(totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                            break;
                    }
                    goodsList.get(position).save();
                };
                holder.setOnClickListener(R.id.goods_add_number, itemClick);
                holder.setOnClickListener(R.id.goods_cut_number, itemClick);
            }

        };

        rv.setLayoutManager(ll);
        rv.setAdapter(adapter);
//        rv.setOnLoadMoreListener(new MyLoadMoreRecycleView.OnLoadMoreListener() {
//            @Override
//            public void loadMoreListener() {
//                initRvList(curretPage, goodsPid + "");
//                curretPage++;
//            }
//        });
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                Intent intent = new Intent(StoreSecondAty.this, GoodsDetailAty.class);
                intent.putExtra("goodsAty", "store");
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", goodsList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLonClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                return false;
            }
        });
    }

    /**
     * 条目分类
     */
    private void getGoodsTab() {
        centerTabs.clear();
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .getUseTab(APP.token, dataBean.getId() + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<TabCenterBean>(this, true, "") {
                    @Override
                    public void _doNext(TabCenterBean centerBean) {
                        if (centerBean.getCode().equals("200")) {
                            for (int i = 0; i < centerBean.getData().size(); i++) {
                                SecondStoreTab centerTab = new SecondStoreTab();
                                centerTab.setId(centerBean.getData().get(i).getId());
                                centerTab.setImage(centerBean.getData().get(i).getImage());
                                centerTab.setName(centerBean.getData().get(i).getName());
                                centerTab.setImage(centerBean.getData().get(i).getImage());
                                centerTab.setCheck(false);
                                centerTabs.add(centerTab);
                            }
                            setCenterTab(centerTabs);
                        } else {
                            TU.cT(centerBean.getMsg());
                        }
                    }
                });
    }

    private void setCenterTab(List<SecondStoreTab> tabData) {
        LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        bindingView.secondStoreTab.setLayoutManager(ll);
        MyRecyclerViewAdapter tabAdapter = new MyRecyclerViewAdapter(this, R.layout.item_store_second_tab, tabData) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                TextView bottomLine = holder.getView(R.id.second_store_tab_line);
                TextView centerTab = holder.getView(R.id.second_store_tab_name);
                centerTab.setText(tabData.get(position).getName() + "");
                if (tabData.get(position).isCheck()) {
                    bottomLine.setVisibility(View.VISIBLE);
                    centerTab.setTextColor(getResources().getColor(R.color.app_title_gb));
                } else {
                    bottomLine.setVisibility(View.INVISIBLE);
                    centerTab.setTextColor(getResources().getColor(R.color.default_text_color));
                }
            }
        };
        tabAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                goodsPid = tabData.get(position).getId();
                goodsList.clear();
                currentPage = 1;
                initRvList(currentPage, goodsPid + "");
                for (int i = 0; i < tabData.size(); i++) {
                    tabData.get(i).setCheck(false);
                }
                tabData.get(position).setCheck(true);
                tabAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLonClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                return false;
            }
        });
        bindingView.secondStoreTab.setAdapter(tabAdapter);
    }

    private void netBannerRequest() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class).getBanner(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BannerBean>(this, false, "") {
                    @Override
                    public void _doNext(BannerBean bannerBean) {
                        if (bannerBean.getCode().equals("200")) {
//                            list.addAll(bannerBean.getData());
                            for (int i = 0; i < bannerBean.getData().size(); i++) {
                                bannerImg.add(UrlConfig.baseUrl + bannerBean.getData().get(i).getImg());
                            }
                            initBanner();
                        } else if (bannerBean.getCode().equals(UrlConfig.logoutCodeTwo)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(StoreSecondAty.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void initBanner() {
        banner = (Banner) findViewById(R.id.my_store_second_banner);
        //设置图片加载器
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerImg);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
    }

    private void initTile() {
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        rv = (RecyclerView) findViewById(R.id.second_store_use_rv);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.second_sm);
        bindingView.secondStorePostOrder.setOnClickListener(clickListener);
        bundle = new Bundle();
        Intent intent = getIntent();
        bundle = intent.getExtras();
        dataBean = (TabCenterBean.DataBean) bundle.getSerializable("lifeUser");
        setTitle(dataBean.getName());
        refreshLayout.setEnableLoadmore(true);
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
                if (currentPage * 10 < totalItem) {
                    currentPage++;
                    initRvList(currentPage, dataBean.getId() + "");
                } else {
                    refreshlayout.finishLoadmore();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.store_men, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        openNextActivity(BuyCarAty.class);
        return true;
    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.second_store_post_order:
                int goodsNumber = DataSupport.where("bookNumber > ?", "0")
                        .find(RowBean.class).size();
                if (goodsNumber == 0) {
                    TU.cT("至少选择一个商品");
                    return;
                }
                Intent intent = new Intent(StoreSecondAty.this, ConfirmBuyGoodsAty.class);
                intent.putExtra("goodAty", 2);
                startActivity(intent);
                break;
        }
    };

    private void showMoney(String money) {
        bindingView.secondChooseGoodsMoney.setText("合计：" + money + "元");
    }

    @Override
    protected void onDestroy() {
        DataSupport.deleteAll(RowBean.class);
        super.onDestroy();
    }

}
