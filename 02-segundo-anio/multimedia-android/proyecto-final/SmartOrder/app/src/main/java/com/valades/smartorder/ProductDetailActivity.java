package com.valades.smartorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private double precioBaseCalculado = 0.0;
    private List<CheckBoxExtra> listaExtrasDinamicos = new ArrayList<>();

    // Clase auxiliar para guardar la info de cada extra que se genere dinámicamente
    private class CheckBoxExtra {
        CheckBox checkBox;
        String nombreExtra;
        double precioAdicional;

        CheckBoxExtra(CheckBox cb, String nombre, double precio) {
            this.checkBox = cb;
            this.nombreExtra = nombre;
            this.precioAdicional = precio;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        final LinearLayout layoutHeader = findViewById(R.id.layoutHeader);
        ScrollView detailScrollView = findViewById(R.id.detailScrollView);
        ImageView btnBack = findViewById(R.id.btnBack);

        TextView tvDetailName = findViewById(R.id.tvDetailName);
        ImageView ivDetailImage = findViewById(R.id.ivDetailImage);
        final Button btnAddToCart = findViewById(R.id.btnAddToCart);

        // NUEVO CONTENEDOR DONDE SE DIBUJARÁN LOS EXTRAS
        LinearLayout llExtrasContainer = findViewById(R.id.llExtrasContainer);

        // --- 1. RECIBIR LOS DATOS ---
        String nombrePlato = getIntent().getStringExtra("PRODUCTO_NOMBRE");
        String precioString = getIntent().getStringExtra("PRODUCTO_PRECIO");
        String urlImagen = getIntent().getStringExtra("PRODUCTO_IMAGEN");
        String extrasJson = getIntent().getStringExtra("PRODUCTO_EXTRAS");

        // --- SOLUCIÓN AL ERROR DE MEMORIA (TransactionTooLargeException) ---
        // Si la etiqueta dice que usemos el proveedor, sacamos el texto gigante de la memoria
        if ("USE_PROVIDER".equals(urlImagen)) {
            urlImagen = ImageProvider.currentBase64Image;
        }
        // -------------------------------------------------------------------

        if (nombrePlato == null) nombrePlato = "Plato Delicioso";
        if (precioString == null) precioString = "10.00";
        if (extrasJson == null) extrasJson = "[]";

        try {
            precioBaseCalculado = Double.parseDouble(precioString.replace(" €", "").replace(",", "."));
        } catch (Exception e) { e.printStackTrace(); }

        if (tvDetailName != null) tvDetailName.setText(nombrePlato);
        actualizarBotonPrecio(btnAddToCart);

        // --- 2. CARGA DE IMAGEN (BASE64 O PICASSO) ---
        if (ivDetailImage != null && urlImagen != null && !urlImagen.isEmpty()) {
            if (urlImagen.startsWith("data:image")) {
                try {
                    String base64Image = urlImagen.split(",")[1];
                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    ivDetailImage.setImageBitmap(decodedByte);
                } catch (Exception e) { e.printStackTrace(); }
            } else {
                Picasso.get().load(urlImagen).placeholder(R.drawable.bg_header_flat).fit().centerCrop().into(ivDetailImage);
            }
        }

        // --- 3. INFLADO DINÁMICO DE EXTRAS ---
        if (llExtrasContainer != null) {
            try {
                JSONArray arrayExtras = new JSONArray(extrasJson);
                if (arrayExtras.length() == 0) {
                    TextView tvVacio = new TextView(this);
                    tvVacio.setText("Este producto no tiene personalizaciones.");
                    tvVacio.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    llExtrasContainer.addView(tvVacio);
                } else {
                    for (int i = 0; i < arrayExtras.length(); i++) {
                        JSONObject extraObjeto = arrayExtras.getJSONObject(i);
                        String nombreExtra = extraObjeto.getString("name");
                        double precioExtra = extraObjeto.optDouble("price", 0.0);

                        // Crear el CheckBox en tiempo real
                        CheckBox cb = new CheckBox(this);
                        cb.setText(nombreExtra + (precioExtra > 0 ? String.format(" (+%.2f€)", precioExtra) : ""));
                        cb.setTextSize(16);
                        cb.setPadding(0, 10, 0, 10);

                        // Si el usuario lo marca, recalculamos el precio del botón al instante
                        cb.setOnCheckedChangeListener((buttonView, isChecked) -> actualizarBotonPrecio(btnAddToCart));

                        llExtrasContainer.addView(cb);
                        listaExtrasDinamicos.add(new CheckBoxExtra(cb, nombreExtra, precioExtra));
                    }
                }
            } catch (JSONException e) { e.printStackTrace(); }
        }

        final String nombreFinal = nombrePlato;

        // --- 4. LÓGICA DEL BOTÓN AÑADIR ---
        btnAddToCart.setOnClickListener(v -> {
            String nombreA_Guardar = nombreFinal;
            double precioFinalCalculado = precioBaseCalculado;

            // Recorremos la lista para ver qué ha marcado el usuario
            for (CheckBoxExtra extra : listaExtrasDinamicos) {
                if (extra.checkBox.isChecked()) {
                    nombreA_Guardar += "\n  + " + extra.nombreExtra; // Lo tabulamos para que quede bonito en el carrito
                    precioFinalCalculado += extra.precioAdicional;
                }
            }

            CartManager.agregarProducto(nombreA_Guardar, precioFinalCalculado);

            btnAddToCart.setText("¡Añadido con éxito! ✔");
            btnAddToCart.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#10B981")));

            new Handler(Looper.getMainLooper()).postDelayed(this::finish, 800);
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void actualizarBotonPrecio(Button btn) {
        double precioActual = precioBaseCalculado;
        for (CheckBoxExtra extra : listaExtrasDinamicos) {
            if (extra.checkBox.isChecked()) precioActual += extra.precioAdicional;
        }
        btn.setText(String.format("Añadir al carrito - %.2f €", precioActual));
    }
}