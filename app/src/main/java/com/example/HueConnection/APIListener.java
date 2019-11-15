package com.example.HueConnection;

import com.example.hueapp.Model.HueLamp;

public interface APIListener {

    void onHueLampAvailable(HueLamp lamp);
}
