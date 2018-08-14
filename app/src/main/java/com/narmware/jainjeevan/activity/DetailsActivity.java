package com.narmware.jainjeevan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        FacilitiesFragment.OnFragmentInteractionListener,BhojanshalaFragment.OnFragmentInteractionListener,RulesFragment.OnFragmentInteractionListener{
    PagerAdapter pagerAdapter;
    ViewPager mViewPager;
    RequestQueue mVolleyRequest;
    String id,name,address,img;
    TextView mTxtTitle,mTxtAddress;
    ImageView mImgDharam;
    ArrayList<DetailedItem> mRoomList;
    ArrayList<DetailedItem> mRuleList;
    ArrayList<DetailedItem> mFacilityList;
    ArrayList<DetailedItem> mBhojanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();

        mVolleyRequest = Volley.newRequestQueue(DetailsActivity.this);

        Intent intent=getIntent();
        id=intent.getStringExtra(Constants.ID);
        address=intent.getStringExtra(Constants.ADDRESS);
        name=intent.getStringExtra(Constants.TITLE);
        img=intent.getStringExtra(Constants.IMAGE);

        init();
        GetData();
    }

    private void init() {
        mTxtTitle=findViewById(R.id.txt_title);
        mTxtAddress=findViewById(R.id.txt_address);
        mImgDharam=findViewById(R.id.img_dharam);

        mTxtTitle.setText(name);
        mTxtAddress.setText(address);
        Picasso.with(DetailsActivity.this)
                .load(img)
                .into(mImgDharam);
        mViewPager=findViewById(R.id.view_pager);

        pagerAdapter=new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);

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
    }

    private void GetData() {
        final ProgressDialog dialog = new ProgressDialog(DetailsActivity.this);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();
        HashMap<String,String> param = new HashMap();
        param.put(Constants.ID,id);

        //url with params
        String url= SupportFunctions.appendParam(EndPoints.GET_DATA,param);

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

                            DetailedItemResponse detailedItemResponse=gson.fromJson(response.toString(),DetailedItemResponse.class);
                            DetailedItem[] rooms=detailedItemResponse.getRoom();
                            for(DetailedItem roomItem:rooms)
                            {
                                mRoomList.add(roomItem);
                            }
                            pagerAdapter.addFragment(RoomsFragment.newInstance(mRoomList),"Rooms");
                            pagerAdapter.notifyDataSetChanged();

                            DetailedItem[] rules=detailedItemResponse.getRules();
                            for(DetailedItem ruleItem:rules)
                            {
                                mRuleList.add(ruleItem);
                            }
                            pagerAdapter.addFragment(RoomsFragment.newInstance(mRuleList),"Rules");
                            pagerAdapter.notifyDataSetChanged();

                            DetailedItem[] bhojan=detailedItemResponse.getBhojanshala();
                            for(DetailedItem bhojanItem:bhojan)
                            {
                                mBhojanList.add(bhojanItem);
                            }
                            pagerAdapter.addFragment(RoomsFragment.newInstance(mBhojanList),"Bhojan");
                            pagerAdapter.notifyDataSetChanged();

                            DetailedItem[] facilities=detailedItemResponse.getFacility();
                            for(DetailedItem factem:facilities)
                            {
                                DetailedItem single=new DetailedItem(factem.getItem(),R.drawable.ac);
                                mFacilityList.add(single);
                            }
                            pagerAdapter.addFragment(RoomsFragment.newInstance(mFacilityList),"Facilities");
                            pagerAdapter.notifyDataSetChanged();
                            Log.e("room item size",mRoomList.size()+"");

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
