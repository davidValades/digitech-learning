package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        final LinearLayout layoutHeader = findViewById(R.id.layoutHeader);
        ScrollView menuScrollView = findViewById(R.id.menuScrollView);
        LinearLayout btnHeaderCartContainer = findViewById(R.id.btnHeaderCartContainer);

        LinearLayout cardStarters = findViewById(R.id.cardStarters);
        LinearLayout cardMains = findViewById(R.id.cardMains);
        LinearLayout cardDrinks = findViewById(R.id.cardDrinks);
        LinearLayout cardSalads = findViewById(R.id.cardSalads);
        LinearLayout cardDesserts = findViewById(R.id.cardDesserts);

        // Referencias del Footer
        LinearLayout navScan = findViewById(R.id.navScan);
        LinearLayout navProfile = findViewById(R.id.navProfile); // <-- ESTO FALTABA

        if (menuScrollView != null) {
            menuScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
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

        View.OnClickListener goToProducts = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, ProductListActivity.class);
                if (v.getId() == R.id.cardStarters) intent.putExtra("CATEGORIA", "Entrantes");
                else if (v.getId() == R.id.cardMains) intent.putExtra("CATEGORIA", "Principales");
                else if (v.getId() == R.id.cardDrinks) intent.putExtra("CATEGORIA", "Bebidas");
                else if (v.getId() == R.id.cardSalads) intent.putExtra("CATEGORIA", "Ensaladas");
                else if (v.getId() == R.id.cardDesserts) intent.putExtra("CATEGORIA", "Postres");
                startActivity(intent);
            }
        };

        cardStarters.setOnClickListener(goToProducts);
        cardMains.setOnClickListener(goToProducts);
        cardDrinks.setOnClickListener(goToProducts);
        cardSalads.setOnClickListener(goToProducts);
        cardDesserts.setOnClickListener(goToProducts);

        btnHeaderCartContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        // <-- ESTA LÓGICA FALTABA PARA IR AL PERFIL
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
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