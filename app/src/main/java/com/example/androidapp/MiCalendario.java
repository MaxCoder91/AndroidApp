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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MiCalendario extends AppCompatActivity {
    ListView lvEventos;
    Button btnVolver;
    RequestQueue requestQueue;

    //Método de refresh.(AutoPostBack)
    @Override
    protected void onPostResume() {
        super.onPostResume();
        //inflateView();
        cargarDatos();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_calendario);

        lvEventos=findViewById(R.id.lvEventos);
        btnVolver = findViewById(R.id.btnVolver);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //inflateView();
        cargarDatos();

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


    public void cargarDatos(){

        String url = "http://192.168.0.107:80/serviciosApp/serviciolistareventos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    //vamos a recibir lo que me envia el servicio o web service.
                    JSONArray jsonArray = response.getJSONArray("evento");
                    //necesito un array para llenarlo con los datos devueltos por elservicio, response.
                    ArrayList<String> datos = new ArrayList<>();
                    // recorremos la estructura JSON con un ciclo y vamos poblando el arreglo.
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject evento = jsonArray.getJSONObject(i);
                        int id = evento.getInt("id");
                        String titulo = evento.getString("titulo");
                        String descripcion = evento.getString("descripcion");
                        String fecha = evento.getString("fecha");
                        String hinicio = evento.getString("hinicio");
                        String hfinal = evento.getString("hfinal");
                        String item = id+" / "+titulo+" / "+descripcion+" / "+fecha+" / "+hinicio+" / "+hfinal;
                        datos.add(item);
                    }
                    //fin del recorrido y carga de datos en un arraylist.

                    //armamos el array Adaptar.
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, datos);
                    //seteamos el array adapter.
                    lvEventos.setAdapter(arrayAdapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msjerror = error.getMessage();
                Toast.makeText(MiCalendario.this, "Error "+msjerror, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest); //ejecutamos la petición que está cargada en jsonObjectRequest.
    }









    public ArrayList<String>ListarEventos(){

        ArrayList<String>datos= new ArrayList<>();
        DbHelper dbHelper = new DbHelper( this,"dbCheckp",null,1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int idUser = getIntent().getExtras().getInt("idUser");
        String query = "SELECT DISTINCT * FROM tblEvento INNER JOIN tblUsuarioEvento ON (tblevento.id = tblusuarioevento.idEvento) WHERE tblUsuarioEvento.idUsuario="+idUser;
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