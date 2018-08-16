package com.narmware.jainjeevan.activity;

import android.app.ProgressDialog;
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
import com.narmware.jainjeevan.support.EndPoints;

import org.json.JSONObject;

import java.util.ArrayList;

public class DharamshalaActivity2 extends AppCompatActivity {

    RecyclerView mRecyclerDharam;
    ArrayList<DharamshalaItem> dharamshalaItems;
    DharamshalaAdapter dharamshalaAdapter;
    RequestQueue mVolleyRequest;
    TextView mTxtTitle;
    ImageView mBtnBack;

    FloatingActionButton mFabFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dharamshala);
        getSupportActionBar().hide();

        init();
        setDharamshalaAdapter(new LinearLayoutManager(DharamshalaActivity2.this));
        GetDharamshalas();
    }
    private void init() {
        mVolleyRequest = Volley.newRequestQueue(DharamshalaActivity2.this);
        mRecyclerDharam=findViewById(R.id.recycler_dharamshala);
        mTxtTitle=findViewById(R.id.txt_title);
        mBtnBack=findViewById(R.id.btn_back);

       // mFabFilter=findViewById(R.id.fab_filter);

        mTxtTitle.setText("Dharamshala");

      /*  mFabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DharamshalaActivity2.this,FilterActivity.class);
                startActivity(intent);
            }
        });
*/
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

    private void GetDharamshalas() {
        final ProgressDialog dialog = new ProgressDialog(DharamshalaActivity2.this);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        //url without params
        String url= EndPoints.GET_DHARAMSHALA;

        Log.e("Dharam url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //Log.e("Dharam Json_string",response.toString());
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
