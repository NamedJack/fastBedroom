package com.ejar.fastbedroom.buycar;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.buycar.bean.BuyCarBean;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.mystore.bean.RowBean;

import java.util.List;

/**
 * Created by json on 2017/9/7.
 */

public class BuyCarAdapter extends BaseItemDraggableAdapter<BuyCarBean.DataBean, BaseViewHolder> {
    private Context context;

    public BuyCarAdapter(int layoutResId, @Nullable List<BuyCarBean.DataBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder holder, BuyCarBean.DataBean item) {
        holder.setText(R.id.goods_title, item.getName());
        holder.setText(R.id.goods_content, item.getName());
        holder.setText(R.id.goods_price, item.getShopPrice() + "元/"
                + item.getUnit());
        ImageView iv = holder.getView(R.id.goods_img);
        Glide.with(context).load(UrlConfig.baseUrl + item.getImg())
                .error(R.drawable.defult_img).into(iv);
        holder.setText(R.id.goods_number, item.getNumber()+"");
        holder.addOnClickListener(R.id.goods_add_number);
        holder.addOnClickListener(R.id.goods_cut_number);

    }





    OnItemSwipeListener swipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            mOnItemSwipeListener.clearView(viewHolder, getViewHolderPosition(viewHolder));
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };



}