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
import com.example.hueapp.Model.NetworkManager;
import com.example.hueapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class APIConnectionSettings extends AppCompatActivity implements TokenListener {

    private final TokenListener tokenListener = this;
    private HueNetwork selectedNetwork;
    private NetworkManager networkManager;
    private ArrayList<HueNetwork> networks;

    private TextView ip;
    private TextView token;

    private Button tokenButton;
    private FloatingActionButton backButton;
    private FloatingActionButton connectButton;
    private Spinner spinner;

    private ApiManager manager;

//    private ArrayList<HueNetwork> networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiconnection_settings);


        ip = findViewById(R.id.settingIP);
        token = findViewById(R.id.tokenConnected);

        tokenButton = findViewById(R.id.refreshToken);
        backButton = findViewById(R.id.settingsBackButton);
        spinner = findViewById(R.id.settingsSpinner);

        // TODO: 11/22/2019 add database instead of array;

        this.networkManager = new NetworkManager(this);
        //networkManager.fillDatabase();
        this.networks = new ArrayList<>();
        for (String IP : this.networkManager.getNetworMap().keySet()) {
            this.networks.add(new HueNetwork(IP, networkManager.getNetworMap().get(IP)));
        }
        //==========================================================

        ArrayAdapter<HueNetwork> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, this.networks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HueNetwork hueNetwork = (HueNetwork) parent.getSelectedItem();
                CentralVariables.getInstance().setSelectedNetwork(hueNetwork);
                //displayUserData(hueNetwork);
                updateUiInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectedNetwork = CentralVariables.getInstance().getSelectedNetwork();

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
        HueNetwork selectedNetwork = getSelectedHueNetwork();
        updateIP(selectedNetwork);
        updateConnection(selectedNetwork);

    }

    private void  updateConnection(HueNetwork selectedNetwork) {
        this.manager.tesConnection(selectedNetwork);
    }

    private void updateIP(HueNetwork selectedNetwork) {
        String ipText = "IP: " + selectedNetwork.getIp();
        ip.setText(ipText);
    }

    private HueNetwork getSelectedHueNetwork() {
        return(HueNetwork) spinner.getSelectedItem();
    }

    private void setSelectedNetwork() {
        for (HueNetwork network : this.networks) {
            if (network.getIp().equals(
                    CentralVariables.getInstance().getSelectedNetwork().getIp()
            )) {
                spinner.setSelection(this.networks.indexOf(network), true);
            } else {
                //Log.e("Test", network.getIp() + "<---Checking  Selected--->" + CentralVariables.getInstance().getSelectedNetwork().getIp());
            }
        }
    }

    @Override
    public void onTokenAvailable(String token) {
        Log.i("API Connection", "Token: " + token);
        getSelectedHueNetwork().setToken(token);
        this.networkManager.appendTokenToIP(getSelectedHueNetwork().getIp(), token);

        updateUiInfo();
    }

    @Override
    public void onTokenError() {
        Log.e("API Connection", "Couldnt get token");
    }

    @Override
    public void onLinkButtonNotPressed() {
        Toast.makeText(this, "Link Button Wasn't Pressed On HueBridge", Toast.LENGTH_SHORT).show();
    }
}
