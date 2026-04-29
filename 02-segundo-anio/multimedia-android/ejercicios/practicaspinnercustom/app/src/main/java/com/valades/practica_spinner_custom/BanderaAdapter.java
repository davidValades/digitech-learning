package com.valades.practica_spinner_custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BanderaAdapter extends ArrayAdapter<String> {

    // 2. Variables
    private Context context;
    private String[] nombresPaises;
    private int[] imagenesBanderas;

    public BanderaAdapter(Context context, String[] nombresPaises, int[] imagenesBanderas) {
        super(context, R.layout.item_bandera, nombresPaises);
        this.context = context;
        this.nombresPaises = nombresPaises;
        this.imagenesBanderas = imagenesBanderas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return crearFilaPersonalizada(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return crearFilaPersonalizada(position, convertView, parent);
    }


    private View crearFilaPersonalizada(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View fila = inflater.inflate(R.layout.item_bandera, parent, false);

        ImageView ivBandera = fila.findViewById(R.id.ivBandera);
        TextView tvNombrePais = fila.findViewById(R.id.tvNombrePais);

        tvNombrePais.setText(nombresPaises[position]);
        ivBandera.setImageResource(imagenesBanderas[position]);

        return fila;
    }
}