package com.ejar.baseframe.base.aty;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ejar.baseframe.R;
import com.ejar.baseframe.databinding.AtyBaseBinding;

/**
 * Created by json on 2017/8/14.
 */

public class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {
    private AtyBaseBinding baseBinding;
    protected VB bindingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        baseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.aty_base, null, false);
        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        FrameLayout mContainer = (FrameLayout) baseBinding.getRoot().findViewById(R.id.sub_layout);
        mContainer.addView(bindingView.getRoot());
        getWindow().setContentView(baseBinding.getRoot());
        initView();
    }

    private void initView() {
        setSupportActionBar(baseBinding.baseToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        baseBinding.baseToolbar.setNavigationIcon(R.drawable.icon_base_back);
    }

    /**
     * 设置返回按钮图标
     *
     * @param drawable
     */
    public void setHomeBackIcon(int drawable) {
        baseBinding.baseToolbar.setNavigationIcon(drawable);
    }


    public void setNavigationOnClickListener(View.OnClickListener clickListener) {
        baseBinding.baseToolbar.setNavigationOnClickListener(clickListener);
    }

    public void setMenuItemClickListener(Toolbar.OnMenuItemClickListener clickListener) {
        baseBinding.baseToolbar.setOnMenuItemClickListener(clickListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * toolbar标题
     *
     * @param leftTitle
     */
    protected void setLeftTitle(String leftTitle) {
        baseBinding.baseToolbar.setTitle(leftTitle);
    }

    /**
     * aty中间标题
     *
     * @param title
     */
    public void setTitle(String title) {
        baseBinding.baseTitle.setText(title);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.removeActivity(this);
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
//        return super.onPrepareOptionsPanel(view, menu);
//    }


    public void openNextActivity(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openNextActivity(Class<?> pClass) {
        openNextActivity(pClass, null);
    }
}
