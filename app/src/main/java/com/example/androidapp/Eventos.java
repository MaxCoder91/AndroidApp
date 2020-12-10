package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Eventos extends AppCompatActivity {

    private Button btnCrearEventos;
    ArrayList<DaoEvento> listaEventos;
    RecyclerView recycler;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        btnCrearEventos= findViewById(R.id.buttonEvents2);
        listaEventos = new ArrayList<>();
        recycler = findViewById(R.id.recycler);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        cargarEventos();

        AdaptadorDatos adaptadorDatos = new AdaptadorDatos(listaEventos);
        recycler.setAdapter(adaptadorDatos);

        btnCrearEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Eventos.this, AgEventos.class);
                startActivity(intent);
            }
        });
    }

    public void cargarEventos(){
        String url = "http://192.168.0.107:80/serviciosApp/servicioeventospublicos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray jsonArray = response.getJSONArray("evento");
                    // recorremos la estructura JSON con un ciclo y vamos poblando el arreglo.
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject evento = jsonArray.getJSONObject(i);
                        listaEventos.add(new DaoEvento(evento.getInt("id"),evento.getString("titulo"),evento.getString("descripcion"),evento.getString("fecha"),evento.getString("hinicio"),evento.getString("hfinal"),evento.getInt("idTipo"),evento.getInt("idCategoria"),R.drawable.eventicon));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msjerror = error.getMessage();
                Toast.makeText(Eventos.this, "Error "+msjerror, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest); //ejecutamos la petición que está cargada en jsonObjectRequest.
    }
}