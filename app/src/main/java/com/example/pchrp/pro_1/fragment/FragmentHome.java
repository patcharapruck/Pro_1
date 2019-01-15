package com.example.pchrp.pro_1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.example.pchrp.pro_1.activity.MainActivity;
import com.example.pchrp.pro_1.manager.RequestHandlerDevelorment;
import com.example.pchrp.pro_1.manager.SharedPrefManager;
import com.example.pchrp.pro_1.manager.SharedPrefManagerKid;
import com.example.pchrp.pro_1.util.Constants;
import com.example.pchrp.pro_1.util.TagConfigChild;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentHome extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    ImageButton addbaby;

    TextView textViewUsername,textViewUserEmail,textViewNamekid,textViewBirthday;
    ImageButton exit,settings;

    private Spinner spinner_kid;
    private ArrayList<String> child;
    private JSONArray result;

    int u = SharedPrefManager.getInstance(getContext()).getID();

    String u_id = "";


    public FragmentHome() {
        super();
    }

    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        if(u<10&&u>0){
            u_id = "00"+u;
        }
        else if(u<100&&u>10){
            u_id = "0"+u;
        }
        else {
            u_id = u+"";
        }
        child = new ArrayList<String>();
        spinner_kid = (Spinner) rootView.findViewById(R.id.spinner_kid);
        spinner_kid.setOnItemSelectedListener(this);
        getData();

        addbaby = (ImageButton) rootView.findViewById(R.id.addbaby);

        textViewUsername = (TextView) rootView.findViewById(R.id.username);
        textViewUserEmail = (TextView) rootView.findViewById(R.id.useremail);

        textViewNamekid= (TextView) rootView.findViewById(R.id.namekid);
        textViewBirthday = (TextView) rootView.findViewById(R.id.birthdaykid);

        textViewNamekid.setText(SharedPrefManagerKid.getInstance(getContext()).getCname());
        textViewBirthday.setText(SharedPrefManagerKid.getInstance(getContext()).getCdate());

        textViewUserEmail.setText(SharedPrefManager.getInstance(getContext()).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(getContext()).getUsername());

        exit = (ImageButton) rootView.findViewById(R.id.logout);
        settings = (ImageButton) rootView.findViewById(R.id.setting);

        exit.setOnClickListener(this);
        settings.setOnClickListener(this);

        addbaby.setOnClickListener(this);
    }

    private void getData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CHILD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray(TagConfigChild.JSON_ARRAY);
                   // Toast.makeText(getContext(),u_id,Toast.LENGTH_LONG).show();
                    getName(result);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",u_id);

                return hashMap;
            }
        };
        RequestHandlerDevelorment.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void getName(JSONArray result) {

        for(int i=0;i<result.length();i++){
            try {
                JSONObject json = result.getJSONObject(i);
                child.add(json.getString(TagConfigChild.TAG_NAME));

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        spinner_kid.setAdapter(new ArrayAdapter<String>(getContext()
                ,android.R.layout.simple_spinner_dropdown_item,child));
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

        if (v == addbaby){
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame_home,FragmentGrown.newInstance())
                    .addToBackStack(null)
                    .commit();
        }

        if (v == exit){
            SharedPrefManager.getInstance(getContext()).logout();
            getActivity().finish();
            startActivity(new Intent(getContext(), MainActivity.class));
        }

        if (v == settings){
            DialogEditUserFragment dialog = new DialogEditUserFragment();
            dialog.show(getFragmentManager(),"DialogEditUserFragment");

            textViewUserEmail.setText(SharedPrefManager.getInstance(getContext()).getUserEmail());
            textViewUsername.setText(SharedPrefManager.getInstance(getContext()).getUsername());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setSharedPrefManager(position);
    }

    private void setSharedPrefManager(int position) {

        String C_id="",C_name="",C_gender="",C_weight="",C_height="",C_birthday="",C_blood="";
        try {
            JSONObject json = result.getJSONObject(position);
            C_id = json.getString(TagConfigChild.TAG_ID);
            C_name = json.getString(TagConfigChild.TAG_NAME);
            C_gender = json.getString(TagConfigChild.TAG_GENDER);
            C_weight = json.getString(TagConfigChild.TAG_WEIGHT);
            C_weight = json.getString(TagConfigChild.TAG_HEIGHT);
            C_birthday = json.getString(TagConfigChild.TAG_BIRTHDAY);
            C_blood = json.getString(TagConfigChild.TAG_BLOOD);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPrefManagerKid.getInstance(getContext())
                .datakid(C_id, C_name, C_gender,C_birthday,C_weight,C_height,C_blood);


        textViewNamekid.setText(SharedPrefManagerKid.getInstance(getContext()).getCname());
        textViewBirthday.setText(SharedPrefManagerKid.getInstance(getContext()).getCdate());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
       // SharedPrefManagerKid.getInstance(getContext()).onClear();
    }
}
