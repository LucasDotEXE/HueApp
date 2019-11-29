package com.example.hueapp.Controller.ApiInterface;

import com.example.hueapp.Model.HueNetwork;

public interface TestConnectionListener extends TokenListener {
    void onConnection();
    void onConnectionError();
    void tokenRefreshed(HueNetwork network);
}
