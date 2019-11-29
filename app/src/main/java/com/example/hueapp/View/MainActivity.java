package com.example.hueapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hueapp.Controller.ApiInterface.NetworkListener;
import com.example.hueapp.Controller.ApiManager;
import com.example.hueapp.Controller.MainActivity_HeuNetworkAdapter;
import com.example.hueapp.Model.CentralVariables;
import com.example.hueapp.Model.HueNetwork;
import com.example.hueapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NetworkListener, SwipeRefreshLayout.OnRefreshListener {


    private MainActivity_HeuNetworkAdapter adapter;
    private ApiManager manager;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAPIManager();

        CentralVariables.getInstance().getSelectedNetwork();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        v.getContext(), APIConnectionSettings.class);
                v.getContext().startActivity(intent);
            }
        });

        this.recyclerView = findViewById(R.id.mainRecyclerview);
        this.refreshLayout = findViewById(R.id.refreshContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        this.recyclerView.setLayoutManager(new GridLayoutManager(
                this, 1, GridLayoutManager.VERTICAL, false
        ));
        this.adapter = new MainActivity_HeuNetworkAdapter(CentralVariables.getInstance().getSelectedNetwork().getHueLamps());
        this.recyclerView.setAdapter(adapter);

//        manager.getAllInfo(selectedNetwork, this);
//        adapter.notifyDataSetChanged();

    }


    private void getAPIManager()
    {
        this.manager = ApiManager.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAPIManager();
        manager.getAllInfo(CentralVariables.getInstance().getSelectedNetwork(), this);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHueNetworkError() {

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onHueNetworkAvailable(HueNetwork network) {
        CentralVariables.getInstance().setSelectedNetwork(network);
        adapter.setDataSet(network.getHueLamps());
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

        refreshLayout.setRefreshing(true);
        manager.getAllInfo(CentralVariables.getInstance().getSelectedNetwork(), this);
    }
}
