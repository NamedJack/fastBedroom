package com.ejar.fastbedroom.buycar;

import android.graphics.Color;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.bean.BuyCarBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyBuyCarBinding;
import com.ejar.fastbedroom.mystore.StoreApi;
import com.ejar.fastbedroom.mystore.adapter.StoreSecondAdapter;
import com.ejar.fastbedroom.mystore.bean.RowBean;

import org.litepal.crud.DataSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/22.
 */

public class BuyCarAty extends BaseActivity<AtyBuyCarBinding> implements BaseQuickAdapter.RequestLoadMoreListener {
    private BigDecimal totalMoney = new BigDecimal(0d);
    //购物车列表
    private List<BuyCarBean.DataBean> beans = new ArrayList<>();
    //购物车结算列表
    private List<BuyCarBean.DataBean> paidList = new ArrayList<>();
    private BuyCarAdapter myAdapter;
    private TextView tv;
    private View notDataView;
    private boolean tag = false;//结算界面是否显示
    private int pageNo = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_buy_car);
        init();
        initAdapter();
        initData();
    }

    private void initData() {
        beans.addAll(getBuyCar(pageNo)) ;
        if(beans.size() > 0){
            myAdapter.setNewData(beans);
        }else {
            notDataView = getLayoutInflater().inflate(R.layout.empty_view,
                    (ViewGroup) bindingView.buyCarRv.getParent(), false);
            bindingView.viewPaidInfo.setVisibility(View.GONE);
            myAdapter.setEmptyView(notDataView);
        }
    }

    private void initAdapter() {
        myAdapter = new BuyCarAdapter(R.layout.item_rv, beans, this);
        myAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        myAdapter.setOnLoadMoreListener(this, bindingView.buyCarRv);
        myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView buyNumber = (TextView) adapter.getViewByPosition(bindingView.buyCarRv, position, R.id.goods_number);
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
                        totalMoney = totalMoney.add(new BigDecimal(beans.get(position).getShopPrice()));
                        showTotalMoney(totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        break;
                    case R.id.goods_cut_number:
                        if (number > 0) {
                            number--;
                            buyNumber.setText(number + "");
                            beans.get(position).setNumber(number);
                            totalMoney = totalMoney.subtract(new BigDecimal(beans.get(position).getShopPrice()));
                            showTotalMoney(totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                            if (number == 0) {//减到0的时候删除该商品条目
                                beans.remove(position);
                                adapter.notifyItemRemoved(position);
                                if (beans.size() > 0) {//判断购物车中还存在商品
                                    adapter.notifyItemChanged(0, beans);
                                } else {//判断购物车中不存在商品，显示空白界面
                                    notDataView = getLayoutInflater().inflate(R.layout.empty_view,
                                            (ViewGroup) bindingView.buyCarRv.getParent(), false);
                                    bindingView.viewPaidInfo.setVisibility(View.GONE);
                                    myAdapter.setEmptyView(notDataView);
                                }

                            }
                        } else {//减到0的时候就设置为0
                            number = 0;
                            buyNumber.setText(number + "");
                            beans.get(position).setNumber(number);
                        }
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
    }

    private void getTotalMoney() {
        for (int i = 0; i < beans.size(); i++) {
            BigDecimal price = new BigDecimal(beans.get(i).getShopPrice());
            BigDecimal number = new BigDecimal(beans.get(i).getNumber());
            totalMoney = totalMoney.add(price.multiply(number));
        }
        showTotalMoney(totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    private void showTotalMoney(String money) {
        bindingView.buyCarTotalMoney.setText("合计: " + money + " 元 (不含配送费)");
    }


    View.OnClickListener clickListener = v -> {
        Animation show = new AlphaAnimation(0, 1);
        show.setDuration(1000);
        switch (v.getId()) {
            case R.id.go_to_pay:
                if (!tag) {
                    tag = true;
                    bindingView.paidView.setVisibility(View.VISIBLE);
                    bindingView.paidView.setAnimation(show);
                } else {
                    tag = false;
                    bindingView.paidView.setVisibility(View.GONE);
                }
                break;
        }
    };

    /**
     * 获取网络购物车
     */
    public List<BuyCarBean.DataBean> getBuyCar(int pageNo) {
        List<BuyCarBean.DataBean> netList = new ArrayList<>();
        NetRequest.getInstance(UrlConfig.baseUrl).create(StoreApi.class)
                .getBuyCar(APP.token, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BuyCarBean>(this, true, "") {
                    @Override
                    public void _doNext(BuyCarBean buyCarBean) {
                        if (buyCarBean.getCode().equals("200")) {
                            if (buyCarBean.getData().size() > 0) {

                                netList.addAll(buyCarBean.getData());
//                                myAdapter.setNewData(beans);
//                                getTotalMoney();
                            }
//                            else {
//                                notDataView = getLayoutInflater().inflate(R.layout.empty_view,
//                                        (ViewGroup) bindingView.buyCarRv.getParent(), false);
//                                bindingView.viewPaidInfo.setVisibility(View.GONE);
//                                myAdapter.setEmptyView(notDataView);
//
//                            }
                        }
                    }
                });
        return netList;
    }

//    public BigDecimal moneyUtils(double d) {
//        BigDecimal decimal = new BigDecimal(d);
//        return decimal;
//    }

    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        if (getBuyCar(pageNo).size() < 10) {
            myAdapter.loadMoreEnd(false);
        }
    }


}
