package com.example.myapplication.ui;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adaptadores.AdapterBase;
import com.example.myapplication.entidades.Gasto;
import com.example.myapplication.sqlite.SQLite;
import com.example.myapplication.ui.ayudas.AyudaConsultarFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConsultarFragment extends Fragment {

    AdapterBase adapterEstandar;
    RecyclerView recyclerViewGastos;
    ArrayList<Gasto> listaGastos;

    TextView tvGastadoNum;

    SQLite sqlite;
    MainActivity mainActivity;

    static int id;

    FloatingActionButton fabBuscar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultar, container, false);
        recyclerViewGastos = view.findViewById(R.id.recyclerView);
        sqlite = new SQLite(view.getContext());

        tvGastadoNum = view.findViewById(R.id.tvGastadoNum);
        DecimalFormat df = new DecimalFormat("#.00");
        tvGastadoNum.setText(df.format(sqlite.consultaImporteTotal().getDato2()).replace(".", ",") + " €");

        // Leer lista y llenar RecycledView
        leerLista();

        fabBuscar = view.findViewById(R.id.fabBuscar);
        fabBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.filtrarFragment);
            }
        });

        return view;
    }


    public void leerLista() {
        mainActivity = new MainActivity();
        listaGastos = new ArrayList<Gasto>();

        listaGastos = sqlite.consultaTotal();


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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getContext(),"AYUDA", Toast.LENGTH_SHORT).show();
            AyudaConsultarFragment ayudaFragment = new AyudaConsultarFragment();
            ayudaFragment.show(getChildFragmentManager(), "Fragment Ayuda");
        }

        return super.onOptionsItemSelected(item);
    }
}