package com.narmware.jainjeevan.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.narmware.jainjeevan.adapter.RestoAdapter;
import com.narmware.jainjeevan.pojo.DharamshalaItem;
import com.narmware.jainjeevan.pojo.RestoItemResponse;
import com.narmware.jainjeevan.pojo.RestoItems;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.SupportFunctions;

import org.json.JSONObject;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {

    RecyclerView mRecyclerResto;
    ArrayList<RestoItems> restoItems;
    RestoAdapter restoAdapter;
    RequestQueue mVolleyRequest;
    TextView mTxtTitle;
    ImageView mBtnBack;
    public static LinearLayout mLinEmpty;
    Dialog mNoConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        getSupportActionBar().hide();

        init();
        setRestoAdapter(new LinearLayoutManager(RestaurantActivity.this));
        GetRestos();
    }

    private void init() {
        mVolleyRequest = Volley.newRequestQueue(RestaurantActivity.this);
        mRecyclerResto=findViewById(R.id.recycler_resto);
        mTxtTitle=findViewById(R.id.txt_title);
        mBtnBack=findViewById(R.id.btn_back);
        mLinEmpty=findViewById(R.id.lin_empty);
        mNoConnectionDialog = new Dialog(RestaurantActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);

        mTxtTitle.setText("Restaurants");
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setRestoAdapter(RecyclerView.LayoutManager mLayoutManager) {
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

    private void GetRestos() {

        final ProgressDialog dialog = new ProgressDialog(RestaurantActivity.this);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        String url=EndPoints.GET_HOTELS;

        Log.e("Resto url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //getting test master array
                            // testMasterDetails = testMasterArray.toString();

                            Log.e("Resto Json_string",response.toString());
                            Gson gson = new Gson();

                            RestoItemResponse restoItemResponse=gson.fromJson(response.toString(),RestoItemResponse.class);
                            RestoItems[] resto=restoItemResponse.getData();

                            for(RestoItems item:resto)
                            {
                                restoItems.add(item);
                            }
                            restoAdapter.notifyDataSetChanged();
                        } catch (Exception e) {

                            e.printStackTrace();
                            //Toast.makeText(NavigationActivity.this, "Invalid album id", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        if(mNoConnectionDialog.isShowing()==true)
                        {
                            mNoConnectionDialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
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

    public void showNoConnectionDialog() {
        mNoConnectionDialog.setContentView(R.layout.dialog_no_internet);
        mNoConnectionDialog.setCancelable(false);
        mNoConnectionDialog.show();

        Button tryAgain = mNoConnectionDialog.findViewById(R.id.txt_retry);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetRestos();
            }
        });
    }
}
