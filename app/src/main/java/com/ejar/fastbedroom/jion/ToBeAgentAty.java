package com.ejar.fastbedroom.jion;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ejar.fastbedroom.base.BaseActivity;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.databinding.AtyToBeAgentBinding;

/**
 * Created by json on 2017/9/18.
 * 成为代理人Aty
 */

public class ToBeAgentAty extends BaseActivity<AtyToBeAgentBinding>{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_to_be_agent);
        initTitle();
        setMessageText();
    }

    private void setMessageText() {
        bindingView.agentTitle.setText("\"快到寝\"校园负责人");
        bindingView.content.setText("成为快到寝校园负责人后拥有该大学的运营管理权限，同时享受" +
                "每单百分之20的运营费，快到寝后续功能会持续更新，校园负责人可以享受新功能带来的福利。\n\n\n" +
                "校园负责人的职责\n" +
                "1.组建配送团队\n" +
                "2.快到寝宣传推广\n" +
                "3.合理优化快递员配送，根据本校情况制定配送方案\n" +
                "4.活动策划实施安排");


        bindingView.giveYouMessage.setText("  亲爱的同学您好，大学相当于半个社会了，如何正确" +
                "对待自己的大学。我认为在不耽搁学业的前提下可以去丰富自己的大学生活。大学毕业后面临的就是工作，" +
                "我不希望同学你在大学生活中过的迷茫，应当多做一些实践，应当每个阶段给自己指定一个目标,同时向着" +
                "自己的目标去努力");
    }

    private void initTitle() {
        setTitle("成为校园负责人");
        setHomeBackIcon(R.drawable.icon_back_buy_car);
        setNavigationOnClickListener(v -> {finish();});
        bindingView.toBeAgent.setOnClickListener(v -> {
            openNextActivity(AgentAty.class);
        });
    }
}
