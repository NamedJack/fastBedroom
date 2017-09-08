package com.ejar.fastbedroom.register.aty;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ejar.baseframe.base.aty.AppManager;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyRegisterInfoBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.register.adapter.MyAdapter;
import com.ejar.fastbedroom.register.bean.ConfirmRegisterBean;
import com.ejar.fastbedroom.register.bean.School;
import com.ejar.fastbedroom.register.model.RegisterApi;
import com.ejar.fastbedroom.register.view.ClearEditText;
import com.ejar.fastbedroom.register.view.PinyinComparator;
import com.ejar.fastbedroom.register.view.TitleItemDecoration;
import com.ejar.fastbedroom.register.view.WaveSideBarView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/15.
 */

public class RegisterInfoAty extends BaseActivity<AtyRegisterInfoBinding> {
    private String startYear = "", pwd = "", confirmPwd = "", userName = "", userPhone = "";
    private int sex = -1; // 1.男  2.女 , -1 未选
    private AlertDialog dialog;
//    private String time; //查询学校 需要传入的时间 xxxx-xx-xx xx:xx:xx
    private MyRecyclerViewAdapter adapter;
    private Dialog mDialog;
    private MyAdapter myAdapter;
    private LinearLayoutManager manager;
    private PinyinComparator comparator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_register_info);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
       String name = (String) SpUtils.get(this,"schoolName","");
        bindingView.chooseSchool.setText(name);
    }

    private void init() {
        setNavigationOnClickListener(v -> {
            finish();
        });
        setTitle("完善资料");
        bindingView.chooseSchool.setOnClickListener(clickListener);
        bindingView.chooseStartYear.setOnClickListener(clickListener);
        bindingView.chooseSex.setOnClickListener(clickListener);
        bindingView.registerCommit.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.choose_school:
                openNextActivity(ChooseSchoolAty.class);
                break;
            case R.id.choose_start_year:
//                showYearDialog();
                break;
            case R.id.choose_sex:
                showSexDialog();
                break;
            case R.id.register_commit:
                confirmRegisterInfo();
                break;
            default:
                break;
        }
    };



    public void cancelDialog() {
        NetDialog.closeDialog(mDialog);
    }

    /**
     * 入学年份
     */
    private void showYearDialog() {
        List<String> yearList = new ArrayList<>();
        for (int i = 17; i > 10; i--) {
            yearList.add("20" + i);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.year_rl, null);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.start_year_rv);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, R.layout.item_school, yearList) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.school_name, yearList.get(position));
                holder.setOnClickListener(R.id.school_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bindingView.chooseStartYear.setText(yearList.get(position));
                        startYear = yearList.get(position);
                        dialog.dismiss();
                    }
                });
            }
        };
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        showDialog(view);
    }




    /**
     * 性别选择
     */
    private void showSexDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null, false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.sex_choose);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                bindingView.chooseSex.setText(radioButton.getText().toString());
                if ("男神".equals(radioButton.getText().toString())) {
                    sex = 1;
                    dialog.dismiss();
                } else if ("女神".equals(radioButton.getText().toString())) {
                    sex = 2;
                    dialog.dismiss();
                }

            }
        });
        showDialog(view);
    }

    /**
     * 确认注册
     */
    private void confirmRegisterInfo() {
        pwd = bindingView.registerPwd.getText().toString().trim();
        confirmPwd = bindingView.confirmRegisterPwd.getText().toString().trim();
        userName = bindingView.registerName.getText().toString().trim();
        int schoolId = (int) SpUtils.get(this,"schoolPoint", -1);
        if (schoolId == -1) {
            TU.cT("请选择学校");
            return;
        }  else if ("".equals(pwd) || "".equals(confirmPwd)) {
            TU.cT("请输入密码");
            return;
        } else if (!pwd.equals(confirmPwd)) {
            TU.cT("两次输入密码不一致");
            return;
        }else if ("".equals(userName)) {
            TU.cT("请输入用户昵称");
            return;
        } else if (sex == -1) {
            TU.cT("请选择性别");
            return;
        }

        userPhone = (String) SpUtils.get(APP.getInstance(), "userName", "");
        mDialog = NetDialog.createDialog(this, "注册中...");
        NetRequest.getInstance(UrlConfig.baseUrl).create(RegisterApi.class)
                .doRegister(userPhone, schoolId, pwd, confirmPwd, userName, sex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConfirmRegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ConfirmRegisterBean confirmRegisterBean) {
                        NetDialog.closeDialog(mDialog);
                        if (confirmRegisterBean.getCode().equals("200")) {
                            TU.cT("注册成功");
                            SpUtils.put(APP.getInstance(), "userName", userPhone);
                            AppManager.removeAllAty();
                            openNextActivity(LoginActivity.class);
                        } else {
                            TU.cT("" + confirmRegisterBean.getMsg() + confirmRegisterBean.getCode());
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


    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        dialog = builder.show();
    }


}
