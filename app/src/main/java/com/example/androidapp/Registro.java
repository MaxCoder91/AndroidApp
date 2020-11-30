package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Registro extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private EditText edtNombre, edtApellido, edtCorreo, edtPass, edtRePass;
    private Button btnRegistrar;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtNombre=findViewById(R.id.editTextTextName);
        edtApellido=findViewById(R.id.editTextTextLastName);
        edtCorreo=findViewById(R.id.editTextTextEmailAddress);
        edtPass=findViewById(R.id.editTextTextPass);
        edtRePass=findViewById(R.id.editTextTextPass2);
        btnRegistrar=findViewById(R.id.buttonReg);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearCta();
            }
        });
    }

    public void crearCta(){
        String nombre,apellido,mail,pass;

        if(edtNombre.getText().toString().trim().equalsIgnoreCase("")){
            edtNombre.setError("Debe ingresar su nombre");
        }else if(edtApellido.getText().toString().trim().equalsIgnoreCase("")){
            edtApellido.setError("Debe ingresar su apellido");
        }else if(edtCorreo.getText().toString().trim().equalsIgnoreCase("")){
            edtCorreo.setError("Debe ingresar un correo");
        }else if(edtPass.getText().toString().trim().equalsIgnoreCase("")){
            edtPass.setError("Debe ingresar una contraseña");
        }else if(edtRePass.getText().toString().trim().equalsIgnoreCase("")){
            edtPass.setError("Debe ingresar una contraseña");
            //else if(edtPass.getText().toString()!=edtRePass.getText().toString()){
            //    edtRePass.setError("La contraseña debe coincidir");
        }else if(!edtPass.getText().toString().trim().equals(edtRePass.getText().toString().trim())){
            edtRePass.setError("La contraseña ingresada no coincide");
        }else{
            nombre = edtNombre.getText().toString();
            apellido = edtApellido.getText().toString();
            mail = edtCorreo.getText().toString();
            pass = edtPass.getText().toString();
            int idRol = 2;

            String url = "http://192.168.0.107:80/serviciosApp/servicioinsertarusuario.php?nombre="+nombre+"&apellido="+apellido+"&correo="+mail+"&pass="+pass+"&idRol="+idRol;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
            requestQueue.add(jsonObjectRequest);

            DbHelper dbHelper = new DbHelper(this,"dbCheckp",null,1);
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            if(sqLiteDatabase!=null){
                ContentValues cvalues = new ContentValues();
                cvalues.put("nombre",nombre);
                cvalues.put("apellido", apellido);
                cvalues.put("correo",mail);
                cvalues.put("pass",pass);
                cvalues.put("idRol",2);
                long nfilas = sqLiteDatabase.insert("tblUsuario",null,cvalues);
                if(nfilas>0){
                    Toast.makeText(this, "Usuario registrado con éxito.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Bienvenido "+nombre+" "+apellido, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Verifique que sus datos estén correctos o no exista una cuenta asociada a su correo.", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Error al crear la bd", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error al sincronizar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Registro sincronizado con éxito", Toast.LENGTH_SHORT).show();
    }
}