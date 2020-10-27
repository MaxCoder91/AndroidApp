package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
                Intent intent = new Intent(MainActivity.this, RestablecerPass.class);
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

            List<DaoUsuario> tblUsuarios = new ArrayList();
            tblUsuarios = ListarUsuarios();

            String msj="Usuario y/o contraseña incorrecta";

            for (DaoUsuario user : tblUsuarios){
                if(user.getCorreo().equals(mail) && user.getPass().equals(pass)){
                    Intent intent = new Intent(this, MenuPrincipal.class);
                    intent.putExtra("id",user.getId());
                    intent.putExtra("nombre", user.getNombre());
                    intent.putExtra("apellido",user.getApellido());
                    intent.putExtra("correo",user.getCorreo());
                    intent.putExtra("pass",user.getPass());
                    intent.putExtra("idRol",user.getIdRol());
                    msj="Bienvenido "+user.getNombre()+" "+user.getApellido();
                    startActivity(intent);
                }
            }
            Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<DaoUsuario> ListarUsuarios(){
        ArrayList<DaoUsuario>datos= new ArrayList<>();
        DbHelper dbHelper = new DbHelper( this,"dbCheckp",null,1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM tblUsuario";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                DaoUsuario user = new DaoUsuario();
                user.setId(cursor.getInt(0));
                user.setNombre(cursor.getString(1));
                user.setApellido(cursor.getString(2));
                user.setCorreo(cursor.getString(3));
                user.setPass(cursor.getString(4));
                user.setIdRol(cursor.getInt(5));
                datos.add(user);
            }while (cursor.moveToNext());
        }
        return datos;
    }
}