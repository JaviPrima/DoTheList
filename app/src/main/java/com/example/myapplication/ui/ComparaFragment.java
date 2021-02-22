package com.example.myapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entidades.Gasto;
import com.example.myapplication.sqlite.SQLite;
import com.example.myapplication.ui.ayudas.AyudaCompararFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ComparaFragment extends Fragment {

    PieChart pieChart;

    FloatingActionButton fabComparar;

    ArrayList<PieEntry> alGPastel;

    EditText etElemento1, etElemento2, etElemento3, etElemento4, etElemento5;

    View view;

    SQLite sqlite;

    Gasto gasto1, gasto2, gasto3, gasto4, gasto5;

    FloatingActionButton fabActualizar, fabNuevoCampo;
    Button add3, add4, add5;

    String strElemento1 = null, strElemento2 = null, strElemento3 = null, strElemento4 = null, strElemento5 = null;

    float floElemento1, floElemento2, floElemento3, floElemento4, floElemento5;

    boolean bElemento1, bElemento2, bElemento3, bElemento4, bElemento5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_compara, container, false);

        etElemento1  = (EditText) view.findViewById(R.id.etElemento1);
        etElemento2  = (EditText) view.findViewById(R.id.etElemento2);

        etElemento3  = (EditText) view.findViewById(R.id.etElemento3);
        etElemento4  = (EditText) view.findViewById(R.id.etElemento4);
        etElemento5  = (EditText) view.findViewById(R.id.etElemento5);

        add3 = (Button) view.findViewById(R.id.add3);
        add4 = (Button) view.findViewById(R.id.add4);
        add5 = (Button) view.findViewById(R.id.add5);



        pieChart = view.findViewById(R.id.cpGastoIndividual);

        fabActualizar = view.findViewById(R.id.fabActualizar);
        fabActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strElemento1 = etElemento1.getText().toString();
                strElemento2 = etElemento2.getText().toString();
                strElemento3 = etElemento3.getText().toString();
                strElemento4 = etElemento4.getText().toString();
                strElemento5 = etElemento5.getText().toString();

                if (strElemento1 != null && strElemento2 != null) {
                    sqlite = new SQLite(view.getContext());
                    gasto1 = sqlite.consultaImporteDeCategoria(strElemento1);       // Compactar
                    gasto2 = sqlite.consultaImporteDeCategoria(strElemento2);

                    if (strElemento3 != null) {
                        gasto3 = sqlite.consultaImporteDeCategoria(strElemento3);

                        if (strElemento4!= null) {
                            gasto4 = sqlite.consultaImporteDeCategoria(strElemento4);

                            if (strElemento5 != null) {
                                gasto5 = sqlite.consultaImporteDeCategoria(strElemento5);
                            }
                        }
                    }
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etElemento2.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etElemento3.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etElemento4.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etElemento5.getWindowToken(), 0);


                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "¡Necesitas al menos dos elementos!", Toast.LENGTH_SHORT).show();
                }

                floElemento1 = (float) gasto1.getDato2();
                floElemento2 = (float) gasto2.getDato2();
                floElemento3 = (float) gasto3.getDato2();
                if (strElemento1 == null) { floElemento3 = 0; }
                floElemento4 = (float) gasto4.getDato2();
                if (strElemento4 == null) { floElemento4 = 0; }
                floElemento5 = (float) gasto5.getDato2();
                if (strElemento5 == null) { floElemento5 = 0; }

                mostrarGrafico(floElemento1, floElemento2, floElemento3, floElemento4, floElemento5);

            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCampo();
            }
        });
        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCampo();
            }
        });
        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCampo();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        etElemento1.setEnabled(true);
        etElemento1.setCursorVisible(true);
        bElemento1 = true;

        etElemento2.setEnabled(true);
        etElemento2.setCursorVisible(true);
        bElemento2 = true;

        etElemento3.setEnabled(false);
        etElemento3.setCursorVisible(false);
        bElemento3 = false;

        etElemento4.setEnabled(false);
        etElemento4.setCursorVisible(false);
        bElemento4 = false;

        etElemento5.setEnabled(false);
        etElemento5.setCursorVisible(false);
        bElemento5 = false;
    }

    private void mostrarGrafico(float importe1, float importe2, float importe3, float importe4, float importe5) {
        Description description = new Description();

        ArrayList<PieEntry> alGPastel = new ArrayList<>();

        alGPastel.add(new PieEntry(importe1));
        alGPastel.add(new PieEntry(importe2));
        alGPastel.add(new PieEntry(importe3));
        alGPastel.add(new PieEntry(importe4));
        alGPastel.add(new PieEntry(importe5));

        PieDataSet pieDataSet = new PieDataSet(alGPastel, "Leyenda: Categorías");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);

    }


    private void mostrarCampo() {       // Buscar maneras de compactar el codigo.

        if(bElemento1 == false) {
            etElemento1.setFocusable(true);
            etElemento1.setEnabled(true);
            etElemento1.setCursorVisible(true);
            bElemento1 = true;
        }
        else if(bElemento2 == false) {
            etElemento2.setFocusable(true);
            etElemento2.setEnabled(true);
            etElemento2.setCursorVisible(true);
            bElemento2 = true;
        }
        else if(bElemento3 == false) {
            etElemento3.setFocusable(true);
            etElemento3.setEnabled(true);
            etElemento3.setCursorVisible(true);
            bElemento3 = true;
            add3.setVisibility(view.INVISIBLE);
            add4.setVisibility(view.VISIBLE);
        }
        else if(bElemento4 == false) {
            etElemento4.setFocusable(true);
            etElemento4.setEnabled(true);
            etElemento4.setCursorVisible(true);
            bElemento4 = true;
            add4.setVisibility(view.INVISIBLE);
            add5.setVisibility(view.VISIBLE);
        }
        else if(bElemento5 == false) {

            etElemento5.setFocusable(true);
            etElemento5.setEnabled(true);
            etElemento5.setCursorVisible(true);
            bElemento5 = true;
            add5.setVisibility(view.INVISIBLE);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getContext(),"AYUDA", Toast.LENGTH_SHORT).show();
            AyudaCompararFragment ayudaFragment = new AyudaCompararFragment();
            ayudaFragment.show(getChildFragmentManager(), "Fragment Ayuda");
        }

        return super.onOptionsItemSelected(item);
    }
}