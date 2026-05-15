package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        final LinearLayout layoutHeader = findViewById(R.id.layoutHeader);
        ScrollView productScrollView = findViewById(R.id.productScrollView);
        ImageView btnBack = findViewById(R.id.btnBack);
        LinearLayout btnHeaderCartContainer = findViewById(R.id.btnHeaderCartContainer);

        TextView tvCategoryTitle = findViewById(R.id.tvCategoryTitle);

        final TextView tvName1 = findViewById(R.id.tvName1); TextView tvPrice1 = findViewById(R.id.tvPrice1);
        final TextView tvName2 = findViewById(R.id.tvName2); TextView tvPrice2 = findViewById(R.id.tvPrice2);
        final TextView tvName3 = findViewById(R.id.tvName3); TextView tvPrice3 = findViewById(R.id.tvPrice3);

        // NUEVO: Referencias para las imágenes dinámicas de los platos
        ImageView ivProduct1 = findViewById(R.id.ivProduct1);
        ImageView ivProduct2 = findViewById(R.id.ivProduct2);
        ImageView ivProduct3 = findViewById(R.id.ivProduct3);

        final LinearLayout cardProduct1 = findViewById(R.id.cardProduct1);
        final LinearLayout cardProduct2 = findViewById(R.id.cardProduct2);
        final LinearLayout cardProduct3 = findViewById(R.id.cardProduct3);

        android.widget.EditText etSearch = findViewById(R.id.etSearch);

        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navScan = findViewById(R.id.navScan);
        LinearLayout navProfile = findViewById(R.id.navProfile);

        String categoria = getIntent().getStringExtra("CATEGORIA");
        if (categoria == null) categoria = "Principales";
        tvCategoryTitle.setText(categoria);

        // --- MAPEO DE TEXTOS E IMÁGENES SEGÚN CATEGORÍA ---
        switch (categoria) {
            case "Entrantes":
                tvName1.setText("Nachos con Guacamole"); tvPrice1.setText("8.50 €");
                ivProduct1.setImageResource(R.drawable.nachos);

                tvName2.setText("Tequeños (6 ud.)"); tvPrice2.setText("7.90 €");
                ivProduct2.setImageResource(R.drawable.tequenos);

                tvName3.setText("Croquetas Caseras"); tvPrice3.setText("9.00 €");
                ivProduct3.setImageResource(R.drawable.croquetas);
                break;

            case "Bebidas":
                tvName1.setText("Refresco de Cola"); tvPrice1.setText("2.50 €");
                ivProduct1.setImageResource(R.drawable.cola);

                tvName2.setText("Cerveza Artesanal"); tvPrice2.setText("4.00 €");
                ivProduct2.setImageResource(R.drawable.cerveza);

                tvName3.setText("Agua Mineral"); tvPrice3.setText("1.80 €");
                ivProduct3.setImageResource(R.drawable.agua);
                break;

            case "Ensaladas":
                tvName1.setText("Ensalada César"); tvPrice1.setText("10.50 €");
                ivProduct1.setImageResource(R.drawable.ensalada);

                tvName2.setText("Ensalada Mediterránea"); tvPrice2.setText("9.50 €");
                ivProduct2.setImageResource(R.drawable.mediterranea);

                tvName3.setText("Ensalada de Queso de Cabra"); tvPrice3.setText("11.00 €");
                ivProduct3.setImageResource(R.drawable.queso_cabra);
                break;

            case "Postres":
                tvName1.setText("Tarta de Queso"); tvPrice1.setText("6.50 €");
                ivProduct1.setImageResource(R.drawable.tarta_queso);

                tvName2.setText("Brownie con Helado"); tvPrice2.setText("5.90 €");
                ivProduct2.setImageResource(R.drawable.brownie);

                tvName3.setText("Tiramisú Casero"); tvPrice3.setText("6.00 €");
                ivProduct3.setImageResource(R.drawable.tiramisu);
                break;

            default: // Principales
                tvName1.setText("Smash Burger Doble"); tvPrice1.setText("13.50 €");
                ivProduct1.setImageResource(R.drawable.smashburger);

                tvName2.setText("Costillas BBQ"); tvPrice2.setText("16.90 €");
                ivProduct2.setImageResource(R.drawable.costillas);

                tvName3.setText("Tacos al Pastor"); tvPrice3.setText("10.50 €");
                ivProduct3.setImageResource(R.drawable.tacos);
                break;
        }

        // --- EFECTO HEADER ELÁSTICO ---
        if(productScrollView != null) {
            productScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        // Intent dinámico hacia la pantalla de detalle
        View.OnClickListener goToDetail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                if (v.getId() == R.id.cardProduct1) {
                    intent.putExtra("PRODUCTO_NOMBRE", tvName1.getText().toString());
                    intent.putExtra("PRODUCTO_PRECIO", tvPrice1.getText().toString());
                } else if (v.getId() == R.id.cardProduct2) {
                    intent.putExtra("PRODUCTO_NOMBRE", tvName2.getText().toString());
                    intent.putExtra("PRODUCTO_PRECIO", tvPrice2.getText().toString());
                } else if (v.getId() == R.id.cardProduct3) {
                    intent.putExtra("PRODUCTO_NOMBRE", tvName3.getText().toString());
                    intent.putExtra("PRODUCTO_PRECIO", tvPrice3.getText().toString());
                }
                startActivity(intent);
            }
        };

        cardProduct1.setOnClickListener(goToDetail);
        cardProduct2.setOnClickListener(goToDetail);
        cardProduct3.setOnClickListener(goToDetail);

        btnHeaderCartContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, HomeMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Buscador en tiempo real
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                if (tvName1.getText().toString().toLowerCase().contains(query)) cardProduct1.setVisibility(View.VISIBLE);
                else cardProduct1.setVisibility(View.GONE);

                if (tvName2.getText().toString().toLowerCase().contains(query)) cardProduct2.setVisibility(View.VISIBLE);
                else cardProduct2.setVisibility(View.GONE);

                if (tvName3.getText().toString().toLowerCase().contains(query)) cardProduct3.setVisibility(View.VISIBLE);
                else cardProduct3.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tvCartBadge = findViewById(R.id.tvCartBadge);
        if (tvCartBadge != null) {
            int totalItems = CartManager.obtenerTotalItems();
            if (totalItems > 0) {
                tvCartBadge.setVisibility(View.VISIBLE);
                tvCartBadge.setText(String.valueOf(totalItems));
            } else {
                tvCartBadge.setVisibility(View.GONE);
            }
        }
    }
}