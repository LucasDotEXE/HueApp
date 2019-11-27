package com.example.hueapp.Controller.ApiInterface;

import com.example.hueapp.Model.HueNetwork;

public interface NetworkListener {
    void onHueNetworkError();
    void onHueNetworkAvailable(HueNetwork network);
}
