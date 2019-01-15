package com.example.pchrp.pro_1.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.activity.MainActivity;
import com.example.pchrp.pro_1.manager.Provinsi;
import com.example.pchrp.pro_1.manager.RequestHandler;
import com.example.pchrp.pro_1.manager.RequestHandlerDevelorment;
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
public class FragmentDevelorment extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    Spinner age;
    String[] item_spinner = {"แรกเกิด - 1 เดือน","1 - 2 เดือน","3 - 4 เดือน","5 - 6 เดือน","7 - 8 เดือน"};
    String BD_age = "แรกเกิด - 1 เดือน";

    ListView mListView;
    List<Provinsi> provinsiList;

    public FragmentDevelorment() {
        super();
    }

    public static FragmentDevelorment newInstance() {
        FragmentDevelorment fragment = new FragmentDevelorment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_development, container, false);
        initInstances(rootView);


        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        mListView = (ListView) rootView.findViewById(R.id.listview_develor);

        age = (Spinner) rootView.findViewById(R.id.spinner_develor);
        ArrayAdapter<String> ageadapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,item_spinner);

        ageadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(ageadapter);
        age.setOnItemSelectedListener(this);

        provinsiList = new ArrayList<>();
        mListView.setOnItemClickListener(this);
        //showList();
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SHOW_DEVELORMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("pchp");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object =  jsonArray.getJSONObject(i);
                                Provinsi p = new Provinsi(object.getString("BD_data")
                                        ,object.getString("BD_type"),object.getInt("BD_id"));
                                provinsiList.add(p);
                            }
                            MyAdapter adapter = new MyAdapter(provinsiList,getActivity().getApplicationContext());
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
                hashMap.put("BD_age",BD_age);

                return hashMap;
            }
        };
        RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
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
                BD_age = "แรกเกิด - 1 เดือน";
                provinsiList.clear();
                showList();
                break;
            case 1:
                BD_age = "1 - 2 เดือน";
                provinsiList.clear();
                showList();
                break;
            case 2:
                BD_age = "3 - 4 เดือน";
                provinsiList.clear();
                showList();
                break;
            case 3:
                BD_age = "5 - 6 เดือน";
                provinsiList.clear();
                showList();
                break;
            case 4:
                BD_age = "7 - 8 เดือน";
                provinsiList.clear();
                showList();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Provinsi provinsi = provinsiList.get(position);

        DialogFragmentPutDev dialog = new DialogFragmentPutDev();
        dialog.show(getFragmentManager(),"DialogFragmentPutDev");

        //Toast.makeText(getContext(),provinsi.getIdDevelorment(),Toast.LENGTH_SHORT).show();
    }
}
