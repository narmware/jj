package com.narmware.jainjeevan.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.FilterAdapter;
import com.narmware.jainjeevan.pojo.AddDharamshala;
import com.narmware.jainjeevan.pojo.Facility;
import com.narmware.jainjeevan.pojo.FilterResponse;
import com.narmware.jainjeevan.pojo.ApiResponse;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;
import com.narmware.jainjeevan.support.SupportFunctions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddDharamshalaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddDharamshalaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDharamshalaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    protected Spinner mServiceType;
    RecyclerView mRecyclerFacility;
    ArrayList<Facility> facilities;
    FilterAdapter facilityAdapter;

    RecyclerView mRecyclerBhojanFacility;
    ArrayList<Facility> bhojanFacilities;
    FilterAdapter bhojanFacilityAdapter;

    public static ArrayList<String> selected_filters;
    public static Set<String> facilitySet;

    public static ArrayList<String> selected_bhojan_filters;
    public static Set<String> bhojanFacilitySet;

    RequestQueue mVolleyRequest;
    public static Context mContext;

    Button mBtnSubmitForm;
    EditText mEdtName,mEdtContactPerson,mEdtMail,mEdtMobile,mEdtPhone,mEdtCity,mEdtAddress,mEdtPincode,mEdtState;
    public static String mName,mContactPerson,mMail,mMobile,mPhone,mPincode,mCity,mAddress,mType,mState;
    CardView mCardDharamFacility,mCardBhojanFacility;
    int validFlag=0;

    public AddDharamshalaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDharamshalaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDharamshalaFragment newInstance(String param1, String param2) {
        AddDharamshalaFragment fragment = new AddDharamshalaFragment();
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
        View view= inflater.inflate(R.layout.fragment_add_dharamshala, container, false);
        mContext=getContext();
        init(view);
        return view;
    }

    private void init(View view) {
        mVolleyRequest = Volley.newRequestQueue(getContext());
        facilitySet = new HashSet<>();
        selected_filters=new ArrayList<>();

        bhojanFacilitySet = new HashSet<>();
        selected_bhojan_filters=new ArrayList<>();

        mEdtName=view.findViewById(R.id.edt_name);
        mEdtContactPerson=view.findViewById(R.id.edt_contact_person);
        mEdtMail=view.findViewById(R.id.edt_mail);
        mEdtMobile=view.findViewById(R.id.edt_mobile);
        mEdtPhone=view.findViewById(R.id.edt_phone);
        mEdtState=view.findViewById(R.id.edt_state);
        mEdtCity=view.findViewById(R.id.edt_city);
        mEdtAddress=view.findViewById(R.id.edt_address);
        mEdtPincode=view.findViewById(R.id.edt_pincode);

        mCardDharamFacility=view.findViewById(R.id.card_dharam_facility);
        mCardBhojanFacility=view.findViewById(R.id.card_bhojan_facility);

        mServiceType=view.findViewById(R.id.spinn_type);
        mRecyclerFacility=view.findViewById(R.id.recycler_facilities);
        mRecyclerBhojanFacility=view.findViewById(R.id.recycler_bhojan_facilities);

        setSpinner();

        mBtnSubmitForm=view.findViewById(R.id.btn_submit_form);
        mBtnSubmitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName=mEdtName.getText().toString().trim();
                mContactPerson=mEdtContactPerson.getText().toString().trim();
                mMail=mEdtMail.getText().toString().trim();
                mMobile=mEdtMobile.getText().toString().trim();
                mPhone=mEdtPhone.getText().toString().trim();
                mCity=mEdtCity.getText().toString().trim();
                mState=mEdtState.getText().toString().trim();
                mAddress=mEdtAddress.getText().toString().trim();
                mPincode=mEdtPincode.getText().toString().trim();

                SetFilters();
                SetBhojanFilters();
                validateData();
                if(validFlag==0)
                {
                    registerData();
                }
            }
        });

        setFacilityAdapter(new GridLayoutManager(getContext(),2));
        setBhojanFacilityAdapter(new GridLayoutManager(getContext(),2));

        GetFacilities(Constants.TYPE_DHARMSHALA);
        GetBhojanFacilities(Constants.TYPE_BHOJANALAYA);
    }

    public void validateData()
    {
        validFlag=0;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(mAddress.equals(""))
        {
            validFlag=1;
            mEdtAddress.setError("Enter address");
        }
        if(mPincode.length()<6)
        {
            validFlag=1;
            mEdtPincode.setError("Enter valid pincode");
        }
        if(mCity.equals(""))
        {
            validFlag=1;
            mEdtCity.setError("Enter city");
        }
        if(mState.equals(""))
        {
            validFlag=1;
            mEdtState.setError("Enter state");
        }
        if(mPhone.length()<10)
        {
            validFlag=1;
            mEdtPhone.setError("Phone number is short.If entering landline insert STD code");
        }
        if(!mMobile.equals("")) {
            if (mMobile.length() < 10) {
                validFlag = 1;
                mEdtMobile.setError("Enter valid mobile");
            }
        }
        if(mMail.equals("") || !mMail.matches(emailPattern))
        {
            validFlag=1;
            mEdtMail.setError("Enter valid email");
        }
        if(mContactPerson.equals(""))
        {
            validFlag=1;
            mEdtContactPerson.setError("Enter contact person");
        }
        if(mName.equals(""))
        {
            validFlag=1;
            mEdtName.setError("Enter name");
        }

    }
    public void setFacilityAdapter(RecyclerView.LayoutManager mLayoutManager) {
        facilities = new ArrayList<>();
        SnapHelper snapHelper = new LinearSnapHelper();

        facilityAdapter = new FilterAdapter(getContext(), facilities,Constants.DHARAMSHALA);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerFacility.setLayoutManager(mLayoutManager);
        mRecyclerFacility.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerFacility.setAdapter(facilityAdapter);
        mRecyclerFacility.setNestedScrollingEnabled(false);
        mRecyclerFacility.setFocusable(false);

        facilityAdapter.notifyDataSetChanged();
    }

    public void setBhojanFacilityAdapter(RecyclerView.LayoutManager mLayoutManager) {
        bhojanFacilities = new ArrayList<>();
    /*    bhojanFacilities.add(new Facility("1","Chauvihar","",false));
        bhojanFacilities.add(new Facility("2","Weekend open","",false));
        bhojanFacilities.add(new Facility("3","Ambil Khata","",false));
        bhojanFacilities.add(new Facility("4","Breakfast","",false));



        for(int i=0;i<bhojanFacilities.size();i++)
        {
        if(SharedPreferencesHelper.getBhojanFacilities(getContext())!=null)
        {
            bhojanFacilitySet=SharedPreferencesHelper.getBhojanFacilities(getContext());
            if(bhojanFacilitySet.contains(bhojanFacilities.get(i).getFacility_name()))
            {
                Log.e("Sel_bhojan size set",bhojanFacilitySet.size()+"");
                bhojanFacilities.get(i).setSelected(true);
            }
        }
        }*/

        SnapHelper snapHelper = new LinearSnapHelper();

        bhojanFacilityAdapter = new FilterAdapter(getContext(), bhojanFacilities,Constants.BHOJANMSHALA);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerBhojanFacility.setLayoutManager(mLayoutManager);
        mRecyclerBhojanFacility.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerBhojanFacility.setAdapter(bhojanFacilityAdapter);
        mRecyclerBhojanFacility.setNestedScrollingEnabled(false);
        mRecyclerBhojanFacility.setFocusable(false);

        bhojanFacilityAdapter.notifyDataSetChanged();
    }

    private void setSpinner() {
        final List<String> categories = new ArrayList<String>();
        categories.add("Dharamshala");
        categories.add("Bhojanshala");
        categories.add("Both");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mServiceType.setAdapter(dataAdapter);

        mServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType=categories.get(position);


                if(mType.equals("Dharamshala"))
                {
                    mCardDharamFacility.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInRight)
                            .duration(400)
                            .playOn(mCardDharamFacility);


                    YoYo.with(Techniques.SlideOutRight)
                            .duration(400)
                            .playOn(mCardBhojanFacility);
                    mCardBhojanFacility.setVisibility(View.GONE);
                }

                if(mType.equals("Bhojanshala"))
                {
                    mCardBhojanFacility.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInRight)
                            .duration(400)
                            .playOn(mCardBhojanFacility);

                    YoYo.with(Techniques.SlideOutRight)
                            .duration(400)
                            .playOn(mCardDharamFacility);
                    mCardDharamFacility.setVisibility(View.GONE);
                }

                if(mType.equals("Both"))
                {
                    mCardDharamFacility.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInRight)
                            .duration(400)
                            .playOn(mCardDharamFacility);

                    mCardBhojanFacility.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInRight)
                            .duration(400)
                            .playOn(mCardBhojanFacility);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static void SetFilters() {
        facilitySet.clear();
        for(int i=0;i<selected_filters.size();i++) {
            facilitySet.add(selected_filters.get(i));
        }
        // Toast.makeText(context, "Size: "+facilitySet.size(), Toast.LENGTH_SHORT).show();
        SharedPreferencesHelper.setDharamshalaFacilities(null,mContext);
        SharedPreferencesHelper.setDharamshalaFacilities(facilitySet,mContext);
    }

    public static void SetBhojanFilters() {

        bhojanFacilitySet.clear();
        for(int i=0;i<selected_bhojan_filters.size();i++) {
            bhojanFacilitySet.add(selected_bhojan_filters.get(i));
        }
        // Toast.makeText(context, "Size: "+facilitySet.size(), Toast.LENGTH_SHORT).show();
        SharedPreferencesHelper.setBhojanFacilities(null,mContext);
        SharedPreferencesHelper.setBhojanFacilities(bhojanFacilitySet,mContext);
    }

