package com.example.hueapp.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hueapp.Controller.ApiInterface.TestConnectionListener;
import com.example.hueapp.Controller.ApiInterface.TokenListener;
import com.example.hueapp.Controller.ApiManager;
import com.example.hueapp.Model.CentralVariables;
import com.example.hueapp.Model.HueNetwork;
import com.example.hueapp.Model.NetworkManager;
import com.example.hueapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class APIConnectionSettings extends AppCompatActivity implements TestConnectionListener {

    private final TokenListener tokenListener = this;
    private final APIConnectionSettings context = this;

    private HueNetwork selectedNetwork;
    private NetworkManager networkManager;
    private ArrayList<HueNetwork> networks;
    private ApiManager manager;

    private TextView ip;
    private TextView token;

    private FloatingActionButton connectButton;
    private Button deleteIPButton;
    private Spinner spinner;

    private ArrayAdapter<HueNetwork> adapter;


//    private ArrayList<HueNetwork> networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiconnection_settings);

        ip = findViewById(R.id.settingIP);
        token = findViewById(R.id.tokenConnected);
        deleteIPButton = findViewById(R.id.deleteIPButton);
        connectButton = findViewById(R.id.connectToNetworkButton);

        spinner = findViewById(R.id.settingsSpinner);

        // TODO: 11/22/2019 add database instead of array;

        this.networkManager = new NetworkManager(this);
        //networkManager.fillDatabase();
        this.networks = new ArrayList<>();
        for (String IP : this.networkManager.getNetworMap().keySet()) {
            this.networks.add(new HueNetwork(IP, networkManager.getNetworMap().get(IP)));
        }
        //==========================================================

        adapter = new ArrayAdapter<>(this,
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

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUiInfo();
            }
        });

        selectedNetwork = CentralVariables.getInstance().getSelectedNetwork();

        Button ipAddButton = findViewById(R.id.ipAddButton);
        final EditText editText = findViewById(R.id.editText);

        ipAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipAdress = editText.getText().toString();
                //Todo: Check string for IP Adress format
                networkManager.addIP(ipAdress);
                HueNetwork network = new HueNetwork(ipAdress);
                networks.add(network);
                //CentralVariables.getInstance().setNetwork(network);
                adapter.notifyDataSetChanged();
            }});

        deleteIPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networks.size() > 1) {
                    areYouSureYouWantToDelete();
                } else {
                    Toast.makeText(context, R.string.warning_IPmin_1, Toast.LENGTH_SHORT).show();
                }
            }
        });




        manager = new ApiManager(this);
        //manager.getNetworkToken(selectedNetwork, this);
        setSelectedNetwork();
    }

    private void areYouSureYouWantToDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.warning_delete_IP + " " + getSelectedHueNetwork().getIp())
                .setCancelable(true)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HueNetwork network = getSelectedHueNetwork();
                        spinner.setSelection(0);
                        networks.remove(network);
                        networkManager.removeIP(network.getIp());
                        adapter.notifyDataSetChanged();
                    }
                })
        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateUiInfo() {
        HueNetwork selectedNetwork = getSelectedHueNetwork();
        updateIP(selectedNetwork);
        updateConnection(selectedNetwork);

    }

    private void  updateConnection(HueNetwork selectedNetwork) {
        this.manager.testConnection(selectedNetwork, this);
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
        this.token.setText(R.string.warning_failed_connect);
    }

    @Override
    public void onLinkButtonNotPressed() {
        Toast.makeText(this, R.string.warning_link_not_pressed, Toast.LENGTH_LONG).show();
        this.token.setText(R.string.warning_huebridge_link);
    }

    @Override
    public void onConnection() {
        this.token.setText(R.string.connected_network);
    }

    @Override
    public void onConnectionError() {
        Toast.makeText(this, R.string.warning_IP_not_found, Toast.LENGTH_SHORT).show();
        this.token.setText("Unknown IP");
    }

    @Override
    public void tokenRefreshed(HueNetwork network) {
        this.manager.testConnection(network, this);
    }
}
