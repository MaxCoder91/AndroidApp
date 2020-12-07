package com.example.androidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorDatos extends RecyclerView.Adapter<AdaptadorDatos.ViewHoldersDatos> {

    ArrayList<DaoEvento> listaEventos;

    public AdaptadorDatos(ArrayList<DaoEvento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    @NonNull
    @Override
    public AdaptadorDatos.ViewHoldersDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist,null,false);
        return new ViewHoldersDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDatos.ViewHoldersDatos holder, int position) {
        holder.titulo.setText(listaEventos.get(position).getTitulo());
        holder.descripcion.setText(listaEventos.get(position).getDescripcion());
        holder.fecha.setText(listaEventos.get(position).getFecha());
        holder.foto.setImageResource(listaEventos.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        TextView titulo,descripcion, fecha;
        ImageView foto;
        public ViewHoldersDatos(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textView16);
            fecha = itemView.findViewById(R.id.textView15);
            descripcion = itemView.findViewById(R.id.textView17);
            foto = itemView.findViewById(R.id.imageView3);
        }
    }
}
