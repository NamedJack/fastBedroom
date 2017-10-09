package com.ejar.fastbedroom.useraddr;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyAddUserAddrBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.useraddr.bean.AddressBean;
import com.ejar.fastbedroom.useraddr.bean.SchoolAreaBean;
import com.ejar.fastbedroom.utils.TU;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/11.
 */

public class UserAddAddressAty extends BaseActivity<AtyAddUserAddrBinding> {
    private String addName;
    private int addAddr = -1;
    private String addPhone;
    private String addDoor;
    private AlertDialog dialog;
    private AddressBean.DataBean changeAddress;
    private int changedId = -1;
//    private List<SchoolAreaBean.DataBean> areaList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_add_user_addr);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        if(bundle != null){
            setTitle("修改收货地址");
            changeAddress = (AddressBean.DataBean) bundle.getSerializable("changeAddress");
            bindingView.addUserName.setText(changeAddress.getReceivename()+"");
            bindingView.addUserPhone.setText(changeAddress.getReceivetel() + "");
            bindingView.addConfirm.setText("确认修改");
            changedId = changeAddress.getId();
            init();
        }else {
            setTitle("增加收货地址");
            init();
        }



    }

    private void init() {
        setNavigationOnClickListener(v -> {finish();});
        bindingView.addConfirm.setOnClickListener(clickListener);
        bindingView.addUserAddr.setOnClickListener(clickListener);

    }


    View.OnClickListener clickListener = v -> {
        switch (v.getId()){
            case R.id.add_confirm:
                if(changedId == -1){
                    checkInputInfo();
                }else {
                    changeUserAddress(changedId);
                }

                break;
            case R.id.add_user_addr:
                getSchoolArea();
                break;
        }
    };

    private void changeUserAddress(int changedId) {
        addName = bindingView.addUserName.getText().toString().trim();
        addPhone = bindingView.addUserPhone.getText().toString().trim();
        addDoor = bindingView.addUserDoor.getText().toString().trim();

        if(addName.equals("") || addName == null){
            TU.cT("请填写收货人姓名");
            return;}
        if(addPhone.equals("") || addPhone == null){
            TU.cT("请填写收货人电话");
            return;}
        if(addAddr == -1){
            TU.cT("请选择收货地址");
            return;}
        if(addDoor.equals("") || addDoor == null){
            TU.cT("请输入门牌号");
            return;}
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .changeUserAddress(changedId, addName, addPhone, addDoor, addAddr, APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this,true,"修改地址中...") {
                    @Override
                    public void _doNext(BaseBean result) {
                        if(result.getCode().equals("200")){
                            TU.cT("修改成功");
                            UserAddAddressAty.this.finish();
                        } else if (result.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(UserAddAddressAty.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            TU.cT(result.getMsg());
                        }
                    }
                });
    }

    private void getSchoolArea() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getSchoolAreaAddress(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<SchoolAreaBean>(this,false,"") {
                    @Override
                    public void _doNext(SchoolAreaBean area) {
                        if(area.getCode().equals("200")){
                            showChooseDialog(area.getData());
                        } else if (area.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(UserAddAddressAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else if (area.getCode().equals("201")) {
                            TU.cT("暂时没有可选区域");
                        }else {
                            TU.cT("" + area.getMsg());
                        }
                    }
                });
    }

    private void showChooseDialog(List<SchoolAreaBean.DataBean> data) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_choos_address, null,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_item_area);
        rv.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, R.layout.item_school,data) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.school_name, data.get(position).getAreaname());
                holder.setOnClickListener(R.id.school_name, v -> {
                    addAddr = data.get(position).getId();
                    bindingView.addUserAddr.setText(data.get(position).getAreaname());
                    dialog.dismiss();
                });
            }
        };
        rv.setAdapter(adapter);
        showDialog(view);

    }

    private void checkInputInfo() {
        addName = bindingView.addUserName.getText().toString().trim();
        addPhone = bindingView.addUserPhone.getText().toString().trim();
        addDoor = bindingView.addUserDoor.getText().toString().trim();

        if(addName.equals("") || addName == null){
            TU.cT("请填写收货人姓名");
            return;}
        if(addPhone.equals("") || addPhone == null){
            TU.cT("请填写收货人电话");
            return;}
        if(addAddr == -1){
            TU.cT("请选择收货地址");
            return;}
        if(addDoor.equals("") || addDoor == null){
            TU.cT("请输入门牌号");
            return;}

        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .addUserAddress(addName, addPhone, addDoor, addAddr, APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this,true, "地址提交中") {
                    @Override
                    public void _doNext(BaseBean result) {
                        if(result.getCode().equals("200")){
                            UserAddAddressAty.this.finish();
                        } else if (result.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(UserAddAddressAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(result.getMsg()+"");
                        }
                    }
                });


    }

    private void showDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        dialog = builder.show();
    }

}
