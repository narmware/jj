package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.pojo.Area;
import com.narmware.jainjeevan.pojo.City;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class AreaAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<Area> areas;

    public AreaAdapter(Context mContext, ArrayList<Area> areas) {
        this.mContext = mContext;
        this.areas = areas;
    }

    @Override
    public int getCount() {
        return areas.size();
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_city, parent, false);

        TextView mTxtFilter=view.findViewById(R.id.txt_city);
        mTxtFilter.setText(areas.get(position).getArea_name());
        return view;
    }

}
