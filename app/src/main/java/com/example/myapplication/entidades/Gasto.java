package com.example.myapplication.entidades;

import java.io.Serializable;

public class Gasto implements Serializable {

    // Variables para enviar a la base de datos

    // Variables para el filtro definido - Categoria, Producto, Improte.
    private String dato0;
    private String dato1;
    private double dato2;
    // Variables añadidas la vista Detalles - Fecha, Comentario.
    private String dato3;
    private String dato4;

    private int id;

    // Para registrar y/o mostrar todos (seria en el fragment detalles)
    public Gasto(int ID, String categoria, String producto, double importe, String fecha, String comentario) {
        this.id = ID;
        this.dato0 = categoria;
        this.dato1 = producto;
        this.dato2 = importe;
        this.dato3 = fecha;
        this.dato4 = comentario;
    }

    // Para mostrar en RecycledView
    // Por defecto muestra Categoria, Producto, Importe
    public Gasto(String dato0, String dato1, double dato2) {
        this.dato0 = dato0;
        this.dato1 = dato1;
        this.dato2 = dato2;
    }

    public Gasto() {
    }

    public String getDato0() {
        return dato0;
    }

    public void setDato0(String dato0) {
        this.dato0 = dato0;
    }

    public String getDato1() {
        return dato1;
    }

    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }

    public double getDato2() {
        return dato2;
    }

    public void setDato2(double dato2) {
        this.dato2 = dato2;
    }

    public String getDato3() {
        return dato3;
    }

    public void setDato3(String dato3) {
        this.dato3 = dato3;
    }

    public String getDato4() {
        return dato4;
    }

    public void setDato4(String dato4) {
        this.dato4 = dato4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
