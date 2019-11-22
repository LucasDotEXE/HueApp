package com.example.hueapp.Model;

import com.example.hueapp.TestingHelpers.HueNetworkTestHelper;

import java.util.ArrayList;

public class CentralVariables {

    private final static CentralVariables instance = new CentralVariables();

    private CentralVariables() {
    }

    public static CentralVariables getInstance() {
        return instance;
    }

    private HueNetwork network = HueNetworkTestHelper.LucasLocalTestNetwork;

    public HueNetwork getNetwork() {
        return network;
    }

    public void setNetwork(HueNetwork network) {
        this.network = network;
    }

    public ArrayList<String> getIPArray() {
        ArrayList<String> ipArray = new ArrayList<>();
        ipArray.add("123.213.2324 kj8s9f03dh8kj7f26dl11s2gs3");
        ipArray.add("123.213.2324 kj8s9f03dh8kj7f26dl11s2gs3");
        ipArray.add("123.213.2324 kj8s9f03dh8kj7f26dl11s2gs3");
        ipArray.add("123.213.2324 kj8s9f03dh8kj7f26dl11s2gs3");
        ipArray.add("123.213.2324 kj8s9f03dh8kj7f26dl11s2gs3");

        return ipArray;
    }
}
