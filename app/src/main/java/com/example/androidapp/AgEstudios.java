package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.Calendar;

public class AgEstudios extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText edtTitulo, edtFecha, edtHoraIni, edtHoraFin, edtDesc;
    private Button btnAgendar;
    RequestQueue requestQueue, requestQueue2;
    JsonObjectRequest jsonObjectRequest, jsonObjectRequest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudios);

        edtTitulo=findViewById(R.id.editTextTitle2);
        edtFecha=findViewById(R.id.editTextDate2);
        edtHoraIni=findViewById(R.id.editTextHour);
        edtHoraFin=findViewById(R.id.editTextHour3);
        edtDesc=findViewById(R.id.editTextMultiLine2);
        btnAgendar=findViewById(R.id.buttonCreate2);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue2 = Volley.newRequestQueue(getApplicationContext());

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
        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agendarEvento();
            }
        });
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
    public void agendarEvento(){
        String titulo, fecha, horaIni="", horaFin="", desc;
        if(edtTitulo.getText().toString().trim().equalsIgnoreCase("")){
            edtTitulo.setError("Debe ingresar un tíulo");
        }else if(edtFecha.getText().toString().trim().equalsIgnoreCase("")){
            edtFecha.setError("Seleccione una fecha");
        }else if(edtHoraIni.getText().toString().trim().equalsIgnoreCase("")){
            edtHoraIni.setError("Seleccione hora de inicio");
        }else if(edtHoraFin.getText().toString().trim().equalsIgnoreCase("")){
            edtHoraFin.setError("Seleccione hora de término");
        }else{
            titulo = edtTitulo.getText().toString();
            fecha = edtFecha.getText().toString();
            horaIni = edtHoraIni.getText().toString();
            horaFin = edtHoraFin.getText().toString();
            desc = edtDesc.getText().toString();
            titulo = titulo.replaceAll(" ", "%20");
            desc = desc.replaceAll(" ", "%20");
            int idTipo, idCategoria;
            idTipo = 1;
            idCategoria = 2;

            String url = "http://192.168.0.107:80/serviciosApp/servicioinsertareventos.php?titulo="+titulo+"&descripcion="+desc+"&fecha="+fecha+"&hinicio="+horaIni+"&hfinal="+horaFin+"&idTipo="+idTipo+"&idCategoria="+idCategoria;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
            requestQueue.add(jsonObjectRequest);

            /*
            DbHelper dbHelper = new DbHelper(this,"dbCheckp",null,1);
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            if(sqLiteDatabase!=null){
                ContentValues cvalues = new ContentValues();
                cvalues.put("titulo",titulo);
                cvalues.put("descripcion", desc);
                cvalues.put("fecha",fecha);
                cvalues.put("hinicio",horaIni);
                cvalues.put("hfinal",horaFin);
                cvalues.put("idTipo",1);
                cvalues.put("idCategoria",2);
                long nfilas = sqLiteDatabase.insert("tblEvento",null,cvalues);
                if(nfilas>0){
                    SQLiteDatabase insert2 = dbHelper.getWritableDatabase();
                    ContentValues cvalues2 = new ContentValues();
                    cvalues2.put("idUsuario",getIntent().getExtras().getInt("idUser"));
                    cvalues2.put("idEvento",RescatarId(titulo,fecha,horaIni,horaFin,1,2));
                    insert2.insert("tblUsuarioEvento",null,cvalues2);
                    Toast.makeText(this, "Evento registrado con éxito", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error al insertar", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Error al crear la bbdd", Toast.LENGTH_SHORT).show();
            }
            */
            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
        }
    }
    /*
    public int RescatarId(String t,String f, String hi, String hf, int idt, int idc){
        int idEvent=0;
        ArrayList<String> datos = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(this,"dbCheckp",null,1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM tblevento";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                if(t.equals(cursor.getString(1))&&f.equals(cursor.getString(3))&&
                        hi.equals(cursor.getString(4))&&hf.equals(cursor.getString(5))&&idt==cursor.getInt(6)&&
                        idc==cursor.getInt(7)){
                    idEvent=cursor.getInt(0);
                }
            }while(cursor.moveToNext());
        }
        return idEvent;
    }
    */
    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(this, "Error al sincronizar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Registro sincronizado con éxito", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("id", 0);

        String url2 = "http://192.168.0.107:80/serviciosApp/susuarioevento.php?idUser="+idUser;
        jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url2,null,this,this);
        requestQueue2.add(jsonObjectRequest2);
    }
}