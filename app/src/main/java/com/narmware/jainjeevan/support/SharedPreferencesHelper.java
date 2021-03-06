package com.narmware.jainjeevan.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.narmware.jainjeevan.pojo.Facility;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by comp16 on 12/19/2017.
 */

public class SharedPreferencesHelper {

    private static final String IS_LOGIN="login";
    private static final String USER_ID="user_id";
    private static final String USER_NAME="user_name";
    private static final String USER_MOBILE="user_mobile";
    private static final String USER_EMAIL="user_email";
    private static final String USER_PROFILE_IMAGE="profile_image";
    private static final String FILTERED_FACILITIES="filters";
    private static final String DHARAMSHALA_FACILITIES="dharam_facilities";
    private static final String BHOJAN_FACILITIES="bhojan_facilities";
    private static final String FOOD_TYPES="food_types";
    private static final String FILTERED_CITY="city";
    private static final String USER_LOCATION="loc";
    private static final String FILTERED_AREA="area";

    public static void setFoodTypes(Set<String> food, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putStringSet(FOOD_TYPES,food);
        edit.commit();
    }

    public static Set<String> getFoodTypes(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> food=pref.getStringSet(FOOD_TYPES,null);
        return food;
    }

    public static void setBhojanFacilities(Set<String> facilities1, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putStringSet(BHOJAN_FACILITIES,facilities1);
        edit.commit();
    }

    public static Set<String> getBhojanFacilities(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> facility1=pref.getStringSet(BHOJAN_FACILITIES,null);
        return facility1;
    }

    public static void setDharamshalaFacilities(Set<String> facilities1, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putStringSet(DHARAMSHALA_FACILITIES,facilities1);
        edit.commit();
    }

    public static Set<String> getDharamshalaFacilities(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> facility1=pref.getStringSet(DHARAMSHALA_FACILITIES,null);
        return facility1;
    }

    public static void setFilteredFacilities(Set<String> facilities, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putStringSet(FILTERED_FACILITIES,facilities);
        edit.commit();
    }

    public static Set<String> getFilteredFacilities(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> facility=pref.getStringSet(FILTERED_FACILITIES,null);
        return facility;
    }
    public static void setUserProfileImage(String prof_img, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(USER_PROFILE_IMAGE,prof_img);
        edit.commit();
    }

    public static String getUserProfileImage(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String prof_img=pref.getString(USER_PROFILE_IMAGE,null);
        return prof_img;
    }


    public static void setFilteredCity(String city, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(FILTERED_CITY,city);
        edit.commit();
    }

    public static String getFilteredCity(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String city=pref.getString(FILTERED_CITY,null);
        return city;
    }

    public static void setUserLocation(String loc, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(USER_LOCATION,loc);
        edit.commit();
    }

    public static String getUserLocation(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String loc=pref.getString(USER_LOCATION,null);
        return loc;
    }

    public static void setUserEmail(String email, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(USER_EMAIL,email);
        edit.commit();
    }

    public static String getUserEmail(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String email=pref.getString(USER_EMAIL,null);
        return email;
    }


    public static void setIsLogin(boolean login, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putBoolean(IS_LOGIN,login);
        edit.commit();
    }

    public static boolean getIsLogin(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        boolean login=pref.getBoolean(IS_LOGIN,false);
        return login;
    }

    public static void setUserId(String user_id, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(USER_ID,user_id);
        edit.commit();
    }

    public static String getUserId(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String user_id=pref.getString(USER_ID,null);
        return user_id;
    }

    public static void setUserName(String user_name, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(USER_NAME,user_name);
        edit.commit();
    }

    public static String getUserName(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String user_name=pref.getString(USER_NAME,null);
        return user_name;
    }

    public static void setUserMobile(String user_email, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(USER_MOBILE,user_email);
        edit.commit();
    }

    public static String getUserMobile(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String user_email=pref.getString(USER_MOBILE,null);
        return user_email;
    }


  /*  public static void setUserProfImg(Bitmap user_img, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        user_img.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        edit.putString(USER_PROF_IMG,temp);
        edit.commit();
    }

    public static String getUserProfImg(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String user_img=pref.getString(USER_PROF_IMG,null);
        return user_img;
    }*/

    public static void setFilteredArea(String area, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(FILTERED_AREA,area);
        edit.commit();
    }

    public static String getFilteredArea(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String area=pref.getString(FILTERED_AREA,null);
        return area;
    }


}

