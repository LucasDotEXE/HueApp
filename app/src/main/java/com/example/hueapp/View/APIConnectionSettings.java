package com.example.hueapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hueapp.Controller.ApiInterface.TokenListener;
import com.example.hueapp.Controller.ApiManager;
import com.example.hueapp.Model.CentralVariables;
import com.example.hueapp.Model.HueNetwork;
import com.example.hueapp.R;
import com.example.hueapp.TestingHelpers.HueNetworkTestHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class APIConnectionSettings extends AppCompatActivity implements TokenListener {

    private final TokenListener tokenListener = this;
    private HueNetwork selectedNetwork;

    private TextView ip;
    private TextView token;

    private Button tokenButton;
    private FloatingActionButton backButton;
    private Spinner spinner;

    private ApiManager manager;

    private ArrayList<HueNetwork> networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiconnection_settings);


        ip = findViewById(R.id.settingIP);
        token = findViewById(R.id.token);

        tokenButton = findViewById(R.id.refreshToken);
        backButton = findViewById(R.id.settingsBackButton);
        spinner = findViewById(R.id.settingsSpinner);

        // TODO: 11/22/2019 add database instead of array;
        networks = new ArrayList<>();
        networks.add(new HueNetwork("145.48.205.33", "iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB"));
        networks.add(HueNetworkTestHelper.LucasLocalTextNetworkEmpty);
        networks.add(new HueNetwork("145.49.15.52"));
        networks.add(new HueNetwork("192.168.1.179", "zzzMr8hp0ikDLnj-giTMF7z6Q6fai38lYGOpkEJE"));
        networks.add(new HueNetwork("192.168.1.191"));
        networks.add(new HueNetwork("10.0.2.2", "e0bc5ab0100aa15b9e58af4254ccf80"));
        //==========================================================

        ArrayAdapter<HueNetwork> adapter = new ArrayAdapter<HueNetwork>(this,
                android.R.layout.simple_spinner_item, networks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HueNetwork hueNetwork = (HueNetwork) parent.getSelectedItem();
                CentralVariables.getInstance().setNetwork(hueNetwork);
                //displayUserData(hueNetwork);
                updateUiInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectedNetwork = CentralVariables.getInstance().getNetwork();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                  v.getContext(), MainActivity.class);
//                intent.putExtra("SelectedNetwork", selectedNetwork);
                v.getContext().startActivity(intent);
            }
        });

        tokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.getNetworkToken(selectedNetwork, tokenListener);
            }
        });

        manager = new ApiManager(this);
        //manager.getNetworkToken(selectedNetwork, this);
        setSelectedNetwork();
    }

    private void updateUiInfo() {
        ip.setText("IP: " + CentralVariables.getInstance().getNetwork().getIp());
        token.setText("Token: " + CentralVariables.getInstance().getNetwork().getToken());
    }

    private HueNetwork getSelectedHueNetwork() {
        return(HueNetwork) spinner.getSelectedItem();
    }

    private void setSelectedNetwork() {
        for (HueNetwork network : networks) {
            if (network.getIp().equals(
                    CentralVariables.getInstance().getNetwork().getIp()
            )) {
                spinner.setSelection(networks.indexOf(network), true);
            }
        }
    }

    private void displayUserData(HueNetwork user) {
        String ip = user.getIp();
        String token = user.getToken();
        String userData = "IP: " + ip + "\nToken: " + token;
        Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTokenAvailable(String token) {
        Log.i("API Connection", "Token: " + token);
        updateUiInfo();
    }

    @Override
    public void onTokenError() {
        Log.e("API Connection", "Couldnt get token");
    }
}
