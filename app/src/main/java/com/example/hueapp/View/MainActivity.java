package com.example.hueapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hueapp.Controller.ApiListener;
import com.example.hueapp.Controller.ApiManager;
import com.example.hueapp.Controller.MainActivity_HeuNetworkAdapter;
import com.example.hueapp.Model.CentralVariables;
import com.example.hueapp.Model.HueNetwork;
import com.example.hueapp.R;
import com.example.hueapp.TestingHelpers.HueNetworkTestHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApiListener {

    private HueNetwork selectedNetwork;
    private MainActivity_HeuNetworkAdapter adapter;
    private ApiManager manager;

    private RecyclerView recyclerView;
    private HueNetwork network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.selectedNetwork = CentralVariables.getInstance().getNetwork();
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        v.getContext(), APIConnectionSettings.class);
                        intent.putExtra("SelectedNetwork", selectedNetwork);
                        v.getContext().startActivity(intent);
            }
        });

        this.manager = new ApiManager(this);
        manager.getAllInfo(selectedNetwork, this);

        this.recyclerView = findViewById(R.id.mainRecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(
                this,1,GridLayoutManager.VERTICAL, false
        ));

        this.adapter = new MainActivity_HeuNetworkAdapter(selectedNetwork.getHueLamps());
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();
    }

   /* @Override
    public void onLampsReturned(JSONArray response) {

        //TODO fix to work with JSONARRAY
        ArrayList<HueLamp> list = new ArrayList<>();
        this.network.setHueLamps(list);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        manager.getAllInfo(selectedNetwork, this);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLampStateReturned() {

    }

    @Override
    public void onHueNetworkReturned(HueNetwork network) {
        this.selectedNetwork = network;
        adapter.setDataSet(network.getHueLamps());
        adapter.notifyDataSetChanged();
    }
}
