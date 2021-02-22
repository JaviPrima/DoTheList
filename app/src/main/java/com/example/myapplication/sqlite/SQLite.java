package com.example.myapplication.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.entidades.Gasto;

import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper {

    private static final String nombresqlite = "contabilidad_gastos.db";
    private static final int version = 1;

    public static final String TABLA_GASTO = "CREATE TABLE GASTOS (ID INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT NOT NULL, producto TEXT NOT NULL, importe DOUBLE NOT NULL, fecha TEXT, comentario TEXT)";


    public SQLite(Context context) {
        super(context, nombresqlite, null, version);
    }

        @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_GASTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* String sentencia = "DROP TABLE IF EXISTS " + TABLA_GASTO;
        db.execSQL(sentencia);
        db.execSQL(TABLA_GASTO);*/
    }

    public void guardarGasto(String categoria, String producto, double importe, String fecha, String comentario) {
        SQLiteDatabase sqldb = getWritableDatabase();
        if(sqldb != null) {
            sqldb.execSQL("INSERT INTO GASTOS VALUES (null,'"+categoria+"','"+producto+"','"+importe+"', '"+fecha+"','"+comentario+"')");
            sqldb.close();
        }
    }

    public ArrayList consultaTotal() {
        ArrayList<Gasto> lista = new ArrayList<>();
        Gasto auxGasto;

        SQLiteDatabase sqldb = getReadableDatabase();
        String sentencia = "SELECT * FROM GASTOS ORDER BY fecha DESC";
        Cursor cursor = sqldb.rawQuery(sentencia, null);

        if(cursor.moveToFirst()) {  // Se puede sustituir por cursor!=null
            do {
                auxGasto = new Gasto();
                auxGasto.setId(cursor.getInt(0));
                auxGasto.setDato0(cursor.getString(1));
                auxGasto.setDato1(cursor.getString(2));
                auxGasto.setDato2(cursor.getDouble(3));
                auxGasto.setDato3(cursor.getString(4));
                auxGasto.setDato4(cursor.getString(5));

                lista.add(auxGasto);

            } while(cursor.moveToNext());
        }

        return lista;
    }


    public Gasto consultaID(int id) {
        Gasto gasto = new Gasto();

        SQLiteDatabase sqldb = getReadableDatabase();
        String sentencia = "SELECT * FROM GASTOS where ID="+ id +"";
        Cursor cursor = sqldb.rawQuery(sentencia, null);

        if(cursor.moveToFirst()) {  // Se puede sustituir por cursor!=null

            String fecha;

            gasto.setId(cursor.getInt(0));
            gasto.setDato0(cursor.getString(1));
            gasto.setDato1(cursor.getString(2));
            gasto.setDato2(cursor.getDouble(3));
            fecha = cursor.getString(4);
            if (fecha.equals("null")) { fecha = ""; }
            if(!fecha.equals("")) {
                String[] parts = fecha.split("/");
                String parteAño = parts[0]; // yyyy
                String parteMes = parts[1]; // mm
                String parteDia = parts[2]; // dd
                fecha = parteDia + "/" + parteMes + "/" + parteAño;
            }
            gasto.setDato3(fecha);

            gasto.setDato4(cursor.getString(5));
            return gasto;
        }
        return  null;
    }


    public Gasto consultaImporteTotal() {
        Gasto gasto = new Gasto();

        SQLiteDatabase sqldb = getReadableDatabase();
        String sentencia = "SELECT SUM(importe) FROM GASTOS";

        Cursor c = sqldb.rawQuery(sentencia,null);
        if(c.moveToFirst()) {
            do {
                gasto.setDato2(c.getDouble(0));
            } while(c.moveToNext());
        }
        c.close();
        sqldb.close();

        return gasto;
    }

    public ArrayList consultaElemento(String tipoElemento, String elemento) {

        ArrayList<Gasto> lista = new ArrayList<>();
        Gasto auxGasto;

        SQLiteDatabase sqldb = getReadableDatabase();
        String sentencia = "SELECT * FROM GASTOS where " + tipoElemento + " = '" + elemento + "' ORDER BY fecha DESC";
        Cursor cursor = sqldb.rawQuery(sentencia, null);

            if (cursor.moveToFirst()) {  // Se puede sustituir por cursor!=null
                do {
                    auxGasto = new Gasto();
                    auxGasto.setId(cursor.getInt(0));
                    auxGasto.setDato0(cursor.getString(1));
                    auxGasto.setDato1(cursor.getString(2));
                    auxGasto.setDato2(cursor.getDouble(3));
                    auxGasto.setDato3(cursor.getString(4));
                    auxGasto.setDato4(cursor.getString(5));

                    lista.add(auxGasto);

                } while (cursor.moveToNext());

        }
        return lista;
    }

    public Gasto consultaElementoID(int id) {

        Gasto gasto = new Gasto();
        String fecha = "";

        SQLiteDatabase sqldb = getReadableDatabase();
        String sentencia = "SELECT * FROM GASTOS where ID="+ id +"";
        Cursor cursor = sqldb.rawQuery(sentencia, null);

        if (cursor.moveToFirst()) {  // Se puede sustituir por cursor!=null

            gasto = new Gasto();
            gasto.setId(cursor.getInt(0));
            gasto.setDato0(cursor.getString(1));
            gasto.setDato1(cursor.getString(2));
            gasto.setDato2(cursor.getDouble(3));
            fecha = cursor.getString(4);
            if(fecha.equals("null")) { fecha = ""; }
            if(!fecha.equals("")) {
                String[] parts = fecha.split("/");
                String parteDia = parts[0]; // yyyy
                String parteMes = parts[1]; // mm
                String parteAño = parts[2]; // dd

                fecha = parteDia + "/" + parteMes + "/" + parteAño;
            }
            gasto.setDato3(fecha);
            gasto.setDato4(cursor.getString(5));
            return gasto;
        }
        return null;
    }

    public void eliminarGasto(int id) {
        SQLiteDatabase sqldb = getWritableDatabase();
        if(sqldb != null) {
            sqldb.execSQL("DELETE FROM GASTOS WHERE id = " + id + "");
            sqldb.close();
        }
    }

    public Gasto consultaImporteDeCategoria(String categoria) {
        Gasto gasto = new Gasto();

        SQLiteDatabase sqldb = getReadableDatabase();
        String sentencia = "SELECT SUM(importe) FROM GASTOS where categoria='" + categoria +"'";

        Cursor c = sqldb.rawQuery(sentencia,null);
        if(c.moveToFirst()) {
            do {
                gasto.setDato2(c.getDouble(0));
            } while(c.moveToNext());
        }
        c.close();
        sqldb.close();

        return gasto;
    }


    public void actualizarGasto(int id, String categoria, String producto, double importe, String fecha, String comentario) {
        SQLiteDatabase sqldb = getWritableDatabase();
        if(sqldb != null) {
            sqldb.execSQL("UPDATE GASTOS SET categoria ='"+categoria+"', producto = '"+producto+"', importe = '"+importe+"', fecha = '"+fecha+"', comentario = '"+comentario+"' where ID = '"+id+"'");
            sqldb.close();
        }
    }
}
