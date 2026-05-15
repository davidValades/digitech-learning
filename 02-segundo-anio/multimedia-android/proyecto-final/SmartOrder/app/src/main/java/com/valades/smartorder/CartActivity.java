package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvCartItems = findViewById(R.id.tvCartItems);
        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);
        Button btnProceedToPayment = findViewById(R.id.btnProceedToPayment);
        Button btnKeepShopping = findViewById(R.id.btnKeepShopping);

        // 1. Mostrar los productos que están en memoria
        if (CartManager.productosEnCarrito.isEmpty()) {
            tvCartItems.setText("Tu carrito está vacío.\n¡Anímate a pedir algo!");
            btnProceedToPayment.setEnabled(false); // No puedes pagar si está vacío
        } else {
            // Construimos una lista en texto con todos los productos
            StringBuilder lista = new StringBuilder();
            for (String producto : CartManager.productosEnCarrito) {
                lista.append("• 1x ").append(producto).append("\n");
            }
            tvCartItems.setText(lista.toString());
        }

        // 2. Mostrar el precio total formateado
        tvTotalPrice.setText(String.format("%.2f €", CartManager.precioTotal));

        // 3. Lógica de botones
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        btnKeepShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        btnProceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}