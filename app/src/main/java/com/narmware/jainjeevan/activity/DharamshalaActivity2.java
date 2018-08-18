package com.narmware.jainjeevan.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.DharamshalaAdapter;
import com.narmware.jainjeevan.adapter.RecommendedAdapter;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.DharamshalaResponse;
import com.narmware.jainjeevan.pojo.RecommendedItems;
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

public class DharamshalaActivity2 extends AppCompatActivity {

    RecyclerView mRecyclerDharam;
    public static ArrayList<DharamshalaItem> dharamshalaItems;
    public static DharamshalaAdapter dharamshalaAdapter;
    public static RequestQueue mVolleyRequest;
    TextView mTxtTitle;
    ImageView mBtnBack;

    public static ArrayList<String> selected_filters;
    public static String selected_city_id;
    public static Set<String> facilitySet;

    FloatingActionButton mFabFilter;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dharamshala);
        getSupportActionBar().hide();
        context=DharamshalaActivity2.this;
        init();
        setDharamshalaAdapter(new LinearLayoutManager(DharamshalaActivity2.this));

        if(SharedPreferencesHelper.getFilteredFacilities(DharamshalaActivity2.this)!=null)
        {
            selected_filters=new ArrayList<>();
            SendFilters sendFilters=new SendFilters();
            sendFilters.setCity_id(SharedPreferencesHelper.getFilteredCity(DharamshalaActivity2.this));
            facilitySet=SharedPreferencesHelper.getFilteredFacilities(DharamshalaActivity2.this);
            //Toast.makeText(context, "Size: "+facilitySet.size(), Toast.LENGTH_SHORT).show();

            selected_filters.addAll(facilitySet);
            sendFilters.setFacilities(selected_filters);

            Gson gson=new Gson();
            String json_string=gson.toJson(sendFilters);
            Log.e("Filter json_string",json_string);

            HashMap<String,String> param = new HashMap();
            param.put(Constants.JSON_STRING,json_string);

            //url with params
            String url= SupportFunctions.appendParam(EndPoints.GET_FILTERED_DATA,param);
            GetDharamshalas(url);
        }else{
            GetDharamshalas(EndPoints.GET_DHARAMSHALA);
        }
    }
    private void init() {
        mVolleyRequest = Volley.newRequestQueue(DharamshalaActivity2.this);
        mRecyclerDharam=findViewById(R.id.recycler_dharamshala);
        mTxtTitle=findViewById(R.id.txt_title);
        mBtnBack=findViewById(R.id.btn_back);

        mFabFilter=findViewById(R.id.fab_filter);

        mTxtTitle.setText("Dharamshala");

        mFabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DharamshalaActivity2.this,FilterActivity.class);
                startActivity(intent);
            }
        });
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    public static void GetDharamshalas(String url) {
        dharamshalaItems.clear();
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        Log.e("Dharam url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Dharam Json_string",response.toString());
                            Gson gson = new Gson();

                            DharamshalaResponse dharamshalaResponse=gson.fromJson(response.toString(),DharamshalaResponse.class);
                            DharamshalaItem[] mlist=dharamshalaResponse.getData();

                            for(DharamshalaItem item:mlist){
                                dharamshalaItems.add(item);
                            }
                            dharamshalaAdapter.notifyDataSetChanged();
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


}