public void registerData()
{
    final ProgressDialog dialog = new ProgressDialog(getContext());
    dialog.setMessage("Getting Details...");
    dialog.setCancelable(false);
    dialog.show();

    Gson gson=new Gson();
    AddDharamshala addDharamshala=new AddDharamshala(mName,mContactPerson,mMail,mMobile,mPhone,mCity,mPincode,mAddress,mState);
    addDharamshala.setOption(mType);
    addDharamshala.setDharamshala_facilities(selected_filters);
    addDharamshala.setBhojan_facilities(selected_bhojan_filters);

    String json_string= gson.toJson(addDharamshala);
    Log.e("Add data json",json_string);

    HashMap<String,String> param = new HashMap();
    param.put(Constants.JSON_STRING,json_string);

    String url= SupportFunctions.appendParam(EndPoints.SET_DHARAM_BHOJAN,param);
    Log.e("Add url",url);

    JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
            new Response.Listener<JSONObject>() {

                // Takes the response from the JSON request
                @Override
                public void onResponse(JSONObject response) {

                    try
                    {
                        Log.e("Add Json_string",response.toString());
                        Gson gson = new Gson();

                        ApiResponse vendorResponse=gson.fromJson(response.toString(),ApiResponse.class);
                        if(vendorResponse.getResponse().equals("100")) {
                            emailCall(mName,mContactPerson,mMail,mMobile,mPhone,mCity,mPincode,mAddress,mState,mType);
                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Registration Successful !")
                                    //.setContentText("Your want to Logout")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            getActivity().onBackPressed();
                                        }
                                    })
                                    .show();
                        }

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

    private void GetFacilities(String filterType) {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.TYPE, filterType);

        String url= SupportFunctions.appendParam(EndPoints.GET_FILTERS,param);
        //url without params
       // String url= EndPoints.GET_FILTERS;

        Log.e("Filter url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Filter Json_string",response.toString());
                            Gson gson = new Gson();

                            FilterResponse filterResponse=gson.fromJson(response.toString(),FilterResponse.class);
                            Facility[] facility=filterResponse.getFacility();

                            for(Facility itemFac:facility){

/*
                                if(SharedPreferencesHelper.getDharamshalaFacilities(getContext())!=null)
                                {
                                    facilitySet=SharedPreferencesHelper.getDharamshalaFacilities(getContext());
                                    if(facilitySet.contains(itemFac.getFacility_name()))
                                    {
                                        facilities.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),true));
                                    }
                                    else{
                                        facilities.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),false));
                                    }
                                }
                                if(SharedPreferencesHelper.getDharamshalaFacilities(getContext())==null)
                                {
                                    facilities.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),false));
                                }*/

                                facilities.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),false));

                            }
                            facilityAdapter.notifyDataSetChanged();

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


    private void GetBhojanFacilities(String filterType) {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.TYPE, filterType);

        String url= SupportFunctions.appendParam(EndPoints.GET_FILTERS,param);
        //url without params
        // String url= EndPoints.GET_FILTERS;

        Log.e("Filter url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Filter Json_string",response.toString());
                            Gson gson = new Gson();

                            FilterResponse filterResponse=gson.fromJson(response.toString(),FilterResponse.class);
                            Facility[] facility=filterResponse.getFacility();

                            for(Facility itemFac:facility){
                              /*
                                if(SharedPreferencesHelper.getDharamshalaFacilities(getContext())!=null)
                                {
                                    bhojanFacilitySet=SharedPreferencesHelper.getDharamshalaFacilities(getContext());
                                    if(bhojanFacilitySet.contains(itemFac.getFacility_name()))
                                    {
                                        bhojanFacilities.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),true));
                                    }
                                    else{
                                        bhojanFacilities.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),false));
                                    }
                                }
                                if(SharedPreferencesHelper.getDharamshalaFacilities(getContext())==null)
                                {
                                    bhojanFacilities.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),false));
                                }*/

                                bhojanFacilities.add(new Facility(itemFac.getFacility_id(),itemFac.getFacility_name(),itemFac.getImg(),false));


                            }
                            bhojanFacilityAdapter.notifyDataSetChanged();

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

    public void emailCall(String mName, String mContactPerson, String mail, String mMobile, String mPhone, String mCity, String mPincode, String mAddress, String mState, String mType)
    {

        HashMap<String,String> param = new HashMap();
        param.put(Constants.NAME,mName);
        param.put(Constants.EMAIL,mail);
        param.put("contact_person",mContactPerson);
        param.put("mobile",mMobile);
        param.put("phone",mPhone);
        param.put("pincode",mPincode);
        param.put("address",mAddress);
        param.put("state",mState);
        param.put("city",mCity);
        param.put("option",mType);

        String url= SupportFunctions.appendParam(EndPoints.EMAIL_CALL,param);
        Log.e("Email url",url);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {


                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                    }
                }
        );
        mVolleyRequest.add(obreq);
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
