package com.narmware.jainjeevan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class SplashActivity extends AppCompatActivity {
    private static int TIMEOUT = 2500;

    ImageView mImgLogo;
    RequestQueue mVolleyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        mVolleyRequest = Volley.newRequestQueue(SplashActivity.this);

        mImgLogo=findViewById(R.id.img_logo);
        YoYo.with(Techniques.Bounce)
                .playOn(mImgLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //checkExistingUser();

                if(SharedPreferencesHelper.getIsLogin(SplashActivity.this)==false)
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }


            }
        }, TIMEOUT);
    }

    private void checkExistingUser() {
        HashMap<String,String> param = new HashMap();
        param.put(Constants.USER_ID,SharedPreferencesHelper.getUserId(SplashActivity.this));

        String url= SupportFunctions.appendParam(EndPoints.CHECK_EXISIING_USER,param);
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

                            Login loginResponse= gson.fromJson(response.toString(), Login.class);
                            if(loginResponse.getResponse().equals(Constants.SUCCESS))
                            {

                               /* SharedPreferencesHelper.setUserName(name,SplashActivity.this);
                                SharedPreferencesHelper.setUserMobile(mobile,SplashActivity.this);
                                SharedPreferencesHelper.setUserEmail(email,SplashActivity.this);*/

                            }
                            else{
                                SharedPreferencesHelper.setIsLogin(false,SplashActivity.this);
                                SharedPreferencesHelper.setFilteredCity(null,SplashActivity.this);
                                SharedPreferencesHelper.setFilteredFacilities(null,SplashActivity.this);
                                SharedPreferencesHelper.setUserLocation(null,SplashActivity.this);
                                SharedPreferencesHelper.setUserName(null,SplashActivity.this);
                                SharedPreferencesHelper.setUserMobile(null,SplashActivity.this);
                                SharedPreferencesHelper.setUserEmail(null,SplashActivity.this);
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

}
