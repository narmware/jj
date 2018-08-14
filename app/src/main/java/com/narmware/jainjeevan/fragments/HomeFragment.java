package com.narmware.jainjeevan.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.BhojanalayActivity;
import com.narmware.jainjeevan.activity.DharamshalaActivity2;
import com.narmware.jainjeevan.activity.RestaurantActivity;
import com.narmware.jainjeevan.adapter.RecommendedAdapter;
import com.narmware.jainjeevan.pojo.BannerImages;
import com.narmware.jainjeevan.pojo.RecommendedItems;

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

        mSlider=view.findViewById(R.id.slider);
        mBottomSlider=view.findViewById(R.id.slider_bottom);

        mRecomRecycler=view.findViewById(R.id.home_recycler_recomm);
        mLinDharamshala=view.findViewById(R.id.home_dharamshala);
        mLinBhojanalay=view.findViewById(R.id.home_bhojanalay);
        mLinResto=view.findViewById(R.id.home_restaurant);

        mLinBhojanalay.setOnClickListener(this);
        mLinDharamshala.setOnClickListener(this);
        mLinResto.setOnClickListener(this);

        setSlider();
        setBottomSlider();
        setRecommendAdapter(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        return view;
    }

    private void setSlider() {
        bannerImages=new ArrayList<>();
        bannerImages.add(new BannerImages("Banner 1","https://qph.fs.quoracdn.net/main-qimg-7edd17cb52dcc5ba1c6385638a7012c2"));
        bannerImages.add(new BannerImages("Banner 2","https://imgcld.yatra.com/ytimages/image/upload/t_hotel_tg_details_seo/v1433503196/Domestic%20Hotels/Hotels_Dharamshala/Club%20Mahindra%20Dharamshala/Overview_copy.jpg"));
        bannerImages.add(new BannerImages("Banner 3","http://static.trip101.com/paragraph_media/pictures/000/439/296/large/8e15efd2-aa9b-45ea-b75a-5501101da8d4.jpg?1516700440"));

        HashMap<String,String> file_maps = new HashMap<String, String>();

        for(int i=0;i<bannerImages.size();i++)
        {
            file_maps.put(bannerImages.get(i).getBanner_title(),bannerImages.get(i).getService_image());
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
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        //mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //mSlider.setCustomIndicator(custom_indicator);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setFitsSystemWindows(true);
        mSlider.setDuration(3000);

    }

    private void setBottomSlider() {
        bottomBannerImages=new ArrayList<>();
        bottomBannerImages.add(new BannerImages("Banner 1","http://movingnomads.com/blog/wp-content/uploads/2017/08/Digital-Nomad-India.jpg"));
        bottomBannerImages.add(new BannerImages("Banner 2","http://jasperhotels.in/wp-content/uploads/2017/12/view-1.jpg"));
        bottomBannerImages.add(new BannerImages("Banner 3","https://ui.cltpstatic.com/places/hotels/3881/388114/images/Capture_w.jpg"));

        HashMap<String,String> file_maps = new HashMap<String, String>();

        for(int i=0;i<bottomBannerImages.size();i++)
        {
            file_maps.put(bottomBannerImages.get(i).getBanner_title(),bottomBannerImages.get(i).getService_image());
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
        recommendedItems.add(new RecommendedItems("Vendor1","","1"));
        recommendedItems.add(new RecommendedItems("Vendor2","","2"));
        recommendedItems.add(new RecommendedItems("Vendor3","","3"));

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
                Intent intentBhojan=new Intent(getContext(), BhojanalayActivity.class);
                startActivity(intentBhojan);
                break;

            case R.id.home_dharamshala:
                Intent intentDharam=new Intent(getContext(), DharamshalaActivity2.class);
                startActivity(intentDharam);
                break;

            case R.id.home_restaurant:
                Intent intentResto=new Intent(getContext(), RestaurantActivity.class);
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
}
