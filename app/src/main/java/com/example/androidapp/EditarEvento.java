package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;

public class EditarEvento extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText edtTitulo, edtFecha, edtHoraIni, edtHoraFin, edtDesc;
    private Button btnActualizar, btnEliminar;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        edtTitulo=findViewById(R.id.editTextTitle4);
        edtFecha=findViewById(R.id.editTextDate4);
        edtHoraIni=findViewById(R.id.editTextHour6);
        edtHoraFin=findViewById(R.id.editTextHour7);
        edtDesc=findViewById(R.id.editTextMultiLine4);
        btnActualizar=findViewById(R.id.buttonEdit);
        btnEliminar=findViewById(R.id.buttonDelete);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        String titulo, descripcion,fecha, hinicio, hfinal;

        titulo = getIntent().getExtras().getString("titulo");
        descripcion = getIntent().getExtras().getString("descripcion");
        fecha = getIntent().getExtras().getString("fecha");
        hinicio = getIntent().getExtras().getString("hinicio");
        hfinal = getIntent().getExtras().getString("hfinal");

        edtTitulo.setText(titulo);
        edtFecha.setText(fecha);
        edtHoraIni.setText(hinicio);
        edtHoraFin.setText(hfinal);
        edtDesc.setText(descripcion);


        edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturarFecha();
            }
        });
        edtHoraIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturarHoraIni();
            }
        });
        edtHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturarHoraFin();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo,descripcion,fecha,hinicio,hfinal,id;
                titulo = edtTitulo.getText().toString();
                descripcion = edtDesc.getText().toString();
                fecha = edtFecha.getText().toString();
                hinicio = edtHoraIni.getText().toString();
                hfinal = edtHoraFin.getText().toString();
                id = getIntent().getExtras().getString("id");

                Actualizar(titulo,descripcion,fecha,hinicio,hfinal,id);
                onBackPressed();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id;
                id = getIntent().getExtras().getString("id");
                Eliminar(id);
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void capturarFecha(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y1, int m1, int d1) {
                String fdia, fmes;

                if(d1<10){
                    fdia = "0"+d1;
                }else{
                    fdia = String.valueOf(d1);
                }
                if((m1+1)<10){
                    fmes = "0"+(m1+1);
                }else{
                    fmes = String.valueOf(m1+1);
                }
                String fecha = y1+"-"+fmes+"-"+fdia;
                edtFecha.setText(fecha);
            }
        },year,month,day);
        dpDialog.show();
    }
    public void capturarHoraIni(){
        final Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        TimePickerDialog tpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                String fhora, fmin;

                if(hourOfDay<10){
                    fhora = "0"+hourOfDay;
                }else{
                    fhora = String.valueOf(hourOfDay);
                }
                if(minutes<10){
                    fmin = "0"+minutes;
                }else{
                    fmin = String.valueOf(minutes);
                }
                String hora=fhora+":"+fmin;
                edtHoraIni.setText(hora);
            }
        },hora,minutos,false);
        tpDialog.show();
    }
    public void capturarHoraFin(){
        final Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        TimePickerDialog tpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                String fhora, fmin;

                if(hourOfDay<10){
                    fhora = "0"+hourOfDay;
                }else{
                    fhora = String.valueOf(hourOfDay);
                }
                if(minutes<10){
                    fmin = "0"+minutes;
                }else{
                    fmin = String.valueOf(minutes);
                }
                String hora=fhora+":"+fmin;
                edtHoraFin.setText(hora);
            }
        },hora,minutos,false);
        tpDialog.show();
    }
    public void Actualizar(String titulo, String descripcion,String fecha, String hinicio, String hfinal, String id) {

        titulo = titulo.replaceAll(" ", "%20");
        descripcion = descripcion.replaceAll(" ", "%20");

        String url = "http://192.168.0.107:80/serviciosApp/servicioactualizar.php?titulo="+titulo+"&descripcion="+descripcion+"&fecha="+fecha+"&hinicio="+hinicio+"&hfinal="+hfinal+"&id="+id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
        requestQueue.add(jsonObjectRequest);

        /*
        DbHelper dbHelper = new DbHelper(this,"dbCheckp",null,1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cvalues = new ContentValues();

        cvalues.put("titulo",titulo);
        cvalues.put("descripcion",descripcion);
        cvalues.put("fecha",fecha);
        cvalues.put("hinicio",hinicio);
        cvalues.put("hfinal",hfinal);

        int nfilas = sqLiteDatabase.update("tblEvento",cvalues,"id="+id,null);
        if(nfilas==1){
            Toast.makeText(this, "Registro actualizado con éxito", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
        */
    }

    public void Eliminar(String id){

        String url = "http://192.168.0.107:80/serviciosApp/servicioeliminar.php?id="+id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
        requestQueue.add(jsonObjectRequest);

        /*
        DbHelper dbHelper = new DbHelper(this,"dbCheckp",null,1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int nfilas = sqLiteDatabase.delete("tblEvento", "id="+id,null);
        Toast.makeText(this, "Evento eliminado con éxito", Toast.LENGTH_SHORT).show();
         */
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(this, "Error al sincronizar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Registro sincronizado con éxito", Toast.LENGTH_SHORT).show();
    }
}