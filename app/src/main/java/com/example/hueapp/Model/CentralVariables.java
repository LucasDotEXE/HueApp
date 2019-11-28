package com.example.hueapp.Model;

import com.example.hueapp.TestingHelpers.HueNetworkTestHelper;

public class CentralVariables {

    private final static CentralVariables instance = new CentralVariables();
    public final static String HueLamp_Key = "HueLampObject";

    private CentralVariables() {
    }

    public static CentralVariables getInstance() {
        return instance;
    }

    private HueNetwork network = new HueNetwork("10.0.2.2");

    public HueNetwork getNetwork() {
        return network;
    }

    public void setNetwork(HueNetwork network) {
        this.network = network;
    }

}
