package com.ejar.fastbedroom.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ejar.baseframe.base.aty.AppManager;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.baseframe.widget.AddCartAnimation;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.BuyCarAty;
import com.ejar.fastbedroom.buycar.bean.PostGoodsBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyStoreSecondBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mystore.adapter.StoreSecondAdapter;
import com.ejar.fastbedroom.mystore.banner.GlideImageLoader;
import com.ejar.fastbedroom.mystore.bean.BannerBean;
import com.ejar.fastbedroom.mystore.bean.RowBean;
import com.ejar.fastbedroom.mystore.bean.SecondStoreRvBean;
import com.ejar.fastbedroom.mystore.bean.TabCenterBean;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/31.
 * 点击自营超市 tab 进入二级aty
 */

public class StoreSecondAty extends BaseActivity<AtyStoreSecondBinding> implements BaseQuickAdapter.RequestLoadMoreListener {
    private Bundle bundle;
    private TabCenterBean.DataBean dataBean;
    private List<RowBean> beanList = new ArrayList<>();
    private List<RowBean> requestList = new ArrayList<>();
    private List<BannerBean.DataBean> list = new ArrayList<>();
    private int pageNo;
    private StoreSecondAdapter adapter;
    private Banner banner;
    private List<String> bannerImg = new ArrayList<>();
//    private Set<RowBean> set = new HashSet<>();

    //    private List<SecondStoreRvBean.RowsBean> buyList = new ArrayList<>();//添加到购物车的集合
//    private HashMap<SecondStoreRvBean.RowsBean, Integer> buyList = new HashMap();
    //添加到购物车的集合
//    private HashMap<Integer ,SecondStoreRvBean.RowsBean> bookGoods = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_store_second);
        initTile();
        getUseTab();
        setDataList(beanList);
        initRvList(1);
        netBannerRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        postMyGoods(beanList);
    }

    private List<RowBean> initRvList(int pageNo) {
        requestList.clear();
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .getUseRvList(APP.token, "", pageNo + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<SecondStoreRvBean>(this, true, "") {
                    @Override
                    public void _doNext(SecondStoreRvBean secondStoreRvBean) {
                        if (secondStoreRvBean.getCode().equals("200")) {
                            beanList.addAll(secondStoreRvBean.getRows());
                            adapter.notifyDataSetChanged();
                        } else if (secondStoreRvBean.getCode().equals("201")) {
                            AppManager.removeAllAty();
                            openNextActivity(LoginActivity.class);
                        } else {
                            TU.cT(secondStoreRvBean.getMsg() + "");
                        }
                    }
                });
        return requestList;
    }

    private void setDataList(List<RowBean> rows) {
        adapter = new StoreSecondAdapter(R.layout.item_rv, rows, this);
        adapter.setOnLoadMoreListener(this, bindingView.secondStoreUseRv);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView tv = (TextView) adapter.getViewByPosition(position, R.id.goods_number);
                ImageView iv = (ImageView) adapter.getViewByPosition(position, R.id.img_add_number);
                int number = 0;
                if (tv.getText().toString().trim() == null ||
                        tv.getText().toString().trim().equals("")) {
                    number = 0;
                } else {
                    number = Integer.parseInt(tv.getText().toString().trim());
                }
                switch (view.getId()) {
                    case R.id.goods_add_number:
                        number++;
                        if(number > rows.get(position).getStock() ){
                            TU.cT("库存不足");
                            break;
                        }
                        tv.setText(number + "");
                        rows.get(position).setBookNumber(number);
                        AddCartAnimation.AddToCart(iv, bindingView.myStoreFlButton,
                                StoreSecondAty.this, bindingView.myStoreRl, 0.5);
                        break;
                    case R.id.goods_cut_number:
                        number--;
                        if (number <= 0) {
                            tv.setText(0 + "");
                            rows.get(position).setBookNumber(0);
                        } else {
                            tv.setText(number + "");
                            rows.get(position).setBookNumber(number);
                        }

                        break;
                }
//                set.add((RowBean) rows);
                rows.get(position).save();
            }
        });
        bindingView.secondStoreUseRv.setLayoutManager(new LinearLayoutManager(this));
        bindingView.secondStoreUseRv.setAdapter(adapter);
    }

    private void getUseTab() {
        NetRequest.getInstance(UrlConfig.baseUrl)
                .create(StoreApi.class).getUseTab(APP.token, dataBean.getId() + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<TabCenterBean>(this, true, "") {
                    @Override
                    public void _doNext(TabCenterBean centerBean) {
                        if (centerBean.getCode().equals("200")) {
                            for (int i = 0; i < centerBean.getData().size(); i++) {
                                RadioButton radioButton = new RadioButton(StoreSecondAty.this);
                                radioButton.setBackground(null);
                                radioButton.setText(centerBean.getData().get(i).getName());
                                radioButton.setTextSize(16);
                                radioButton.setButtonDrawable(null);
                                radioButton.setId(i);
                                radioButton.setPadding(10, 0, 5, 10);
                                bindingView.secondStoreTab.addView(radioButton, LinearLayout.LayoutParams.WRAP_CONTENT
                                        , LinearLayout.LayoutParams.WRAP_CONTENT);
                            }
                        } else {
                            TU.cT(centerBean.getMsg());
                        }
                    }
                });
    }

    private void netBannerRequest() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class).getBanner(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BannerBean>(this, false, "") {
                    @Override
                    public void _doNext(BannerBean bannerBean) {
                        if (bannerBean.getCode().equals("200")) {
                            list.addAll(bannerBean.getData());
                            initBanner();
                        } else if (bannerBean.getCode().equals("201")) {
                            AppManager.removeAllAty();
                            openNextActivity(LoginActivity.class);
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

    private void initTile() {
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        bindingView.myStoreFlButton.setOnClickListener(clickListener);
        bundle = new Bundle();
        Intent intent = getIntent();
        bundle = intent.getExtras();
        dataBean = (TabCenterBean.DataBean) bundle.getSerializable("lifeUser");
        setTitle(dataBean.getName());
        bindingView.secondStoreTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tempButton = (RadioButton) findViewById(checkedId);
            }
        });
    }


    @Override
    public void onLoadMoreRequested() {

        if (initRvList(2).size() < 10) {
            adapter.loadMoreEnd(false);
        }
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

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.my_store_fl_button:
                    openNextActivity(BuyCarAty.class);
                    break;
            }
        }
    };

    List<RowBean> post = new ArrayList<>();

    @Override
    protected void onStop() {
        post.addAll(DataSupport.where("bookNumber > ?", "0")
                .find(RowBean.class));
        DataSupport.deleteAll(RowBean.class);
        postMyGoods(post);
        super.onStop();
    }


    public void postMyGoods(List<RowBean> goodsList) {
        Gson gson = new Gson();
        List<PostGoodsBean> postList = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {
            PostGoodsBean rowBean = new PostGoodsBean();
            rowBean.setGoodsid(goodsList.get(i).getGoodsId());
            rowBean.setNumber(goodsList.get(i).getBookNumber());
            postList.add(rowBean);
        }
        String s = gson.toJson(postList);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .goodsPost(APP.token, s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, false, "购物车提交中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
//                        Log.e("post", baseBean.getCode()  +  "" + baseBean.getMsg() +s);
                    }
                });
    }

}
