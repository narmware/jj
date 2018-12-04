package com.narmware.jainjeevan.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;
import com.narmware.jainjeevan.support.SupportFunctions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FilterActivity extends AppCompatActivity {
    RecyclerView mRecyclerFilter;
    String filterType=null;

    ArrayList<Facility> filters;
    FilterAdapter filterAdapter;
    ImageView mBtnBack;
    RequestQueue mVolleyRequest;

    Spinner mCitySpinner;
    ArrayList<City> cities;
    CityAdapter cityAdapter;

    public static ArrayList<String> selected_filters;
    public static String selected_city_id;
    public static Set<String> facilitySet;

    Button mBtnApply,mBtnClearFilter;
    Dialog mNoConnectionDialog;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().hide();
        context=FilterActivity.this;

        Intent intent=getIntent();

        if(intent!=null) {
            filterType=intent.getStringExtra(Constants.TYPE);
        }
        init();
    }

    private void init() {
        mVolleyRequest = Volley.newRequestQueue(FilterActivity.this);
        facilitySet = new HashSet<>();
        mNoConnectionDialog = new Dialog(FilterActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);

        mBtnApply=findViewById(R.id.btn_apply_filter);
        mRecyclerFilter=findViewById(R.id.recycler_filter);
        setFilterAdapter(new GridLayoutManager(FilterActivity.this,2));
        GetFilters();

        mCitySpinner=findViewById(R.id.spinn_city);
        cities=new ArrayList<>();
        cityAdapter=new CityAdapter(FilterActivity.this,cities);
        mCitySpinner.setAdapter(cityAdapter);

      mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              selected_city_id=cities.get(position).getCity_id();
              SharedPreferencesHelper.setUserLocation(cities.get(position).getCity_name(),FilterActivity.this);
              SharedPreferencesHelper.setFilteredCity(cities.get(position).getCity_id(),FilterActivity.this);
              //Toast.makeText(FilterActivity.this, cities.get(position).getCity_id(), Toast.LENGTH_SHORT).show();
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

                if(filterType.equals(Constants.TYPE_DHARMSHALA))
                {
                    SetFilters();
                }
                if(filterType.equals(Constants.TYPE_BHOJANALAYA))
                {
                    SetBhojanFilters();
                }
                finish();
            }
        });

        mBtnClearFilter=findViewById(R.id.btn_clear_filter);
        mBtnClearFilter.setVisibility(View.VISIBLE);

        mBtnClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FilterActivity.this, filters.size()+"", Toast.LENGTH_SHORT).show();

                for(int i=0;i<filters.size();i++)
                {
                    filters.get(i).setSelected(false);
                    filterAdapter.notifyDataSetChanged();
                    selected_filters.clear();
                    facilitySet.clear();
                }

                if(filterType.equals(Constants.TYPE_DHARMSHALA))
                {
                    SharedPreferencesHelper.setFilteredFacilities(null,context);
                }
                if(filterType.equals(Constants.TYPE_BHOJANALAYA))
                {
                    SharedPreferencesHelper.setBhojanFacilities(null,context);
                }
            }
        });
        selected_filters=new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(filterType.equals(Constants.TYPE_DHARMSHALA))
        {
            Intent intentDharam=new Intent(FilterActivity.this, DharamshalaActivity2.class);
            intentDharam.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentDharam);
        }
        if(filterType.equals(Constants.TYPE_BHOJANALAYA))
        {
            Intent intentBhojan=new Intent(FilterActivity.this, BhojanalayActivity.class);
            intentBhojan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentBhojan);
        }
    }

    public void setFilterAdapter(RecyclerView.LayoutManager mLayoutManager) {
        filters = new ArrayList<>();
        SnapHelper snapHelper = new LinearSnapHelper();

        filterAdapter = new FilterAdapter(FilterActivity.this, filters,Constants.FILTER);
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
        //String url= EndPoints.GET_FILTERS;
        HashMap<String,String> param = new HashMap();

        if(filterType.equals(Constants.TYPE_DHARMSHALA))
        {
            param.put(Constants.TYPE, Constants.TYPE_DHARMSHALA);
        }

        if(filterType.equals(Constants.TYPE_BHOJANALAYA))
        {
            param.put(Constants.TYPE, Constants.TYPE_BHOJANALAYA);
        }

        String url= SupportFunctions.appendParam(EndPoints.GET_FILTERS,param);

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
                            for(int i=0;i<cities.size();i++)
                            {
                                if(SharedPreferencesHelper.getUserLocation(FilterActivity.this)!=null) {
                                    if (SharedPreferencesHelper.getUserLocation(FilterActivity.this).equals(cities.get(i).getCity_name())) {
                                        mCitySpinner.setSelection(i);
                                    }
                                }
                            }
                            cityAdapter.notifyDataSetChanged();


                            Facility[] facility=filterResponse.getFacility();
                            if(filterType.equals(Constants.TYPE_DHARMSHALA))
                            {
                                if(SharedPreferencesHelper.getFilteredFacilities(FilterActivity.this)!=null) {
                                    facilitySet = SharedPreferencesHelper.getFilteredFacilities(FilterActivity.this);
                                }
                            }

                            if(filterType.equals(Constants.TYPE_BHOJANALAYA))
                            {
                                if(SharedPreferencesHelper.getBhojanFacilities(FilterActivity.this)!=null) {
                                    facilitySet = SharedPreferencesHelper.getBhojanFacilities(FilterActivity.this);
                                }
                            }
                            for(Facility itemFac:facility){

                                //filters.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),false));

                                    if(facilitySet.contains(itemFac.getFacility_id()))
                                    {
                                        filters.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),true));
                                    }
                                    else{
                                        filters.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),false));
                                    }

                            }
                            filterAdapter.notifyDataSetChanged();

                        } catch (Exception e) {

                            e.printStackTrace();
                            dialog.dismiss();
                        }
                        if(mNoConnectionDialog.isShowing()==true)
                        {
                            mNoConnectionDialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        showNoConnectionDialog();
                        dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    public static void SetFilters() {

        SendFilters sendFilters=new SendFilters();
        sendFilters.setCity_id(selected_city_id);
        sendFilters.setFacilities(selected_filters);
        facilitySet.clear();
        for(int i=0;i<selected_filters.size();i++) {
            facilitySet.add(selected_filters.get(i));
        }
       // Toast.makeText(context, "Size: "+facilitySet.size(), Toast.LENGTH_SHORT).show();
        SharedPreferencesHelper.setFilteredFacilities(null,context);
        SharedPreferencesHelper.setFilteredFacilities(facilitySet,context);

        Gson gson=new Gson();
        String json_string=gson.toJson(sendFilters);
        Log.e("Filter json_string",json_string);

        HashMap<String,String> param = new HashMap();
        param.put(Constants.JSON_STRING,json_string);
        param.put(Constants.TYPE, Constants.TYPE_DHARMSHALA);

        //url with params
        String url= SupportFunctions.appendParam(EndPoints.GET_FILTERED_DATA,param);
        DharamshalaActivity2.dharamshalaItems.clear();
        DharamshalaActivity2.GetDharamshalas(url);
    }


    public static void SetBhojanFilters() {

        SendFilters sendFilters=new SendFilters();
        sendFilters.setCity_id(selected_city_id);
        sendFilters.setFacilities(selected_filters);
        facilitySet.clear();
        for(int i=0;i<selected_filters.size();i++) {
            facilitySet.add(selected_filters.get(i));
        }
        // Toast.makeText(context, "Size: "+facilitySet.size(), Toast.LENGTH_SHORT).show();
        SharedPreferencesHelper.setBhojanFacilities(null,context);
        SharedPreferencesHelper.setBhojanFacilities(facilitySet,context);

        Gson gson=new Gson();
        String json_string=gson.toJson(sendFilters);
        Log.e("Filter json_string",json_string);

        HashMap<String,String> param = new HashMap();
        param.put(Constants.JSON_STRING,json_string);
        param.put(Constants.TYPE, Constants.TYPE_BHOJANALAYA);

        //url with params
        String url= SupportFunctions.appendParam(EndPoints.GET_FILTERED_DATA,param);
        BhojanalayActivity.dharamshalaItems.clear();
        BhojanalayActivity.GetDharamshalas(url);
    }

    private void showNoConnectionDialog() {
        mNoConnectionDialog.setContentView(R.layout.dialog_no_internet);
        mNoConnectionDialog.setCancelable(false);
        mNoConnectionDialog.show();

        Button tryAgain = mNoConnectionDialog.findViewById(R.id.txt_retry);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
