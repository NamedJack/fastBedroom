package com.ejar.fastbedroom.personal.frg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.HomeAtyApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.advice.AboutUsAty;
import com.ejar.fastbedroom.advice.AdviceAty;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.BuyCarAty;
import com.ejar.fastbedroom.certification.CertificationAty;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.FrgPersonBinding;
import com.ejar.fastbedroom.jion.ToBeAgentAty;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.mybook.view.BookActivity;
import com.ejar.fastbedroom.personal.aty.CashAty;
import com.ejar.fastbedroom.personal.aty.MoneyDetailAty;
import com.ejar.fastbedroom.personal.aty.PersonInfoAty;
import com.ejar.fastbedroom.personal.bean.UserInfoBean;
import com.ejar.fastbedroom.useraddr.UserAddrAty;
import com.ejar.fastbedroom.utils.TU;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//import com.bumptech.glide.Glide;

/**
 * Created by json on 2017/8/22.
 */

public class PersonFrg extends BaseFragment<FrgPersonBinding> {

    private double userAccountMoney = 0.0d;
    public int setContent() {
        return R.layout.frg_person;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
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
                        } else if (userInfoBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
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
        userAccountMoney = userInfoBean.getData().getMoney();
        Glide.with(this)
                .load(UrlConfig.baseUrl + userInfoBean.getData().getImg())
                .error(R.drawable.image_user_head)
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
        bindingView.personBuyAddress.setOnClickListener(clickListener);
        bindingView.personTakeMoney.setOnClickListener(clickListener);




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
            case R.id.person_buy_address://收货地址
                intent = new Intent(getActivity(), UserAddrAty.class);
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
            case R.id.person_to_act://成为代理人
                intent = new Intent(getActivity(), ToBeAgentAty.class);
                startActivity(intent);
                break;
            case R.id.person_certification://实名认证
                intent = new Intent(getActivity(), CertificationAty.class);
                startActivity(intent);
                break;
            case R.id.person_advice://意见建议
                intent = new Intent(getActivity(), AdviceAty.class);
                startActivity(intent);
                break;
            case R.id.person_about_us://关于
                intent = new Intent(getActivity(), AboutUsAty.class);
                startActivity(intent);
                break;
            case R.id.person_take_money://提现
                intent = new Intent(getActivity(), CashAty.class);
                intent.putExtra("accountMoney", userAccountMoney);
                startActivity(intent);
                break;
            case R.id.person_money_detail://收支明细
                intent = new Intent(getActivity(), MoneyDetailAty.class);
                startActivity(intent);
                break;
        }
    };

}
