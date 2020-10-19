package com.example.androidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    String query1 = "CREATE TABLE tblUsuario(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL,apellido TEXT NOT NULL,correo TEXT NOT NULL,pass TEXT NOT NULL)";
    String query2 = "CREATE TABLE tblCategoria(id INTEGER PRIMARY KEY, nombre TEXT)";
    String query3 = "CREATE TABLE tblTipo(id INTEGER PRIMARY KEY, nombre TEXT)";
    String query4 = "CREATE TABLE tblEvento(id INTEGER PRIMARY KEY AUTOINCREMENT,titulo TEXT NOT NULL,descripcion TEXT NOT NULL," +
            "fecha TEXT NOT NULL, hinicio TEXT NOT NULL, hfinal TEXT NOT NULL,"+
            "FOREIGN KEY(idTipo) REFERENCES tblTipo(id),FOREIGN KEY(idCategoria) REFERENCES tblCategoria(id))";
    String query5 = "CREATE TABLE tblUsuarioEvento" +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, FOREIGN KEY(idUsuario) REFERENCES tblUsuario(id),FOREIGN KEY(idEvento) REFERENCES tblEvento(id))";

    //String query4 = "CREATE TABLE tblEvento(id INTEGER PRIMARY KEY AUTOINCREMENT,titulo TEXT NOT NULL,descripcion TEXT NOT NULL," +
    //        "fecha TEXT NOT NULL, hinicio TEXT NOT NULL, hfinal TEXT NOT NULL,"+
    //        "idTipo TEXT,idCategoria TEXT)";

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query2);
        sqLiteDatabase.execSQL(query3);
        sqLiteDatabase.execSQL(query4);
        sqLiteDatabase.execSQL(query5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Es para crear una actualizacion a la base de datos
    }
}
