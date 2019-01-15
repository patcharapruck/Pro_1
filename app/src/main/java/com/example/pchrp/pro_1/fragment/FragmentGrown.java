package com.example.pchrp.pro_1.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.manager.SharedPrefManager;
import com.example.pchrp.pro_1.manager.SharedPrefManagerKid;
import com.example.pchrp.pro_1.util.InsertGrownRquest;
import com.example.pchrp.pro_1.util.RegisterRquest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentGrown extends Fragment implements View.OnClickListener {

    //calendar
    EditText weight,height,date;
    ImageButton calendar;
    Calendar c;
    DatePickerDialog dpc;

    //camera
    Button camera;
    ImageView imageView;

    CardView cardView;
    Button insert_grown;

    String g_weight="",g_height="",g_date="",c_id="";

    public FragmentGrown() {
        super();
    }

    public static FragmentGrown newInstance() {
        FragmentGrown fragment = new FragmentGrown();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.growkid_fragment, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        //calendar
        calendar = (ImageButton) rootView.findViewById(R.id.baby_calendar_grow);
        date = (EditText) rootView.findViewById(R.id.textviewdate_grow);
        weight = (EditText) rootView.findViewById(R.id.weight_grown);
        height = (EditText) rootView.findViewById(R.id.height_grown);
        calendar.setOnClickListener(this);

        //captureimage
        imageView = (ImageView) rootView.findViewById(R.id.imageView_grow);
        camera = (Button) rootView.findViewById(R.id.btnCaptureImage_grow);
        camera.setOnClickListener(this);

        cardView = (CardView) rootView.findViewById(R.id.grow_cardView);
        insert_grown = (Button) rootView.findViewById(R.id.growbtn);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        g_date = formatter.format(todayDate);

        date.setText(g_date);

        cardView.setOnClickListener(this);
        insert_grown.setOnClickListener(this);
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
        if(v == camera){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        }

        if(v == cardView || v == insert_grown){
            InsertGrown();
        }
    }

    private void InsertGrown() {

        g_height = height.getText().toString();
        g_weight = weight.getText().toString();
        g_date = date.getText().toString();
        c_id = SharedPrefManagerKid.getInstance(getContext()).getCid();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success")) {
                        Toast.makeText(getContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        InsertGrownRquest insertGrownRquest = new InsertGrownRquest(g_weight,g_height,g_date,c_id,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(insertGrownRquest);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
}
