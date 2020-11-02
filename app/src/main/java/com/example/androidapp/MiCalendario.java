package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MiCalendario extends AppCompatActivity {
    ListView lvEventos;
    Button btnVolver;

    //Método de refresh.(AutoPostBack)
    @Override
    protected void onPostResume() {
        super.onPostResume();
        inflateView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_calendario);

        lvEventos=findViewById(R.id.lvEventos);
        btnVolver = findViewById(R.id.btnVolver);

        inflateView();

        lvEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item,titulo,descripcion,fecha,hinicio,hfinal,id,idTipo,idCategoria;
                item = lvEventos.getItemAtPosition(i).toString();
                Toast.makeText(MiCalendario.this, "Item recuperado "+item, Toast.LENGTH_SHORT).show();



                id = item.split("/")[0];
                titulo = item.split("/")[1];
                descripcion = item.split("/")[2];
                fecha = item.split("/")[3];
                hinicio = item.split("/")[4];
                hfinal = item.split("/")[5];

                Intent intent = new Intent(MiCalendario.this, EditarEvento.class);
                intent.putExtra("id",id);
                intent.putExtra("titulo", titulo);
                intent.putExtra("descripcion",descripcion);
                intent.putExtra("fecha",fecha);
                intent.putExtra("hinicio",hinicio);
                intent.putExtra("hfinal",hfinal);

                intent.putExtra("idUser",getIntent().getExtras().getInt("idUser"));

                startActivity(intent);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiCalendario.this, MenuPrincipal.class);
                intent.putExtra("idUser",getIntent().getExtras().getInt("idUser"));
                startActivity(intent);
            }
        });
    }

    public ArrayList<String>ListarEventos(){
        ArrayList<String>datos= new ArrayList<>();
        DbHelper dbHelper = new DbHelper( this,"dbCheckp",null,1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM tblEvento";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String fecha = cursor.getString(3);
                String hinicio = cursor.getString(4);
                String hfinal = cursor.getString(5);
                String item = id+" / "+titulo+" / "+descripcion+" / "+fecha+" / "+hinicio+" / "+hfinal;
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