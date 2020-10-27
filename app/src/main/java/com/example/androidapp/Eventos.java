package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Eventos extends AppCompatActivity {

    private Button btnCrearEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        btnCrearEventos= findViewById(R.id.buttonEvents2);

        btnCrearEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Eventos.this,AgEventos.class);
                startActivity(intent);
            }
        });
    }
}