package com.ejar.fastbedroom.deliver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ejar.fastbedroom.R;

import java.util.List;

/**
 * Created by json on 2017/10/13.
 */

public class AutoPollAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {
    private final Context mContext;
    private final List<AutoMessageBean.DataBean> mData;
    private OnItemClickListener mOnItemClickListener = null;

    public AutoPollAdapter(Context context, List<AutoMessageBean.DataBean> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_auto_rv, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        String data = mData.get(position % mData.size()).getSubtitle();
        holder.setText(R.id.head_rv_message, data);
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public  interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
