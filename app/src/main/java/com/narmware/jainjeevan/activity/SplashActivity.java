package com.narmware.jainjeevan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;

public class SplashActivity extends AppCompatActivity {
    private static int TIMEOUT = 2000;

    ImageView mImgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        mImgLogo=findViewById(R.id.img_logo);
        YoYo.with(Techniques.Bounce)
                .playOn(mImgLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

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
}
