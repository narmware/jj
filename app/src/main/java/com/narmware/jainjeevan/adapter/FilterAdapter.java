package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.pojo.BhojanItems;
import com.narmware.jainjeevan.pojo.Filter;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<Filter> filters;
    FragmentManager fragmentManager;

    public FilterAdapter(Context mContext, ArrayList<Filter> filters, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.filters = filters;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Filter filter=filters.get(position);
        holder.mItem=filter;

        holder.mTxtFilter.setText(filter.getFilter_name());
        holder.mIcon.setImageResource(R.drawable.ac);
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox mChkFilter;
        TextView mTxtFilter;
        ImageView mIcon;

        Filter mItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            mChkFilter=itemView.findViewById(R.id.chk_filter);
            mTxtFilter=itemView.findViewById(R.id.txt_filter);
            mIcon=itemView.findViewById(R.id.icon);

            mChkFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        Toast.makeText(mContext,mItem.getFilter_name(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
