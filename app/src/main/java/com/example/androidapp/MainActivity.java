package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {

    private EditText edtEmail, edtPass;
    private Button btnIngresar, btnCrearCta;
    private TextView tvRecPass;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail=findViewById(R.id.editTextTextEmailAddress);
        edtPass=findViewById(R.id.editTextTextPassword);
        btnIngresar=findViewById(R.id.buttonSignIn);
        btnCrearCta=findViewById(R.id.buttonCreateAccount);
        tvRecPass=findViewById(R.id.textViewForgotPass);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

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
        final String mail,pass;
        if(edtEmail.getText().toString().trim().equalsIgnoreCase("")){
            edtEmail.setError("Debe ingresar un correo");
        }else if(edtPass.getText().toString().trim().equalsIgnoreCase("")){
            edtPass.setError("Debe ingresar una contraseña");
        }else{
            mail = edtEmail.getText().toString();
            pass = edtPass.getText().toString();

            String url = "http://192.168.0.107:80/serviciosApp/servicioinisesion.php?correo="+mail+"&pass="+pass;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
            requestQueue.add(jsonObjectRequest);

            /*
            List<DaoUsuario> tblUsuarios = new ArrayList();
            tblUsuarios = ListarUsuarios();

            String msj="Usuario y/o contraseña incorrecta";

            for (DaoUsuario user : tblUsuarios){
                if(user.getCorreo().equals(mail) && user.getPass().equals(pass)){
                    Intent intent = new Intent(this, MenuPrincipal.class);
                    intent.putExtra("idUser",user.getId());
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
            */
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

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //Toast.makeText(this, "Registro sincronizado con éxito "+response, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        DaoUsuario user = new DaoUsuario();

        JSONArray json = response.optJSONArray("correo");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            /*
            user.setId(jsonObject.optInt("id"));
            user.setNombre(jsonObject.optString("nombre"));
            user.setApellido(jsonObject.optString("apellido"));
            user.setCorreo(jsonObject.optString("correo"));
            user.setPass(jsonObject.optString("pass"));
            user.setIdRol(jsonObject.optInt("idRol"));
            */

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id",jsonObject.optInt("id"));
            editor.putString("nombre",jsonObject.optString("nombre"));
            editor.putString("apellido",jsonObject.optString("apellido"));

            editor.commit();

            Intent intent = new Intent(this, MenuPrincipal.class);
            //intent.putExtra("idUser",user.getId());

            Toast.makeText(this, "Bienvenido "+sharedPreferences.getString("nombre","null")+" "+sharedPreferences.getString("apellido","null"), Toast.LENGTH_SHORT).show();
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}