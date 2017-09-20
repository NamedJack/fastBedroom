package com.ejar.fastbedroom.fastmail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyFastMailBinding;
import com.ejar.fastbedroom.fastmail.bean.PostMailBean;
import com.ejar.fastbedroom.pay.PayAty;
import com.ejar.fastbedroom.useraddr.UserAddrAty;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/28.
 * 快递领取
 *
 */

public class FastMailAty extends BaseActivity<AtyFastMailBinding> {
    private String name, tel, area, door, goodsCode, mailName;
    private AlertDialog dialog;
    private int userAddressId;
    private String[] codes = new String[10];
    private String remarks = "";
    private LinearLayout ll ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_fast_mail);
        initTitle();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAddress();
    }

    private void setAddress() {
        name = (String) SpUtils.get(this, "defaultAddrName", "");
        tel = (String) SpUtils.get(this, "defaultAddrTel", "");
        area = (String) SpUtils.get(this, "defaultAddrArea", "");
        door = (String) SpUtils.get(this, "defaultAddrDoor", "");
        userAddressId = (int) SpUtils.get(this, "defaultUserAddrId", -1);
        mailName = (String) SpUtils.get(this, "mailName", "");
        if (!name.equals("")) {
            bindingView.mailUserName.setText("收货人: " + name);
        }
        if (!tel.equals("")) {
            bindingView.mailUserTel.setText("电话: " + tel);
        }
        if (!area.equals("")) {
            bindingView.mailArea.setText("区域: " + area);
        }
        if (!door.equals("")) {
            bindingView.mailAddress.setText("地址: " + door);
        }
        if (!mailName.equals("")) {
            bindingView.mailChooseDeliverWay.setText(mailName + "");
        }

    }

    private void setListener() {
        bindingView.mailChooseAddress.setOnClickListener(clickListener);
        bindingView.mailChooseDeliverWay.setOnClickListener(clickListener);
        bindingView.mailAddCode.setOnClickListener(clickListener);
        bindingView.mailBookOrder.setOnClickListener(clickListener);
    }

    private void initTitle() {
        setTitle("填写订单");
        setNavigationOnClickListener(v -> {
            finish();
        });
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {finish();});
        ll = (LinearLayout) findViewById(R.id.mail_code_rl);
    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.mail_choose_address://选择收货地址
                openNextActivity(UserAddrAty.class);
                break;
            case R.id.mail_choose_deliver_way://选择快递
                if(!TextUtils.isEmpty(mailName)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("重新选择快递将会清空当前取货码，是否继续选择");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ll.removeAllViews();
                            openNextActivity(MailDeliverWayAty.class);
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.create();
                    builder.show();
                }else {
                    openNextActivity(MailDeliverWayAty.class);
                }
                break;
            case R.id.mail_add_code://收货码
                addChildView();
                break;
            case R.id.mail_book_order://下单
                checkOrderInfo();
                break;
        }
    };

    /**
     *提交订单
     */
    private void checkOrderInfo() {
        if (userAddressId == -1) {
            TU.cT("请选择收货地址" + "");
            return;
        }
        if (mailName.equals("")) {
            TU.cT("请选择快递公司" + "");
            return;
        }
        if (codes[0] == null) {
            TU.cT("请填写取货码" + "");
            return;
        }

        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .postMailOrder(userAddressId, mailName, codes, remarks, APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<PostMailBean>(this, true, "") {
                    @Override
                    public void _doNext(PostMailBean mailBean) {
                        if(mailBean.getCode().equals("200")){
                            Intent intent = new Intent(FastMailAty.this, PayAty.class);
                            intent.putExtra("aty","mail");
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("orderInfo", mailBean.getData());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void addChildView() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_input_code, null, false);
        EditText et = (EditText) view.findViewById(R.id.input_goods_code);
        Button post = (Button) view.findViewById(R.id.post_goods_code);
        post.setOnClickListener(v -> {
            goodsCode = et.getText().toString().trim();
            if (goodsCode.equals("") || goodsCode == null) {
                return;
            }
            for (int i = 0; i < codes.length; i++) {
                if (codes[i] == null) {
                    codes[i] = goodsCode;
//                    Log.e("msg",codes[i] +"");
                    break;
                }
            }

            dialog.dismiss();
            addTextView(goodsCode);
        });
        showDialog(view);
    }

    private void addTextView(String code) {
        View childView = LayoutInflater.from(this).inflate(R.layout.item_code_view, null, false);
        TextView tv = (TextView) childView.findViewById(R.id.item_code_view);
        tv.setText("取货码：" + code);
        tv.setOnClickListener(view -> {
            ll.removeView(childView);
        });
        ll.addView(childView);
    }

    private void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        dialog = builder.show();
    }
}
