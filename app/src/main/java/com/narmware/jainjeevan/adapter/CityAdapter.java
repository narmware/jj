package com.narmware.jainjeevan.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.pojo.City;
import com.narmware.jainjeevan.pojo.Filter;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class CityAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<City> cities;

    public CityAdapter(Context mContext, ArrayList<City> cities) {
        this.mContext = mContext;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
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
        mTxtFilter.setText(cities.get(position).getCity_name());
        return view;
    }

}
