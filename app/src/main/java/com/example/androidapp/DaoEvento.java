package com.example.androidapp;

public class DaoEvento {

    private int Id;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String hinicio;
    private String hfinal;
    private int idTipo;
    private int idCategoria;
    private int foto;

    public DaoEvento() {
    }

    public DaoEvento(int id, String titulo, String descripcion, String fecha, String hinicio, String hfinal, int idTipo, int idCategoria, int foto) {
        Id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hinicio = hinicio;
        this.hfinal = hfinal;
        this.idTipo = idTipo;
        this.idCategoria = idCategoria;
        this.foto = foto;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHinicio() {
        return hinicio;
    }

    public void setHinicio(String hinicio) {
        this.hinicio = hinicio;
    }

    public String getHfinal() {
        return hfinal;
    }

    public void setHfinal(String hfinal) {
        this.hfinal = hfinal;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
