package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.narmware.jainjeevan.activity.FilterActivity;
import com.narmware.jainjeevan.pojo.BhojanItems;
import com.narmware.jainjeevan.pojo.Facility;
import com.narmware.jainjeevan.pojo.Filter;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<Facility> filters;
    FragmentManager fragmentManager;

    public FilterAdapter(Context mContext, ArrayList<Facility> filters, FragmentManager fragmentManager) {
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
        Facility filter=filters.get(position);
        holder.mItem=filter;

        holder.mTxtFilter.setText(filter.getFacility_name());

        if(filter.getImg().equals(""))
        {}
        else {
            Picasso.with(mContext)
                    .load(filter.getImg())
                    .into(holder.mIcon);
        }

        if(filter.isSelected()==false)
        {
            holder.mChkFilter.setChecked(false);
        }
        else{
            holder.mChkFilter.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox mChkFilter;
        TextView mTxtFilter;
        ImageView mIcon;

        Facility mItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            mChkFilter=itemView.findViewById(R.id.chk_filter);
            mTxtFilter=itemView.findViewById(R.id.txt_filter);
            mIcon=itemView.findViewById(R.id.icon);

            mChkFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b==true)
                    {
                       // FilterActivity.selected_filters.add(new Facility(mItem.getFacility_id(),mItem.getFacility_name(),mItem.getImg(),false));
                        FilterActivity.selected_filters.add(mItem.getFacility_id());
                        //Toast.makeText(mContext,mItem.getFacility_id(),Toast.LENGTH_SHORT).show();
                    }

                    if(b==false)
                    {
                        FilterActivity.selected_filters.remove(mItem.getFacility_id());
                        //FilterActivity.selected_filters.remove(new Facility(mItem.getFacility_id(),mItem.getFacility_name(),mItem.getImg(),false));
                        Toast.makeText(mContext,FilterActivity.selected_filters.size()+"",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
