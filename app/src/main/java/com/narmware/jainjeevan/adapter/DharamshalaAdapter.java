package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.DetailsActivity;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.RecommendedItems;
import com.narmware.jainjeevan.support.Constants;
import com.squareup.picasso.Picasso;

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

        holder.mTxtTitle.setText(dharamshalaItem.getName());
        holder.mTxtAddress.setText(dharamshalaItem.getAddress());
        holder.mTxtCharges.setText("Rooms availabe from Rs."+dharamshalaItem.getMinamount()+" onwards");

        Picasso.with(mContext)
                .load(dharamshalaItem.getIMG())
                .into(holder.mImgDharam);

        holder.mItem=dharamshalaItem;
    }

    @Override
    public int getItemCount() {
        return dharamshalaItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImgDharam;
        TextView mTxtTitle,mTxtAddress,mTxtCharges;
        ImageButton mBtnCall;
        DharamshalaItem mItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImgDharam=itemView.findViewById(R.id.img_dharam);
            mTxtTitle=itemView.findViewById(R.id.txt_title);
            mTxtAddress=itemView.findViewById(R.id.txt_address);
            mTxtCharges=itemView.findViewById(R.id.txt_room_charge);
            mBtnCall=itemView.findViewById(R.id.btn_call);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,mItem.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(mContext, DetailsActivity.class);
                    intent.putExtra(Constants.TITLE,mItem.getName());
                    intent.putExtra(Constants.ADDRESS,mItem.getAddress());
                    intent.putExtra(Constants.IMAGE,mItem.getIMG());
                    intent.putExtra(Constants.ID,mItem.getDharmshala_id());

                    mContext.startActivity(intent);
                }
            });

            mBtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
