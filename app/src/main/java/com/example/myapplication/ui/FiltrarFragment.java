package com.example.myapplication.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FiltrarFragment extends Fragment {

    RadioButton buscar, categoria, producto, mayor, menor;
    EditText elemento;

    FloatingActionButton fabHecho;

    ConsultarFragment consultarFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        consultarFragment = new ConsultarFragment();

        View view = inflater.inflate(R.layout.fragment_filtrar, container, false);

        fabHecho  = (FloatingActionButton) view.findViewById(R.id.fabHecho);
        categoria = (RadioButton) view.findViewById(R.id.rbCategoria);
        producto  = (RadioButton) view.findViewById(R.id.rbProducto);
        mayor     = (RadioButton) view.findViewById(R.id.rbMayorMenor);
        menor     = (RadioButton) view.findViewById(R.id.rbMenorMayor);
        elemento  = (EditText) view.findViewById(R.id.etElemento);


        fabHecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrado();
                Navigation.findNavController(getView()).navigate(R.id.consultarFragmentFiltro);

                // Cierra el teclado virtual
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(elemento.getWindowToken(), 0);
            }
        });

        return view;
    }


    public void filtrado() {

        // Elemento - 0 defecto, 1 categoria, 2 producto
        if(categoria.isChecked()) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.tipoElemento = "categoria";
        }
        if(producto.isChecked()) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.tipoElemento = "producto";
        }
        if(mayor.isChecked()) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.tipoOrdenar = 1;
        }
        if(menor.isChecked()) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.tipoOrdenar = 2;
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.elemento = String.valueOf(elemento.getText());

/*
        // Ordenar - 0 defecto, 1 mayor a menor, 2 menor a mayor        // NO FUNCIONA
        mayor.setEnabled(false);
        menor.setEnabled(false);
        if(ordenar.isChecked()) {
            Toast.makeText(getActivity().getApplicationContext(), "¡texto de prueba!", Toast.LENGTH_SHORT).show();
            mayor.setEnabled(true);
            menor.setEnabled(true);
            if(mayor.isChecked()) {
                mainActivity.tipoOrdenar = 1;
            }
            if(menor.isChecked()) {
                mainActivity.tipoOrdenar = 2;
            }
        }*/
    }
}
