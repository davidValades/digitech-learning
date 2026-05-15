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
        TextView tvName1 = findViewById(R.id.tvName1); TextView tvPrice1 = findViewById(R.id.tvPrice1);
        TextView tvName2 = findViewById(R.id.tvName2); TextView tvPrice2 = findViewById(R.id.tvPrice2);
        TextView tvName3 = findViewById(R.id.tvName3); TextView tvPrice3 = findViewById(R.id.tvPrice3);

        LinearLayout cardProduct1 = findViewById(R.id.cardProduct1);
        LinearLayout cardProduct2 = findViewById(R.id.cardProduct2);
        LinearLayout cardProduct3 = findViewById(R.id.cardProduct3);

        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navScan = findViewById(R.id.navScan);

        String categoria = getIntent().getStringExtra("CATEGORIA");
        if (categoria == null) categoria = "Principales";
        tvCategoryTitle.setText(categoria);

        switch (categoria) {
            case "Entrantes":
                tvName1.setText("Nachos con Guacamole"); tvPrice1.setText("8.50 €");
                tvName2.setText("Tequeños (6 ud.)"); tvPrice2.setText("7.90 €");
                tvName3.setText("Croquetas Caseras"); tvPrice3.setText("9.00 €");
                break;
            case "Bebidas":
                tvName1.setText("Refresco de Cola"); tvPrice1.setText("2.50 €");
                tvName2.setText("Cerveza Artesanal"); tvPrice2.setText("4.00 €");
                tvName3.setText("Agua Mineral"); tvPrice3.setText("1.80 €");
                break;
            case "Ensaladas":
                tvName1.setText("Ensalada César"); tvPrice1.setText("10.50 €");
                tvName2.setText("Ensalada Mediterránea"); tvPrice2.setText("9.50 €");
                tvName3.setText("Ensalada de Queso de Cabra"); tvPrice3.setText("11.00 €");
                break;
            case "Postres":
                tvName1.setText("Tarta de Queso"); tvPrice1.setText("6.50 €");
                tvName2.setText("Brownie con Helado"); tvPrice2.setText("5.90 €");
                tvName3.setText("Tiramisú Casero"); tvPrice3.setText("6.00 €");
                break;
            default:
                tvName1.setText("Smash Burger Doble"); tvPrice1.setText("13.50 €");
                tvName2.setText("Costillas BBQ"); tvPrice2.setText("16.90 €");
                tvName3.setText("Tacos al Pastor"); tvPrice3.setText("10.50 €");
                break;
        }

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

        View.OnClickListener goToDetail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tvCartBadge = findViewById(R.id.tvCartBadge);
        if (tvCartBadge != null) {
            int totalItems = CartManager.productosEnCarrito.size();
            if (totalItems > 0) {
                tvCartBadge.setVisibility(View.VISIBLE);
                tvCartBadge.setText(String.valueOf(totalItems));
            } else {
                tvCartBadge.setVisibility(View.GONE);
            }
        }
    }
}