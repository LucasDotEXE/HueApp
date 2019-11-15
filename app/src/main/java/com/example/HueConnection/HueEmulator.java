package com.example.HueConnection;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.hueapp.Model.HueLamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HueEmulator {

    private APIListener listener;
    private String adress = "http://localhost/api/newdeveloper/";

    public HueEmulator(APIListener listener)
    {
        Log.d("Emulator", "Emulator started");
        this.listener = listener;
        this.retrieveLights();
    }

    private void retrieveLights() {
        Log.d("Emulator", "Emulator trying to retrieve json");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, adress,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d("Emulator", "Emulator received JSON Array");
                            ArrayList<JSONObject> objects = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = array.getJSONObject(i);
                                    listener.onHueLampAvailable(new HueLamp(object));
                                    Log.d("Emulator", "Emulator called listener");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, null);
        // CustomJsonArrayRequest newRequest = new CustomJsonArrayRequest(0,adress + "/lights",  null, this, null);
    }
}
