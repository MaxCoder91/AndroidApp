package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {

    private Button btnEventos, btnTrabajo, btnEstudio, btnOtros;
    private TextView tvCerrarSesion, MiCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnEventos=findViewById(R.id.buttonEvents);
        btnTrabajo=findViewById(R.id.buttonJobs);
        btnEstudio=findViewById(R.id.buttonStudy);
        btnOtros=findViewById(R.id.buttonOthers);
        tvCerrarSesion=findViewById(R.id.textViewClose);
        MiCalendario=findViewById((R.id.calendarIcon));

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
                Intent intent = new Intent(MenuPrincipal.this, AgTrabjo.class);
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