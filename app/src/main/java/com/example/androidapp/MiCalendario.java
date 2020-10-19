package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MiCalendario extends AppCompatActivity {
    ListView lvEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_calendario);

        lvEventos=findViewById(R.id.lvEventos);

        inflateView();
    }

    public ArrayList<String>ListarEventos(){
        ArrayList<String>datos= new ArrayList<>();
        DbHelper dbHelper = new DbHelper( this,"dbCheckp",null,1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM tblEvento";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                String titulo = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String fecha = cursor.getString(3);
                String hinicio = cursor.getString(4);
                String hfinal = cursor.getString(5);
                //int idTipo = cursor.getInt(6);
                //int idCategoria = cursor.getInt(7);
                String item = titulo+" "+fecha+" De: "+hinicio+" A: "+hfinal;
                datos.add(item);
            }while (cursor.moveToNext());
        }
        return datos;
    }
    public void inflateView(){
        ArrayList<String>origenDatos = ListarEventos();
        ArrayAdapter<String>arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,origenDatos);
        lvEventos.setAdapter(arrayAdapter);
    }
}