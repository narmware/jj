package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.MenuActivity;
import com.narmware.jainjeevan.activity.FoodVendorActivity;
import com.narmware.jainjeevan.pojo.RestoItems;
import com.narmware.jainjeevan.support.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class FoodVendorAdapter extends RecyclerView.Adapter<FoodVendorAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<RestoItems> restoItems;
    FragmentManager fragmentManager;
    String[] mPhone1;

    public FoodVendorAdapter(Context mContext, ArrayList<RestoItems> restoItems, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.restoItems = restoItems;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_food_vendor, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RestoItems restoItem=restoItems.get(position);

        holder.mTxtTitle.setText(restoItem.getName());
        holder.mTxtAddress.setText(restoItem.getAddress());
        holder.mTxtContact.setText("Contact: "+restoItem.getMobile());
        Picasso.with(mContext)
                .load(restoItem.getIMG())
                .placeholder(R.drawable.logo)
                .into(holder.mImgResto);

        holder.mItem=restoItem;

    }

    @Override
    public int getItemCount() {
        if(restoItems.size()==0)
        {
            FoodVendorActivity.mLinEmpty.setVisibility(View.VISIBLE);
        }
        else{
            FoodVendorActivity.mLinEmpty.setVisibility(View.INVISIBLE);
        }
        return restoItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImgResto;
        TextView mTxtTitle,mTxtAddress,mTxtContact;
        ImageButton mBtnCall;
        RestoItems mItem;
        Button mBtnViewMenu;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImgResto=itemView.findViewById(R.id.img_hotel);
            mTxtTitle=itemView.findViewById(R.id.txt_title);
            mTxtAddress=itemView.findViewById(R.id.txt_address);
            mTxtContact=itemView.findViewById(R.id.txt_contact);
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

                    if(phone==null || phone.equals(""))
                    {
                        Toast.makeText(mContext,"No number available",Toast.LENGTH_SHORT).show();
                    }else {

                        if (phone.contains("/")) {
                            String[] separated_nums = phone.split("/");
                            Log.e("Numbers", separated_nums[0] + "  " + separated_nums[1]);

                            mPhone1 = new String[separated_nums.length];
                            PopupMenu popup = new PopupMenu(mContext,mBtnCall);

                            for (int i = 0; i < separated_nums.length; i++) {
                                mPhone1[i] = separated_nums[i];
                                popup.getMenu().add(mPhone1[i]);
                            }

                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(android.view.MenuItem menuItem) {

                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", String.valueOf(menuItem.getTitle()), null));
                                    mContext.startActivity(intent);

                                    return false;
                                }

                            });
                            popup.show();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                            mContext.startActivity(intent);
                        }
                    }

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
