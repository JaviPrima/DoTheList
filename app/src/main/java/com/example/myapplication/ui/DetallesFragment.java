package com.example.myapplication.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.entidades.Gasto;
import com.example.myapplication.sqlite.SQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class DetallesFragment extends Fragment {

    TextView categoria;
    TextView producto;
    TextView importe;
    TextView fecha;
    TextView comentario;

    Gasto gasto;
    SQLite sqlite;
    int id;
    View view;

    FloatingActionButton fabEliminar, fabEditar;


    public static DetallesFragment newInstance() {
        DetallesFragment fragment = new DetallesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detalles, container, false);
        categoria = view.findViewById(R.id.tvCategoria);
        producto = view.findViewById(R.id.tvProducto);
        importe = view.findViewById(R.id.tvImporte);
        fecha = view.findViewById(R.id.tvFecha);
        comentario = view.findViewById(R.id.tvComentario);

        fabEliminar = view.findViewById(R.id.fabEliminar);
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id;

                MainActivity mainActivity = (MainActivity) getActivity();
                id = mainActivity.id;
                sqlite.eliminarGasto(id);

                Navigation.findNavController(getView()).navigate(R.id.consultarFragment);
            }
        });

        fabEditar = view.findViewById(R.id.fabEditar);
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getView()).navigate(R.id.modificarFragment);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity mainActivity = (MainActivity) getActivity();
        this.id = mainActivity.id;

        sqlite = new SQLite(view.getContext());
        gasto = sqlite.consultaID(id);  // ID recibido del RecycledView. Devuelve todos los datos correspodnientes al gasto con esta ID.

        String strFecha, strComentario;

        strFecha = gasto.getDato3();
        strComentario = gasto.getDato4();

        if (strFecha.equals("")) {
            strFecha = "Sin fecha.";
        }
        if (strComentario.equals("")) {
            strComentario = "Sin comentario.";
        }

        DecimalFormat df = new DecimalFormat("#.00");
        String strImporte;

        categoria.setText(gasto.getDato0());
        producto.setText(gasto.getDato1());
        strImporte = df.format(gasto.getDato2()).replace(".", ",");
        importe.setText(strImporte);
        fecha.setText(strFecha);
        comentario.setText(strComentario);
    }

}