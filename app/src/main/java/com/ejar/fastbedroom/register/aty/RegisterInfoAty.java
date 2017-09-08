package com.ejar.fastbedroom.register.aty;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ejar.baseframe.base.aty.AppManager;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.NetWork;
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
import com.ejar.fastbedroom.register.bean.SchoolBean;
import com.ejar.fastbedroom.register.model.IRegisterInfo;
import com.ejar.fastbedroom.register.model.RegisterApi;
import com.ejar.fastbedroom.register.presenter.InfoAtyPresenter;
import com.ejar.fastbedroom.register.view.HintSideBar;
import com.ejar.fastbedroom.register.view.SideBar;

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

public class RegisterInfoAty extends BaseActivity<AtyRegisterInfoBinding> implements IRegisterInfo {
    private InfoAtyPresenter mPresenter;
    private String startYear = "", pwd = "", confirmPwd = "", userName = "", userPhone ="";
    private int sex = -1, schoolId = -1; // 1.男  2.女 , -1 未选
    private AlertDialog dialog;
    private String time; //查询学校 需要传入的时间 xxxx-xx-xx xx:xx:xx
    private MyRecyclerViewAdapter adapter;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_register_info);
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle = intent.getExtras();
//        userPhone = bundle.getString("userPhone");
        init();
    }

    private void init() {
        mPresenter = new InfoAtyPresenter(this, this);
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
                mDialog = NetDialog.createDialog(this,"列表获取中...");
                time = (String) SpUtils.get(this, "queryTime", "");
                mPresenter.searchSchool(time);
                break;
            case R.id.choose_start_year:
                showYearDialog();
                break;
            case R.id.choose_sex:
                showSexDialog();
                break;
            case R.id.register_commit:
//                AppManager.removeAllAty();
//                openNextActivity(LoginActivity.class);
                confirmRegisterInfo();
                break;
            default:
                break;
        }
    };

    @Override
    public void cancelDialog(){
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
     * 学校选择
     *
     * @param schoolList
     */
    @Override
    public void showSchoolDialog(List<SchoolBean.DataBean.SchollBean> schoolList) {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        View view = LayoutInflater.from(this).inflate(R.layout.school_list_view, null, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.school_rv);
        HintSideBar hintSideBar = (HintSideBar) view.findViewById(R.id.hint_side_bar);
        MyAdapter adapter1 = new MyAdapter(this);
        Collections.sort(schoolList);
        adapter1.setData(schoolList);
        adapter1.setItemClickListener(new MyAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                bindingView.chooseSchool.setText(schoolList.get(postion).getSchollName());
                schoolId = schoolList.get(postion).getId();
                dialog.dismiss();
            }
        });
        hintSideBar.setOnChooseLetterChangedListener(new SideBar.OnChooseLetterChangedListener() {
            @Override
            public void onChooseLetter(String s) {
                int i = adapter1.getFirstPositionByChar(s.charAt(0));
//                Log.e("msg",s + "首字母"+ i);
                if (i == -1) {
                    return;
                }
                manager.scrollToPositionWithOffset(i, 0);
            }

            @Override
            public void onNoChooseLetter() {

            }
        });
        rv.setAdapter(adapter1);
        rv.setLayoutManager(manager);
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
        if (schoolId == -1) {
            TU.cT("请选择学校");
            return;
        } else if ("".equals(startYear)) {
            TU.cT("请选择入学年份");
            return;
        } else if ("".equals(pwd) || "".equals(confirmPwd)) {
            TU.cT("请输入密码");
            return;
        } else if ("".equals(userName)) {
            TU.cT("请输入用户昵称");
            return;
        } else if (sex == -1) {
            TU.cT("请选择性别");
            return;
        } else if (!pwd.equals(confirmPwd)) {
            TU.cT("两次输入密码不一致");
            return;
        }

//        openNextActivity(HomeActivity.class);
        userPhone = (String) SpUtils.get(APP.getInstance(),"userName","");
        mDialog = NetDialog.createDialog(this,"注册中...");
        NetWork.getInstance(UrlConfig.baseUrl).create(RegisterApi.class)
                .doRegister(userPhone, schoolId, startYear, pwd, confirmPwd, userName, sex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConfirmRegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ConfirmRegisterBean confirmRegisterBean) {
                        NetDialog.closeDialog(mDialog);
                        if(confirmRegisterBean.getCode().equals("200")){
                            TU.cT("注册成功");
                            SpUtils.put(APP.getInstance(),"userName",userPhone);
                            AppManager.removeAllAty();
                            openNextActivity(LoginActivity.class);
                        }else {
                            TU.cT(""+ confirmRegisterBean.getMsg() + confirmRegisterBean.getCode());
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
