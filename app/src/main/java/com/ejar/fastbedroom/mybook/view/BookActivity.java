package com.ejar.fastbedroom.mybook.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.widget.TabHost;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.databinding.AtyBookBinding;
import com.ejar.fastbedroom.deliver.FastFrg;
import com.ejar.fastbedroom.message.MessageFrg;
import com.ejar.fastbedroom.mybook.frgment.NotPaidFragment;
import com.ejar.fastbedroom.mybook.frgment.WaitAcceptFragment;
import com.ejar.fastbedroom.mybook.frgment.WaitDeliverFragment;
import com.ejar.fastbedroom.personal.PersonFrg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2017/8/28.
 * home 快递领取 进去
 */

public class BookActivity extends BaseActivity<AtyBookBinding> {
    private List<Fragment> fragments;
    private MyBookAdaper adaper;
    private NotPaidFragment notPaidFragment;
    private WaitAcceptFragment waitAcceptFragment;
    private WaitDeliverFragment waitDeliverFragment;
    private int codeAty;

    private String[] titles = {"自营超市","外卖配送","快递领取"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_book);

        Intent intent = getIntent();
        codeAty = intent.getIntExtra("view",-1);


        initTitle(codeAty);
        initData();

        if (savedInstanceState != null) {
            notPaidFragment = (NotPaidFragment) getSupportFragmentManager().findFragmentByTag(NotPaidFragment.class.getName());
            waitAcceptFragment = (WaitAcceptFragment) getSupportFragmentManager().findFragmentByTag(WaitAcceptFragment.class.getName());
            waitDeliverFragment = (WaitDeliverFragment) getSupportFragmentManager().findFragmentByTag(WaitDeliverFragment.class.getName());
            getSupportFragmentManager().beginTransaction().show(notPaidFragment)
                    .hide(waitAcceptFragment)
                    .hide(waitDeliverFragment)
                    .commit();
        } else {
            notPaidFragment = new NotPaidFragment();
            waitDeliverFragment = new WaitDeliverFragment();
            waitAcceptFragment = new WaitAcceptFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.my_book_pager, notPaidFragment, NotPaidFragment.class.getName())
                    .add(R.id.my_book_pager, waitDeliverFragment, WaitDeliverFragment.class.getName())
                    .add(R.id.my_book_pager, waitAcceptFragment, WaitAcceptFragment.class.getName())
                    .hide(waitAcceptFragment)
                    .hide(waitDeliverFragment)
                    .commit();
        }

    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new NotPaidFragment());
        fragments.add(new WaitAcceptFragment());
        fragments.add(new WaitAcceptFragment());
        adaper = new MyBookAdaper(getSupportFragmentManager());
        bindingView.myBookPager.setAdapter(adaper);
        bindingView.myBookTab.setupWithViewPager(bindingView.myBookPager);
    }

    private void initTitle(int codeAty) {
        switch (codeAty){
            case 1:
                setTitle("未支付");
                break;
            case 2:
                setTitle("未接单");
                break;
            case 3:
                setTitle("已支付");
                break;
            case 4:
                setTitle("已完成");
                break;
        }

        setNavigationOnClickListener(v -> {finish();});

    }


    class MyBookAdaper extends FragmentPagerAdapter{
        public MyBookAdaper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
