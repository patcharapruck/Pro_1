package com.example.pchrp.pro_1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.manager.Provinsi;
import com.example.pchrp.pro_1.manager.ProvinsiVaccine;
import com.example.pchrp.pro_1.manager.RequestHandlerDevelorment;
import com.example.pchrp.pro_1.manager.RequestHandlerVaccine;
import com.example.pchrp.pro_1.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentVaccine extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner age;
    String[] item_spinner = {"แรกเกิด","1 - 2 เดือน","2 เดือน","4 เดือน","6 เดือน","9 - 12 เดือน"};
    String V_age = "แรกเกิด";

    ListView mListView;
    List<ProvinsiVaccine> provinsiList;

    public FragmentVaccine() {
        super();
    }

    public static FragmentVaccine newInstance() {
        FragmentVaccine fragment = new FragmentVaccine();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vaccine, container, false);
        initInstances(rootView);


        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        mListView = (ListView) rootView.findViewById(R.id.listview_vaccine);

        age = (Spinner) rootView.findViewById(R.id.spinner_vaccine);
        ArrayAdapter<String> ageadapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,item_spinner);

        ageadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(ageadapter);
        age.setOnItemSelectedListener(this);

        provinsiList = new ArrayList<>();
        //showList();
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SHOW_VACCINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("pchp");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object =  jsonArray.getJSONObject(i);
                                ProvinsiVaccine p = new ProvinsiVaccine(object.getString("V_name")
                                        ,object.getString("V_type"));
                                provinsiList.add(p);
                            }
                            VaccineAdapter adapter = new VaccineAdapter(provinsiList,getActivity().getApplicationContext());
                            mListView.setAdapter(adapter);
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
                hashMap.put("v_age",V_age);

                return hashMap;
            }
        };
        RequestHandlerVaccine.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                V_age = "แรกเกิด";
                provinsiList.clear();
                showList();
                break;
            case 1:
                V_age = "1 - 2 เดือน";
                provinsiList.clear();
                showList();
                break;
            case 2:
                V_age = "2 เดือน";
                provinsiList.clear();
                showList();
                break;
            case 3:
                V_age = "4 เดือน";
                provinsiList.clear();
                showList();
                break;
            case 4:
                V_age = "6 เดือน";
                provinsiList.clear();
                showList();
                break;
            case 5:
                V_age = "9 - 12 เดือน";
                provinsiList.clear();
                showList();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
