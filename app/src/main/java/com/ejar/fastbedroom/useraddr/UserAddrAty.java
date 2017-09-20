package com.ejar.fastbedroom.useraddr;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyUserAddrBinding;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.useraddr.bean.AddressBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/8.
 */

public class UserAddrAty extends BaseActivity<AtyUserAddrBinding> {

    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_user_addr);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserAddress();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_user_address, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        openNextActivity(UserAddAddressAty.class);
        return true;
    }

    private void init() {
        setTitle("收货地址");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });


    }

    private void getUserAddress() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .getUserAddress(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<AddressBean>(this, true,"") {
                    @Override
                    public void _doNext(AddressBean addressBean) {
                        if(addressBean.getCode().equals("200")){
                            setAddrList(addressBean.getData());
                        }
                    }
                });
    }

    private void setAddrList(List<AddressBean.DataBean> data) {
        adapter = new MyRecyclerViewAdapter(this,R.layout.item_address, data) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.user_address_name, data.get(position).getReceivename());
                holder.setText(R.id.user_address_phone, data.get(position).getReceivetel());
                holder.setText(R.id.user_address, data.get(position).getReceivesite());
                TextView tv = holder.getView(R.id.user_address_default);
                if(data.get(position).getState() == 2){
                    Drawable drawableLeft = getResources().getDrawable(R.drawable.icon_default_address);
                    drawableLeft.setBounds( 0, 0, drawableLeft.getMinimumWidth() , drawableLeft.getMinimumHeight());
                    tv.setCompoundDrawables(drawableLeft, null, null,null);
                    tv.setClickable(false);
                    holder.setText(R.id.user_address_default,"默认地址");
                    SpUtils.put(UserAddrAty.this, "defaultAddr",
                            data.get(position).getDpareaname() + data.get(position).getReceivesite() );
                    SpUtils.put(UserAddrAty.this, "defaultAddrId",
                    data.get(position).getId() );
                    //存储默认地址信息
                    SpUtils.put(UserAddrAty.this, "defaultUserAddrId",
                            data.get(position).getId() );
                    SpUtils.put(UserAddrAty.this, "defaultAddrTel",
                            data.get(position).getReceivetel() );
                    SpUtils.put(UserAddrAty.this, "defaultAddrName",
                            data.get(position).getReceivename() );
                    SpUtils.put(UserAddrAty.this, "defaultAddrArea",
                            data.get(position).getDpareaname() );
                    SpUtils.put(UserAddrAty.this, "defaultAddrDoor",
                            data.get(position).getReceivesite() );
//                    RxBus.getDefault().post(RxEventCodeType.CONTACT_REFRESH,data.get(position).getDpareaname());
                }
                holder.setOnClickListener(R.id.user_address_default, v -> {//设置默认
                    setDefaultAddress(data.get(position));
                });
                holder.setOnClickListener(R.id.user_address_change, v -> {//编辑
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("changeAddress", data.get(position));
                    openNextActivity(UserAddAddressAty.class, bundle);
                });
                holder.setOnClickListener(R.id.user_address_delete, v -> {//删除
                    deleteUserAddress(data.get(position).getId());
                    data.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemChanged(0, data);
                });
            }

        };
        bindingView.userAddress.setLayoutManager(new LinearLayoutManager(this));
        bindingView.userAddress.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void deleteUserAddress(int id) {
        NetRequest.getInstance(UrlConfig.baseUrl)
                .create(UserCenterApi.class)
                .deleteUserAddress(APP.token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, false,"") {
                    @Override
                    public void _doNext(BaseBean result) {
                            //直接调用删除接口 未作处理
                        Log.e("msg",result.getCode() + result.getMsg());
                    }
                });
    }

    private void setDefaultAddress(AddressBean.DataBean bean) {
        NetRequest.getInstance(UrlConfig.baseUrl)
                .create(UserCenterApi.class)
                .setDefaultUserAddress(APP.token, bean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this,false,"") {
                    @Override
                    public void _doNext(BaseBean result) {
                        if(result.getCode().equals("200")){
                            TU.cT("设置默认地址成功");
                            SpUtils.put(UserAddrAty.this, "defaultUserAddrId",
                                    bean.getId() );
                            SpUtils.put(UserAddrAty.this, "defaultAddrTel",
                                    bean.getReceivetel() );
                            SpUtils.put(UserAddrAty.this, "defaultAddrName",
                                    bean.getReceivename() );
                            SpUtils.put(UserAddrAty.this, "defaultAddrArea",
                                    bean.getDpareaname() );
                            SpUtils.put(UserAddrAty.this, "defaultAddrDoor",
                                    bean.getReceivesite() );
                            UserAddrAty.this.finish();
                        }else {
                            TU.cT("" + result.getMsg());
                        }
                    }
                });
    }


}
