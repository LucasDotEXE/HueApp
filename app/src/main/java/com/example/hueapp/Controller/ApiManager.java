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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.hueapp.Model.HueLamp;
import com.example.hueapp.Controller.ApiInterface.TokenListener;
import com.example.hueapp.Model.HueNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiManager {

    RequestQueue queue;


    public ApiManager(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }


    public void getAllInfo(final HueNetwork network, final ApiListener listener) {
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://" + network.getIp() +"/api/" + network.getToken(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("API Manager", "Thing recieved");
                        listener.onHueNetwortReteurned(new HueNetwork(network.getIp(), network.getToken(), response));
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

    public void getNetworkToken(final HueNetwork network, final TokenListener listener) {
        try {
            final JsonRequest request = new CustomJsonArrayRequest(
                    Request.Method.POST,
                    "http://" + network.getIp() + "/api",
                    new JSONObject("{\"devicetype\" : \"HeuApp\"}"),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                String token = response.getJSONObject(0).getJSONObject("success").getString("username");
                                network.setToken(token);
                                listener.onTokenAvaileable(token);
                            } catch (JSONException e) {
                                listener.onTokenError();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onTokenError();
                        }
                    }
            );
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 5000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 5000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {
                    Log.e("API Mager GetToken", error.getMessage());
                    error.printStackTrace();
                }
            });
            this.queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
