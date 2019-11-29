package com.example.hueapp.Model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HueLamp implements Parcelable {

    //identification
    private int id;

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

    public HueLamp(int id, JSONObject jsonLamp) {
        try {

            //identification
            this.id = id;
            //state
            JSONObject state = jsonLamp.getJSONObject("state");
            this.isOn = state.getBoolean("on");
            this.brightness = state.getInt("bri");
            this.hue = state.getInt("hue");
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
            Log.d("Hue", "Lamp created: " + this.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return "ID" + this.id + ", Model: " + this.modelId + ", Hue: " + this.hue + ", Brightness: " + this.brightness + ", Saturation" + this.saturation;
    }
    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

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
        if (brightness >= 254)
            this.brightness = 254;
        else if (brightness < 0)
            this.brightness = 0;
        else
            this.brightness = brightness;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        if (hue > 65535)
            this.hue = 65535;
        else if (hue < 0)
            this.hue = 0;
        else
            this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        if (saturation > 255)
            this.saturation = 255;
        else if (saturation < 0)
            this.saturation = 0;
        else
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
        id = in.readInt();
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
        dest.writeInt(id);
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


    public int getColor()
    {
                return JavaColor.HSBtoRGB((float)hue/ (float)65536 ,(float)saturation/ 256,(float)brightness / 256);
    }
}