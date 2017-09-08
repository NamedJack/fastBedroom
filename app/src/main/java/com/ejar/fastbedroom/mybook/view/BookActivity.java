package com.ejar.fastbedroom.mybook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

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
 */

public class BookActivity extends BaseActivity<AtyBookBinding> {
    private List<Fragment> fragments;
    private MyBookAdaper adaper;
    private NotPaidFragment notPaidFragment;
    private WaitAcceptFragment waitAcceptFragment;
    private WaitDeliverFragment waitDeliverFragment;


    private String[] titles = {"自营超市","外卖配送","快递领取"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_book);
        initTitle();
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

    private void initTitle() {
        setTitle("");
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
