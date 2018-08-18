package com.narmware.jainjeevan.fragments;

import android.content.Context;
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
import android.widget.LinearLayout;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.DetailedItemAdapter;
import com.narmware.jainjeevan.adapter.RecommendedAdapter;
import com.narmware.jainjeevan.pojo.DetailedItem;
import com.narmware.jainjeevan.pojo.RecommendedItems;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoomsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoomsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BhojanshalaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST = "list";
    private static final String CALL_FROM = "call_from";

    // TODO: Rename and change types of parameters
    private String callFrom;

    public static ArrayList<DetailedItem> detailedItems;
    public static RecyclerView mRecyclerDetails;
    public static DetailedItemAdapter detailedItemAdapter;
    public static LinearLayout mLinEmpty;

    private OnFragmentInteractionListener mListener;

    public BhojanshalaFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BhojanshalaFragment newInstance(ArrayList<DetailedItem> itemList,String callFrom) {
        BhojanshalaFragment fragment = new BhojanshalaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST,itemList);
        args.putSerializable(CALL_FROM,callFrom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                detailedItems = new ArrayList<>();
                detailedItems = (ArrayList<DetailedItem>) getArguments().getSerializable(ARG_LIST);

                callFrom=getArguments().getString(CALL_FROM);
            }catch (Exception e)
            {}
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bhojanshala, container, false);

        mLinEmpty=view.findViewById(R.id.lin_empty);
        mRecyclerDetails=view.findViewById(R.id.recycler_details);
        setDetailsAdapter(new LinearLayoutManager(getContext()));

        return view;
    }

    public void setDetailsAdapter(RecyclerView.LayoutManager mLayoutManager) {
        Log.e("item size",detailedItems.size()+"");

        SnapHelper snapHelper = new LinearSnapHelper();

        detailedItemAdapter = new DetailedItemAdapter(getContext(), detailedItems,callFrom);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerDetails.setLayoutManager(mLayoutManager);
        mRecyclerDetails.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerDetails.setAdapter(detailedItemAdapter);
        mRecyclerDetails.setNestedScrollingEnabled(false);
        mRecyclerDetails.setFocusable(false);

        detailedItemAdapter.notifyDataSetChanged();
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
