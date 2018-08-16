package com.narmware.jainjeevan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.DharamshalaAdapter;
import com.narmware.jainjeevan.adapter.FilterAdapter;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.Filter;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {
    RecyclerView mRecyclerFilter;
    ArrayList<Filter> filters;
    FilterAdapter filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        init();
    }

    private void init() {

        mRecyclerFilter=findViewById(R.id.recycler_filter);
        setFilterAdapter(new GridLayoutManager(FilterActivity.this,2));
    }

    public void setFilterAdapter(RecyclerView.LayoutManager mLayoutManager) {
        filters = new ArrayList<>();

        filters.add(new Filter("1","AC"));
        filters.add(new Filter("2","Generator"));
        filters.add(new Filter("3","Water Cooler"));
        filters.add(new Filter("3","Cold Water"));
        filters.add(new Filter("3","Hot Water"));

        SnapHelper snapHelper = new LinearSnapHelper();

        filterAdapter = new FilterAdapter(FilterActivity.this, filters,getSupportFragmentManager());
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerFilter.setLayoutManager(mLayoutManager);
        mRecyclerFilter.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerFilter.setAdapter(filterAdapter);
        mRecyclerFilter.setNestedScrollingEnabled(false);
        mRecyclerFilter.setFocusable(false);

        filterAdapter.notifyDataSetChanged();
    }
}
