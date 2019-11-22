package com.example.hueapp.Controller;

import com.example.hueapp.Model.HueNetwork;

public interface ApiListener {
    void onLampsReturned();
    void onError();
    void onLampStateReturned();
    void onHueNetwortReteurned(HueNetwork network);
}
