package com.ejar.fastbedroom.mybook.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.databinding.AtyBookBinding;
import com.ejar.fastbedroom.mybook.frgment.MailFragment;
import com.ejar.fastbedroom.mybook.frgment.MyStoreFragment;
import com.ejar.fastbedroom.mybook.frgment.OutSentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2017/8/28.
 * home 快递领取 进去
 */

public class BookActivity extends BaseActivity<AtyBookBinding> {
    private List<Fragment> fragments;
    private MyBookAdaper adaper;
    private MailFragment mailFragment;
    private OutSentFragment outSentFragment;
    private MyStoreFragment myStoreFragment;
    private int codeAty;
    private Bundle bundle;

    private String[] titles = {"自营超市","外卖配送","快递领取"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_book);

        Intent intent = getIntent();
        codeAty = intent.getIntExtra("view",-1);
        bundle = new Bundle();

        initTitle(codeAty);
        initFragments();

        if (savedInstanceState != null) {
            mailFragment = (MailFragment) getSupportFragmentManager().findFragmentByTag(MailFragment.class.getName());
            outSentFragment = (OutSentFragment) getSupportFragmentManager().findFragmentByTag(OutSentFragment.class.getName());
            myStoreFragment = (MyStoreFragment) getSupportFragmentManager().findFragmentByTag(MyStoreFragment.class.getName());
            getSupportFragmentManager().beginTransaction()
                    .hide(outSentFragment)
                    .hide(mailFragment)
                    .show(myStoreFragment)
                    .commit();
        } else {

            mailFragment = new MailFragment();
            mailFragment.setArguments(bundle);
            myStoreFragment = new MyStoreFragment();
            myStoreFragment.setArguments(bundle);
            outSentFragment = new OutSentFragment();
            outSentFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.my_book_pager, mailFragment, MailFragment.class.getName())
                    .add(R.id.my_book_pager, myStoreFragment, MyStoreFragment.class.getName())
                    .add(R.id.my_book_pager, outSentFragment, OutSentFragment.class.getName())
                    .hide(outSentFragment)
                    .hide(mailFragment)
                    .show(myStoreFragment)
                    .commit();
        }

    }

    private void initFragments() {
        fragments = new ArrayList<>();
        MailFragment mailFragment = new MailFragment();
        mailFragment.setArguments(bundle);

        OutSentFragment outSentFragment = new OutSentFragment();
        outSentFragment.setArguments(bundle);

        MyStoreFragment storeFragment = new MyStoreFragment();
        storeFragment.setArguments(bundle);

        fragments.add(storeFragment);
        fragments.add(outSentFragment);
        fragments.add(mailFragment);
        adaper = new MyBookAdaper(getSupportFragmentManager());
        bindingView.myBookPager.setAdapter(adaper);
        bindingView.myBookTab.setupWithViewPager(bindingView.myBookPager);
    }

    private void initTitle(int codeAty) {
        switch (codeAty){
            case 1:
                bundle.putString("whichAty", "未支付");
                setTitle("未支付");
                break;
            case 2:
                bundle.putString("whichAty", "未接单");
                setTitle("未接单");
                break;
            case 3:
                bundle.putString("whichAty", "已支付");
                setTitle("已支付");
                break;
            case 4:
                bundle.putString("whichAty", "已完成");
                setTitle("已完成");
                break;
        }
        setHomeBackIcon(R.drawable.icon_back_buy_car);
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
