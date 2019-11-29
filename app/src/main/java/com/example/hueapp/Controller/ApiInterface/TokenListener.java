package com.example.hueapp.Controller.ApiInterface;

public interface TokenListener {
    void onTokenAvailable(String token);
    void onTokenError();
    void onLinkButtonNotPressed();
}
