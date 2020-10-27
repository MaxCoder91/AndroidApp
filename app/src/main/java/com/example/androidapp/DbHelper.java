package com.example.androidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    String query1 = "CREATE TABLE tblRol(id INTEGER PRIMARY KEY UNIQUE, nombre TEXT)";
    String query2 = "CREATE TABLE tblCategoria(id INTEGER PRIMARY KEY UNIQUE, nombre TEXT)";
    String query3 = "CREATE TABLE tblTipo(id INTEGER PRIMARY KEY UNIQUE, nombre TEXT)";
    String query4 = "CREATE TABLE tblUsuario(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL," +
            "apellido TEXT NOT NULL,correo TEXT UNIQUE NOT NULL,pass TEXT NOT NULL, idRol INTEGER NOT NULL,FOREIGN KEY(idRol) REFERENCES tblRol(id))";
    String query5 = "CREATE TABLE tblEvento(id INTEGER PRIMARY KEY AUTOINCREMENT,titulo TEXT NOT NULL,descripcion TEXT NOT NULL," +
            "fecha TEXT NOT NULL, hinicio TEXT NOT NULL, hfinal TEXT NOT NULL,idTipo INTEGER NOT NULL, idCategoria INTEGER NOT NULL,"+
            "FOREIGN KEY(idTipo) REFERENCES tblTipo(id),FOREIGN KEY(idCategoria) REFERENCES tblCategoria(id))";
    String query6 = "CREATE TABLE tblUsuarioEvento" +
            "(idUsuario INTEGER NOT NULL,idEvento INTEGER NOT NULL, FOREIGN KEY(idUsuario) REFERENCES tblUsuario(id),FOREIGN KEY(idEvento) REFERENCES tblEvento(id),PRIMARY KEY (idUsuario,idEvento))";

    String insert1 = "INSERT INTO tblRol(id,nombre)VALUES(1,'Admin')";
    String insert2 = "INSERT INTO tblRol(id,nombre)VALUES(2,'User')";
    String insert3 = "INSERT INTO tblCategoria(id,nombre)VALUES(1,'Trabajo')";
    String insert4 = "INSERT INTO tblCategoria(id,nombre)VALUES(2,'Estudios')";
    String insert5 = "INSERT INTO tblCategoria(id,nombre)VALUES(3,'Otros')";
    String insert6 = "INSERT INTO tblTipo(id,nombre)VALUES(1,'Privado')";
    String insert7 = "INSERT INTO tblTipo(id,nombre)VALUES(2,'Público')";

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
        sqLiteDatabase.execSQL(query6);

        sqLiteDatabase.execSQL(insert1);
        sqLiteDatabase.execSQL(insert2);
        sqLiteDatabase.execSQL(insert3);
        sqLiteDatabase.execSQL(insert4);
        sqLiteDatabase.execSQL(insert5);
        sqLiteDatabase.execSQL(insert6);
        sqLiteDatabase.execSQL(insert7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Es para crear una actualizacion a la base de datos
    }
}
