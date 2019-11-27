package com.example.hueapp.Model;

import com.example.hueapp.TestingHelpers.HueNetworkTestHelper;

public class CentralVariables {

    private final static CentralVariables instance = new CentralVariables();

    private CentralVariables() {
    }

    public static CentralVariables getInstance() {
        return instance;
    }

    private HueNetwork network = HueNetworkTestHelper.LucasLocalTextNetworkEmpty;

    public HueNetwork getNetwork() {
        return network;
    }

    public void setNetwork(HueNetwork network) {
        this.network = network;
    }

}
