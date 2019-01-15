package com.example.pchrp.pro_1.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pchrp.pro_1.manager.SharedPrefManager;
import com.example.pchrp.pro_1.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DialogEditUserFragment extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "DialogEditUserFragment";

    private RequestQueue requestQueue;
    private StringRequest request;

    TextView mActionOk, mActionCancel;
    EditText editTextname,editTextpass,editTextconpass,editTextoldpass;

    public DialogEditUserFragment() {
        super();
    }

    public static DialogEditUserFragment newInstance() {
        DialogEditUserFragment fragment = new DialogEditUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_settings, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        mActionOk = (TextView) rootView.findViewById(R.id.action_ok);
        mActionCancel = (TextView) rootView.findViewById(R.id.action_cancel);

        editTextname = (EditText) rootView.findViewById(R.id.edit_name);
        editTextpass = (EditText) rootView.findViewById(R.id.edit_pass);
        editTextconpass = (EditText) rootView.findViewById(R.id.edit_conpass);
        editTextoldpass = (EditText) rootView.findViewById(R.id.edit_oldpass);

        editTextname.setText(SharedPrefManager.getInstance(getContext()).getUsername());

        mActionOk.setOnClickListener(this);
        mActionCancel.setOnClickListener(this);
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
        if (v == mActionCancel){
            getDialog().dismiss();
        }
        if (v == mActionOk){
            String name = editTextname.getText().toString();
            String pass,conpsss,oldpass,old;

            old = SharedPrefManager.getInstance(getContext()).getUserpassword();

            pass = editTextpass.getText().toString();
            conpsss = editTextconpass.getText().toString();
            oldpass = editTextoldpass.getText().toString();

            boolean check = true;

            if(!name.equals("")||name.length()>0){

            }
            else{
                check = false;
            }

            if(pass.length()>0&&conpsss.length()>0){
                if(pass == conpsss){

                }
                else{
                    check = false;
                }
            }else{ check = false;}

            if(oldpass.length()>0){
                if(oldpass == old){ }
                else{ check = false;}
            }else{ check = false;}

            if(check){
                updateuser(pass+"",name+"");
            }

            updateuser(pass+"",name+"");
        }
    }

    private void updateuser(final String Pass, final String Name) {
        final String U_id,email;
        final int u_id = SharedPrefManager.getInstance(getContext()).getID();
        final boolean check = SharedPrefManager.getInstance(getContext()).getCheck();
        email  = SharedPrefManager.getInstance(getContext()).getUserEmail();
        if(u_id<10&&u_id>0){
            U_id = "00"+u_id;
        }
        else if(u_id<100&&u_id>10){
            U_id = "0"+u_id;
        }
        else {
            U_id = u_id+"";
        }
        final String u = u_id+"";
        request = new StringRequest(Request.Method.POST, Constants.URL_UPDATEUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    if (jsonObject.getBoolean("success")) {

                        SharedPrefManager.getInstance(getContext())
                                .userLogin(u_id, Name, email, Pass, check);

                        getDialog().dismiss();

                    } else {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("id",u);
                hashMap.put("name",Name);
                hashMap.put("pass",Pass);
                return hashMap;
            }
        };

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}
