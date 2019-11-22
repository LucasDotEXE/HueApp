package com.example.hueapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class APIConnectionSettings extends AppCompatActivity implements TokenListener {

    private TextView ip;
    private TextView token;

    private Button tokenButton;
    private Spinner spinner;

    private ApiManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiconnection_settings);

        ip = findViewById(R.id.settingIP);
        token = findViewById(R.id.token);

        tokenButton = findViewById(R.id.refreshToken);
        spinner = findViewById(R.id.settingsSpinner);

        // TODO: 11/22/2019 add database instead of array;
        List<HueNetwork> userList = new ArrayList<>();
        userList.add(CentralVariables.getInstance().getNetwork());
        userList.add(CentralVariables.getInstance().getNetwork());
        userList.add(CentralVariables.getInstance().getNetwork());
        userList.add(CentralVariables.getInstance().getNetwork());
        userList.add(CentralVariables.getInstance().getNetwork());
        //==========================================================

        ArrayAdapter<HueNetwork> adapter = new ArrayAdapter<HueNetwork>(this,
                android.R.layout.simple_spinner_item, userList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HueNetwork hueNetwork = (HueNetwork) parent.getSelectedItem();
                displayUserData(hueNetwork);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        HueNetwork hueNetwork = CentralVariables.getInstance().getNetwork();

        ip.setText("IP: " + hueNetwork.getIp());
        token.setText("Token: " + hueNetwork.getToken());


        tokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        manager = new ApiManager(this);
        manager.getNetworkToken(hueNetwork, this);


    }


    private HueNetwork getSelectedUser() {
        return(HueNetwork) spinner.getSelectedItem();
    }

    private void displayUserData(HueNetwork user) {
        String ip = user.getIp();
        String token = user.getToken();
        String userData = "IP: " + ip + "\nToken: " + token;
        Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTokenAvaileable(String token) {

    }

    @Override
    public void onTokenError() {

    }
}
