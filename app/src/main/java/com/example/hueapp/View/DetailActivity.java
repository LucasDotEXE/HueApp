package com.example.hueapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.hueapp.ColorPickerFragment;
import com.example.hueapp.Controller.ApiManager;
import com.example.hueapp.HueLampInfoFragment;
import com.example.hueapp.Model.CentralVariables;
import com.example.hueapp.Model.HueLamp;
import com.example.hueapp.Model.HueNetwork;
import com.example.hueapp.R;

public class DetailActivity extends AppCompatActivity implements HueLampInfoFragment.OnFragmentInteractionListener, ColorPickerFragment.OnColorPickerFragmentInteractionListener {

    private ApiManager apiManager;
    private HueLamp lamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.apiManager = new ApiManager(this);
        lamp =  this.getIntent().getParcelableExtra(CentralVariables.HueLamp_Key);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment infoFragment = new HueLampInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CentralVariables.HueLamp_Key,this.getIntent().getParcelableExtra(CentralVariables.HueLamp_Key));
        infoFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.infofragment,infoFragment);
        fragmentTransaction.commit();

       /* ColorPickerFragment colorPicker = (ColorPickerFragment) fragmentManager.findFragmentById(R.id.colorpickerfragment);
        colorPicker.*/
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment)
    {
        if(fragment.getId() == R.id.colorpickerfragment)
        {
            Bundle bundle = new Bundle();
            bundle.putParcelable(CentralVariables.HueLamp_Key,this.getIntent().getParcelableExtra(CentralVariables.HueLamp_Key));
            fragment.setArguments(bundle);
        }
    }



    @Override
    public void onColorSelect(int color) {
        //Log.d("Hue", "onColorSelect called " + color);
        float[] values = new float[3];
/*
        color = color & 0xffffffff; //prefix opacity to 100%
        Color.colorToHSV(color, values);*/
        Color.RGBToHSV((color >> 16) & 0xff, (color >> 8) & 0xff, (color) & 0xff, values);
        //Log.d("Hue", "Translated: "+  (values[0]) + ", " + (values[1]) + ", " + (values[2]));
        lamp.setHue((int)(values[0]* ((float)65536/360)));
        lamp.setBrightness((int)(values[1] * (float)256));
        lamp.setSaturation((int)(values[2] * (float)256));
        apiManager.sendUpdateToHue(CentralVariables.getInstance().getSelectedNetwork(), lamp.getId(), lamp);
    }

    @Override
    public void OnOnOff() {
        //Log.d("Hue", "lamp is: " + lamp.isOn());
        //lamp.setOn(!lamp.isOn());
        apiManager.sendUpdateToHue(CentralVariables.getInstance().getSelectedNetwork(), lamp.getId(), lamp);
    }
}
