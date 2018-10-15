package com.narmware.jainjeevan.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.narmware.jainjeevan.fragments.AddressFragment;
import com.narmware.jainjeevan.fragments.BhojanshalaFragment;
import com.narmware.jainjeevan.fragments.FacilitiesFragment;
import com.narmware.jainjeevan.fragments.ProfileFragment;
import com.narmware.jainjeevan.fragments.RoomsFragment;
import com.narmware.jainjeevan.fragments.RulesFragment;
import com.narmware.jainjeevan.pojo.DetailedItem;
import com.narmware.jainjeevan.pojo.DetailedItemResponse;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.SupportFunctions;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener,RoomsFragment.OnFragmentInteractionListener,
        FacilitiesFragment.OnFragmentInteractionListener,BhojanshalaFragment.OnFragmentInteractionListener,RulesFragment.OnFragmentInteractionListener,
        AddressFragment.OnFragmentInteractionListener{

    PagerAdapter pagerAdapter;
    ViewPager mViewPager;
    RequestQueue mVolleyRequest;
    String id,name,address,img,latitude,longitude,contact_person,contact_number;
    TextView mTxtTitle;
    ImageView mImgDharam;
    ArrayList<DetailedItem> mRoomList;
    ArrayList<DetailedItem> mRuleList;
    ArrayList<DetailedItem> mFacilityList;
    ArrayList<DetailedItem> mBhojanList;
    ImageView mBtnBack;
    Dialog mNoConnectionDialog;

    String dataType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();


        mVolleyRequest = Volley.newRequestQueue(DetailsActivity.this);
        ActivityCompat.requestPermissions(DetailsActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
        Intent intent=getIntent();
        id=intent.getStringExtra(Constants.ID);
        address=intent.getStringExtra(Constants.ADDRESS);
        name=intent.getStringExtra(Constants.TITLE);
        img=intent.getStringExtra(Constants.IMAGE);
        latitude=intent.getStringExtra(Constants.LATITUDE);
        longitude=intent.getStringExtra(Constants.LONGITUDE);
        contact_number=intent.getStringExtra(Constants.MOBILE_NUMBER);
        contact_person=intent.getStringExtra(Constants.CONTACT_PERSON);
        dataType=intent.getStringExtra(Constants.TYPE);

        init();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.ID,id);

        if(dataType.equals(Constants.TYPE_DHARMSHALA)) {
            String url = SupportFunctions.appendParam(EndPoints.GET_DATA, param);
            GetData(url);
        }
        if(dataType.equals(Constants.TYPE_BHOJANALAYA)) {
            String url = SupportFunctions.appendParam(EndPoints.GET_BHOJANALAY_DATA, param);
            GetBhojanalayData(url);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(DetailsActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void init() {
        mTxtTitle=findViewById(R.id.txt_title);
        //mTxtAddress=findViewById(R.id.txt_address);
        mImgDharam=findViewById(R.id.img_dharam);

        mBtnBack=findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mNoConnectionDialog = new Dialog(DetailsActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);

        mTxtTitle.setText(name);
        //mTxtAddress.setText(address);
        Picasso.with(DetailsActivity.this)
                //.load(img)
                .load(R.drawable.placeholder)
                .into(mImgDharam);
        mViewPager=findViewById(R.id.view_pager);
        pagerAdapter=new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);

        pagerAdapter.addFragment(AddressFragment.newInstance(latitude,longitude,address,name,contact_person,contact_number),"Address");
        pagerAdapter.notifyDataSetChanged();

        TabLayout viewPagerTab =  findViewById(R.id.simpleTabLayout);
        viewPagerTab.setupWithViewPager(mViewPager);

        mRoomList=new ArrayList<>();
        mRuleList=new ArrayList<>();
        mFacilityList=new ArrayList<>();
        mBhojanList=new ArrayList<>();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        List<Fragment> fragments=new ArrayList<>();
        List<String> mFragmentTitleList = new ArrayList<>();

        @Override
        public Fragment getItem(int index) {

            return fragments.get(index);
        }

        public void addFragment(Fragment fragment,String title) {
            fragments.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return fragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private void GetData(String url) {
        final ProgressDialog dialog = new ProgressDialog(DetailsActivity.this);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        Log.e("Login url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {

                            Log.e("Login Json_string",response.toString());
                            Gson gson = new Gson();

                            DetailedItemResponse detailedItemResponse = gson.fromJson(response.toString(), DetailedItemResponse.class);

                                DetailedItem[] rooms = detailedItemResponse.getRoom();
                                for (DetailedItem roomItem : rooms) {
                                    mRoomList.add(roomItem);
                                }
                                pagerAdapter.addFragment(RoomsFragment.newInstance(mRoomList, "Rooms"), "Rooms");
                                pagerAdapter.notifyDataSetChanged();


                            DetailedItem[] rules=detailedItemResponse.getRules();
                            for(DetailedItem ruleItem:rules)
                            {
                                mRuleList.add(ruleItem);
                            }
                            pagerAdapter.addFragment(RulesFragment.newInstance(mRuleList,"Rules"),"Rules");
                            pagerAdapter.notifyDataSetChanged();

                                DetailedItem[] bhojan = detailedItemResponse.getBhojanshala();
                                for (DetailedItem bhojanItem : bhojan) {
                                    mBhojanList.add(bhojanItem);
                                }
                                pagerAdapter.addFragment(BhojanshalaFragment.newInstance(mBhojanList, "Bhojan"), "Bhojan");
                                pagerAdapter.notifyDataSetChanged();

                            DetailedItem[] facilities=detailedItemResponse.getFacility();
                            for(DetailedItem factem:facilities)
                            {
                                if(factem.getImg().equals(""))
                                {
                                    /*DetailedItem single=new DetailedItem(factem.getItem(),"empty");
                                    mFacilityList.add(single);*/
                                }
                                else {
                                    DetailedItem single=new DetailedItem(factem.getItem(),factem.getImg());
                                    mFacilityList.add(single);
                                }
                            }
                            pagerAdapter.addFragment(FacilitiesFragment.newInstance(mFacilityList,"Facilities"),"Facilities");
                            pagerAdapter.notifyDataSetChanged();

                            mViewPager.setOffscreenPageLimit(4);

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

    private void GetBhojanalayData(String url) {
        final ProgressDialog dialog = new ProgressDialog(DetailsActivity.this);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        Log.e("Login url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {

                            Log.e("Login Json_string",response.toString());
                            Gson gson = new Gson();

                            DetailedItemResponse detailedItemResponse = gson.fromJson(response.toString(), DetailedItemResponse.class);

                            DetailedItem[] rules=detailedItemResponse.getRules();
                            for(DetailedItem ruleItem:rules)
                            {
                                mRuleList.add(ruleItem);
                            }
                            pagerAdapter.addFragment(RulesFragment.newInstance(mRuleList,"Rules"),"Rules");
                            pagerAdapter.notifyDataSetChanged();


                            DetailedItem[] bhojan = detailedItemResponse.getBhojanshala();
                            for (DetailedItem bhojanItem : bhojan) {
                                mBhojanList.add(bhojanItem);
                            }
                            pagerAdapter.addFragment(BhojanshalaFragment.newInstance(mBhojanList, "Bhojan"), "Timings");
                            pagerAdapter.notifyDataSetChanged();

                            DetailedItem[] facilities=detailedItemResponse.getFacility();
                            for(DetailedItem factem:facilities)
                            {
                                if(factem.getImg().equals(""))
                                {
                                    /*DetailedItem single=new DetailedItem(factem.getItem(),"empty");
                                    mFacilityList.add(single);*/
                                }
                                else {
                                    DetailedItem single=new DetailedItem(factem.getItem(),factem.getImg());
                                    mFacilityList.add(single);
                                }
                            }
                            pagerAdapter.addFragment(FacilitiesFragment.newInstance(mFacilityList,"Facilities"),"Facilities");
                            pagerAdapter.notifyDataSetChanged();

                            mViewPager.setOffscreenPageLimit(4);

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


    public void showNoConnectionDialog() {
        mNoConnectionDialog.setContentView(R.layout.dialog_no_internet);
        mNoConnectionDialog.setCancelable(false);
        mNoConnectionDialog.show();

        Button tryAgain = mNoConnectionDialog.findViewById(R.id.txt_retry);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> param = new HashMap();
                param.put(Constants.ID,id);
                if(dataType.equals(Constants.TYPE_DHARMSHALA)) {
                    String url = SupportFunctions.appendParam(EndPoints.GET_DATA, param);
                    GetData(url);
                }
                if(dataType.equals(Constants.TYPE_BHOJANALAYA)) {
                    String url = SupportFunctions.appendParam(EndPoints.GET_BHOJANALAY_DATA, param);
                    GetBhojanalayData(url);
                }            }
        });
    }


}
