package com.ejar.fastbedroom.personal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.BuyCarAty;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.FrgPersonBinding;
import com.ejar.fastbedroom.home.HomeAtyApi;
import com.ejar.fastbedroom.mybook.view.BookActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/22.
 */

public class PersonFrg extends BaseFragment<FrgPersonBinding> {


    public int setContent() {
        return R.layout.frg_person;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getUserInfo();
        }
    }


    private void getUserInfo() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                .getPersonInfo(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<UserInfoBean>(getActivity(), true, "") {
                    @Override
                    public void _doNext(UserInfoBean userInfoBean) {
                        if (userInfoBean.getCode().equals("200")) {
                            setPersonMessage(userInfoBean);
                        } else {
                            TU.cT(userInfoBean.getMsg());
                        }
                    }
                });
    }

    private void setPersonMessage(UserInfoBean userInfoBean) {
        bindingView.personName.setText(userInfoBean.getData().getName() + " ");
        bindingView.personPhoneNumber.setVisibility(View.VISIBLE);
        bindingView.personPhoneNumber.setText("" + userInfoBean.getData().getTel());
        bindingView.personTotalMoney.setText("￥  " + userInfoBean.getData().getMoney());
        if (userInfoBean.getData().getTobedistribution() > 0) {
            //未付款数量
            bindingView.personNotPayNumber.setVisibility(View.VISIBLE);
            bindingView.personNotPayNumber.setText(userInfoBean.getData().getTobedistribution() + "");
        }
        if (userInfoBean.getData().getReceiptofgoods() > 0) {
            //未接单数量
            bindingView.personNotAccepteNumber.setVisibility(View.VISIBLE);
            bindingView.personNotAccepteNumber.setText(userInfoBean.getData().getReceiptofgoods() + "");
        }
        if (userInfoBean.getData().getPendingpayment() > 0) {
            //未收货数量
            bindingView.personHavePayNumber.setVisibility(View.VISIBLE);
            bindingView.personHavePayNumber.setText(userInfoBean.getData().getPendingpayment() + "");
        }
        Glide.with(this)
                .load(UrlConfig.baseUrl + userInfoBean.getData().getImg())
                .error(R.drawable.defult_img)
                .into(bindingView.personImg);

    }

    private void initView() {
        bindingView.personInfo.setOnClickListener(clickListener);
        bindingView.personBuyCar.setOnClickListener(clickListener);
        bindingView.personMoneyDetail.setOnClickListener(clickListener);
        bindingView.personAddSecondHand.setOnClickListener(clickListener);
        bindingView.personCertification.setOnClickListener(clickListener);
        bindingView.personToAct.setOnClickListener(clickListener);
        bindingView.personMySecondHand.setOnClickListener(clickListener);
        bindingView.personAdvice.setOnClickListener(clickListener);
        bindingView.personAboutUs.setOnClickListener(clickListener);

        bindingView.personNotPay.setOnClickListener(clickListener);//未支付
        bindingView.personNotAccept.setOnClickListener(clickListener);//未接单
        bindingView.personPaid.setOnClickListener(clickListener);//已支付
        bindingView.personComplete.setOnClickListener(clickListener);//已完成

    }


    View.OnClickListener clickListener = v -> {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.person_info://编辑
                intent = new Intent(getActivity(), PersonInfoAty.class);
//                intent.putExtras(bundleUser);
                startActivity(intent);
                break;
            case R.id.person_buy_car://购物车
                intent = new Intent(getActivity(), BuyCarAty.class);
                startActivity(intent);
                break;
            case R.id.person_not_pay://未支付
                intent = new Intent(getActivity(), BookActivity.class);
                intent.putExtra("view", 1);
                startActivity(intent);
                break;
            case R.id.person_not_accept://未接单
                intent = new Intent(getActivity(), BookActivity.class);
                intent.putExtra("view", 2);
                startActivity(intent);
                break;
            case R.id.person_paid://已支付
                intent = new Intent(getActivity(), BookActivity.class);
                intent.putExtra("view", 3);
                startActivity(intent);
                break;
            case R.id.person_complete://已完成
                intent = new Intent(getActivity(), BookActivity.class);
                intent.putExtra("view", 4);
                startActivity(intent);
                break;

        }
    };

}
