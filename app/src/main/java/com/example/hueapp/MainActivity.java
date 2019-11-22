package com.example.hueapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    public void onLampsReturned() {

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
