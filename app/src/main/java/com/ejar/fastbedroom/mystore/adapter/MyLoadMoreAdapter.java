package com.ejar.fastbedroom.mystore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejar.baseframe.R;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.mystore.bean.RowBean;

import java.util.List;

/**
 * Created by json on 2017/9/26.
 */

//public class MyLoadMoreAdapter extends RecyclerView.Adapter<MyViewHolder> {

//    private List<RowBean> dataList;
//    private Context context;
//
//    public MyLoadMoreAdapter(List<RowBean> dataList, Context context) {
//        this.dataList = dataList;
//        this.context = context;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false));
//        return myViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.goodsTitle.setText("" + dataList.get(position).getName());
//        holder.goodsContent.setText(dataList.get(position).getContent());
//        holder.goodsPrice.setText( dataList.get(position).getShopPrice() + "元/"
//                + dataList.get(position).getUnit());
//        Glide.with(context).load(UrlConfig.baseUrl + dataList.get(position).getImg())
//                .error(com.ejar.fastbedroom.R.drawable.defult_img).into(holder.iv);
//        holder.goodsNumber.setText(dataList.get(position).getBookNumber()+"");
//        holder.setOnClickListener(R.id.goods_add_number, clickListener);
//        holder.setOnClickListener(R.id.goods_cut_number, clickListener);
//    }

//    public class MyViewHolder extends RecyclerView.ViewHolder{
//        private SparseArray<View> views;
//        private View mConvertView;
//        private TextView goodsTitle, goodsContent, goodsPrice, goodsNumber;
//        private ImageView iv, addNumber, cutNumber;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            views = new SparseArray<>();
//            mConvertView = itemView;
//            goodsTitle = (TextView) itemView.findViewById(R.id.goods_title);
//            goodsContent = (TextView) itemView.findViewById(R.id.goods_content);
//            goodsPrice = (TextView) itemView.findViewById(R.id.goods_price);
//            goodsNumber = (TextView) itemView.findViewById(R.id.goods_price);
//            iv = (ImageView) itemView.findViewById(R.id.goods_img);
//            addNumber = (ImageView) itemView.findViewById(R.id.goods_add_number);
//            cutNumber = (ImageView) itemView.findViewById(R.id.goods_cut_number);
//        }
//
//        public MyViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
//            View view = getView(viewId);
//            view.setOnClickListener(listener);
//            return this;
//        }
//
//        public <T extends View> T getView(int viewId) {
//            View view = views.get(viewId);
//            if (view == null) {
//                view = mConvertView.findViewById(viewId);
//                views.put(viewId, view);
//            }
//            return (T) view;
//        }
//    }

//    @Override
//    public int getItemCount() {
//        return (dataList == null || dataList.size() == 0)?0:dataList.size();
//    }
//}
