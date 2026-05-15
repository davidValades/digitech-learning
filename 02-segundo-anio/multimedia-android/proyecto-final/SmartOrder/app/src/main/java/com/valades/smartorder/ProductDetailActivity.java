package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
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

        // Referencias de los Checkboxes (Requisito de la rúbrica)
        CheckBox cbNoOnion = findViewById(R.id.cbNoOnion);
        CheckBox cbGlutenFree = findViewById(R.id.cbGlutenFree);
        CheckBox cbExtraBacon = findViewById(R.id.cbExtraBacon);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);

        // Referencias de la Navegación Inferior
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navScan = findViewById(R.id.navScan);
        LinearLayout navProfile = findViewById(R.id.navProfile);

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
                finish(); // Vuelve a la lista de productos
            }
        });

        // Lógica del botón Añadir al Carrito con Animación UX
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Guardamos el producto en la memoria global
                boolean isGlutenFree = cbGlutenFree.isChecked();
                String nombrePlato = "Smash Burger Doble";
                double precioPlato = 13.50;

                if(isGlutenFree) {
                    nombrePlato += " (Sin Gluten)";
                    precioPlato += 1.50;
                }

                CartManager.agregarProducto(nombrePlato, precioPlato);

                // 2. EFECTO VISUAL (Animación de Éxito)
                // Cambiamos el texto y el color del botón a verde
                btnAddToCart.setText("¡Añadido con éxito! \u2714"); // \u2714 es el check ✔
                btnAddToCart.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#10B981")));

                // Animación de "latido" (aumenta un poco de tamaño y vuelve)
                btnAddToCart.animate()
                        .scaleX(1.05f).scaleY(1.05f)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                btnAddToCart.animate().scaleX(1f).scaleY(1f).setDuration(150);
                            }
                        }).start();

                // 3. RETRASO (Handler) para que dé tiempo a ver la animación antes de salir
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish(); // Cerramos la ventana después de 800 milisegundos
                    }
                }, 800);
            }
        });

        // --- Lógica del Footer ---
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