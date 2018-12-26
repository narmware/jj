package com.narmware.jainjeevan.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    public static EditText mEdtMobile,mEdtPass;
    TextView mTxtRegister;
    Button mBtnLogin,mBtnForgotPassword;
    RequestQueue mVolleyRequest;
    String mobile,password,forgot_mobile;
    int validFlag=0;
    protected Dialog mPassDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

          init();
        }

    private void init() {
        mVolleyRequest = Volley.newRequestQueue(LoginActivity.this);

        mEdtMobile=findViewById(R.id.edt_mobile);
        mEdtPass=findViewById(R.id.edt_pass);

        mTxtRegister=findViewById(R.id.txt_register);

        mBtnLogin=findViewById(R.id.btn_login);
        mBtnForgotPassword=findViewById(R.id.btn_forgot_pass);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validFlag=0;

                mobile=mEdtMobile.getText().toString().trim();
                password=mEdtPass.getText().toString().trim();

                if(mobile.length()<10)
                {
                    validFlag=1;
                    mEdtMobile.setError("Enter Valid number");
                }
                if(password.equals(""))
                {
                    validFlag=1;
                    mEdtPass.setError("Enter password");
                }
                if(validFlag==0)
                {
                    LoginUser();
                }
            }
        });

        mTxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtMobile.setText("");
                mEdtPass.setText("");
                startActivity(new Intent(LoginActivity.this, OtpLoginActivity.class));
                //finish();
            }
        });

        mBtnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        showForgotDialog();
            }
        });
    }

    private void LoginUser() {
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Validating Credentials...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.MOBILE_NUMBER,mobile);
        param.put(Constants.PASSWORD,password);

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
                            if(loginResponse.getResponse().equals(Constants.ALREADY_EXIST))
                            {

                                SharedPreferencesHelper.setUserId(loginResponse.getUser_id(),LoginActivity.this);
                                SharedPreferencesHelper.setUserName(loginResponse.getName(),LoginActivity.this);
                                SharedPreferencesHelper.setUserMobile(loginResponse.getMobile(),LoginActivity.this);
                                SharedPreferencesHelper.setUserEmail(loginResponse.getEmail(),LoginActivity.this);
                                SharedPreferencesHelper.setIsLogin(true,LoginActivity.this);

                                Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            if(loginResponse.getResponse().equals(Constants.ERROR))
                            {
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();
                        } catch (Exception e) {

                            e.printStackTrace();
                            dialog.dismiss();
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
                        dialog.dismiss();
                        // showNoConnectionDialog();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void ForgotPassword() {
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Getting Details...");
        dialog.setCancelable(false);
        dialog.show();

        Gson gson=new Gson();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.MOBILE_NUMBER,forgot_mobile);

        //url with params
        String url= SupportFunctions.appendParam(EndPoints.FORGOT_PASSWORD,param);

        Log.e("Forgot url",url);
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

                            Log.e("Forgot",response.toString());
                            Gson gson = new Gson();
                            Login loginResponse= gson.fromJson(response.toString(), Login.class);
                            if(loginResponse.getResponse().equals(Constants.ERROR))
                            {
                                Toast.makeText(LoginActivity.this, "This number is not registred", Toast.LENGTH_SHORT).show();
                            }

                            if(loginResponse.getResponse().equals(Constants.ALREADY_EXIST)){
                                mPassDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Password sent on registered mobile number", Toast.LENGTH_SHORT).show();
                            }
                                dialog.dismiss();

                        } catch (Exception e) {

                            e.printStackTrace();
                            dialog.dismiss();
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
                        dialog.dismiss();
                        // showNoConnectionDialog();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }


    private void showForgotDialog() {
        mPassDialog = new Dialog(LoginActivity.this);
        mPassDialog.setContentView(R.layout.dialog_pass);
        mPassDialog.setCancelable(false);
        mPassDialog.show();

        Button btnSubmitPass = mPassDialog.findViewById(R.id.btn_submit_pass);
        Button btnCancel = mPassDialog.findViewById(R.id.btn_cancel);
        final EditText edtMobile = mPassDialog.findViewById(R.id.edt_mobile);

        btnSubmitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgot_mobile=edtMobile.getText().toString();

                if(forgot_mobile.equals("") || forgot_mobile==null)
                {
                    Toast.makeText(LoginActivity.this, "Please enter mobile", Toast.LENGTH_SHORT).show();
                }else{

                    ForgotPassword();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPassDialog.dismiss();
            }
        });

    }

}
