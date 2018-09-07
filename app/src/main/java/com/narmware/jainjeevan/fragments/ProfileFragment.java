package com.narmware.jainjeevan.fragments;

import android.Manifest;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.narmware.jainjeevan.activity.OtpLoginActivity;
import com.narmware.jainjeevan.pojo.Profile;
import com.narmware.jainjeevan.pojo.ApiResponse;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.ImageBlur;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;
import com.narmware.jainjeevan.support.SupportFunctions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    protected View mRoot;
    ImageButton mImgBtnProfChange;
    RadioGroup radioGroup;
    RadioButton radBtnMale,radBtnFemale;

    public static CircleImageView mImgProf;
    ImageView imgBlurredBack;

    Button mBtnProfileUpdate;
    public static Button mBtnDob;
    EditText mEdtCity,mEdtState,mEdtPincode,mEdtAddress;
    String mDob,mState,mPincode,mCity,mAddress,mGender;
    RequestQueue mVolleyRequest;
    Bitmap bitmap;
    int validData=0;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    private void init() {
        mVolleyRequest = Volley.newRequestQueue(getContext());
        TextView name = mRoot.findViewById(R.id.profile_name);
        TextView email = mRoot.findViewById(R.id.profile_email);
        TextView mobile = mRoot.findViewById(R.id.profile_mobile);
        Button mBtnLogout=mRoot.findViewById(R.id.btn_logout);
        mEdtCity=mRoot.findViewById(R.id.profile_city);
        mEdtState=mRoot.findViewById(R.id.profile_state);
        mEdtAddress=mRoot.findViewById(R.id.profile_address);
        mEdtPincode=mRoot.findViewById(R.id.profile_pincode);
        imgBlurredBack=mRoot.findViewById(R.id.profile_top_root);

        radioGroup =mRoot.findViewById(R.id.rad_grp_gender);
        radBtnMale=mRoot.findViewById(R.id.rad_male);
        radBtnFemale=mRoot.findViewById(R.id.rad_female);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioButton = mRoot.findViewById(selectedId);
                mGender=radioButton.getText().toString().trim();

            }
        });

        mBtnDob=mRoot.findViewById(R.id.btn_dob);
        mBtnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getActivity().getFragmentManager(), "DatePicker");
            }
        });

        mBtnProfileUpdate=mRoot.findViewById(R.id.profile_update);
        mBtnProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCity=mEdtCity.getText().toString().trim();
                mState=mEdtState.getText().toString().trim();
                mAddress=mEdtAddress.getText().toString().trim();
                mPincode=mEdtPincode.getText().toString().trim();
                mDob=mBtnDob.getText().toString().trim();


                updateProfile();
            }
        });

        mImgProf=mRoot.findViewById(R.id.prof_image);

        try {
            if (SharedPreferencesHelper.getUserProfileImage(getContext()) != null) {
                Picasso.with(getContext())
                        .load(SharedPreferencesHelper.getUserProfileImage(getContext()))
                        .placeholder(R.drawable.logo)
                        .into(mImgProf);

                bitmap = new ImageBlur().getBitmapFromURL(SharedPreferencesHelper.getUserProfileImage(getContext()));
                imgBlurredBack.setImageBitmap(new ImageBlur().fastblur(bitmap, 20));

                //mImgProf.setImageBitmap(BitmapFactory.decodeFile(SharedPreferencesHelper.getUserProfileImage(getContext())));
            }
        }catch (Exception e)
        {
            
        }
        mImgBtnProfChange=mRoot.findViewById(R.id.edit_prof_img);
        mImgBtnProfChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Constants.REQUEST_CODE);
            }
        });

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure")
                        .setContentText("Your want to Logout")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                SharedPreferencesHelper.setIsLogin(false,getContext());
                                SharedPreferencesHelper.setFilteredCity(null,getContext());
                                SharedPreferencesHelper.setFilteredFacilities(null,getContext());
                                SharedPreferencesHelper.setUserLocation(null,getContext());

                                Intent intent=new Intent(getContext(),OtpLoginActivity.class);
                                getContext().startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .showCancelButton(true)
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        name.setText(SharedPreferencesHelper.getUserName(getActivity()));
        email.setText(SharedPreferencesHelper.getUserEmail(getActivity()));
        mobile.setText(SharedPreferencesHelper.getUserMobile(getActivity()));
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        mRoot = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        getUserProfile();
        return mRoot;
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
                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
            // other 'case' lines to check for other
            // permissions this app might request
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

    private void updateProfile() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Updating profile...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();
        Profile updateProfile=new Profile(SharedPreferencesHelper.getUserId(getContext()),mCity,mState,mPincode,mAddress,mDob,mGender) ;
        String json_string=gson.toJson(updateProfile);

        HashMap<String,String> param = new HashMap();
        param.put(Constants.JSON_STRING,json_string);
        Log.e("profile json_String",json_string);

        String url= SupportFunctions.appendParam(EndPoints.SET_PROFILE,param);

        Log.e("profile url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("profile Json_string",response.toString());
                            Gson gson = new Gson();

                            ApiResponse vendorResponse=gson.fromJson(response.toString(),ApiResponse.class);
                            if(vendorResponse.getResponse().equals("100")) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Profile update Successfully !")
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

    private void getUserProfile() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting profile...");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.USER_ID,SharedPreferencesHelper.getUserId(getContext()));

        String url= SupportFunctions.appendParam(EndPoints.GET_PROFILE,param);

        Log.e("profile url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("profile Json_string",response.toString());
                            Gson gson = new Gson();

                            Profile profileResponse=gson.fromJson(response.toString(),Profile.class);
                            if(profileResponse!=null)
                            {
                                mEdtCity.setText(profileResponse.getProfile_city());
                                mEdtState.setText(profileResponse.getProfile_state());
                                mEdtPincode.setText(profileResponse.getProfile_pincode());
                                mEdtAddress.setText(profileResponse.getProfile_address());
                                mBtnDob.setText(profileResponse.getProfile_dob());

                                if(profileResponse.getProfile_gender().equals(Constants.MALE))
                                {
                                    radBtnMale.setChecked(true);
                                }
                                if(profileResponse.getProfile_gender().equals(Constants.FEMALE))
                                {
                                    radBtnFemale.setChecked(true);
                                }
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
