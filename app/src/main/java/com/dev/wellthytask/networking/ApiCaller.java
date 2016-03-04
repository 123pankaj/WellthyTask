package com.dev.wellthytask.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ApiCaller {
    Context networkContext;
    ApiResponseFetcher serverResponse;
    ProgressDialog dialog;

    //Constructor
    public ApiCaller(ApiResponseFetcher response, Context ctx) {
        this.serverResponse = response;
        this.networkContext = ctx;
        dialog = new ProgressDialog(networkContext);
    }

    // Get Request To Wellthy Server
    public void getData(String url) {
        StartProgressBar();
        RequestQueue queue = Volley.newRequestQueue(networkContext);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StopProgressBar();
                if (response != null && response.length() > 0) {
                    try {
                        serverResponse.result(new JSONObject(response));
                    } catch (Exception e) {
                        e.printStackTrace();
                        StopProgressBar();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StopProgressBar();
                Log.d("ERROR", "error => " + error.toString());
                Class klass = error.getClass();
                if (klass == AuthFailureError.class) {
                    Log.d("TAG", "AuthFailureError");
                    serverResponse.error("AuthFailureError");
                } else if (klass == com.android.volley.NetworkError.class) {
                    Log.d("TAG", "NetworkError");
                    serverResponse.error("NetworkError");
                } else if (klass == com.android.volley.NoConnectionError.class) {
                    Log.d("TAG", "NoConnectionError");
                    serverResponse.error("NoConnectionError");
                } else if (klass == com.android.volley.ServerError.class) {
                    Log.d("TAG", "ServerError");
                    serverResponse.error("ServerError");
                } else if (klass == com.android.volley.TimeoutError.class) {
                    Log.d("TAG", "TimeoutError");
                    serverResponse.error("TimeoutError");
                } else if (klass == com.android.volley.ParseError.class) {
                    Log.d("TAG", "ParseError");
                    serverResponse.error("ParseError");
                } else if (klass == VolleyError.class) {
                    Log.d("TAG", "General error");
                    serverResponse.error("General error");
                }
            }
        }) {
        };
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(50 * 1000, 1, 1.0f));
        queue.add(request);
    }


    // ProgressBar Start Here
    private void StartProgressBar(){
        dialog.setMessage("Loading...");
        dialog.show();
        dialog.setCancelable(false);
        ProgressBar bar = (ProgressBar)dialog.findViewById(android.R.id.progress);
        bar.getIndeterminateDrawable().setColorFilter(0xFF4EB478, PorterDuff.Mode.MULTIPLY);
    }

    // ProgressBar Stops Here
    private void StopProgressBar(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
