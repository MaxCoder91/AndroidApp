package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditarEvento extends AppCompatActivity {

    private EditText edtTitulo, edtFecha, edtHoraIni, edtHoraFin, edtDesc, edtAlarma;
    private Button btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        edtTitulo=findViewById(R.id.editTextTitle4);
        edtFecha=findViewById(R.id.editTextDate4);
        edtHoraIni=findViewById(R.id.editTextHour6);
        edtHoraFin=findViewById(R.id.editTextHour7);
        edtDesc=findViewById(R.id.editTextMultiLine4);
        edtAlarma=findViewById(R.id.editTextAlarm4);
        btnActualizar=findViewById(R.id.buttonEdit);
        btnEliminar=findViewById(R.id.buttonDelete);

        String titulo, descripcion,fecha, hinicio, hfinal,id,idTipo,idCategoria;

        id = getIntent().getExtras().getString("id");
        titulo = getIntent().getExtras().getString("titulo");
        descripcion = getIntent().getExtras().getString("descripcion");
        fecha = getIntent().getExtras().getString("fecha");
        hinicio = getIntent().getExtras().getString("hinicio");
        hfinal = getIntent().getExtras().getString("hfinal");
        idTipo = getIntent().getExtras().getString("idTipo");
        idCategoria = getIntent().getExtras().getString("idCategoria");

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
                String titulo,descripcion,fecha,hinicio,hfinal,id,idTipo,idCategoria;
                titulo = edtTitulo.getText().toString();
                descripcion = edtDesc.getText().toString();
                fecha = edtFecha.getText().toString();
                hinicio = edtHoraIni.getText().toString();
                hfinal = edtHoraFin.getText().toString();
                id = getIntent().getExtras().getString("id");
                idTipo = getIntent().getExtras().getString("idTipo");
                idCategoria = getIntent().getExtras().getString("idCategoria");
                Actualizar(titulo,descripcion,fecha,hinicio,hfinal,id,idTipo,idCategoria);
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
                String fecha = fdia+"-"+fmes+"-"+y1;
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
    public void Actualizar(String titulo, String descripcion,String fecha, String hinicio, String hfinal, String id, String idTipo, String idCategoria) {
        DbHelper dbHelper = new DbHelper(this,"dbCheckp",null,1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cvalues = new ContentValues();

        cvalues.put("titulo",titulo);
        cvalues.put("descripcion",descripcion);
        cvalues.put("fecha",fecha);
        cvalues.put("hinicio",hinicio);
        cvalues.put("hfinal",hfinal);

        int nfilas = sqLiteDatabase.update("tblEvento",cvalues,"id="+id,null);
        Toast.makeText(this, "Update "+nfilas, Toast.LENGTH_SHORT).show();

    }
}