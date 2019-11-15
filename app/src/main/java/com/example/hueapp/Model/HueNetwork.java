package com.example.hueapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class HueNetwork implements Parcelable {

    private String url;
    private BridgeConfig config;
    private ArrayList<HueLamp> hueLamps;


    public HueNetwork(@NonNull String url) {
        this.url = url;
        this.hueLamps = new ArrayList<>();
    }

    public  HueNetwork() {
        this.hueLamps = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BridgeConfig getConfig() {
        return config;
    }

    public void setConfig(BridgeConfig config) {
        this.config = config;
    }

    public ArrayList<HueLamp> getHueLamps() {
        return hueLamps;
    }

    public void setHueLamps(ArrayList<HueLamp> hueLamps) {
        this.hueLamps = hueLamps;
    }

    protected HueNetwork(Parcel in) {
        url = in.readString();
        config = (BridgeConfig) in.readValue(BridgeConfig.class.getClassLoader());
        if (in.readByte() == 0x01) {
            hueLamps = new ArrayList<HueLamp>();
            in.readList(hueLamps, HueLamp.class.getClassLoader());
        } else {
            hueLamps = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeValue(config);
        if (hueLamps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(hueLamps);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HueNetwork> CREATOR = new Parcelable.Creator<HueNetwork>() {
        @Override
        public HueNetwork createFromParcel(Parcel in) {
            return new HueNetwork(in);
        }

        @Override
        public HueNetwork[] newArray(int size) {
            return new HueNetwork[size];
        }
    };
}