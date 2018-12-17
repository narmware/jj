package com.narmware.jainjeevan.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.activity.FilterActivity;
import com.narmware.jainjeevan.adapter.AreaAdapter;
import com.narmware.jainjeevan.adapter.CityAdapter;
import com.narmware.jainjeevan.adapter.FilterAdapter;
import com.narmware.jainjeevan.pojo.AddVendor;
import com.narmware.jainjeevan.pojo.ApiResponse;
import com.narmware.jainjeevan.pojo.Area;
import com.narmware.jainjeevan.pojo.AreaResponse;
import com.narmware.jainjeevan.pojo.City;
import com.narmware.jainjeevan.pojo.Facility;
import com.narmware.jainjeevan.pojo.FilterResponse;
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
 * {@link AddVendorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddVendorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVendorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    protected View mRoot;
    protected Spinner mServiceType;
    CheckBox mChkDelAvail;
    Dialog mNoConnectionDialog;

    EditText mEdtName,mEdtContactPerson,mEdtMail,mEdtMobile,mEdtCity,mEdtAddress,mEdtPincode,mEdtKm,mEdtSpecialFood;
    Button mBtnSubmitForm;
    RequestQueue mVolleyRequest;
    int validFlag=0;
    public static Context mContext;

    String mName,mProdName,mContactPerson,mMail,mMobile,mPincode,mAddress,mKm,mSpecialFood;
    String mCity="";
    String mArea="";
    RecyclerView mRecyclerFoodType;
    ArrayList<Facility> foodTypes;
    FilterAdapter foodTypesAdapter;


    Spinner mCitySpinner;
    ArrayList<City> cities;
    CityAdapter cityAdapter;

    Spinner mAreaSpinner;
    ArrayList<Area> areas;
    AreaAdapter areaAdapter;
    public static String selected_city_id;
    public static String selected_area_id;

    public static ArrayList<String> selected_food_items;
    public static Set<String> foodTypesSet;

    private OnFragmentInteractionListener mListener;

    public AddVendorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVendorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVendorFragment newInstance(String param1, String param2) {
        AddVendorFragment fragment = new AddVendorFragment();
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

    private void init(View view) {

        foodTypesSet = new HashSet<>();
        selected_food_items=new ArrayList<>();
        mNoConnectionDialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);

        mVolleyRequest = Volley.newRequestQueue(getContext());
        //mServiceType = mRoot.findViewById(R.id.form_spinner);
        mEdtName=mRoot.findViewById(R.id.edt_name);
        //mEdtProdName=mRoot.findViewById(R.id.edt_prod_name);
        mEdtContactPerson=mRoot.findViewById(R.id.edt_contact_person);
        mEdtMail=mRoot.findViewById(R.id.edt_mail);
        mEdtMobile=mRoot.findViewById(R.id.edt_mobile);
        mEdtCity=mRoot.findViewById(R.id.edt_city);
        mEdtAddress=mRoot.findViewById(R.id.edt_address);
        mEdtPincode=mRoot.findViewById(R.id.edt_pincode);
        mEdtKm=mRoot.findViewById(R.id.edt_km);
        mEdtSpecialFood=mRoot.findViewById(R.id.edt_special_food);

        mRecyclerFoodType=mRoot.findViewById(R.id.recycler_food_type);
        mChkDelAvail=mRoot.findViewById(R.id.chk_del_avail);

        mBtnSubmitForm=mRoot.findViewById(R.id.btn_submit_form);
        mBtnSubmitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validFlag=0;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                mName=mEdtName.getText().toString().trim();
               // mProdName=mEdtProdName.getText().toString().trim();
                mContactPerson=mEdtContactPerson.getText().toString().trim();
                mMail=mEdtMail.getText().toString().trim();
                mMobile=mEdtMobile.getText().toString().trim();
               // mCity=mEdtCity.getText().toString().trim();
                mAddress=mEdtAddress.getText().toString().trim();
                mPincode=mEdtPincode.getText().toString().trim();
                mSpecialFood=mEdtSpecialFood.getText().toString().trim();

                if(mChkDelAvail.isChecked()==true)
                {
                    mKm=mEdtKm.getText().toString().trim();

                    if(!mKm.equals("")) {
                        int tempKm= Integer.parseInt(mKm);
                        if (tempKm == 0 || tempKm < 0) {
                            validFlag = 1;
                            mEdtKm.setError("Kilometers should not be 0");
                        }
                    }

                    if(mKm.equals(""))
                    {
                        validFlag=1;
                        mEdtKm.setError("Kilometers should not be blank");
                    }
                }

                if(mChkDelAvail.isChecked()==false)
                {
                    mKm="-1";
                }

                if(mPincode.length()<6)
                {
                    validFlag=1;
                    mEdtPincode.setError("Enter valid pincode");
                }

                if(mAddress.equals(""))
                {
                    validFlag=1;
                    mEdtAddress.setError("Enter address");
                }
                if(mCity.equals(""))
                {
                    validFlag=1;
                    Toast.makeText(getContext(), "Please select city", Toast.LENGTH_SHORT).show();
                }
                if(mArea.equals(""))
                {
                    validFlag=1;
                    Toast.makeText(getContext(), "Please select area", Toast.LENGTH_SHORT).show();
                }
                if(mMobile.length()<10)
                {
                    validFlag=1;
                    mEdtMobile.setError("Enter valid mobile");
                }
                if(mMail.equals("") || !mMail.matches(emailPattern))
                {
                    validFlag=1;
                    mEdtMail.setError("Enter valid email");
                }
                //as per requirnment
                /* if(mContactPerson.equals(""))
                {
                    validFlag=1;
                    mEdtContactPerson.setError("Enter contact person");
                }
               if(mProdName.equals(""))
                {
                    validFlag=1;
                    mEdtProdName.setError("Enter product name");
                }*/

                if(mName.equals(""))
                {
                    validFlag=1;
                    mEdtName.setError("Enter name");
                }

                setFoodFilter();

                    if(validFlag==0) {
                        setVendor();
                }


            }
        });

        mChkDelAvail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==false)
                {
                    mEdtKm.setEnabled(false);
                }
                else {
                    mEdtKm.setEnabled(true);
                }
            }
        });

        setFoodTypesAdapter(new GridLayoutManager(getContext(),2));

        // setSpinner();

        areas = new ArrayList<>();
        cities=new ArrayList<>();
        City city=new City("Pune","2");
        City city1=new City("Other","0");
        cities.add(city);
        cities.add(city1);

        mCitySpinner=view.findViewById(R.id.spinn_city);
        cityAdapter=new CityAdapter(getContext(),cities);
        mCitySpinner.setAdapter(cityAdapter);

        mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCity=cities.get(position).getCity_name();
                Toast.makeText(getContext(), mCity, Toast.LENGTH_SHORT).show();
                selected_city_id=cities.get(position).getCity_id();
                if(selected_city_id=="2") {
                    GetAreas(selected_city_id);
                }

                if(selected_city_id=="0") {
                    areas.clear();
                    Area area=new Area();
                    area.setArea_id("0");
                    area.setArea_name("Other");
                    areas.add(area);
                    areaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        mAreaSpinner = view.findViewById(R.id.spinn_area);
        areaAdapter = new AreaAdapter(getContext(), areas);
        mAreaSpinner.setAdapter(areaAdapter);

        mAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mArea=areas.get(position).getArea_name();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //GetFilters();
    }

    public void setFoodTypesAdapter(RecyclerView.LayoutManager mLayoutManager) {
        foodTypes = new ArrayList<>();
        foodTypes.add(new Facility("1","Lunch","",false));
        foodTypes.add(new Facility("2","Breakfast","",false));
        foodTypes.add(new Facility("3","Dinner","",false));
        foodTypes.add(new Facility("4","Snacks","",false));
        foodTypes.add(new Facility("5","Jain Cake","",false));
        foodTypes.add(new Facility("6","Others","",false));


        for(int i=0;i<foodTypes.size();i++)
        {
            if(SharedPreferencesHelper.getFoodTypes(getContext())!=null)
            {
                foodTypesSet=SharedPreferencesHelper.getFoodTypes(getContext());
                if(foodTypesSet.contains(foodTypes.get(i).getFacility_name()))
                {
                    Log.e("food type size set",foodTypesSet.size()+"");
                    foodTypes.get(i).setSelected(true);
                }
            }
        }

        SnapHelper snapHelper = new LinearSnapHelper();

        foodTypesAdapter = new FilterAdapter(getContext(), foodTypes,Constants.ADD_VENDOR);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerFoodType.setLayoutManager(mLayoutManager);
        mRecyclerFoodType.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerFoodType.setAdapter(foodTypesAdapter);
        mRecyclerFoodType.setNestedScrollingEnabled(false);
        mRecyclerFoodType.setFocusable(false);

        foodTypesAdapter.notifyDataSetChanged();
    }

    public static void setFoodFilter() {

        foodTypesSet.clear();
        for(int i=0;i<selected_food_items.size();i++) {
            foodTypesSet.add(selected_food_items.get(i));
        }
         //Toast.makeText(mContext, "Size: "+foodTypesSet.size(), Toast.LENGTH_SHORT).show();
        SharedPreferencesHelper.setFoodTypes(null,mContext);
        SharedPreferencesHelper.setFoodTypes(foodTypesSet,mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_add_vendor, container, false);
        mContext=getContext();
        init(mRoot);
        return mRoot;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setVendor() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();
        AddVendor addVendor=new AddVendor(mName,mProdName,mContactPerson,mMail,mMobile,mCity,mPincode,mAddress,mKm,mSpecialFood,mArea);
        //addVendor.setFood_types(selected_food_items);
        String json_string=gson.toJson(addVendor);

        HashMap<String,String> param = new HashMap();
        param.put(Constants.JSON_STRING,json_string);
        Log.e("Vendor json_String",json_string);

        String url= SupportFunctions.appendParam(EndPoints.SET_VENDOR,param);

        Log.e("Vendor url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Vendor Json_string",response.toString());
                            Gson gson = new Gson();

                            ApiResponse vendorResponse=gson.fromJson(response.toString(),ApiResponse.class);
                            if(vendorResponse.getResponse().equals("100")) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Registeration Successfull !")
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

    private void GetFilters() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        //url without params
        //String url= EndPoints.GET_FILTERS;
        HashMap<String,String> param = new HashMap();

        String url= SupportFunctions.appendParam(EndPoints.GET_FILTERS,param);

        Log.e("Filter url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                          //  Log.e("Filter Json_string",response.toString());
                            Gson gson = new Gson();

                            FilterResponse filterResponse=gson.fromJson(response.toString(),FilterResponse.class);
                            City[] city=filterResponse.getCity();
                            for(City item:city){
                                cities.add(item);
                            }

                            cityAdapter.notifyDataSetChanged();
                            areas.clear();
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

    private void GetAreas(String selected_city_id) {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        //url without params
        //String url= EndPoints.GET_FILTERS;
        HashMap<String,String> param = new HashMap();
        param.put("city_id",selected_city_id);

        String url= SupportFunctions.appendParam(EndPoints.GET_AREA,param);

        Log.e("Area url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //Log.e("Filter Json_string",response.toString());
                            Gson gson = new Gson();

                            AreaResponse filterResponse=gson.fromJson(response.toString(),AreaResponse.class);
                            Area[] area=filterResponse.getData();
                            areas.clear();

                            for(Area item:area){
                                areas.add(item);
                            }
                           areaAdapter.notifyDataSetChanged();

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

    private void showNoConnectionDialog() {
        mNoConnectionDialog.setContentView(R.layout.dialog_no_internet);
        mNoConnectionDialog.setCancelable(false);
        mNoConnectionDialog.show();

        Button tryAgain = mNoConnectionDialog.findViewById(R.id.txt_retry);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
