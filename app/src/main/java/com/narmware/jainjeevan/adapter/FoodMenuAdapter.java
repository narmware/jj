package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.DetailsActivity;
import com.narmware.jainjeevan.activity.DharamshalaActivity2;
import com.narmware.jainjeevan.activity.MenuActivity;
import com.narmware.jainjeevan.pojo.MenuItem;
import com.narmware.jainjeevan.pojo.RestoItems;
import com.narmware.jainjeevan.support.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<MenuItem> restoItems;
    FragmentManager fragmentManager;

    public FoodMenuAdapter(Context mContext, ArrayList<MenuItem> restoItems, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.restoItems = restoItems;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_food, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenuItem restoItem=restoItems.get(position);

        holder.mTxtTitle.setText(restoItem.getMenu_name());
        holder.mTxtRate.setText("Rs."+restoItem.getRate());
        Picasso.with(mContext)
                .load(restoItem.getIMG())
                .into(holder.mImgResto);

        holder.mItem=restoItem;

    }

    @Override
    public int getItemCount() {
        if(restoItems.size()==0)
        {
            MenuActivity.mLinEmpty.setVisibility(View.VISIBLE);
        }
        else{
            MenuActivity.mLinEmpty.setVisibility(View.INVISIBLE);
        }
        return restoItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImgResto;
        TextView mTxtTitle,mTxtRate;
        MenuItem mItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImgResto=itemView.findViewById(R.id.img_food);
            mTxtTitle=itemView.findViewById(R.id.txt_title);
            mTxtRate=itemView.findViewById(R.id.txt_rate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent=new Intent(mContext, DetailsActivity.class);
                    intent.putExtra(Constants.HOTEL_ID,mItem.getHotel_id());
                    mContext.startActivity(intent);*/
                }
            });

        }
    }
}
