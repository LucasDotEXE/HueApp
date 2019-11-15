package com.example.HueConnection;

import com.android.volley.Response;

import org.json.JSONArray;

public class HueEmulator implements Response.Listener<JSONArray> {

    private APIListener listener;
    private String adress = "http://localhost/api/newdeveloper/";

    public HueEmulator(APIListener listener)
    {
        this.listener = listener;
        this.retrieveLights();
    }

    private void retrieveLights()
    {
        CustomJsonArrayRequest newRequest = new CustomJsonArrayRequest(0,adress + "/lights", null, this, null);
    }



    @Override
    public void onResponse(JSONArray response) {
        //parse stuff here, get Lämps
        listener.onHueLampAvailable(new HueLamp());
    }
}
