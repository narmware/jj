package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.DetailsActivity;
import com.narmware.jainjeevan.activity.DharamshalaActivity2;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.MenuItem;
import com.narmware.jainjeevan.pojo.RecommendedItems;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class DharamshalaAdapter extends RecyclerView.Adapter<DharamshalaAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<DharamshalaItem> dharamshalaItems;
    FragmentManager fragmentManager;
    String[] mPhone1;

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
                //.load(recommendedItem.getIMG())
                .load(R.drawable.logo)
                .into(holder.mImgDharam);

        holder.mItem=dharamshalaItem;
    }

    @Override
    public int getItemCount() {

        if(dharamshalaItems.size()==0)
        {
            DharamshalaActivity2.mLinEmpty.setVisibility(View.VISIBLE);

            if(SharedPreferencesHelper.getFilteredFacilities(mContext)==null || SharedPreferencesHelper.getFilteredCity(mContext)==null)
            {
            }
            else{
                DharamshalaActivity2.mTxtNoData.setText("No content available for selected filter. \n Please change or remove filter.");
            }
        }
        else{
            DharamshalaActivity2.mLinEmpty.setVisibility(View.INVISIBLE);
        }
        return dharamshalaItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        ImageView mImgDharam;
        TextView mTxtTitle,mTxtAddress,mTxtCharges;
        ImageButton mBtnCall;
        DharamshalaItem mItem;

        public MyViewHolder(final View itemView) {
            super(itemView);

            mImgDharam=itemView.findViewById(R.id.img_dharam);
            mTxtTitle=itemView.findViewById(R.id.txt_title);
            mTxtAddress=itemView.findViewById(R.id.txt_address);
            mTxtCharges=itemView.findViewById(R.id.txt_room_charge);
            mBtnCall=itemView.findViewById(R.id.btn_call);
            itemView.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,mItem.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(mContext, DetailsActivity.class);
                    intent.putExtra(Constants.TITLE,mItem.getName());
                    intent.putExtra(Constants.ADDRESS,mItem.getAddress());
                    intent.putExtra(Constants.IMAGE,mItem.getIMG());
                    intent.putExtra(Constants.ID,mItem.getDharmshala_id());
                    intent.putExtra(Constants.LATITUDE,mItem.getLatitude());
                    intent.putExtra(Constants.LONGITUDE,mItem.getLongitude());
                    intent.putExtra(Constants.MOBILE_NUMBER,mItem.getMobile());
                    intent.putExtra(Constants.CONTACT_PERSON,mItem.getManager());
                    intent.putExtra(Constants.TYPE,Constants.TYPE_DHARMSHALA);

                    mContext.startActivity(intent);
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
        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
            Log.e("Numbers",mPhone1.length+"  ");

            if (v.getId() == R.id.btn_call) {

                menu.setHeaderTitle("Select phone number to dial");
                for(int i=0;i<mPhone1.length;i++)
                {
                    menu.add(mPhone1[i]);
                }
            }
        }
    }
}
