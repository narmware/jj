package com.narmware.jainjeevan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.narmware.jainjeevan.R;
import com.narmware.jainjeevan.broadcast.SingleUploadBroadcastReceiver;
import com.narmware.jainjeevan.fragments.AboutFragment;
import com.narmware.jainjeevan.fragments.AddBhojanshalaFragment;
import com.narmware.jainjeevan.fragments.AddDharamshalaFragment;
import com.narmware.jainjeevan.fragments.AddVendorFragment;
import com.narmware.jainjeevan.fragments.HomeFragment;
import com.narmware.jainjeevan.fragments.ProfileFragment;
import com.narmware.jainjeevan.pojo.ImageUploadResponse;
import com.narmware.jainjeevan.support.Constants;
import com.narmware.jainjeevan.support.EndPoints;
import com.narmware.jainjeevan.support.SharedPreferencesHelper;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener,AboutFragment.OnFragmentInteractionListener
,AddVendorFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,AddDharamshalaFragment.OnFragmentInteractionListener,
        AddBhojanshalaFragment.OnFragmentInteractionListener,SingleUploadBroadcastReceiver.Delegate{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();
    ProgressDialog dialog;

    private void setHeader(View header) {
        TextView name = header.findViewById(R.id.header_name);
        TextView email = header.findViewById(R.id.header_mail);
        TextView mobile = header.findViewById(R.id.header_mobile);
        CircleImageView imageView = header.findViewById(R.id.imageView);

        try {
            if (SharedPreferencesHelper.getUserProfileImage(MainActivity.this) != null) {
                Picasso.with(MainActivity.this)
                        .load(SharedPreferencesHelper.getUserProfileImage(MainActivity.this))
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setHeader(navigationView.getHeaderView(0));

        setFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void setFragment(Fragment fragment)
    {
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       switch (id)
       {
           case R.id.nav_home:
               setFragment(new HomeFragment());
               break;

           case R.id.nav_profile:
               setFragment(new ProfileFragment());
               break;

           case R.id.nav_about:
               setFragment(new AboutFragment());
               break;

           case R.id.nav_add_vendor:
               setFragment(new AddVendorFragment());
               break;

           case R.id.nav_add_dharamshala:
               setFragment(new AddDharamshalaFragment());
               break;

           case R.id.nav_add_bhojanalay:
               setFragment(new AddBhojanshalaFragment());
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
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Uploading...");
        dialog.setCancelable(false);
        dialog.show();
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
        dialog.dismiss();
        Log.e("ServerResponse", new String(serverResponseBody) + "   " + serverResponseCode);
        Gson gson=new Gson();
        ImageUploadResponse imageUploadResponse=gson.fromJson(new String(serverResponseBody),ImageUploadResponse.class);
       SharedPreferencesHelper.setUserProfileImage(imageUploadResponse.getUrl(),MainActivity.this);
        Picasso.with(MainActivity.this)
                .load(imageUploadResponse.getUrl())
                .into(ProfileFragment.mImgProf);
    }

    @Override
    public void onCancelled() {
    }

}
