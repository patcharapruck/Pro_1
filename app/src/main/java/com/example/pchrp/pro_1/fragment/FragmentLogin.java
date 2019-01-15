package com.example.pchrp.pro_1.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.activity.AddBabyActivity;
import com.example.pchrp.pro_1.activity.HomeActivity;
import com.example.pchrp.pro_1.activity.MainActivity;
import com.example.pchrp.pro_1.manager.SharedPrefManager;
import com.example.pchrp.pro_1.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener {

    TextView sigup;
    TextView forgot,textEmilHist,textPassHist;
    CardView log_in,logface;
    Button loginbtn,logfacebtn;


    EditText useremail , userpass;

    private RequestQueue requestQueue;
    private StringRequest request;
    String user_email,user_pass;

    public FragmentLogin(){
        super();
    }


    public static FragmentLogin newInstance() {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {


        useremail = (EditText) rootView.findViewById(R.id.login_emailid);
        userpass = (EditText) rootView.findViewById(R.id.login_password);

        sigup = (TextView) rootView.findViewById(R.id.createAccount);
        forgot = (TextView) rootView.findViewById(R.id.forgot_password);
        textEmilHist = (TextView) rootView.findViewById(R.id.textemilhist);
        textPassHist = (TextView) rootView.findViewById(R.id.textpasshist);

        log_in = (CardView) rootView.findViewById(R.id.loginBtn_cardView);
        logface = (CardView) rootView.findViewById(R.id.loginface_cardView);
        loginbtn = (Button) rootView.findViewById(R.id.tv_login);
        logfacebtn = (Button) rootView.findViewById(R.id.login_face_Btn);

        requestQueue = Volley.newRequestQueue(getContext());

        sigup.setOnClickListener(this);
        forgot.setOnClickListener(this);
        log_in.setOnClickListener(this);
        logface.setOnClickListener(this);
        loginbtn.setOnClickListener(this);
        logfacebtn.setOnClickListener(this);
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

        if (v == sigup){
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer,new FragmentSignUp())
                    .addToBackStack(null).commit();
        }
        if (v == forgot){
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer,new FragmentForgot())
                    .addToBackStack(null).commit();
        }
        if (v == log_in || v == loginbtn){
            user_email = useremail.getText().toString();
            user_pass = userpass.getText().toString();

            if (user_email.equals("") || user_email.length() == 0) {
                textEmilHist.setText("กรุณากรอกข้อมูลอีเมลล์");
            }
            // Check if email id is valid or not
            else if(user_pass.equals("") || user_pass.length() == 0){
                textPassHist.setText("กรุณากรอกข้อมูลรหัสผ่าน");
            }
            else{
                OnLogin();
            }
        }
        if (v == logface || v == logfacebtn){
            Toast.makeText(getContext(), "login_face", Toast.LENGTH_SHORT).show();
        }
    }

    //Login
    public void OnLogin() {

        user_email = useremail.getText().toString();
        user_pass = userpass.getText().toString();

        request = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(getContext(), "Error " +
                                jsonObject.getString("errort"), Toast.LENGTH_SHORT).show();

                    } else {
                        int a = jsonObject.getInt("id");
                        SharedPrefManager.getInstance(getContext())
                                .userLogin(
                                        a,
                                        jsonObject.getString("name"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("pass"),
                                        jsonObject.getBoolean("check")
                                );

                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("user_email",user_email);
                hashMap.put("user_pass",user_pass);

                return hashMap;
            }
        };

        requestQueue.add(request);
    }
}
