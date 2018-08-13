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

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.BhojanalayActivity;
import com.narmware.jainjeevan.activity.DharamshalaActivity2;
import com.narmware.jainjeevan.activity.RestaurantActivity;
import com.narmware.jainjeevan.adapter.RecommendedAdapter;
import com.narmware.jainjeevan.pojo.RecommendedItems;

import java.util.ArrayList;

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

    LinearLayout mLinDharamshala,mLinResto,mLinBhojanalay;

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

        mRecomRecycler=view.findViewById(R.id.home_recycler_recomm);
        mLinDharamshala=view.findViewById(R.id.home_dharamshala);
        mLinBhojanalay=view.findViewById(R.id.home_bhojanalay);
        mLinResto=view.findViewById(R.id.home_restaurant);

        mLinBhojanalay.setOnClickListener(this);
        mLinDharamshala.setOnClickListener(this);
        mLinResto.setOnClickListener(this);

        setRecommendAdapter(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        return view;
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
