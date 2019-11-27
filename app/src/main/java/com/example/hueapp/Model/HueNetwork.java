package com.example.hueapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HueNetwork implements Parcelable {

    //private String url; //URL with token
    private BridgeConfig config;
    private ArrayList<HueLamp> hueLamps;

    private String ip;
    private String token = "NoTokenFound";



    public HueNetwork(@NonNull String ip) {

        this.ip = ip;
        this.hueLamps = new ArrayList<>();
    }

    public HueNetwork(@NonNull String ip, @NonNull String token, JSONObject jsonHueNetwork) {
        this.ip = ip;
        this.token = token;

        this.hueLamps = new ArrayList<>();
        try {
            this.config = new BridgeConfig(jsonHueNetwork.getJSONObject("config"));
            this.hueLamps = getHueLampsFromJson(jsonHueNetwork.getJSONObject("lights"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<HueLamp> getHueLampsFromJson(JSONObject lights) {

        ArrayList<HueLamp> lamps = new ArrayList<>();
        try {
            int i = 1;
            while (true) {
                lamps.add(new HueLamp(lights.getJSONObject("" + i + "")));
                i++;
            }
        } catch (JSONException e) {
            return lamps;
        }
    }


    public  HueNetwork() {
        this.hueLamps = new ArrayList<>();
    }

    /*public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }*/

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    protected HueNetwork(Parcel in) {
        //url = in.readString();
        config = (BridgeConfig) in.readValue(BridgeConfig.class.getClassLoader());
        if (in.readByte() == 0x01) {
            hueLamps = new ArrayList<HueLamp>();
            in.readList(hueLamps, HueLamp.class.getClassLoader());
        } else {
            hueLamps = null;
        }
    }

    public void sendUpdateToHue(int id, HueLamp lamp)
    {
        JSONObject data = new JSONObject();
        try {
            if (lamp.isOn()) {
                data.put("on", lamp.isOn());
                data.put("hue", lamp.getHue());
                data.put("bri", lamp.getBrightness());
                data.put("sat", lamp.getSaturation());
            }
            else
            {
                data.put("on", lamp.isOn());
            }
            //Todo: implement Json put
            //new JsonObjectRequest()

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeString(url);
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

    @Override
    public String toString() {
        return ip;
    }
}