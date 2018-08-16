package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.pojo.BhojanItems;
import com.narmware.jainjeevan.pojo.DetailedItem;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class DetailedItemAdapter extends RecyclerView.Adapter<DetailedItemAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<DetailedItem> detailedItems;
    FragmentManager fragmentManager;

    public DetailedItemAdapter(Context mContext, ArrayList<DetailedItem> detailedItems, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.detailedItems = detailedItems;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detailed, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DetailedItem detailedItem=detailedItems.get(position);

        if(detailedItem.getImg()!=0)
        {
            holder.mImgIcon.setVisibility(View.VISIBLE);
            holder.mImgIcon.setImageResource(detailedItem.getImg());
        }
        else{
            holder.mImgIcon.setVisibility(View.GONE);
        }
        holder.mTxtDetail.setText(detailedItem.getItem());
    }

    @Override
    public int getItemCount() {
        return detailedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTxtDetail;
        ImageView mImgIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTxtDetail=itemView.findViewById(R.id.txt_item);
            mImgIcon=itemView.findViewById(R.id.icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
