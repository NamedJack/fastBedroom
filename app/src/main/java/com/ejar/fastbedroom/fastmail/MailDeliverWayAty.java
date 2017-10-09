package com.ejar.fastbedroom.fastmail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyMailDeliverWayBinding;
import com.ejar.fastbedroom.fastmail.bean.MailWayBean;
import com.ejar.fastbedroom.fastmail.bean.MailWayWarrper;
import com.ejar.fastbedroom.register.adapter.MyAdapter;
import com.ejar.fastbedroom.register.view.PinyinUtils;
import com.ejar.fastbedroom.register.view.WaveSideBarView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/12.
 */

public class MailDeliverWayAty extends BaseActivity<AtyMailDeliverWayBinding> {
    private List<MailWayBean> mailWays = new ArrayList<>();
    private MailWayAdapter adapter;
    private LinearLayoutManager layoutManager;
    private PinyinComparatorMail mComparator;
    private TitleItemDecorationMail mDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_mail_deliver_way);
        initTitle();
        initData();
    }

    private void initTitle() {
        setTitle("快递选择");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {finish();});
        layoutManager = new LinearLayoutManager(this);
    }

    private void initData() {
        NetRequest.getInstance(UrlConfig.baseUrl)
                .create(UserCenterApi.class).getMailWay(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<MailWayWarrper>(this,true,"快递公司获取中") {
                    @Override
                    public void _doNext(MailWayWarrper mailWayWarrper) {
                        if(mailWayWarrper.getCode().equals("200")){
                            mailWays.addAll(filledData(mailWayWarrper.getData()));
                            setView();
                        }
                    }
                });

    }

    private List<MailWayBean> filledData(List<MailWayBean> data) {
//        dialog = NetDialog.createDialog(this,"shujuchuli");
        if(data.size() ==0 || data == null){ return null;}
        List<MailWayBean> lettersList = new ArrayList<>();
        if (DataSupport.findAll(MailWayBean.class).size() > 0) {
            DataSupport.deleteAll(MailWayBean.class);
        } else {
//            requestData(searchTime);//用户误删数据库 数据
        }
        for (int i = 0; i < data.size(); i++) {
            MailWayBean mail = new MailWayBean();
            String pinyin = PinyinUtils.getPingYin(data.get(i).getExpressname());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                mail.setLetters(sortString.toUpperCase());
//                sortModel.setLetters(sortString.toUpperCase());
            } else {
                mail.setLetters("#");
            }
            mail.setExpressname(data.get(i).getExpressname());
            mail.setMaidId(data.get(i).getMaidId());
            mail.save();
            lettersList.add(mail);
        }
//
        return lettersList;
    }

    private void setView() {
        mComparator = new PinyinComparatorMail();
        Collections.sort(mailWays, mComparator);
        adapter = new MailWayAdapter(this, mailWays);
        bindingView.mailRv.setLayoutManager(layoutManager);
        bindingView.mailRv.setAdapter(adapter);
        mDecoration = new TitleItemDecorationMail(this, mailWays);
        //渲染字母TITLE
        bindingView.mailRv.addItemDecoration(mDecoration);
        bindingView.mailRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        bindingView.mailSideBar.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = adapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        bindingView.mailEt.addTextChangedListener(new TextWatcher() {
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
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SpUtils.put(MailDeliverWayAty.this,"mailName", mailWays.get(position).getExpressname());
                SpUtils.put(MailDeliverWayAty.this,"mailId", mailWays.get(position).getMaidId());
                MailDeliverWayAty.this.finish();
            }
        });
    }

    private void filterData(String s) {
        List<MailWayBean> searchList = new ArrayList<>();
        if (TextUtils.isEmpty(s)) {
            searchList.addAll(DataSupport.findAll(MailWayBean.class));
        } else {
            searchList.addAll(DataSupport.where("expressname like ?", "%" + s
                    + "%").find(MailWayBean.class));
        }
        Collections.sort(searchList, mComparator);
        mailWays.clear();
        mailWays.addAll(searchList);

        adapter.notifyDataSetChanged();
    }
}
