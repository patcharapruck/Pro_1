package com.example.pchrp.pro_1.util;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRquest extends StringRequest {
    private Map<String,String> params;

    public RegisterRquest(String user_email, String user_pass, String user_name, Response.Listener<String>listener){
        super(Method.POST,Constants.URL_REGISTER,listener,null);
        params = new HashMap<>();
        params.put("user_email",user_email);
        params.put("user_pass",user_pass);
        params.put("user_name",user_name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
