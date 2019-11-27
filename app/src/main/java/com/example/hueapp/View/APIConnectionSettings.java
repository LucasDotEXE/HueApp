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
        //==========================================================

        ArrayAdapter<HueNetwork> adapter = new ArrayAdapter<HueNetwork>(this,
                android.R.layout.simple_spinner_item, networks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HueNetwork hueNetwork = (HueNetwork) parent.getSelectedItem();
                selectedNetwork = getSelectedHueNetwork();
                displayUserData(hueNetwork);
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
        ip.setText("IP: " + selectedNetwork.getIp());
        token.setText("Token: " + selectedNetwork.getToken());
    }

    private HueNetwork getSelectedHueNetwork() {
        return(HueNetwork) spinner.getSelectedItem();
    }

    private void setSelectedNetwork() {
        Log.e("Test(APIConSet)", CentralVariables.getInstance().getNetwork().getIp());
        for (HueNetwork network : networks) {
            if (network.getIp().equals(
                    CentralVariables.getInstance().getNetwork().getIp()
            )) {
                spinner.setSelection(networks.indexOf(network), true);
//                Log.e("Test", "dsghjdsgfhj;lgfdelkbjgflkbj");
            } else {
//                Log.e("Test", network.getIp());
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
