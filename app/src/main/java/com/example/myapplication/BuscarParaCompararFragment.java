package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class BuscarParaCompararFragment extends Fragment {

    EditText etElemento1, etElemento2;

    public BuscarParaCompararFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compara, container, false);

        etElemento1  = (EditText) view.findViewById(R.id.etElemento1);
        etElemento2  = (EditText) view.findViewById(R.id.etElemento2);

        return view;
    }

}