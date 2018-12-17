package com.narmware.jainjeevan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.adapter.NavAdapter;
import com.narmware.jainjeevan.broadcast.SingleUploadBroadcastReceiver;
import com.narmware.jainjeevan.fragments.AboutFragment;
import com.narmware.jainjeevan.fragments.AddDharamshalaFragment;
import com.narmware.jainjeevan.fragments.AddVendorFragment;
import com.narmware.jainjeevan.fragments.HomeFragment;
import com.narmware.jainjeevan.fragments.PrivacyFragment;
import com.narmware.jainjeevan.fragments.ProfileFragment;
import com.narmware.jainjeevan.pojo.ImageUploadResponse;
import com.narmware.jainjeevan.pojo.NavMenu;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener,AboutFragment.OnFragmentInteractionListener
,AddVendorFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,
        AddDharamshalaFragment.OnFragmentInteractionListener, SingleUploadBroadcastReceiver.Delegate,PrivacyFragment.OnFragmentInteractionListener{

    public static FragmentManager fragmentManager;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;
    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();
    public static NavigationView navigationView;
    //ProgressDialog dialog;
    public static  int fragment_call=0;

    ListView mListNav;
    NavAdapter navAdapter;
    ArrayList<NavMenu> navMenus;

    private void setHeader(View header) {
        TextView name = findViewById(R.id.header_name);
        TextView email = findViewById(R.id.header_mail);
        TextView mobile = findViewById(R.id.header_mobile);
        CircleImageView imageView = findViewById(R.id.imageView);

        try {
            if (SharedPreferencesHelper.getUserProfileImage(MainActivity.this) != null) {
                Picasso.with(MainActivity.this)
                        .load(SharedPreferencesHelper.getUserProfileImage(MainActivity.this))
                        .placeholder(R.drawable.logo)
                        .into(imageView);
                //mImgProf.setImageBitmap(BitmapFactory.decodeFile(SharedPreferencesHelper.getUserProfileImage(getContext())));
            }
        }catch (Exception e)
        {

        }
        name.setText(SharedPreferencesHelper.getUserName(this));
        email.setText(SharedPreferencesHelper.getUserEmail(this));
        mobile.setText(SharedPreferencesHelper.getUserMobile(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager=getSupportFragmentManager();
        fm = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setHeader(navigationView.getHeaderView(0));

        setNavData();
        mListNav=findViewById(R.id.list_nav);
        navAdapter=new NavAdapter(MainActivity.this,navMenus);
        mListNav.setAdapter(navAdapter);

        mListNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this,navMenus.get(i).getNav_title(),Toast.LENGTH_SHORT).show();
                {
                    switch (navMenus.get(i).getNav_title())
                    {
                        case Constants.HOME:
                            fragment_call=0;
                            setFragment(new HomeFragment(),"Home");
                            break;

                        case Constants.PROFILE:
                            fragment_call=1;
                            setFragment(new ProfileFragment(),"Profile");
                            break;

                        case Constants.ABOUT:
                            fragment_call=1;
                            setFragment(new AboutFragment(),"About");
                            break;

                        case Constants.ADD_VENDOR:
                            fragment_call=1;
                            setFragment(new AddVendorFragment(),"Vendor");
                            break;

                        case Constants.ADD_DHARAMSHALA:
                            fragment_call=1;
                            setFragment(new AddDharamshalaFragment(),"Dharamshala");
                            break;

                        case Constants.SHARE:
                            String shareBody = "Here is the share content body";
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            startActivity(Intent.createChooser(sharingIntent,"Share Using"));
                            break;

                        case Constants.LOGOUT:
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Are you sure")
                                    .setContentText("Your want to Logout")
                                    .setConfirmText("Yes")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {

                                            SharedPreferencesHelper.setIsLogin(false,MainActivity.this);
                                            SharedPreferencesHelper.setFilteredCity(null,MainActivity.this);
                                            SharedPreferencesHelper.setFilteredFacilities(null,MainActivity.this);
                                            SharedPreferencesHelper.setUserLocation(null,MainActivity.this);

                                            Intent intent=new Intent(MainActivity.this,OtpLoginActivity.class);
                                            MainActivity.this.startActivity(intent);
                                            finish();
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
                            break;

                    }
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        setFragment(new HomeFragment(),"Home");
    }

    public void setNavData()
    {
        navMenus=new ArrayList<>();
        navMenus.add(new NavMenu(Constants.HOME,R.drawable.ic_home));
        navMenus.add(new NavMenu(Constants.PROFILE,R.drawable.ic_person_profile));
        navMenus.add(new NavMenu(Constants.ADD_VENDOR,R.drawable.ic_person_black));
        navMenus.add(new NavMenu(Constants.ADD_DHARAMSHALA,R.drawable.ic_account));
        navMenus.add(new NavMenu(Constants.ABOUT,R.drawable.ic_info));
        navMenus.add(new NavMenu(Constants.SHARE,R.drawable.ic_share));
        navMenus.add(new NavMenu(Constants.LOGOUT,R.drawable.ic_logout));
    }
    @Override
    public void onBackPressed() {
/*
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure")
                    .setContentText("Your want to exit app")
                    .setConfirmText("Yes")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            finish();
                        }
                    })
                    .showCancelButton(true)
                    .setCancelText("No")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .show();
            }
    }


    public static void setFragment(Fragment fragment,String tag)
    {
       /* if(fragment_call==1) {

            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }}

        fragmentTransaction=fragmentManager.beginTransaction();
        if(fragment_call==1) {
            fragmentTransaction.replace(R.id.fragment_container,fragment,tag);
            fragmentTransaction.addToBackStack(null);
        }
        if(fragment_call==0) {
            fragmentTransaction.replace(R.id.fragment_container,fragment,tag);
        }
*/
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment,tag);

        fragmentTransaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /*switch (id)
       {
           case R.id.nav_home:
               fragment_call=0;
               setFragment(new HomeFragment(),"Home");
               break;

           case R.id.nav_profile:
               fragment_call=1;
               setFragment(new ProfileFragment(),"Profile");
               break;

           case R.id.nav_about:
               fragment_call=1;
               setFragment(new AboutFragment(),"About");
               break;

           case R.id.nav_add_vendor:
               fragment_call=1;
               setFragment(new AddVendorFragment(),"Vendor");
               break;

           case R.id.nav_add_dharamshala:
               fragment_call=1;
               setFragment(new AddDharamshalaFragment(),"Dharamshala");
               break;

           case R.id.nav_share:
               String shareBody = "Here is the share content body";
               Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
               sharingIntent.setType("text/plain");
               sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
               sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
               startActivity(Intent.createChooser(sharingIntent,"Share Using"));
               break;
       }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       /* if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //SharedPreferencesHelper.setUserProfImg(selectedImage,ProfileActivity.this);
                Log.e("UserProfileImage",selectedImage+"");

                ProfileFragment.mImgProf.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(MainActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }*/


        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            SharedPreferencesHelper.setUserProfileImage(picturePath,MainActivity.this);
            cursor.close();
            uploadMultipart(picturePath);

            //ProfileFragment.mImgProf.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        else {
            Toast.makeText(MainActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    public void uploadMultipart(String path) {

        String uploadId = UUID.randomUUID().toString();

        uploadReceiver.setDelegate(this);
        uploadReceiver.setUploadID(uploadId);

        //Uploading code
        try {
            //Creating a multi part request
            new MultipartUploadRequest(MainActivity.this,uploadId,EndPoints.SET_PROFILE_IMG)
                    .addFileToUpload(path, Constants.PROF_IMG) //Adding file
                    .addParameter(Constants.USER_ID, SharedPreferencesHelper.getUserId(MainActivity.this))//Adding text parameter to the request
                    .setMaxRetries(2)
                    //.setNotificationConfig(new UploadNotificationConfig())
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(MainActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }

    @Override
    public void onProgress(int progress) {
        /*dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Uploading...");
        dialog.setCancelable(false);
        dialog.show();*/
        Log.e("Progress",""+progress);
    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {
        Log.e("ServerProgress",uploadedBytes+" ");
    }

    @Override
    public void onError(Exception exception) {
        Log.e("ServerError","Errrrrorrrr!!!!");
        Toast.makeText(this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        //dialog.dismiss();
        Log.e("ServerResponse", new String(serverResponseBody) + "   " + serverResponseCode);
        Gson gson=new Gson();
        ImageUploadResponse imageUploadResponse=gson.fromJson(new String(serverResponseBody),ImageUploadResponse.class);
       SharedPreferencesHelper.setUserProfileImage(imageUploadResponse.getUrl(),MainActivity.this);
        Picasso.with(MainActivity.this)
                .load(imageUploadResponse.getUrl())
                .placeholder(R.drawable.logo)
                .into(ProfileFragment.mImgProf);
    }

    @Override
    public void onCancelled() {
    }

}
