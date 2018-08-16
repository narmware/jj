package com.narmware.jainjeevan.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.CityAdapter;
import com.narmware.jainjeevan.adapter.DharamshalaAdapter;
import com.narmware.jainjeevan.adapter.FilterAdapter;
import com.narmware.jainjeevan.pojo.City;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.DharamshalaResponse;
import com.narmware.jainjeevan.pojo.Facility;
import com.narmware.jainjeevan.pojo.Filter;
import com.narmware.jainjeevan.pojo.FilterResponse;
import com.narmware.jainjeevan.pojo.SendFilters;
import com.narmware.jainjeevan.support.EndPoints;

import org.json.JSONObject;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {
    RecyclerView mRecyclerFilter;

    ArrayList<Facility> filters;
    FilterAdapter filterAdapter;
    ImageView mBtnBack;
    RequestQueue mVolleyRequest;

    Spinner mCitySpinner;
    ArrayList<City> cities;
    CityAdapter cityAdapter;

    ArrayList<Facility> selected_filters;

    Button mBtnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().hide();

        init();
    }

    private void init() {
        mVolleyRequest = Volley.newRequestQueue(FilterActivity.this);

        mBtnApply=findViewById(R.id.btn_apply_filter);
        mRecyclerFilter=findViewById(R.id.recycler_filter);
        setFilterAdapter(new GridLayoutManager(FilterActivity.this,2));
        GetFilters();

        mCitySpinner=findViewById(R.id.spinn_city);
        cities=new ArrayList<>();
        cities.add(new City("Pune","1"));
        cities.add(new City("Nagpur","2"));
        cities.add(new City("Mumbai","3"));

        cityAdapter=new CityAdapter(FilterActivity.this,cities);
        mCitySpinner.setAdapter(cityAdapter);

      mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(FilterActivity.this, "Hello", Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }

      });
               // Toast.makeText(FilterActivity.this, "Hello", Toast.LENGTH_SHORT).show();

        mBtnBack=findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mBtnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetFilters();
            }
        });

        selected_filters=new ArrayList<>();
    }

    public void setFilterAdapter(RecyclerView.LayoutManager mLayoutManager) {
        filters = new ArrayList<>();
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

    private void GetFilters() {
        final ProgressDialog dialog = new ProgressDialog(FilterActivity.this);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        //url without params
        String url= EndPoints.GET_FILTERS;

        Log.e("Filter url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Filter Json_string",response.toString());
                            Gson gson = new Gson();

                            FilterResponse filterResponse=gson.fromJson(response.toString(),FilterResponse.class);
                            City[] city=filterResponse.getCity();

                            for(City item:city){
                                cities.add(item);
                            }
                            cityAdapter.notifyDataSetChanged();


                            Facility[] facility=filterResponse.getFacility();

                            for(Facility itemFac:facility){
                                filters.add(itemFac);
                            }
                            filterAdapter.notifyDataSetChanged();

                        } catch (Exception e) {

                            e.printStackTrace();
                            dialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void SetFilters() {
        final ProgressDialog dialog = new ProgressDialog(FilterActivity.this);
        dialog.setMessage("Applying filters...");
        dialog.setCancelable(false);
        dialog.show();

        selected_filters.add(new Facility("1","",""));
        selected_filters.add(new Facility("2","",""));
        selected_filters.add(new Facility("3","",""));

        SendFilters sendFilters=new SendFilters();
        sendFilters.setCity_id("1");
        sendFilters.setFacilities(selected_filters);

        Gson gson=new Gson();
        String json_string=gson.toJson(sendFilters);
        Log.e("Filter json_string",json_string);

       /* String url= EndPoints.GET_FILTERS;

        Log.e("Filter url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Filter Json_string",response.toString());
                            Gson gson = new Gson();

                            FilterResponse filterResponse=gson.fromJson(response.toString(),FilterResponse.class);
                            City[] city=filterResponse.getCity();

                            for(City item:city){
                                cities.add(item);
                            }
                            cityAdapter.notifyDataSetChanged();


                            Facility[] facility=filterResponse.getFacility();

                            for(Facility itemFac:facility){
                                filters.add(itemFac);
                            }
                            filterAdapter.notifyDataSetChanged();

                        } catch (Exception e) {

                            e.printStackTrace();
                            dialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);*/
    }

}
