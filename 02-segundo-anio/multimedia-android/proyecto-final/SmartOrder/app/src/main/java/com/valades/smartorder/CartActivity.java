package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    private LinearLayout llCartItemsContainer;
    private TextView tvTotalPrice;
    private Button btnProceedToPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Referencias
        ImageView btnBack = findViewById(R.id.btnBack);
        llCartItemsContainer = findViewById(R.id.llCartItemsContainer);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnProceedToPayment = findViewById(R.id.btnProceedToPayment);
        Button btnKeepShopping = findViewById(R.id.btnKeepShopping);

        // Botones de navegación
        btnBack.setOnClickListener(v -> finish());
        btnKeepShopping.setOnClickListener(v -> finish());

        btnProceedToPayment.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            startActivity(intent);
        });

        // Dibujar el carrito por primera vez
        actualizarInterfazCarrito();
    }

    private void actualizarInterfazCarrito() {
        // 1. Limpiamos el contenedor para no duplicar filas
        llCartItemsContainer.removeAllViews();

        if (CartManager.productosEnCarrito.isEmpty()) {
            // Si está vacío, mostramos un mensaje (puedes crear un TextView dinámico)
            TextView emptyMsg = new TextView(this);
            emptyMsg.setText("Tu carrito está vacío");
            emptyMsg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            emptyMsg.setPadding(0, 50, 0, 50);
            llCartItemsContainer.addView(emptyMsg);
            btnProceedToPayment.setEnabled(false);
            btnProceedToPayment.setAlpha(0.5f);
        } else {
            btnProceedToPayment.setEnabled(true);
            btnProceedToPayment.setAlpha(1.0f);

            // 2. Por cada producto en la memoria, "inflamos" una fila
            LayoutInflater inflater = LayoutInflater.from(this);

            for (CartManager.CartItem item : CartManager.productosEnCarrito) {
                View rowView = inflater.inflate(R.layout.item_cart_row, null);

                // Referencias de la fila
                TextView tvName = rowView.findViewById(R.id.tvRowName);
                TextView tvPrice = rowView.findViewById(R.id.tvRowPrice);
                TextView tvQty = rowView.findViewById(R.id.tvRowQuantity);
                Button btnPlus = rowView.findViewById(R.id.btnPlus);
                Button btnMinus = rowView.findViewById(R.id.btnMinus);

                // Datos
                tvName.setText(item.nombre);
                tvPrice.setText(String.format("%.2f €", item.precioBase * item.cantidad));
                tvQty.setText(String.valueOf(item.cantidad));

                // Lógica de los botones dinámicos
                btnPlus.setOnClickListener(v -> {
                    CartManager.agregarProducto(item.nombre, item.precioBase);
                    actualizarInterfazCarrito(); // Refrescamos la pantalla
                });

                btnMinus.setOnClickListener(v -> {
                    CartManager.quitarProducto(item.nombre);
                    actualizarInterfazCarrito(); // Refrescamos la pantalla
                });

                // Añadimos la fila al contenedor principal
                llCartItemsContainer.addView(rowView);
            }
        }

        // 3. Actualizamos el precio total de la pantalla
        tvTotalPrice.setText(String.format("%.2f €", CartManager.obtenerPrecioTotal()));
    }
}