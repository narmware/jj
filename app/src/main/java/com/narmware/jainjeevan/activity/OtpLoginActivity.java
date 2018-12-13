package com.narmware.jainjeevan.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.narmware.jainjeevan.pojo.Login;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;
import com.narmware.jainjeevan.support.SupportFunctions;

import org.json.JSONObject;

import java.util.HashMap;


public class OtpLoginActivity extends AppCompatActivity {

    //@BindView(R.id.btn_signin)
    Button mbtnSignIn;

    //@BindView(R.id.btn_submit_otp)
    Button mbtnSubmitOtp;

    //@BindView(R.id.btn_resend)
    Button mbtnResendOtp;

    //@BindView(R.id.login_card)
    CardView mCardLogin;
    //@BindView(R.id.otp_card)
    CardView mCardOtp;

    public static EditText mEdtOtp,mEdtName,mEdtMobile,mEdtMail;
    RequestQueue mVolleyRequest;
    String name,mobile,otp,server_otp,user_id,email;
    int validFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);
        mVolleyRequest = Volley.newRequestQueue(OtpLoginActivity.this);

        ActivityCompat.requestPermissions(OtpLoginActivity.this,
                new String[]{Manifest.permission.READ_SMS},
                1);

        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mEdtOtp=findViewById(R.id.edt_otp);
        mEdtName=findViewById(R.id.edt_name);
        mEdtMobile=findViewById(R.id.edt_mobile);
        mEdtMail=findViewById(R.id.edt_mail);

        mbtnSignIn=findViewById(R.id.btn_signin);
        mbtnSubmitOtp=findViewById(R.id.btn_submit_otp);
        mbtnResendOtp=findViewById(R.id.btn_resend);
        mCardLogin=findViewById(R.id.login_card);
        mCardOtp=findViewById(R.id.otp_card);

        mbtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validFlag=0;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                name=mEdtName.getText().toString().trim();
                mobile=mEdtMobile.getText().toString().trim();
                email=mEdtMail.getText().toString().trim();

                if(name.equals(""))
                {
                    validFlag=1;
                    mEdtName.setError("Enter name");
                }
                if(mobile.length()<10)
                {
                    validFlag=1;
                    mEdtMobile.setError("Enter Valid number");
                }
                if(email.equals("") || !email.matches(emailPattern))
                {
                    validFlag=1;
                    mEdtMail.setError("Enter email id");
                }
                if(validFlag==0)
                {
                    mCardLogin.setVisibility(View.GONE);
                    mCardOtp.setVisibility(View.VISIBLE);

                    YoYo.with(Techniques.SlideInRight)
                            .duration(400)
                            .playOn(mCardOtp);

                    LoginUser();
                }
            }
        });

        mbtnSubmitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp=mEdtOtp.getText().toString().trim();

                if(otp.equals(""))
                {
                    mEdtOtp.setError("Enter OTP");
                }
                else{
                    if(otp.equals(server_otp))
                    {
                        SharedPreferencesHelper.setUserId(user_id,OtpLoginActivity.this);
                        SharedPreferencesHelper.setUserName(name,OtpLoginActivity.this);
                        SharedPreferencesHelper.setUserMobile(mobile,OtpLoginActivity.this);
                        SharedPreferencesHelper.setUserEmail(email,OtpLoginActivity.this);
                        SharedPreferencesHelper.setIsLogin(true,OtpLoginActivity.this);

                        Toast.makeText(OtpLoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(OtpLoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        mbtnResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendOtp();
            }
        });
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
                    Toast.makeText(OtpLoginActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed() {

        if(mCardOtp.getVisibility()== View.VISIBLE)
        {
            mCardLogin.setVisibility(View.VISIBLE);
            mCardOtp.setVisibility(View.GONE);

            YoYo.with(Techniques.SlideInLeft)
                    .duration(400)
                    .playOn(mCardLogin);
        }
        else{
            super.onBackPressed();
        }
    }


    private void LoginUser() {


        Gson gson=new Gson();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.MOBILE_NUMBER,mobile);
        param.put(Constants.NAME,name);
        param.put(Constants.EMAIL,email);

        //url with params
        String url= SupportFunctions.appendParam(EndPoints.LOGIN_USER,param);

        //url without params
        //String url= MyApplication.GET_CATEGORIES;

        Log.e("Login url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //getting test master array
                            // testMasterDetails = testMasterArray.toString();

                            Log.e("Login Json_string",response.toString());
                            Gson gson = new Gson();

                            Login loginResponse= gson.fromJson(response.toString(), Login.class);
                            if(loginResponse.getResponse().equals("100") || loginResponse.getResponse().equals("200") )
                            {

                                server_otp=loginResponse.getOTP();
                                //mEdtOtp.setText(server_otp);
                                user_id=loginResponse.getUser_id();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                            //Toast.makeText(NavigationActivity.this, "Invalid album id", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        // showNoConnectionDialog();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void ResendOtp() {


        Gson gson=new Gson();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.MOBILE_NUMBER,mobile);
        param.put(Constants.NAME,name);
        param.put(Constants.EMAIL,email);

        //url with params
        String url= SupportFunctions.appendParam(EndPoints.LOGIN_USER,param);

        //url without params
        //String url= MyApplication.GET_CATEGORIES;

        Log.e("Login url",url);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //getting test master array
                            // testMasterDetails = testMasterArray.toString();

                            Log.e("Login Json_string",response.toString());
                            Gson gson = new Gson();

                            Login loginResponse= gson.fromJson(response.toString(), Login.class);
                            if(loginResponse.getResponse().equals("100") || loginResponse.getResponse().equals("200") )
                            {
                                server_otp=loginResponse.getOTP();
                                user_id=loginResponse.getUser_id();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                            //Toast.makeText(NavigationActivity.this, "Invalid album id", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        // showNoConnectionDialog();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }



}
