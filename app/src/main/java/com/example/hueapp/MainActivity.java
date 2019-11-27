package com.example.hueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.hueapp.Controller.ApiListener;
import com.example.hueapp.Controller.ApiManager;
import com.example.hueapp.Controller.MainActivity_HeuNetworkAdapter;
import com.example.hueapp.Model.HueNetwork;
import com.example.hueapp.TestingHelpers.HueNetworkTestHelper;

public class MainActivity extends AppCompatActivity implements ApiListener {

    private HueNetwork network;
    private MainActivity_HeuNetworkAdapter adapter;
    private ApiManager manager;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        this.network = HueNetworkTestHelper.LucasLocalTestNetwork;

        this.manager = new ApiManager(this, this);
        manager.getAllInfo(network.getUrl());

        this.recyclerView = findViewById(R.id.mainRecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(
                this,1,GridLayoutManager.VERTICAL, false
        ));

        this.adapter = new MainActivity_HeuNetworkAdapter(network.getHueLamps());
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLampsReturned(JSONArray response) {

        //TODO fix to work with JSONARRAY
        ArrayList<HueLamp> list = new ArrayList<>();
        this.network.setHueLamps(list);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLampStateReturned() {

    }

    @Override
    public void onHueNetwortReteurned(HueNetwork network) {
        this.network = network;
        adapter.setDataSet(network.getHueLamps());
        adapter.notifyDataSetChanged();
    }
}
