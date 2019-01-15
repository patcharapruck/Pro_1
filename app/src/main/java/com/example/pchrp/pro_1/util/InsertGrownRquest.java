package com.example.pchrp.pro_1.util;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertGrownRquest extends StringRequest {

    private Map<String,String> params;


    public InsertGrownRquest(String g_weight, String g_height, String g_date,String c_id, Response.Listener<String>listener){
        super(Method.POST,Constants.URL_INSERT_GROWN,listener,null);
        params = new HashMap<>();
        params.put("h",g_height);
        params.put("w",g_weight);
        params.put("dateday",g_date);
        params.put("c_id",c_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
