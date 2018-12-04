package com.narmware.jainjeevan;

import android.app.Application;

import com.narmware.jainjeevan.support.TypefaceUtil;

/**
 * Created by rohitsavant on 16/08/18.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "madeleina_sans.otf");

        super.onCreate();
    }
}
