package com.ejar.fastbedroom.mybook.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.AllOrderInfoApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by json on 2017/8/29.
 */

public class MailFragment extends BaseFragment {
    @Override
    public int setContent() {
        return R.layout.frg_not_paid;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String data = getArguments().getString("whichAty");
        switch (data) {
            case "未支付":
                initRvDataNoPay();
                break;
            case "未接单":
                initRvDataNoDeliver();
                break;
            case "已支付":
                initRvDatahavaPay();
                break;
            case "已完成":
                initRvDatafinish();
                break;
            default:
                break;

        }
    }

    private void initRvDatafinish() {

    }

    private void initRvDatahavaPay() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .getUserHaveOrder(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<ResponseBody>(getActivity(), true, "订单获取中...") {
                    @Override
                    public void _doNext(ResponseBody body) {
                        try {
                            Log.e("msg", "已支付" + body.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void initRvDataNoDeliver() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .getNotSendOrder(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<ResponseBody>(getActivity(), true, "订单获取中...") {
                    @Override
                    public void _doNext(ResponseBody body) {
                        try {
                            Log.e("msg", "未接单" + body.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initRvDataNoPay() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(AllOrderInfoApi.class)
                .getNotPayMailOrder(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<ResponseBody>(getActivity(), true, "订单获取中...") {
                    @Override
                    public void _doNext(ResponseBody body) {
                        try {
                            Log.e("msg", "未支付" + body.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
