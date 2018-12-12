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
import com.narmware.jainjeevan.activity.DharamshalaActivity2;
import com.narmware.jainjeevan.fragments.RoomsFragment;
import com.narmware.jainjeevan.pojo.BhojanItems;
import com.narmware.jainjeevan.pojo.DetailedItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class DetailedItemAdapter extends RecyclerView.Adapter<DetailedItemAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<DetailedItem> detailedItems;
    String callFrom;

    public DetailedItemAdapter(Context mContext, ArrayList<DetailedItem> detailedItems,String callFrom) {
        this.mContext = mContext;
        this.detailedItems = detailedItems;
        this.callFrom=callFrom;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detailed, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DetailedItem detailedItem=detailedItems.get(position);

        try {
            if (callFrom.equals("Facilities")) {
                if (detailedItem.getImg().trim().equals("") || detailedItem.getImg().trim().isEmpty() || detailedItem.getImg() == null) {
                    holder.mImgIcon.setVisibility(View.GONE);
                } else {
                    holder.mImgIcon.setVisibility(View.VISIBLE);

                    if (detailedItem.getImg().equals("empty")) {

                    } else {
                        Picasso.with(mContext)
                                .load(detailedItem.getImg())
                                .placeholder(R.drawable.logo)
                                .into(holder.mImgIcon);
                    }
                }
            }
        }catch (Exception e)
        {

        }
        holder.mTxtDetail.setText(detailedItem.getItem());
    }

    @Override
    public int getItemCount() {
       /* if(detailedItems.size()==0)
        {
            RoomsFragment.mLinEmpty.setVisibility(View.VISIBLE);
        }
        else{
            RoomsFragment.mLinEmpty.setVisibility(View.INVISIBLE);
        }*/
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
