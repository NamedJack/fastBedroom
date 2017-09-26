package com.ejar.fastbedroom.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.ejar.baseframe.base.aty.AppManager;
import com.ejar.baseframe.base.aty.BaseActivity;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.baseframe.utils.toast.TU;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.databinding.HomeActivityBinding;
import com.ejar.fastbedroom.deliver.FastFrg;
import com.ejar.fastbedroom.message.MessageFrg;
import com.ejar.fastbedroom.mystore.bean.RowBean;
import com.ejar.fastbedroom.personal.PersonFrg;

import org.litepal.crud.DataSupport;

/**
 * Created by json on 2017/8/14.
 */

public class HomeActivity extends BaseActivity<HomeActivityBinding> {
    private FastFrg fastFrg;
    private MessageFrg messageFrg;
    private PersonFrg personFrg;
    private FragmentManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        setTitle("快到寝");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        APP.token = (String) SpUtils.get(this, "token", "");
        manager = getSupportFragmentManager();
//        initFragment();
        if (savedInstanceState != null) {
            fastFrg = (FastFrg) getSupportFragmentManager().findFragmentByTag(FastFrg.class.getName());
            messageFrg = (MessageFrg) getSupportFragmentManager().findFragmentByTag(MessageFrg.class.getName());
            personFrg = (PersonFrg) getSupportFragmentManager().findFragmentByTag(PersonFrg.class.getName());
            getSupportFragmentManager().beginTransaction().show(fastFrg)
                    .hide(messageFrg)
                    .hide(personFrg)
                    .commit();
        } else {
            fastFrg = new FastFrg();
            messageFrg = new MessageFrg();
            personFrg = new PersonFrg();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.home_frg, fastFrg, FastFrg.class.getName())
                    .add(R.id.home_frg, messageFrg, MessageFrg.class.getName())
                    .add(R.id.home_frg, personFrg, PersonFrg.class.getName())
                    .hide(messageFrg)
                    .hide(personFrg)
                    .commit();
        }

        bindingView.homeBottom.setOnCheckedChangeListener(changeListener);
        bindingView.btnFast.setChecked(true);


    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (fastFrg != null) {
            transaction.hide(fastFrg);
        }
        if (messageFrg != null) {
            transaction.hide(messageFrg);
        }
        if (personFrg != null) {
            transaction.hide(personFrg);
        }
    }


    RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction transaction = manager.beginTransaction();
            hideAllFragment(transaction);
            switch (checkedId) {
                case R.id.btn_fast:
                    if (fastFrg == null) {
                        fastFrg = new FastFrg();
                        transaction.add(R.id.home_frg, fastFrg);
                    } else {
                        transaction.show(fastFrg);
                    }
                    setTitle("快到寝");
                    break;
                case R.id.btn_message:
                    if (messageFrg == null) {
                        messageFrg = new MessageFrg();
                        transaction.add(R.id.home_frg, messageFrg);
                    } else {
                        transaction.show(messageFrg);
                    }
                    setTitle("消息推送");
                    break;
                case R.id.btn_person:
                    if (personFrg == null) {
                        personFrg = new PersonFrg();
                        transaction.add(R.id.home_frg, personFrg);
                    } else {
                        transaction.show(personFrg);
                    }
                    setTitle("个人中心");
                    break;
                default:
                    break;
            }
            transaction.commit();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_home_right:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                TU.cT( "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                DataSupport.deleteAll(RowBean.class);
                AppManager.removeAllAty();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
