package com.narmware.jainjeevan.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.DharamshalaActivity2;
import com.narmware.jainjeevan.activity.FoodVendorActivity;
import com.narmware.jainjeevan.adapter.RecommendedAdapter;
import com.narmware.jainjeevan.pojo.BannerImages;
import com.narmware.jainjeevan.pojo.BannerResponse;
import com.narmware.jainjeevan.pojo.RecommendResponse;
import com.narmware.jainjeevan.pojo.RecommendedItems;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<RecommendedItems> recommendedItems;
    RecyclerView mRecomRecycler;
    RecommendedAdapter recommendAdapter;
    SliderLayout mSlider;
    SliderLayout mBottomSlider;
    LinearLayout mLinDharamshala,mLinResto,mLinBhojanalay;
    ArrayList<BannerImages> bannerImages;
    ArrayList<BannerImages> bottomBannerImages;
    RequestQueue mVolleyRequest;
    Dialog mNoConnectionDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        mVolleyRequest = Volley.newRequestQueue(getContext());

        mSlider=view.findViewById(R.id.slider);
        mBottomSlider=view.findViewById(R.id.slider_bottom);

        mRecomRecycler=view.findViewById(R.id.home_recycler_recomm);
        mLinDharamshala=view.findViewById(R.id.home_dharamshala);
        mLinBhojanalay=view.findViewById(R.id.home_bhojanalay);
        mLinResto=view.findViewById(R.id.home_restaurant);

        mLinBhojanalay.setOnClickListener(this);
        mLinDharamshala.setOnClickListener(this);
        mLinResto.setOnClickListener(this);

        /*setSlider();
        setBottomSlider();*/
        bannerImages=new ArrayList<>();
        bottomBannerImages=new ArrayList<>();
        GetBanners();

        setRecommendAdapter(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        GetRecommended();

        mNoConnectionDialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);

        return view;
    }

    private void setSlider() {
        HashMap<String,String> file_maps = new HashMap<String, String>();

        for(int i=0;i<bannerImages.size();i++)
        {
            file_maps.put(bannerImages.get(i).getBanner_title(),bannerImages.get(i).getBanner_image());
        }

        for(String name : file_maps.keySet()){
            //textSliderView displays image with text title
            //TextSliderView textSliderView = new TextSliderView(NavigationActivity.this);
            //DefaultSliderView displays only image
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        //mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //mSlider.setCustomIndicator(custom_indicator);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setFitsSystemWindows(true);
        mSlider.setDuration(3000);

    }

    private void setBottomSlider() {
        HashMap<String,String> file_maps = new HashMap<String, String>();

        for(int i=0;i<bottomBannerImages.size();i++)
        {
            file_maps.put(bottomBannerImages.get(i).getBanner_title(),bottomBannerImages.get(i).getBanner_image());
        }

        for(String name : file_maps.keySet()){
            //textSliderView displays image with text title
            //TextSliderView textSliderView = new TextSliderView(NavigationActivity.this);
            //DefaultSliderView displays only image
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mBottomSlider.addSlider(textSliderView);
        }
        mBottomSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        //mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //mSlider.setCustomIndicator(custom_indicator);
        mBottomSlider.setCustomAnimation(new DescriptionAnimation());
        mBottomSlider.setFitsSystemWindows(true);
        mBottomSlider.setDuration(3000);

    }

    public void setRecommendAdapter(RecyclerView.LayoutManager mLayoutManager) {
        recommendedItems = new ArrayList<>();

        SnapHelper snapHelper = new LinearSnapHelper();

        recommendAdapter = new RecommendedAdapter(getContext(), recommendedItems,getActivity().getSupportFragmentManager());
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecomRecycler.setLayoutManager(mLayoutManager);
        mRecomRecycler.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecomRecycler.setAdapter(recommendAdapter);
        mRecomRecycler.setNestedScrollingEnabled(false);
        mRecomRecycler.setFocusable(false);

        recommendAdapter.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.home_bhojanalay:
               /* Intent intentBhojan=new Intent(getContext(), BhojanalayActivity.class);
                startActivity(intentBhojan);*/

                Toast.makeText(getContext(), "Coming soon !", Toast.LENGTH_SHORT).show();
                break;

            case R.id.home_dharamshala:
                Intent intentDharam=new Intent(getContext(), DharamshalaActivity2.class);
                startActivity(intentDharam);
                break;

            case R.id.home_restaurant:
                Intent intentResto=new Intent(getContext(), FoodVendorActivity.class);
                startActivity(intentResto);
            break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void GetRecommended() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();
        String url=EndPoints.GET_RECOMMENDED;

        //Log.e("Recomm url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Recomm Json_string",response.toString());
                            Gson gson = new Gson();

                            RecommendResponse recommendResponse=gson.fromJson(response.toString(),RecommendResponse.class);
                            RecommendedItems[] resto=recommendResponse.getData();

                            for(RecommendedItems item:resto)
                            {
                                recommendedItems.add(item);
                            }
                            recommendAdapter.notifyDataSetChanged();
                        } catch (Exception e) {

                            e.printStackTrace();
                            //Toast.makeText(NavigationActivity.this, "Invalid album id", Toast.LENGTH_SHORT).show();
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
                        showNoConnectionDialog();
                        dialog.dismiss();
                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void GetBanners() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();
        String url=EndPoints.GET_BANNERS;

        Log.e("Banner url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Banner Json_string",response.toString());
                            Gson gson = new Gson();

                            BannerResponse bannerResponse=gson.fromJson(response.toString(),BannerResponse.class);
                            BannerImages[] banner=bannerResponse.getData();

                            for(BannerImages item:banner)
                            {
                                if(item.getBanner_type().equals(Constants.BANNER_UP))
                                {
                                    bannerImages.add(item);
                                }
                                if(item.getBanner_type().equals(Constants.BANNER_BOTTOM))
                                {
                                    bottomBannerImages.add(item);
                                }
                            }
                            setSlider();
                            setBottomSlider();

                        } catch (Exception e) {

                            e.printStackTrace();
                            //Toast.makeText(NavigationActivity.this, "Invalid album id", Toast.LENGTH_SHORT).show();
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


    public void showNoConnectionDialog() {
        mNoConnectionDialog.setContentView(R.layout.dialog_no_internet);
        mNoConnectionDialog.setCancelable(false);
        mNoConnectionDialog.show();

        Button tryAgain = mNoConnectionDialog.findViewById(R.id.txt_retry);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetRecommended();
            }
        });
    }
}
