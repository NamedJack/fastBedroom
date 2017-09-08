package com.ejar.fastbedroom.register.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.register.bean.SchoolBean;
import com.ejar.fastbedroom.register.view.PinyinUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2017/8/21.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyRvHolder> implements View.OnClickListener {


    private List<SchoolBean.DataBean.SchollBean> schoolList;
    private LayoutInflater inflater;
    private MyItemClickListener itemClickListener = null;

    public MyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        schoolList = new ArrayList<>();
    }

    @Override
    public MyRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_school, parent, false);
        MyRvHolder holder = new MyRvHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyRvHolder holder, int position) {
        holder.schoolName.setText(schoolList.get(position).getSchollName());
        holder.itemView.setTag(position);
    }

    public void setData(List<SchoolBean.DataBean.SchollBean> userList) {
        this.schoolList.clear();
        this.schoolList = userList;
    }

    /**
     * 用于获得列表中第一个首字母为sign的item位置
     *
     * @param sign
     * @return
     */
    public int getFirstPositionByChar(char sign) {
        if (sign == '#') {
            return 0;
        }
        for (int i = 0; i < schoolList.size(); i++) {

            String name = schoolList.get(i).getSchollName();
            char head = PinyinUtils.getHeadChar(name);
            if (head == sign) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return schoolList.size();
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    class MyRvHolder extends RecyclerView.ViewHolder {
        TextView schoolName;

        public MyRvHolder(View itemView) {
            super(itemView);
            schoolName = (TextView) itemView.findViewById(R.id.school_name);
        }

    }

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public static interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }


}
