package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.DetailsActivity;
import com.narmware.jainjeevan.pojo.RecommendedItems;
import com.narmware.jainjeevan.support.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<RecommendedItems> recommendedItems;
    FragmentManager fragmentManager;

    public RecommendedAdapter(Context mContext, ArrayList<RecommendedItems> recommendedItems, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.recommendedItems = recommendedItems;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recommended, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecommendedItems recommendedItem=recommendedItems.get(position);
        holder.mItem=recommendedItem;

        holder.mTxtRecomm.setText(recommendedItem.getName());
        Picasso.with(mContext)
                //.load(recommendedItem.getIMG())
                .load(R.drawable.placeholder)
                .into(holder.mImgRecomm);
    }

    @Override
    public int getItemCount() {
        return recommendedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImgRecomm;
        TextView mTxtRecomm;
        RecommendedItems mItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImgRecomm=itemView.findViewById(R.id.recomm_image);
            mTxtRecomm=itemView.findViewById(R.id.recomm_title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, DetailsActivity.class);
                    intent.putExtra(Constants.TITLE,mItem.getName());
                    intent.putExtra(Constants.ADDRESS,mItem.getAddress());
                    intent.putExtra(Constants.IMAGE,mItem.getIMG());
                    intent.putExtra(Constants.ID,mItem.getDharmshala_id());
                    intent.putExtra(Constants.LATITUDE,mItem.getLatitude());
                    intent.putExtra(Constants.LONGITUDE,mItem.getLongitude());
                    intent.putExtra(Constants.MOBILE_NUMBER,mItem.getMobile());
                    intent.putExtra(Constants.CONTACT_PERSON,mItem.getManager());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
