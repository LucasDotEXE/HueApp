package com.example.hueapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class BridgeConfig implements Parcelable {
    private String name;
    private String ipaddress;
    private String localTime;
    private String timeZone;
    private String apiVersion;

    public BridgeConfig(String name) {
        this.name = name;
    }

    public BridgeConfig (JSONObject jsonConfig) {
        try {
            this.name = jsonConfig.getString("name");
            this.ipaddress = jsonConfig.getString("ipaddress");
//            this.localTime = jsonConfig.getString("localtime");
//            this.timeZone = jsonConfig.getString("timezone");
//            this.apiVersion = jsonConfig.getString("apiversion");

            if (this.name == null) {
                this.name = "Name Not Found";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public String getLocalTime() {
        return localTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    protected BridgeConfig(Parcel in) {
        name = in.readString();
        ipaddress = in.readString();
        localTime = in.readString();
        timeZone = in.readString();
        apiVersion = in.readString();

        if (this.name == null) {
            this.name = "Name Not Found";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(ipaddress);
        dest.writeString(localTime);
        dest.writeString(timeZone);
        dest.writeString(apiVersion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BridgeConfig> CREATOR = new Parcelable.Creator<BridgeConfig>() {
        @Override
        public BridgeConfig createFromParcel(Parcel in) {
            return new BridgeConfig(in);
        }

        @Override
        public BridgeConfig[] newArray(int size) {
            return new BridgeConfig[size];
        }
    };
}