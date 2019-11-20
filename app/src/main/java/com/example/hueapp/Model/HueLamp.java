package com.example.hueapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HueLamp implements Parcelable {

    //state
    private boolean isOn;
    private int brightness;
    private int hue;
    private int saturation;
    private String effect;
    private double x;
    private double y;
    private boolean reachable;

    //extra
    private String name;
    private String type;
    private String modelId;
    private String manufacturerName;
    private String productName;

    public HueLamp(JSONObject jsonLamp) {
        try {
            //state
            JSONObject state = jsonLamp.getJSONObject("state");
            this.isOn = state.getBoolean("on");
            this.brightness = state.getInt("bri");
            //this.hue = state.getInt("hue");
            this.saturation = state.getInt("sat");
            this.effect = state.getString("effect");
            this.reachable = state.getBoolean("reachable");
//            JSONArray xy = state.getJSONArray("xy");
//            this.x = xy.getDouble(0);
//            this.y = xy.getDouble(1);

            //extra
            this.name = jsonLamp.getString("name");
            this.type = jsonLamp.getString("type");
//            this.modelId = jsonLamp.getString("modleid");
//            this.manufacturerName = jsonLamp.getString("manufacturername");
//            this.productName = jsonLamp.getString("productname");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    protected HueLamp(Parcel in) {
        isOn = in.readByte() != 0x00;
        brightness = in.readInt();
        hue = in.readInt();
        saturation = in.readInt();
        effect = in.readString();
        x = in.readDouble();
        y = in.readDouble();
        reachable = in.readByte() != 0x00;
        name = in.readString();
        type = in.readString();
        modelId = in.readString();
        manufacturerName = in.readString();
        productName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isOn ? 0x01 : 0x00));
        dest.writeInt(brightness);
        dest.writeInt(hue);
        dest.writeInt(saturation);
        dest.writeString(effect);
        dest.writeDouble(x);
        dest.writeDouble(y);
        dest.writeByte((byte) (reachable ? 0x01 : 0x00));
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(modelId);
        dest.writeString(manufacturerName);
        dest.writeString(productName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HueLamp> CREATOR = new Parcelable.Creator<HueLamp>() {
        @Override
        public HueLamp createFromParcel(Parcel in) {
            return new HueLamp(in);
        }

        @Override
        public HueLamp[] newArray(int size) {
            return new HueLamp[size];
        }
    };
}