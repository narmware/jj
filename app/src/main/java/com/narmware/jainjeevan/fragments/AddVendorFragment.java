package com.narmware.jainjeevan.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.pojo.AddVendor;
import com.narmware.jainjeevan.pojo.ApiResponse;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.SupportFunctions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    EditText mEdtName,mEdtProdName,mEdtContactPerson,mEdtMail,mEdtMobile,mEdtCity,mEdtAddress,mEdtPincode;
    Button mBtnSubmitForm;
    RequestQueue mVolleyRequest;
    int validFlag=0;

    String mName,mProdName,mContactPerson,mMail,mMobile,mPincode,mCity,mAddress;

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

    private void init() {

        mVolleyRequest = Volley.newRequestQueue(getContext());
        //mServiceType = mRoot.findViewById(R.id.form_spinner);
        mEdtName=mRoot.findViewById(R.id.edt_name);
        mEdtProdName=mRoot.findViewById(R.id.edt_prod_name);
        mEdtContactPerson=mRoot.findViewById(R.id.edt_contact_person);
        mEdtMail=mRoot.findViewById(R.id.edt_mail);
        mEdtMobile=mRoot.findViewById(R.id.edt_mobile);
        mEdtCity=mRoot.findViewById(R.id.edt_city);
        mEdtAddress=mRoot.findViewById(R.id.edt_address);
        mEdtPincode=mRoot.findViewById(R.id.edt_pincode);

        mBtnSubmitForm=mRoot.findViewById(R.id.btn_submit_form);
        mBtnSubmitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validFlag=0;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                mName=mEdtName.getText().toString().trim();
                mProdName=mEdtProdName.getText().toString().trim();
                mContactPerson=mEdtContactPerson.getText().toString().trim();
                mMail=mEdtMail.getText().toString().trim();
                mMobile=mEdtMobile.getText().toString().trim();
                mCity=mEdtCity.getText().toString().trim();
                mAddress=mEdtAddress.getText().toString().trim();
                mPincode=mEdtPincode.getText().toString().trim();

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
                    mEdtCity.setError("Enter city");
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
                if(mContactPerson.equals(""))
                {
                    validFlag=1;
                    mEdtContactPerson.setError("Enter contact person");
                }
                if(mProdName.equals(""))
                {
                    validFlag=1;
                    mEdtProdName.setError("Enter product name");
                }
                if(mName.equals(""))
                {
                    validFlag=1;
                    mEdtName.setError("Enter name");
                }


                    if(validFlag==0) {
                    setVendor();
                }
            }
        });
       // setSpinner();
    }

    private void setSpinner() {
        List<String> categories = new ArrayList<String>();
        categories.add("Dharamshala");
        categories.add("Bhojanalaya");
        categories.add("Food Vendor");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mServiceType.setAdapter(dataAdapter);

        mServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_add_vendor, container, false);
        init();
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
        AddVendor addVendor=new AddVendor(mName,mProdName,mContactPerson,mMail,mMobile,mCity,mPincode,mAddress);
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

}
