package com.ejar.fastbedroom.fastmail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.fastmail.bean.MailWayBean;
import com.ejar.fastbedroom.register.adapter.MyAdapter;
import com.ejar.fastbedroom.register.bean.School;

import java.util.List;

/**
 * Created by json on 2017/9/12.
 */

public class MailWayAdapter extends RecyclerView.Adapter<MailWayAdapter.ViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    //    private List<SortModel> mData;
    private List<MailWayBean> mData;
    private Context mContext;

    public MailWayAdapter(Context context, List<MailWayBean> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.mContext = context;
    }

    @Override
    public MailWayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_school, parent, false);
        MailWayAdapter.ViewHolder viewHolder = new MailWayAdapter.ViewHolder(view);
        viewHolder.tvName = (TextView) view.findViewById(R.id.school_name);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MailWayAdapter.ViewHolder holder, final int position) {

        holder.tvName.setText(this.mData.get(position).getExpressname());
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private MyAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(MyAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }
//**************************************************************

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 提供给Activity刷新数据
     *
     * @param list
     */
    public void updateList(List<MailWayBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
