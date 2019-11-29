package com.example.hueapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hueapp.Model.CentralVariables;
import com.example.hueapp.Model.HueLamp;
import com.example.hueapp.View.APIConnectionSettings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HueLampInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HueLampInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HueLampInfoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private HueLamp hueLamp;
    private TextView lampID;
    //private Button buttononoff;
    //private TextView lampHue;
    private FloatingActionButton floatingActionButtonOnOff;

    public HueLampInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HueLampInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HueLampInfoFragment newInstance(HueLamp hueLamp) {
        HueLampInfoFragment fragment = new HueLampInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("lamp", hueLamp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hueLamp = getArguments().getParcelable(CentralVariables.HueLamp_Key);
            Log.d("Hue", "HueInfo received args. Hue:  " +   String.valueOf(hueLamp.getHue()));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_hue_lamp_info, container, false);
        lampID = view.findViewById(R.id.lamp_id);
        //buttononoff = view.findViewById(R.id.button_on_off);
        //lampHue = view.findViewById(R.id.lamp_hue);
        floatingActionButtonOnOff = view.findViewById(R.id.onoffbutton);
        floatingActionButtonOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hueLamp.setOn(!hueLamp.isOn());
                mListener.OnOnOff();
                if (hueLamp.isOn())
                    floatingActionButtonOnOff.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                else
                    floatingActionButtonOnOff.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorLampOff)));

            }
        });

        if(this.hueLamp != null) {
            lampID.setText(String.valueOf("Lamp " + hueLamp.getId()));
            //lampID.setText("test2");
            //buttononoff.setText(hueLamp.isOn());
            Log.d("Hue", String.valueOf(hueLamp.getBrightness()));
            //lampHue.setText(String.valueOf(hueLamp.getHue()));
        }
        return view;
    }

   /* // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnOnOff();
    }
}
