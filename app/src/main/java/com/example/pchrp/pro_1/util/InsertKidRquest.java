package com.example.pchrp.pro_1.util;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertKidRquest extends StringRequest {

    private Map<String,String> params;


    public InsertKidRquest(String C_name,String C_gender, String C_birthday,String C_weight , String C_height, String  C_blood,String u_id, Response.Listener<String>listener){
        super(Method.POST,Constants.URL_INSERT_KID,listener,null);
        params = new HashMap<>();
        params.put("c_name",C_name);
        params.put("c_gender",C_gender);
        params.put("c_weight",C_weight);
        params.put("c_height",C_height);
        params.put("c_birthday",C_birthday);
        params.put("c_blood",C_blood);
        params.put("u_id",u_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
