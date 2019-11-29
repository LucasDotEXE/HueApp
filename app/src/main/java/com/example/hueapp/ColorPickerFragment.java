package com.example.hueapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hueapp.Model.CentralVariables;
import com.example.hueapp.Model.HueLamp;
import com.example.hueapp.Model.JavaColor;
import com.madrapps.pikolo.ColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;



public class ColorPickerFragment extends Fragment {
    private ColorPicker colorPicker;
    private OnColorPickerFragmentInteractionListener mListener;
    private HueLamp hueLamp;


    public ColorPickerFragment() {
        // Required empty public constructor
    }

    public static ColorPickerFragment newInstance() {
        ColorPickerFragment fragment = new ColorPickerFragment();
        Bundle args = new Bundle();
        //Put arguments here
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hueLamp = getArguments().getParcelable(CentralVariables.HueLamp_Key);
            Log.d("Hue", "Colorpicker received args");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_colorpicker, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnColorPickerFragmentInteractionListener) {
            mListener = (OnColorPickerFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnColorPickerFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        colorPicker = getView().findViewById(R.id.colorpicker);
        if (this.hueLamp != null)
            colorPicker.setColor(hueLamp.getColor());
        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                //getView().setBackgroundColor(color);
                mListener.onColorSelect(color);
            }
        });
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
    public interface OnColorPickerFragmentInteractionListener {

        void onColorSelect(int color);
    }
}
