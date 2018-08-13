package com.narmware.jainjeevan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.BhojanAdapter;
import com.narmware.jainjeevan.adapter.DharamshalaAdapter;
import com.narmware.jainjeevan.pojo.BhojanItems;
import com.narmware.jainjeevan.pojo.DharamshalaItem;

import java.util.ArrayList;

public class BhojanalayActivity extends AppCompatActivity {

    RecyclerView mRecyclerBhojan;
    ArrayList<BhojanItems> bhojanItems;
    BhojanAdapter bhojanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhojanalay);
        getSupportActionBar().hide();

        init();
    }

    private void init() {
        mRecyclerBhojan=findViewById(R.id.recycler_bhojan);
    }

    public void setBhojanAdapter(RecyclerView.LayoutManager mLayoutManager) {
        bhojanItems = new ArrayList<>();

        SnapHelper snapHelper = new LinearSnapHelper();

        bhojanAdapter = new BhojanAdapter(BhojanalayActivity.this, bhojanItems,getSupportFragmentManager());
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerBhojan.setLayoutManager(mLayoutManager);
        mRecyclerBhojan.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerBhojan.setAdapter(bhojanAdapter);
        mRecyclerBhojan.setNestedScrollingEnabled(false);
        mRecyclerBhojan.setFocusable(false);

        bhojanAdapter.notifyDataSetChanged();
    }
}
