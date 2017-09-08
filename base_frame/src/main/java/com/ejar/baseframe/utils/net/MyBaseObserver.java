package com.ejar.baseframe.utils.net;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.baseframe.utils.toast.TU;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by json on 2017/8/31.
 */

public abstract class MyBaseObserver<T> implements Observer<T> {

    private Context context;
    private boolean isShowDialog;
    private String errorCode = "";
    private String errorMsg = "";
    private static final String RESPONSE_CODE_FAILED = -1 + "";
    private Dialog dialog;

    public MyBaseObserver(Context context, boolean isShowDialog, String tips) {
        this.context = context;
        if(isShowDialog){
            if(tips.equals("") || tips == null){
                tips = "数据请求中,请稍后...";
            }
            dialog = NetDialog.createDialog(context, tips);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if(!NetUtils.isNetworkConnected(context)){
            TU.cT("当前网络不可用,请检查网络设置");
            return;
        }
    }

    @Override
    public void onNext(T t) {
        NetDialog.closeDialog(dialog);
        _doNext(t);
    }

    @Override
    public void onError(Throwable e) {
        NetDialog.closeDialog(dialog);
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorCode = httpException.code() + "";
            errorMsg = httpException.getMessage();
            getErrorMsg(httpException);
        } else if( e instanceof SocketTimeoutException) { //VPN open
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "服务器响应超时"; }
            _doError(errorCode, errorMsg);
    }

    private void _doError(String errorCode, String errorMsg) {
        if(errorCode.equals("-1")){
            TU.cT(errorMsg + "");
        }else {
            Log.e("netError",errorCode + " " +errorMsg );
        }
    }

    private void getErrorMsg(HttpException httpException) {

    }

    @Override
    public void onComplete() {

    }


    public abstract void _doNext(T t);
//    public abstract void _doError(String code, String msg);



}
