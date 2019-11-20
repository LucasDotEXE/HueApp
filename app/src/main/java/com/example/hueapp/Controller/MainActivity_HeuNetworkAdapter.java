package com.example.hueapp.Controller;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hueapp.Model.HueLamp;
import com.example.hueapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity_HeuNetworkAdapter extends RecyclerView.Adapter<MainActivity_HeuNetworkAdapter.HeuLampViewHolder> {

    private ArrayList<HueLamp> dataSet;

    public MainActivity_HeuNetworkAdapter(ArrayList<HueLamp> dataSet) {
        this.dataSet = dataSet;
    }

    public void setDataSet(ArrayList<HueLamp> dataSet) {
        this.dataSet = dataSet;
    }

    public ArrayList<HueLamp> getDataSet() {
        return dataSet;
    }

    @NonNull
    @Override
    public HeuLampViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.heu_lamp_resyclerview_list_item, parent, false);
        return new HeuLampViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeuLampViewHolder holder, int position) {
        HueLamp hueLamp = dataSet.get(position);

        holder.productName.setText(hueLamp.getProductName());
        holder.name.setText(hueLamp.getName());
        if (hueLamp.isReachable()) {
            if (hueLamp.isOn()) {
                Picasso.get()
                        .load("https://img.icons8.com/ios-glyphs/30/000000/rgb-lamp.png")
                        .into(holder.imageAvailibility);
            } else {
                Picasso.get()
                        .load("https://img.icons8.com/material/24/000000/light--v1.png")
                        .into(holder.imageAvailibility);
            }
        } else {
            Log.e("LampError", "Lamp: " + hueLamp + "\nIsn't reachable");
        }

    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }

    public class HeuLampViewHolder extends RecyclerView.ViewHolder {

        public TextView productName;
        public TextView name;
        public ImageView imageAvailibility;

        public HeuLampViewHolder(View itemView) {
            super(itemView);
            this.productName = (TextView) itemView.findViewById(R.id.hueLampItemProductName);
            this.name = (TextView) itemView.findViewById(R.id.hueLampItemName);
            this.imageAvailibility = (ImageView) itemView.findViewById(R.id.hueLampItemImage);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(
//                            v.getContext(),
//                            DetailedBlindWallView.class);
//                    Log.i("POSITION", "" + BlindwallsViewHolder.super.getAdapterPosition());
//
//                    BlindWall blindWall = dataset.get(BlindwallsViewHolder.super.getAdapterPosition());
//                    intent.putExtra("Wall_OBJECT", blindWall);
//
//                    v.getContext().startActivity(intent);
//                }
//            });
        }
    }

}
