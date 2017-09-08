package com.ejar.fastbedroom.register.aty;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.NetDialog;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyChooseSchoolBinding;
import com.ejar.fastbedroom.register.adapter.MyAdapter;
import com.ejar.fastbedroom.register.bean.PointBean;
import com.ejar.fastbedroom.register.bean.School;
import com.ejar.fastbedroom.register.bean.SchoolBean;
import com.ejar.fastbedroom.register.model.RegisterApi;
import com.ejar.fastbedroom.register.view.PinyinComparator;
import com.ejar.fastbedroom.register.view.PinyinUtils;
import com.ejar.fastbedroom.register.view.TitleItemDecoration;
import com.ejar.fastbedroom.register.view.WaveSideBarView;
import com.ejar.fastbedroom.schoolpoint.TobeSchoolPoinAty;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by json on 2017/8/24.
 */

public class ChooseSchoolAty extends BaseActivity<AtyChooseSchoolBinding> {
//    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式


    private LinearLayoutManager manager;
    private MyAdapter mAdapter;
    private TitleItemDecoration mDecoration;
    private String searchTime;
    private List<School> mList = new ArrayList<>();
    private PinyinComparator mComparator;
    private List<PointBean.DataBean> pointList = new ArrayList<>();
    private int pointID;
    private String schoolName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_choose_school);
        dialog = NetDialog.createDialog(this, "数据处理中...");
        setTitle("选择学校");
        setNavigationOnClickListener(v -> {
            finish();
        });
        searchTime = (String) SpUtils.get(this, "searchSchoolTime", "");
        requestData(searchTime);
    }

    private void requestData(String time) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(RegisterApi.class).querySchool("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<SchoolBean>(this, false, "") {
                    @Override
                    public void _doNext(SchoolBean schoolBean) {
                        if (schoolBean.getCode().equals("200")) {
                            if (schoolBean.getData().getScholl().size() > 0) {
                                mList.addAll(filledData(schoolBean.getData().getScholl()));
                                SpUtils.put(ChooseSchoolAty.this, "searchSchoolTime"
                                        , schoolBean.getData().getTime());
                            } else {
                                mList.addAll(DataSupport.findAll(School.class));
                            }
                            setView();
                        }
                    }
                });
    }

    private void setView() {
        manager = new LinearLayoutManager(this);
        mComparator = new PinyinComparator();
        Collections.sort(mList, mComparator);
        mAdapter = new MyAdapter(this, mList);
        bindingView.schoolRv.setLayoutManager(manager);
        bindingView.schoolRv.setAdapter(mAdapter);
        mDecoration = new TitleItemDecoration(this, mList);
        //渲染字母TITLE
        bindingView.schoolRv.addItemDecoration(mDecoration);
        bindingView.schoolRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        bindingView.schoolSideBar.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        bindingView.schoolEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(s.toString());
            }
        });
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                schoolName = mList.get(position).getSchollName();
                requestSchoolPoint(mList.get(position).getSchoolId());
            }
        });
        NetDialog.closeDialog(dialog);
    }

    /**
     * 请求学校代理点
     *
     * @param schoolId
     */
    private void requestSchoolPoint(int schoolId) {
        NetRequest.getInstance(UrlConfig.baseUrl).create(RegisterApi.class)
                .querySchoolPoint(schoolId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<PointBean>(this, true, "") {
                    @Override
                    public void _doNext(PointBean pointBean) {

                        if (pointBean.getCode().equals("200")) {
                            if (pointBean.getData().size() > 0) {
                                pointList.clear();
                                pointList.addAll(pointBean.getData());
                                showChoosePointDialog();
                            }
                        }else if(pointBean.getCode().equals("201")){
                                showNoPointDialog();
                        }
                    }
                });
    }

    private void showNoPointDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("该校尚未有代理点,是否要成为代理人");
        builder.setCancelable(true);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                openNextActivity(TobeSchoolPoinAty.class);
            }
        });
        builder.create();
        dialog = builder.show();
    }

    private void showChoosePointDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.year_rl, null);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.start_year_rv);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, R.layout.item_school, pointList) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                holder.setText(R.id.school_name, pointList.get(position).getAgentname());
                holder.setOnClickListener(R.id.school_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pointID = pointList.get(position).getId();
                        SpUtils.put( ChooseSchoolAty.this,"schoolPoint", pointID);
                        SpUtils.put( ChooseSchoolAty.this,"schoolName", schoolName);
                        dialog.dismiss();
                        ChooseSchoolAty.this.finish();

                    }
                });
            }
        };
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        showDialog(view);

    }

    /**
     * 模糊查询 返回数据结果
     * @param s
     */
    private void filterData(String s) {
        List<School> searchList = new ArrayList<>();
        if (TextUtils.isEmpty(s)) {
            searchList.addAll(DataSupport.findAll(School.class));
        } else {
            searchList.addAll(DataSupport.where("schollName like ?", "%" + s
                    + "%").find(School.class));
        }
        Collections.sort(searchList, mComparator);
        mList.clear();
        mList.addAll(searchList);

        mAdapter.notifyDataSetChanged();
    }

    private Dialog dialog;

    /**
     * 网络获取的学校没有首字母拼音  对象只有 id  name
     * 此方法获取学校首字母拼音 重新赋值的对象里面包含  id  name  letter
     *
     * @param data
     * @return
     */
    private List<School> filledData(List<School> data) {
//        dialog = NetDialog.createDialog(this,"shujuchuli");
        List<School> lettersList = new ArrayList<>();
        if (DataSupport.findAll(School.class).size() > 0) {
            DataSupport.deleteAll(School.class);
        } else {
            requestData(searchTime);//用户误删数据库 数据
        }
        for (int i = 0; i < data.size(); i++) {
            School school = new School();
            String pinyin = PinyinUtils.getPingYin(data.get(i).getSchollName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                school.setLetters(sortString.toUpperCase());
//                sortModel.setLetters(sortString.toUpperCase());
            } else {
                school.setLetters("#");
            }
            school.setSchollName(data.get(i).getSchollName());
            school.setSchoolId(data.get(i).getSchoolId());
            school.save();
            lettersList.add(school);
        }
//
        return lettersList;
    }


    public void showDialog(View view) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        dialog = builder.show();
    }
}
