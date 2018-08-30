package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.pojo.City;
import com.narmware.jainjeevan.pojo.NavMenu;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class NavAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<NavMenu> menus;

    public NavAdapter(Context mContext, ArrayList<NavMenu> menus) {
        this.mContext = mContext;
        this.menus = menus;
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nav, parent, false);

        TextView mTxtFilter=view.findViewById(R.id.txt_nav);
        mTxtFilter.setText(menus.get(position).getNav_title());

        ImageView mImgNav=view.findViewById(R.id.img_nav);
        mImgNav.setImageResource(menus.get(position).getNav_img());
        return view;
    }

}
