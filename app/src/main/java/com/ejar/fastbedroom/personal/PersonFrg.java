package com.ejar.fastbedroom.personal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.ejar.baseframe.base.aty.AppManager;
import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.baseframe.utils.net.NetWork;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.buycar.BuyCarAty;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.FrgPersonBinding;
import com.ejar.fastbedroom.home.HomeAtyApi;
import com.ejar.fastbedroom.login.LoginActivity;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by json on 2017/8/22.
 */

public class PersonFrg extends BaseFragment<FrgPersonBinding> {

    private Dialog mDialog;
    private AlertDialog.Builder builder;
    public int setContent() {
        return R.layout.frg_person;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getUserInfo();
    }

    private void getUserInfo() {
        mDialog = NetDialog.createDialog(getActivity(),"获取个人信息中...");
        NetWork.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                .getPersonInfo(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        NetDialog.closeDialog(mDialog);
                        if(userInfoBean.getCode().equals("200")){
                            setPersonMessage(userInfoBean);
                        }else {
                            TU.cT(userInfoBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onComplete() {
                        NetDialog.closeDialog(mDialog);
                    }
                });
    }

    private void setPersonMessage(UserInfoBean userInfoBean) {
        bindingView.personName.setText(""+userInfoBean.getData().getUsername());
        bindingView.personPhoneNumber.setText(""+userInfoBean.getData().getTel());
        bindingView.personTotalMoney.setText("￥  "+userInfoBean.getData().getMoney());
        Glide.with(this)
                .load(userInfoBean.getData().getImg())
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


    }



    View.OnClickListener clickListener = v -> {
        Intent intent = null;
        switch (v.getId()){
            case R.id.person_info:
                 intent = new Intent(getActivity(), PersonInfoAty.class);
                startActivity(intent);
                break;
            case R.id.person_buy_car:
                 intent = new Intent(getActivity(), BuyCarAty.class);
                startActivity(intent);
                break;
//            case R.id.person_out_login:
//               builder.show();
//                break;

        }
//        startActivity(intent);
    };

}
