package com.ejar.fastbedroom.mystore;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ejar.baseframe.base.aty.AppManager;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyMyStoreBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mystore.banner.GlideImageLoader;
import com.ejar.fastbedroom.mystore.bean.BannerBean;
import com.ejar.fastbedroom.mystore.bean.RecommendBean;
import com.ejar.fastbedroom.mystore.bean.TabCenterBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_my_store);
        initTitle();
        netBannerRequest();
        netRecommendRequest();
        netRvTabRequest();
    }

    /**
     * 商品目录获取
     */
    private void netRvTabRequest() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class).getRvTab(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<TabCenterBean>(this, false,"") {
                    @Override
                    public void _doNext(TabCenterBean tabCenterBean) {
                        if(tabCenterBean.getCode().equals("200")){
                            tabList.addAll(tabCenterBean.getData());
                            initCenterTab(tabCenterBean.getData());
                        } else if(tabCenterBean.getCode().equals("201")){
                            Log.e("msg","201 center" +  tabCenterBean.getMsg());
//                            AppManager.removeAllAty();
//                            openNextActivity(LoginActivity.class);
                        }
                    }
                });
    }

    private void initCenterTab(List<TabCenterBean.DataBean> data) {
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
//                TU.cT(data.get(position).getId() + "");
                Bundle bundle = new Bundle();
                bundle.putSerializable("lifeUser",  data.get(position));
                openNextActivity(StoreSecondAty.class , bundle);
            }

            @Override
            public boolean onItemLonClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                return false;
            }
        });

    }

    /**
     * 推荐商品列表获取
     */
    private void netRecommendRequest() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .getRecommend(APP.token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<RecommendBean>(this, true, "") {
                    @Override
                    public void _doNext(RecommendBean recommendBean) {
                        if (recommendBean.getCode().equals("200")) {
//                            recommendBeanList.addAll(recommendBean.getRows());
                            initRecommendRv(recommendBean.getRows());
                        }  else if(recommendBean.getCode().equals("201")){
                            Log.e("msg","201 bottom" +  recommendBean.getMsg());
//                            AppManager.removeAllAty();
//                            openNextActivity(LoginActivity.class);
                        }else {
                            TU.cT(recommendBean.getCode() + recommendBean.getMsg());
                        }
                    }
                });
    }


    /**
     * 推荐商品列表
     *
     * @param rows
     */
    private void initRecommendRv(List<RecommendBean.RowsBean> rows) {
        bindingView.storeRecommendRv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MyRecyclerViewAdapter(this, R.layout.item_store_recommend, rows) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                ImageView imageView = (ImageView) holder.getView(R.id.recommend_goods_img);
                Glide.with(StoreActivity.this).load(UrlConfig.baseUrl + rows.get(position).getImg())
                        .error(R.drawable.defult_img).into(imageView);
                holder.setText(R.id.recommend_goods_title, rows.get(position).getName());
                holder.setText(R.id.recommend_goods_describe, rows.get(position).getSummary());
                holder.setText(R.id.recommend_goods_price, "¥" + rows.get(position).getShopPrice() + "元/" +
                        rows.get(position).getUnit());
            }
        };
        bindingView.storeRecommendRv.setAdapter(adapter);
}

    /**
     * banner 信息获取
     */
    private void netBannerRequest() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class).getBanner(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BannerBean>(this, false,"") {
                    @Override
                    public void _doNext(BannerBean bannerBean) {
                        if(bannerBean.getCode().equals("200")){
                            list.addAll(bannerBean.getData());
                            initBanner();
                        } else if(bannerBean.getCode().equals("201")){
                            Log.e("msg","201 top" + bannerBean.getMsg());
//                            AppManager.removeAllAty();
//                            openNextActivity(LoginActivity.class);
                        }else {
//                            TU.cT();
                        }
                    }
                });
    }

    private void initBanner() {
        banner = (Banner) findViewById(R.id.my_store_banner);
        //设置图片加载器
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
//        for (int i = 0; i < list.size(); i++) {
//            bannerImg.add(list.get(i).getImg());
//        }
        bannerImg.add("http://pic49.nipic.com/file/20140927/19617624_230415502002_2.jpg");
        bannerImg.add("http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=ea57f833b91bb0519b29bb6b5e13b0c1/f9198618367adab409cdef5281d4b31c8701e486.jpg");
        bannerImg.add("http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=f98d7c40b919ebc4d4757edaea4fa589/b64543a98226cffc9eabfc97b3014a90f603ea16.jpg");
        bannerImg.add("http://imgsrc.baidu.com/imgad/pic/item/1ad5ad6eddc451da2c3118f5bcfd5266d11632ec.jpg");

        banner.setImages(bannerImg);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                TU.cT(position + "");
            }
        });
    }

    private void initTitle() {
        setTitle("自营超市");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
