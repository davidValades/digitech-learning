package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Referencias del Header y ScrollView para el efecto elástico
        final LinearLayout layoutHeader = findViewById(R.id.layoutHeader);
        ScrollView detailScrollView = findViewById(R.id.detailScrollView);
        ImageView btnBack = findViewById(R.id.btnBack);

        // Referencias de textos e imagen
        TextView tvDetailName = findViewById(R.id.tvDetailName);
        ImageView ivDetailImage = findViewById(R.id.ivDetailImage); // NUEVA REFERENCIA

        // Referencias de los Checkboxes (Requisito de la rúbrica)
        CheckBox cbNoOnion = findViewById(R.id.cbNoOnion);
        CheckBox cbGlutenFree = findViewById(R.id.cbGlutenFree);
        CheckBox cbExtraBacon = findViewById(R.id.cbExtraBacon);
        final Button btnAddToCart = findViewById(R.id.btnAddToCart);

        // Referencias de la Navegación Inferior
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navScan = findViewById(R.id.navScan);
        // Puedes añadir el navProfile aquí si lo tienes en el XML del detalle

        // --- 1. RECIBIR LOS DATOS REALES DE LA LISTA ---
        String nombrePlato = getIntent().getStringExtra("PRODUCTO_NOMBRE");
        String precioString = getIntent().getStringExtra("PRODUCTO_PRECIO");

        // Valores por defecto por si falla algo
        if (nombrePlato == null) nombrePlato = "Plato Delicioso";
        if (precioString == null) precioString = "10.00 €";

        // Convertimos el precio de texto ("13.50 €") a número (13.50)
        double precioCalculado = 10.00;
        try {
            precioCalculado = Double.parseDouble(precioString.replace(" €", "").replace(",", "."));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- 2. ACTUALIZAR LA PANTALLA ---
        if (tvDetailName != null) {
            tvDetailName.setText(nombrePlato);
        }
        btnAddToCart.setText("Añadir al carrito - " + precioString);

        // --- MAPEO DE LA IMAGEN (Fotorrealismo) ---
        // Buscamos una palabra clave en el nombre para saber qué foto poner
        if (ivDetailImage != null) {
            String nombreMinusculas = nombrePlato.toLowerCase();

            if (nombreMinusculas.contains("nachos")) ivDetailImage.setImageResource(R.drawable.nachos);
            else if (nombreMinusculas.contains("tequeños")) ivDetailImage.setImageResource(R.drawable.tequenos);
            else if (nombreMinusculas.contains("croquetas")) ivDetailImage.setImageResource(R.drawable.croquetas);

            else if (nombreMinusculas.contains("burger")) ivDetailImage.setImageResource(R.drawable.smashburger);
            else if (nombreMinusculas.contains("costillas")) ivDetailImage.setImageResource(R.drawable.costillas);
            else if (nombreMinusculas.contains("tacos")) ivDetailImage.setImageResource(R.drawable.tacos);

            else if (nombreMinusculas.contains("refresco")) ivDetailImage.setImageResource(R.drawable.cola);
            else if (nombreMinusculas.contains("cerveza")) ivDetailImage.setImageResource(R.drawable.cerveza);
            else if (nombreMinusculas.contains("agua")) ivDetailImage.setImageResource(R.drawable.agua);

            else if (nombreMinusculas.contains("césar")) ivDetailImage.setImageResource(R.drawable.ensalada);
            else if (nombreMinusculas.contains("mediterránea")) ivDetailImage.setImageResource(R.drawable.mediterranea);
            else if (nombreMinusculas.contains("cabra")) ivDetailImage.setImageResource(R.drawable.queso_cabra);

            else if (nombreMinusculas.contains("tarta")) ivDetailImage.setImageResource(R.drawable.tarta_queso);
            else if (nombreMinusculas.contains("brownie")) ivDetailImage.setImageResource(R.drawable.brownie);
            else if (nombreMinusculas.contains("tiramisú")) ivDetailImage.setImageResource(R.drawable.tiramisu);

            else ivDetailImage.setImageResource(R.drawable.smashburger); // Imagen por defecto
        }

        // Guardamos las variables como finales para usarlas dentro del botón
        final String nombreFinal = nombrePlato;
        final double precioFinal = precioCalculado;


        // --- 3. LÓGICA DEL BOTÓN AÑADIR (Dinámico y con Animación) ---
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Evaluamos los extras
                boolean isGlutenFree = cbGlutenFree != null && cbGlutenFree.isChecked();

                String nombreA_Guardar = nombreFinal;
                double precioA_Guardar = precioFinal;

                if(isGlutenFree) {
                    nombreA_Guardar += " (Sin Gluten)";
                    precioA_Guardar += 1.50;
                }

                // Guardamos en memoria global
                CartManager.agregarProducto(nombreA_Guardar, precioA_Guardar);

                // EFECTO VISUAL (Animación de Éxito a verde)
                btnAddToCart.setText("¡Añadido con éxito! \u2714");
                btnAddToCart.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#10B981")));

                // Animación de "latido"
                btnAddToCart.animate()
                        .scaleX(1.05f).scaleY(1.05f)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                btnAddToCart.animate().scaleX(1f).scaleY(1f).setDuration(150);
                            }
                        }).start();

                // RETRASO (Handler) antes de volver a la lista
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 800);
            }
        });

        // --- EFECTO HEADER ELÁSTICO ---
        if (detailScrollView != null) {
            detailScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > 10) {
                        layoutHeader.setBackgroundResource(R.drawable.bg_header_flat);
                        layoutHeader.setElevation(8f);
                    } else {
                        layoutHeader.setBackgroundResource(R.drawable.bg_header_curved);
                        layoutHeader.setElevation(0f);
                    }
                }
            });
        }

        // Lógica del botón Atrás
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Lógica del Footer
        if (navHome != null) {
            navHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDetailActivity.this, HomeMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        if (navScan != null) {
            navScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDetailActivity.this, ScanActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}