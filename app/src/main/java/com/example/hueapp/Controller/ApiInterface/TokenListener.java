package com.example.hueapp.Controller.ApiInterface;

public interface TokenListener {
    void onTokenAvaileable(String token);
    void onTokenError();
}
