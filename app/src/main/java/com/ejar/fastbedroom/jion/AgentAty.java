package com.ejar.fastbedroom.jion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.baseframe.utils.sp.SpUtils;
import com.ejar.fastbedroom.Api.UserCenterApi;
import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.AtyAgentBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.register.aty.ChooseSchoolAty;
import com.ejar.fastbedroom.utils.TU;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/9/18.
 */

public class AgentAty extends BaseActivity<AtyAgentBinding> {
    private String agentName;
    private String agentQqNumber;
    private String agentPhone;
    private String agentZw;
    private String  agentSchool;
    private int  agentSchoolId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_agent);
        initTitle();
        setText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        agentSchool = (String) SpUtils.get(AgentAty.this,"agentSchoolName","");
        agentSchoolId = (int) SpUtils.get(AgentAty.this,"agentSchoolId",-1);
        bindingView.agentChooseSchool.setText(agentSchool);
    }

    private void setText() {
        bindingView.agentResponsibility.setText("组建自己的校园团队可以锻炼你的领导能力，拥有责任心\n" +
                "快到寝宣传推广可以锻炼你的思维，提高你的人际交往\n" +
                "快递分配的优化可以锻炼你的敏锐度，从而更好的处理身边的事情\n" +
                "活动策划可以锻炼你的策划能力\n" +
                "1.你能实现经济独立,不再向自己的父母索取生活费\n" +
                "2.丰富自己的大学生活，使大学过得更加有意义\n" +
                "3.认识更多的大学同学，接触更多有能力的人\n\n\n" +
                "何不让自己的大学生活过得更有意义\n" +
                "久而久之你会感谢自己当初选择了我们\n" +
                "辉煌腾达的时候就会想起自己当初艰苦奋斗的岁月");
    }

    private void initTitle() {
        setTitle("成为校园负责人");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {
            finish();
        });
        bindingView.agentConfirm.setOnClickListener(clickListener);
        bindingView.agentChooseSchool.setOnClickListener(clickListener);
    }


    View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.agent_confirm:
                checkInputInfo();
                break;
            case R.id.agent_choose_school:
                Intent intent = new Intent(AgentAty.this, ChooseSchoolAty.class);
                intent.putExtra("agentAty","agentAty");
                startActivity(intent);
                break;
        }
    };

    private void checkInputInfo() {
        agentName = bindingView.agentName.getText().toString().trim();
        agentQqNumber = bindingView.agentQqNumber.getText().toString().trim();
        agentPhone = bindingView.agentPhone.getText().toString().trim();
        agentZw = bindingView.agentZw.getText().toString().trim();
        if (TextUtils.isEmpty(agentName)) {
            TU.cT("请输入名字");
            return;
        }
        if (TextUtils.isEmpty(agentQqNumber)) {
            TU.cT("请输入QQ号码");
            return;
        }
        if (TextUtils.isEmpty(agentPhone)) {
            TU.cT("请输入电话号码");
            return;
        }
        if (TextUtils.isEmpty(agentZw)) {
            TU.cT("请输入职位");
            return;
        }
        if (TextUtils.isEmpty(agentSchool)) {
            TU.cT("请选择学校");
            return;
        }

        NetRequest.getInstance(UrlConfig.baseUrl).create(UserCenterApi.class)
                .toBeAgent(agentSchoolId, "2017", agentName, agentPhone, agentQqNumber, agentZw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<BaseBean>(this, true, "信息提交中...") {
                    @Override
                    public void _doNext(BaseBean baseBean) {
                        if (baseBean.getCode().equals("200")) {
                            TU.cT("提交成功");
                        } else if (baseBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(AgentAty.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(baseBean.getMsg() + " ");
                        }
                    }
                });
    }
}
