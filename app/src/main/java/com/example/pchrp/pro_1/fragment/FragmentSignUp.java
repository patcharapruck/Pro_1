package com.example.pchrp.pro_1.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pchrp.pro_1.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pchrp.pro_1.activity.AppController;
import com.example.pchrp.pro_1.activity.MainActivity;
import com.example.pchrp.pro_1.util.RegisterRquest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentSignUp extends Fragment implements View.OnClickListener {

    public FragmentSignUp() {
        super();
    }


    private Button btnsign;
    private EditText email,pass,con_pass,name;
    private TextView textView_email,textView_pass,textView_conpass,textView_name;
    private CardView signcard;
    String url;

    //CapCha
    private static final String TAG = FragmentSignUp.class.getSimpleName();
    public static final String SITE_KEY = "6Lf8pngUAAAAAOSLtqxHYDduMkTO-Q5bMI5Tmc7G";
    public static final String SITE_SECRET_KEY = "6Lf8pngUAAAAAAgOwYjBBiLBwFW505z1DaqHDmjh";
    String userResponseToken;
    CheckBox checkBox;

    //Register
    private RequestQueue requestQueue;
    private StringRequest request;
    String user_email,user_pass,user_name;


    public static FragmentSignUp newInstance() {
        FragmentSignUp fragment = new FragmentSignUp();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sigup_layout, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnsign   = (Button) rootView.findViewById(R.id.signupbtn);
        email     = (EditText) rootView.findViewById(R.id.sigup_emailid);
        pass      = (EditText) rootView.findViewById(R.id.sigup_password);
        con_pass  = (EditText) rootView.findViewById(R.id.sigup_confirmpassword);
        name      = (EditText) rootView.findViewById(R.id.sigup_name);
        signcard  = (CardView) rootView.findViewById(R.id.signup_cardView);

        textView_email = (TextView) rootView.findViewById(R.id.textview_email);
        textView_pass = (TextView) rootView.findViewById(R.id.textview_pass);
        textView_conpass = (TextView) rootView.findViewById(R.id.textview_conpass);
        textView_name = (TextView) rootView.findViewById(R.id.textview_name);

        checkBox = (CheckBox) rootView.findViewById(R.id.checkcaptcha);

        requestQueue = Volley.newRequestQueue(getContext());

        btnsign.setOnClickListener(this);
        signcard.setOnClickListener(this);
        checkBox.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Override
    public void onClick(View v) {

        if (v == checkBox){
            SafetyNet.getClient(getActivity()).verifyWithRecaptcha(SITE_KEY)
                    .addOnSuccessListener(getActivity(),
                            new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                @Override
                                public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                    userResponseToken = response.getTokenResult();
                                    if (!userResponseToken.isEmpty()) {
                                        sendRequest();
                                    }
                                }
                            })
                    .addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof ApiException) {
                                ApiException apiException = (ApiException) e;
                                int statusCode = apiException.getStatusCode();
                                Log.d(TAG, "Error: " + CommonStatusCodes
                                        .getStatusCodeString(statusCode));
                            } else {
                                Log.d(TAG, "Error: " + e.getMessage());
                            }
                        }
                    });
        }
        if (v == btnsign || v == signcard){
            boolean check=true;
            String password = pass.getText().toString(),con_password = con_pass.getText().toString();
            if(email.length()<=0){
               textView_email.setText("กรุณากรอกข้อมูล");
               check = false;
            } else{
                textView_email.setText("");
                check = true;
            }
            if(pass.length()<=0){
                textView_pass.setText("กรุณากรอกข้อมูล");
                check = false;
            }else{
                textView_pass.setText("");
                check = true;
            }
            if(con_pass.length()<=0){
                textView_conpass.setText("กรุณากรอกข้อมูล");
                check = false;
            }else {
                textView_conpass.setText("");
                check = true;
            }
            if(name.length()<=0){
                textView_name.setText("กรุณากรอกข้อมูล");
                check = false;
            }else {
                textView_name.setText("");
                check = true;
            }
            if(password.equals(con_password)){
                textView_pass.setText("");
                check = true;
            }else {
                textView_conpass.setText("");
                textView_pass.setText("รหัสผ่านไม่ตรงกันกรุณากรอกใหม่");
                check = false;
            }
            if(check){
                if(checkBox.isChecked()==true){
                    onRegister();
                }
            }
        }
    }

    //Register
    private void onRegister() {
        user_email = email.getText().toString();
        user_pass = pass.getText().toString();
        user_name = name.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success")) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.frameContainer,new FragmentLogin()).commit();
                    } else {
                        Toast.makeText(getContext(), "Error "
                                + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RegisterRquest registerRquest = new RegisterRquest(user_email,user_pass,user_name,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(registerRquest);

    }


    //captcha
    public void sendRequest()  {

        url = "https://www.google.com/recaptcha/api/siteverify";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("success").equals("true")){
                                moveNewActivity();
                            }
                            else{
                                checkBox.setChecked(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            checkBox.setChecked(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("secret", SITE_SECRET_KEY);
                params.put("response", userResponseToken);
                return params;
            }
        };
        AppController.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    public void moveNewActivity(){
        checkBox.setChecked(true);
    }

}
