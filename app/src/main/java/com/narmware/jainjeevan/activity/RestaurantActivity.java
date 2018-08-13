package com.narmware.jainjeevan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.DharamshalaAdapter;
import com.narmware.jainjeevan.adapter.RestoAdapter;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.RestoItems;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {

    RecyclerView mRecyclerResto;
    ArrayList<RestoItems> restoItems;
    RestoAdapter restoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        getSupportActionBar().hide();

        init();
    }

    private void init() {
        mRecyclerResto=findViewById(R.id.recycler_resto);
    }

    public void setDharamshalaAdapter(RecyclerView.LayoutManager mLayoutManager) {
        restoItems = new ArrayList<>();

        SnapHelper snapHelper = new LinearSnapHelper();

        restoAdapter = new RestoAdapter(RestaurantActivity.this, restoItems,getSupportFragmentManager());
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerResto.setLayoutManager(mLayoutManager);
        mRecyclerResto.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerResto.setAdapter(restoAdapter);
        mRecyclerResto.setNestedScrollingEnabled(false);
        mRecyclerResto.setFocusable(false);

        restoAdapter.notifyDataSetChanged();
    }
}
