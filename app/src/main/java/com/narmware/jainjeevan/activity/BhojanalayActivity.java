package com.narmware.jainjeevan.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.BhojanAdapter;
import com.narmware.jainjeevan.adapter.DharamshalaAdapter;
import com.narmware.jainjeevan.pojo.BhojanItems;
import com.narmware.jainjeevan.pojo.DharamshalaItem;

import org.json.JSONObject;

import java.util.ArrayList;

public class BhojanalayActivity extends AppCompatActivity {

    RecyclerView mRecyclerBhojan;
    ArrayList<BhojanItems> bhojanItems;
    BhojanAdapter bhojanAdapter;
    RequestQueue mVolleyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhojanalay);
        getSupportActionBar().hide();

        init();
        setBhojanAdapter(new LinearLayoutManager(BhojanalayActivity.this));
    }

    private void init() {
        mVolleyRequest = Volley.newRequestQueue(BhojanalayActivity.this);
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

    private void GetBhojanalays() {


        final ProgressDialog dialog = new ProgressDialog(BhojanalayActivity.this);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();
        // String json_string=gson.toJson(bookSchedule);
        //Log.e("Schedule json",json_string);
/*

        HashMap<String,String> param = new HashMap();
        param.put(Constants.MOBILE_NUMBER,mMobile);
        param.put(Constants.PASSWORD,mPassword);
*/

        //url with params
        //String url= SupportFunctions.appendParam(EndPoints.DETAILS_URL,param);

        //url without params
        String url= "";

        Log.e("Login url",url);
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

                            Log.e("Login Json_string",response.toString());
                            Gson gson = new Gson();

                        } catch (Exception e) {

                            e.printStackTrace();
                            //Toast.makeText(NavigationActivity.this, "Invalid album id", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
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
                        dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

}
