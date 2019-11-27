package com.example.hueapp.Controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hueapp.Model.HueLamp;
import com.example.hueapp.Model.HueNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiManager {

    RequestQueue queue;
    ApiListener listener;

    public ApiManager(Context context, ApiListener apiListener) {
        this.queue = Volley.newRequestQueue(context);
        this.listener = apiListener;
    }


    public void getAllInfo(final String url) {
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://145.49.38.213/api/89ca8e653a5e6abef8e02c403ebcf8a",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("API Manager", "Thing recieved");
                        listener.onHueNetwortReteurned(new HueNetwork(url, response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ApiManager getAllInfo", error.getMessage());
                        //error.printStackTrace();
                    }
                }
        );
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d("VOLLEY_REQ", error.toString());
            }
        });
        this.queue.add(request);
    }


    public void getLampInfo() {
        final JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "URL",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d("VOLLEY_REQ", error.toString());
            }
        });
        this.queue.add(request);
    }
}
