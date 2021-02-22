package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adaptadores.AdapterBase;
import com.example.myapplication.entidades.Gasto;
import com.example.myapplication.sqlite.SQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ConsultarFragmentFiltro extends Fragment {

    AdapterBase adapterEstandar;
    RecyclerView recyclerViewGastos;
    ArrayList<Gasto> listaGastos;

    TextView tvGastadoNum, tvGastadoFiltrado;

    SQLite sqlite;
    Gasto gasto;

    FloatingActionButton fabBuscar;

    static int id;

    static String tipoElemento;
    static int tipoOrdenar;
    static String elemento;

    static String categoria;
    static String producto;
    static String importe;
    static String fecha;
    static String comentario;


    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_consultar, container, false);
        recyclerViewGastos = view.findViewById(R.id.recyclerView);
        sqlite = new SQLite(view.getContext());

        MainActivity mainActivity = (MainActivity) getActivity();
        this.tipoElemento = mainActivity.tipoElemento;
        elemento = mainActivity.elemento;

        this.tipoOrdenar = mainActivity.tipoOrdenar;
        tipoOrdenar = mainActivity.tipoOrdenar;

        listaGastos = new ArrayList<Gasto>();
        listaGastos = sqlite.consultaElemento(tipoElemento, elemento);

        double importeTotalFiltrado = 0;
        for(int i = 0; i < listaGastos.size(); i++) {
            importeTotalFiltrado = importeTotalFiltrado + listaGastos.get(i).getDato2();
        }

        tvGastadoNum = view.findViewById(R.id.tvGastadoNum);
        DecimalFormat df = new DecimalFormat("#.00");
        tvGastadoNum.setText(String.valueOf(df.format(importeTotalFiltrado)).replace(".", ",") + " €");

        // Leer lista y llenar RecycledView
        leerLista();

        fabBuscar = view.findViewById(R.id.fabBuscar);
        fabBuscar.setImageResource(R.drawable.close_circle_outline);
        fabBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.consultarFragment);
            }
        });

        return view;
    }

    public void leerLista() {
        listaGastos = new ArrayList<Gasto>();

        listaGastos = sqlite.consultaElemento(tipoElemento, elemento);

        if(tipoOrdenar == 1) {
            Collections.sort(listaGastos, new Comparator<Gasto>() {
                @Override
                public int compare(Gasto g1, Gasto g2) {
                    return new Double(g1.getDato2()).compareTo(new Double(g2.getDato2()));
                }
            });
        }
        if(tipoOrdenar == 2) {
            Collections.sort(listaGastos, new Comparator<Gasto>() {
                @Override
                public int compare(Gasto g1, Gasto g2) {
                    return new Double(g2.getDato2()).compareTo(new Double(g1.getDato2()));
                }
            });
        }

        MainActivity mainActivity = (MainActivity) getActivity();
        tipoElemento = mainActivity.tipoElemento;
        elemento = mainActivity.elemento;

        // Carga la ArrayList listaGastos en el RecyclerView y muestra estos datos mediante un adaptador
        recyclerViewGastos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterEstandar = new AdapterBase(getContext(), listaGastos);
        recyclerViewGastos.setAdapter(adapterEstandar);

        // Permite interactuar con los elementos de la lista (con los gastos)
        adapterEstandar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Texto superpuesto al fragment indicando el nombre del producto seleccionado.
                String dato1 = listaGastos.get(recyclerViewGastos.getChildAdapterPosition(v)).getDato1();
                Toast.makeText(getContext(), String.valueOf(dato1), Toast.LENGTH_SHORT).show();

                // Pasar la id del gasto de la lista al fragment detalles y para que haga una consulta total de ese gasto
                id = listaGastos.get(recyclerViewGastos.getChildAdapterPosition(v)).getId();

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.id = id;

                // Abre el fragment detallesFragment en el que se visualizan mas detalles sobre el gasto seleccionado
                Navigation.findNavController(getView()).navigate(R.id.detallesFragment);

                listaGastos.clear();
            }
        });
    }
}