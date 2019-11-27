package com.example.hueapp.Controller;

import com.example.hueapp.Model.HueNetwork;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ApiListener {
    //void onLampsReturned(JSONArray response);
    void onError();
    void onLampStateReturned();
    void onHueNetworkReturned(HueNetwork network);

}
