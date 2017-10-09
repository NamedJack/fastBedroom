package com.ejar.fastbedroom.personal.aty;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.utils.TU;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyCashBinding;
import com.ejar.fastbedroom.personal.bean.CashBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/27.
 * 提现aty
 */

public class CashAty extends BaseActivity<AtyCashBinding> {
    private double accountMoney = 0.0d;
    private AlertDialog dialog;
    private int witchAccount = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_cash);
        Intent intent = getIntent();
        accountMoney = intent.getDoubleExtra("accountMoney", 0.0);
        bindingView.userAppAccountMoney.setText("￥ " + accountMoney);
        initTitle();
        setListener();
    }

    private void setListener() {
        bindingView.chooseAccount.setOnClickListener(clickListener);
        bindingView.takeCash.setOnClickListener(clickListener);
    }

    private void initTitle() {
        setTitle("提现");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
    }


    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.choose_account:
                showCashAccountChoose();
                break;
            case R.id.take_cash:
                postTakeCash();
                break;
        }
    };

    private void postTakeCash() {
        String money = bindingView.cashNumber.getText().toString().trim();
        String accountName = bindingView.accountName.getText().toString().trim();
        String accountNumber = bindingView.accountNumber.getText().toString().trim();
        String pwd = bindingView.accountAppPwd.getText().toString().trim();
        if (witchAccount == -1) {
            TU.cT("请选择提现账户");
            return;
        }
        if (TextUtils.isEmpty(money)) {
            TU.cT("请输入提现金额");
            return;
        }
        if (TextUtils.isEmpty(accountName)) {
            TU.cT("请输入提现账户昵称");
            return;
        }
        if (TextUtils.isEmpty(accountNumber)) {
            TU.cT("请输入提现账户帐号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            TU.cT("请输入输入当前APP用户密码");
            return;
        }
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .takeCash(APP.token, accountNumber, accountName, witchAccount, pwd, Double.parseDouble(money))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<CashBean>(this, true, "提交中..") {
                    @Override
                    public void _doNext(CashBean cashBean) {
                        if (cashBean.getCode().equals("200")) {
                            TU.cT("申请提现成功");
                            finish();
                        } else {
                            TU.cT("" + cashBean.getMsg());
                        }
                    }
                });
    }

    private void showCashAccountChoose() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_choose_accoun, null, false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.account_choose);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                bindingView.witchAccount.setText(radioButton.getText().toString());
                if ("微信".equals(radioButton.getText().toString())) {
                    witchAccount = 2;
                    dialog.dismiss();
                } else if ("支付宝".equals(radioButton.getText().toString())) {
                    witchAccount = 1;
                    dialog.dismiss();
                }
            }
        });
        showDialog(view);
    }


    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        dialog = builder.show();
    }
}
