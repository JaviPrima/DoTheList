package com.example.myapplication.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.entidades.Gasto;
import com.example.myapplication.sqlite.SQLite;
import com.example.myapplication.ui.ayudas.AyudaRegistrarFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.Calendar;

public class ModificarFragment extends Fragment{

    Calendar calendar;
    int year;
    int month;
    int day;
    String fechaGuardar;
    String fechaMostrar;
    String strImporte;

    EditText etCategoria;
    EditText etProducto;
    EditText etImporte;
    EditText etFecha;
    EditText etComentario;

    FloatingActionButton fabGuardar, fabCancelar;

    DatePickerDialog.OnDateSetListener dateSetListener;

    Gasto gasto;
    int id;

    public static ModificarFragment newInstance() {
        ModificarFragment fragment = new ModificarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_registrar, container, false);

        etCategoria  = (EditText) view.findViewById(R.id.etElemento1);
        etProducto   = (EditText) view.findViewById(R.id.etProductoServicio);
        etImporte    = (EditText) view.findViewById(R.id.etImporte);
        etFecha      = (EditText) view.findViewById(R.id.etFecha);
        etComentario = (EditText) view.findViewById(R.id.tmContenido);

        gasto = new Gasto();

        final SQLite sqlite = new SQLite(getActivity().getApplicationContext());
        MainActivity mainActivity = (MainActivity) getActivity();

        this.id = mainActivity.id;
        gasto = sqlite.consultaElementoID(id);

        etCategoria.setText(gasto.getDato0());
        etProducto.setText(gasto.getDato1());
        etImporte.setText(String.valueOf(gasto.getDato2()));

        String fecha = gasto.getDato3();
        String[] parts = fecha.split("/");
        String parteAño = parts[0]; // yyyy
        String parteMes = parts[1]; // mm
        String parteDia = parts[2]; // dd

        fecha = parteDia + "/" + parteMes + "/" + parteAño;

        etFecha.setText(fecha);
        fechaGuardar = gasto.getDato3();
        etComentario.setText(gasto.getDato4());

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpDialog = new DatePickerDialog(getContext(), dateSetListener, year, month, day);
                dpDialog.show();

                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;

                        // Muestra en el textView el formato dd/mm/yyyy y la almacena con el formato yyyy/mm/dd para poder ordenar por fecha en la consulta.
                        if(dayOfMonth <= 9 && month <= 9) {
                            fechaMostrar = "0" + dayOfMonth + "/0" + month + "/" +year;
                            fechaGuardar = year + "/0" + month + "/0" + dayOfMonth;
                        } else if(dayOfMonth <= 9 && month >= 10) {
                            fechaMostrar = "0" + dayOfMonth + "/" + month + "/" + year;
                            fechaGuardar = year + "/" + month + "/0" + dayOfMonth;
                        } else if(dayOfMonth >= 10 && month <= 9) {
                            fechaMostrar = dayOfMonth + "/0" + month + "/" + year;
                            fechaGuardar =  year + "/0" + month + "/" + dayOfMonth;
                        } else {
                            fechaMostrar =  dayOfMonth + "/" + month + "/" + year;
                            fechaGuardar =  year + "/" + month + "/" + dayOfMonth;
                        }
                        etFecha.setText(fechaMostrar);
                        // no almacena correctamente la fecha o no la lee
                    }
                };
            }
        });

        fabCancelar = view.findViewById(R.id.fabCancelar);
        fabCancelar.setVisibility(View.VISIBLE);
        fabCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cierra el teclado virtual
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etImporte.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etComentario.getWindowToken(), 0);

                Navigation.findNavController(getView()).navigate(R.id.consultarFragment);
            }
        });


        fabGuardar = view.findViewById(R.id.fabGuardar);
        fabGuardar.setImageResource(R.drawable.save_outline);
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCategoria.getText().toString().equals("") || etProducto.getText().toString().equals("") || etImporte.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(),"Algunos campos están vacíos", Toast.LENGTH_SHORT).show();

                } else {
                    DecimalFormat df = new DecimalFormat("#.00");
                    strImporte = df.format(Double.valueOf(etImporte.getText().toString()));
                    double douImporte = Double.valueOf(strImporte.replace(",", "."));


                    sqlite.actualizarGasto(id, etCategoria.getText().toString(), etProducto.getText().toString(), douImporte, fechaGuardar, etComentario.getText().toString());
                    Toast.makeText(getActivity().getApplicationContext(), "¡Gasto guardados!", Toast.LENGTH_SHORT).show();

                    etCategoria.getText().clear();
                    etProducto.getText().clear();
                    etImporte.getText().clear();
                    etFecha.getText().clear();
                    etComentario.getText().clear();

                    // Cierra el teclado virtual
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etImporte.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etComentario.getWindowToken(), 0);
                }
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getContext(),"AYUDA", Toast.LENGTH_SHORT).show();
            AyudaRegistrarFragment ayudaFragment = new AyudaRegistrarFragment();
            ayudaFragment.show(getChildFragmentManager(), "Fragment Ayuda");
        }

        return super.onOptionsItemSelected(item);
    }
}