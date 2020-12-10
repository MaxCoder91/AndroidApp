package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {

    private Button btnEventos, btnTrabajo, btnEstudio, btnOtros;
    private TextView tvCerrarSesion, MiCalendario, Usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("nombre", "null");
        String lastname = sharedPreferences.getString("apellido", "null");

        btnEventos=findViewById(R.id.buttonEvents);
        btnTrabajo=findViewById(R.id.buttonJobs);
        btnEstudio=findViewById(R.id.buttonStudy);
        btnOtros=findViewById(R.id.buttonOthers);
        tvCerrarSesion=findViewById(R.id.textViewClose);
        MiCalendario=findViewById((R.id.calendarIcon));
        Usuario=findViewById(R.id.textViewName);

        Usuario.setText(name+" "+lastname);

        btnEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Eventos.class);
                startActivity(intent);
            }
        });

        btnTrabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, AgTrabajo.class);
                startActivity(intent);
            }
        });

        btnEstudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, AgEstudios.class);
                startActivity(intent);
            }
        });

        btnOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, AgOtros.class);
                startActivity(intent);
            }
        });

        tvCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id",0);
                editor.putString("nombre",null);
                editor.putString("apellido",null);

                editor.commit();

                Intent intent = new Intent(MenuPrincipal.this, MainActivity.class);
                startActivity(intent);
            }
        });
        MiCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, MiCalendario.class);
                startActivity(intent);
            }
        });

    }
}