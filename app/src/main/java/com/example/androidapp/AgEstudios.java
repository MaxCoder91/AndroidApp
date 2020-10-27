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

public class AgEstudios extends AppCompatActivity {

    private EditText edtTitulo, edtFecha, edtHoraIni, edtHoraFin, edtDesc;
    private Button btnAgendar;

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
                    Toast.makeText(this, "Evento registrado con éxito", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error al insertar", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Error al crear la bbdd", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
        }
    }
}