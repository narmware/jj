package com.narmware.jainjeevan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;

public class SplashActivity extends AppCompatActivity {
    private static int TIMEOUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(SharedPreferencesHelper.getIsLogin(SplashActivity.this)==false)
                {
                    startActivity(new Intent(SplashActivity.this, OtpLoginActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }


            }
        }, TIMEOUT);
    }
}
