package com.example.lzp.ganhuo.net;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lzp.ganhuo.app.BaseApplication;

/**
 * Created by SKJP on 2017/3/9.
 */

public class MyVolley {
    public static final String TAG = "MyVolley";
    private static RequestQueue sRequestQueue;

    public static void request(String url, String tag, Response.Listener<String> responseListener, Response
            .ErrorListener errorListener) {
        if (sRequestQueue == null) {
            sRequestQueue = Volley.newRequestQueue(BaseApplication.sApplication);
        }
        Log.e(TAG, "request url=" + url);
        StringRequest request = new StringRequest(url, responseListener, errorListener);
        request.setTag(tag);
        sRequestQueue.add(request);
    }

    public static void cancleRequest(String tag) {
        Log.e(TAG, "cancleRequest");
        if (sRequestQueue != null) {
            sRequestQueue.cancelAll(tag);
        }
    }
}
