package com.ejar.fastbedroom.personal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ejar.fastbedroom.R;

import java.util.List;

/**
 * Created by json on 2017/8/24.
 */

public class MyListAdaper extends BaseAdapter {
    private List<String> list;
    private Context context;

    public MyListAdaper(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_dialog_lv, parent, false);
        TextView itemMethod = (TextView) view.findViewById(R.id.choose_item_name);
        itemMethod.setText(list.get(position));
        return view;
    }

}
