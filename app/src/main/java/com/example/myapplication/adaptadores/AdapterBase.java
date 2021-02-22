package com.example.myapplication.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entidades.Gasto;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterBase extends RecyclerView.Adapter<AdapterBase.ViewHolder> implements OnClickListener {

    LayoutInflater inflater;
    ArrayList<Gasto> model;

    // OnClickListener
    private OnClickListener listener;

    public AdapterBase(Context context, ArrayList<Gasto> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_gastos, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Por defecto Categoria, Producto, Importe
        String dato0Categoria = model.get(position).getDato0();
        String dato1Producto = model.get(position).getDato1();
        double dato2Importe = model.get(position).getDato2();

        DecimalFormat df = new DecimalFormat("#.00");
        String strDato2Importe = df.format(dato2Importe).replace(".", ",");

        holder.dato0.setText(dato0Categoria);
        holder.dato1.setText(dato1Producto);
        holder.dato2.setText(strDato2Importe);
    }

    @Override
    public int getItemCount() {

        return model.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dato0, dato1, dato2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dato0 = itemView.findViewById(R.id.dato0);
            dato1 = itemView.findViewById(R.id.dato1);
            dato2 = itemView.findViewById(R.id.dato2);
        }
    }

}
