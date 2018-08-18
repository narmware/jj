package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.narmware.jainjeevan.activity.RestaurantActivity;
import com.narmware.jainjeevan.pojo.BhojanItems;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.RestoItems;
import com.narmware.jainjeevan.support.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class RestoAdapter extends RecyclerView.Adapter<RestoAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<RestoItems> restoItems;
    FragmentManager fragmentManager;

    public RestoAdapter(Context mContext, ArrayList<RestoItems> restoItems, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.restoItems = restoItems;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hotel, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RestoItems restoItem=restoItems.get(position);

        holder.mTxtTitle.setText(restoItem.getName());
        holder.mTxtAddress.setText(restoItem.getAddress());
        Picasso.with(mContext)
                .load(restoItem.getIMG())
                .into(holder.mImgResto);

        holder.mItem=restoItem;

    }

    @Override
    public int getItemCount() {
        if(restoItems.size()==0)
        {
            RestaurantActivity.mLinEmpty.setVisibility(View.VISIBLE);
        }
        else{
            RestaurantActivity.mLinEmpty.setVisibility(View.INVISIBLE);
        }
        return restoItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImgResto;
        TextView mTxtTitle,mTxtAddress;
        ImageButton mBtnCall;
        RestoItems mItem;
        Button mBtnViewMenu;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImgResto=itemView.findViewById(R.id.img_hotel);
            mTxtTitle=itemView.findViewById(R.id.txt_title);
            mTxtAddress=itemView.findViewById(R.id.txt_address);
            mBtnCall=itemView.findViewById(R.id.btn_call);
            mBtnViewMenu=itemView.findViewById(R.id.btn_view_menu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mBtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phone = mItem.getMobile();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    mContext.startActivity(intent);
                }
            });

            mBtnViewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, MenuActivity.class);
                    intent.putExtra(Constants.HOTEL_ID,mItem.getHotel_id());
                    intent.putExtra(Constants.HOTEL_TITLE,mItem.getName());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
