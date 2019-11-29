package com.example.hueapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.drm.DrmStore;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class NetworkManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "HUE_NETWORK_CONTACTS";
    private static final String DB_TABLE_NAME = "networks";
    private static final int DB_VERSION = 1;

    private static final String KEY_IP = "IP";
    private static final String KEY_TOKEN = "TOKEN";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + DB_TABLE_NAME + " (" +
                    KEY_IP + " TEXT, " +
                    KEY_TOKEN + " TEXT);";

    public NetworkManager(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public HashMap<String, String> getNetworMap() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_NAME + ";", new String[]{});

        HashMap<String, String> networks = new HashMap();

        if(cursor.moveToFirst()) {
            do {
                String ip = cursor.getString(0);
                String token = cursor.getString(1);

                networks.put(ip, token);
            } while ( cursor.moveToNext());
        }
        return networks;
    }

    public void addIP(String ip) {
        ContentValues values = new ContentValues();
        values.put(KEY_IP, ip);
        values.put(KEY_TOKEN, "Not Available");

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DB_TABLE_NAME, null, values);

    }

    public void appendTokenToIP(String ip, String token) {
        printDataBase();
       String updateStatement =  "UPDATE " + DB_TABLE_NAME +
                                 " SET " + KEY_TOKEN + "='" + token +
                                 "' WHERE " + KEY_IP + "='" + ip + "';";
       getWritableDatabase().execSQL(updateStatement);

       printDataBase();

    }

    private void printDataBase() {
        HashMap<String, String> data = getNetworMap();

        for (String key : data.keySet()) {
            Log.e("TestDatabase", key + " <--- IP Token ---> "+ data.get(key));
        }
    }

    public void addIpToken(String ip, String token) {
        ContentValues values = new ContentValues();
        values.put(KEY_IP, ip);
        values.put(KEY_TOKEN, token);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DB_TABLE_NAME, null, values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);


    }

    public void fillDatabase() {
        addIpToken("145.48.205.33", "iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB"); //beneden
        addIpToken("145.49.38.213", "TOKEN_NOT_FOUNT");// Lucas
        addIpToken("145.49.15.52", "TOKEN_NOT_FOUNT"); //Sebastiaan
//      134 network  MAD2016TI
        addIpToken("192.168.1.179", "zzzMr8hp0ikDLnj-giTMF7z6Q6fai38lYGOpkEJE"); //actual
        addIpToken("192.168.1.191", "TOKEN_NOT_FOUNT"); //emulator
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
