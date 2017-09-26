//package com.ejar.fastbedroom.mystore.adapter;
//
//import android.content.Context;
//import android.support.annotation.Nullable;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.ejar.fastbedroom.R;
//import com.ejar.fastbedroom.buycar.bean.BuyCarBean;
//import com.ejar.fastbedroom.config.UrlConfig;
//import com.ejar.fastbedroom.mystore.bean.RowBean;
//
//import java.util.List;
//
///**
// * Created by json on 2017/9/1.
// */
//
//public class StoreSecondAdapter extends BaseQuickAdapter<RowBean, BaseViewHolder> {
//    private Context context;
//    private int number;
//
//    public StoreSecondAdapter(int layoutResId, @Nullable List<RowBean> data, Context context) {
//        super(layoutResId, data);
//        this.context = context;
//    }
//
//
//    @Override
//    protected void convert(BaseViewHolder holder, RowBean item) {
//        holder.setText(R.id.goods_title, item.getName());
//        holder.setText(R.id.goods_content, item.getName());
//        holder.setText(R.id.goods_price, item.getShopPrice() + "元/"
//                + item.getUnit());
//        ImageView iv = holder.getView(R.id.goods_img);
//        Glide.with(context).load(UrlConfig.baseUrl + item.getImg())
//                .error(R.drawable.defult_img).into(iv);
//        holder.setText(R.id.goods_number, item.getBookNumber()+"");
//        holder.addOnClickListener(R.id.goods_add_number);
//        holder.addOnClickListener(R.id.goods_cut_number);
//
//    }
//
//}
