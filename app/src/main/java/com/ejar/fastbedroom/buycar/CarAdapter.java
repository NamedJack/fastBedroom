package com.ejar.fastbedroom.buycar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.buycar.bean.BuyCarBean;

import java.util.List;

/**
 * Created by json on 2017/9/12.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyHolder> {

    private List<BuyCarBean.DataBean> list;
    private Context context;
    private LayoutInflater inflater;
    private OnItemListener itemLListener;


    public CarAdapter(List<BuyCarBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        MyHolder holder = null;
//        if(holder ==  null){
        View view = inflater.inflate(R.layout.item_rv, parent, false);
        MyHolder holder = new MyHolder(view);
//        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }


    public interface OnItemListener {
        void onItemListener(View view, int postion);
    }

    public void setOnItemListener(OnItemListener itemLListener) {
        this.itemLListener = itemLListener;
    }


}
