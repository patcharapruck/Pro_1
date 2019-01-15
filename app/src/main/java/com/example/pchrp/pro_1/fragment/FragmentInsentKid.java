package com.example.pchrp.pro_1.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.activity.HomeActivity;
import com.example.pchrp.pro_1.manager.SharedPrefManager;
import com.example.pchrp.pro_1.manager.SharedPrefManagerKid;
import com.example.pchrp.pro_1.util.Constants;
import com.example.pchrp.pro_1.util.InsertKidRquest;
import com.example.pchrp.pro_1.util.RegisterRquest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentInsentKid extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // calendar
    ImageButton calendar;
    EditText date;
    Calendar c;
    DatePickerDialog dpc;

    //captureimage
    ImageView image;
    Button btn_capture;

    Spinner blood;
    String[] item_spinner = {"A","B","O","AB"};
    EditText name,weight,height;
    RadioGroup gender;
    Button insert;

    //insert
    private RequestQueue requestQueue;
    private StringRequest request;
    String C_name="",C_gender="",C_weight="",C_height=""
            ,C_birthday="",C_blood="",C_image="",u_id="";

    public FragmentInsentKid() {
        super();
    }

    public static FragmentInsentKid newInstance() {
        FragmentInsentKid fragment = new FragmentInsentKid();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.insentkid_fragment, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here

        //calendar
        calendar = (ImageButton) rootView.findViewById(R.id.baby_calendar);
        date = (EditText) rootView.findViewById(R.id.textviewdate);
        calendar.setOnClickListener(this);

        //captureimage
        image = (ImageView) rootView.findViewById(R.id.imageView);
        btn_capture = (Button) rootView.findViewById(R.id.btnCaptureImage);
        btn_capture.setOnClickListener(this);

        name = (EditText) rootView.findViewById(R.id.baby_name);
        weight = (EditText) rootView.findViewById(R.id.baby_weight);
        height = (EditText) rootView.findViewById(R.id.baby_height);

        blood = (Spinner) rootView.findViewById(R.id.baby_blood);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,item_spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood.setAdapter(adapter);
        blood.setOnItemSelectedListener(this);

        gender = (RadioGroup) rootView.findViewById(R.id.gender);
        
        insert = (Button) rootView.findViewById(R.id.insertkid);
        insert.setOnClickListener(this);
        
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
        if(v == calendar){
            c = Calendar.getInstance();

            final int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            dpc = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    String mm = ""+month;
                    String dd = ""+dayOfMonth;

                    if (month<10){
                        mm = "0"+month;
                    }
                    if (dayOfMonth < 10){
                        dd = "0"+dayOfMonth;
                    }
                    date.setText(year+ "-" + mm + "-" +dd);
                }
            },day,month,year);
            dpc.show();
        }
        if(v == btn_capture){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        }
        if(v == insert){
          onInsert();
        }
    }

    private void onInsert() {


        C_name = name.getText().toString();

        switch (gender.getCheckedRadioButtonId()){
            case R.id.rd_genderb:
                C_gender = "1";
                break;
            case R.id.rd_genderg:
                C_gender = "2";
                break;
        }

        C_birthday = date.getText().toString();
        C_weight = weight.getText().toString();
        C_height = height.getText().toString();

        int u = SharedPrefManager.getInstance(getContext()).getID();
        u_id = u+"";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("tf")) {

                        SharedPrefManagerKid.getInstance(getContext())
                                .datakid(jsonObject.getString("id"),
                                        C_name, C_gender,C_birthday,C_weight,C_height,C_blood
                                );

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "Error " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        InsertKidRquest insertKidRquest = new InsertKidRquest(C_name,C_gender,C_birthday
                ,C_weight,C_height,C_blood,u_id,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(insertKidRquest);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        image.setImageBitmap(bitmap);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                C_blood = "A";
                break;
            case 1:
                C_blood = "B";
                break;
            case 2:
                C_blood = "O";
                break;
            case 3:
                C_blood = "AB";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
