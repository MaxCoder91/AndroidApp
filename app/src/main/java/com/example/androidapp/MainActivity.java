package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail, edtPass;
    private Button btnIngresar, btnCrearCta;
    private TextView tvRecPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail=findViewById(R.id.editTextTextEmailAddress);
        edtPass=findViewById(R.id.editTextTextPassword);
        btnIngresar=findViewById(R.id.buttonSignIn);
        btnCrearCta=findViewById(R.id.buttonCreateAccount);
        tvRecPass=findViewById(R.id.textViewForgotPass);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniSesion();
            }
        });
        btnCrearCta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });
        tvRecPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void iniSesion(){
        String mail,pass;
        if(edtEmail.getText().toString().trim().equalsIgnoreCase("")){
            edtEmail.setError("Debe ingresar un correo");
        }else if(edtPass.getText().toString().trim().equalsIgnoreCase("")){
            edtPass.setError("Debe ingresar una contraseña");
        }else{
            mail = edtEmail.getText().toString();
            pass = edtPass.getText().toString();

            Toast.makeText(this, "\nUsuario: "+mail+" \nContraseña: "+pass, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
        }

    }
}