package com.futonchild.android.hotornotdog;

import android.content.Context;
import android.location.LocationManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by matt on 8/17/17.
 */

public class UploadSingleton {
    private static UploadSingleton mInstance;
    private RequestQueue requestQueue;
    private Context mCtx;
    public LocationManager mLocationManager;

    private UploadSingleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue() ;
        mLocationManager = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        return requestQueue;
    }

    public static synchronized  UploadSingleton getInstance(Context context) {
        if (mInstance==null) {
            mInstance = new UploadSingleton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request) {

        getRequestQueue().add(request);
    }
}