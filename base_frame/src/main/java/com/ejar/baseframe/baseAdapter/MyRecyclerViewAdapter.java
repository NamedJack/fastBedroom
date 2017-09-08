package com.ejar.baseframe.baseAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zyj on 2017/7/23.
 */

public abstract class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context mContext, int mLayoutId, List<T> mDatas) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = MyViewHolder.createViewHoler(mContext, parent, mLayoutId);
        setListener(parent, holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        convert(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(MyViewHolder holder, int position);

    public interface OnItemClickListener{
        void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position);
        boolean onItemLonClick(View view, RecyclerView.ViewHolder viewHolder, int position);
    }

    public void  setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    protected boolean isEnabled(int viewType) {
        return true;
    }
    protected void setListener(final ViewGroup parent, final MyViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    onItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return onItemClickListener.onItemLonClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

}
