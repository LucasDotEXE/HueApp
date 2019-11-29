package com.example.hueapp.Model;

import java.util.ArrayList;

public class CentralVariables {

    private final static CentralVariables instance = new CentralVariables();

    public HueNetwork lucasEmulator = new HueNetwork("145.49.38.213");
    public HueNetwork sebastiaanEmulator = new HueNetwork("145.49.15.52");
    public HueNetwork lucasEmulatorMADlokaal = new HueNetwork("192.168.1.191");
    public HueNetwork avansBegaandeGrond = new HueNetwork("145.48.205.33", "iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB");
    public HueNetwork MadLokaal = new HueNetwork("192.168.1.179", "zzzMr8hp0ikDLnj-giTMF7z6Q6fai38lYGOpkEJE");

    public ArrayList<HueNetwork> networks;


    private CentralVariables() {
        this.networks = new ArrayList<>();
        networks.add(lucasEmulator);
        networks.add(avansBegaandeGrond);
        networks.add(MadLokaal);
        networks.add(lucasEmulatorMADlokaal);
        networks.add(sebastiaanEmulator);
    }

    public static CentralVariables getInstance() {
        return instance;
    }

    private HueNetwork selectedNetwork = lucasEmulator;

    public HueNetwork getSelectedNetwork() {
        return selectedNetwork;
    }

    public void setSelectedNetwork(HueNetwork selectedNetwork) {
        this.selectedNetwork = selectedNetwork;
    }

}
