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
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.RecommendedItems;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class DharamshalaAdapter extends RecyclerView.Adapter<DharamshalaAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<DharamshalaItem> dharamshalaItems;
    FragmentManager fragmentManager;

    public DharamshalaAdapter(Context mContext, ArrayList<DharamshalaItem> dharamshalaItems, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.dharamshalaItems = dharamshalaItems;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dharamshala, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DharamshalaItem dharamshalaItem=dharamshalaItems.get(position);
    }

    @Override
    public int getItemCount() {
        return dharamshalaItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImgRecomm;
        TextView mTxtRecomm;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
