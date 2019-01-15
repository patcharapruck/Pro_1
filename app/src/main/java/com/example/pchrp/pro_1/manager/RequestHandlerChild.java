package com.example.pchrp.pro_1.manager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Belal on 26/11/16.
 */

public class RequestHandlerChild {
    private static RequestHandlerChild mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private RequestHandlerChild(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestHandlerChild getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestHandlerChild(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }

}

