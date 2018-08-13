package com.narmware.jainjeevan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.DharamshalaAdapter;
import com.narmware.jainjeevan.adapter.RecommendedAdapter;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.RecommendedItems;

import java.util.ArrayList;

public class DharamshalaActivity2 extends AppCompatActivity {

    RecyclerView mRecyclerDharam;
    ArrayList<DharamshalaItem> dharamshalaItems;
    DharamshalaAdapter dharamshalaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dharamshala);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        init();
    }
    private void init() {
        mRecyclerDharam=findViewById(R.id.recycler_dharamshala);
    }

    public void setDharamshalaAdapter(RecyclerView.LayoutManager mLayoutManager) {
        dharamshalaItems = new ArrayList<>();

        SnapHelper snapHelper = new LinearSnapHelper();

        dharamshalaAdapter = new DharamshalaAdapter(DharamshalaActivity2.this, dharamshalaItems,getSupportFragmentManager());
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerDharam.setLayoutManager(mLayoutManager);
        mRecyclerDharam.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerDharam.setAdapter(dharamshalaAdapter);
        mRecyclerDharam.setNestedScrollingEnabled(false);
        mRecyclerDharam.setFocusable(false);

        dharamshalaAdapter.notifyDataSetChanged();
    }

}
