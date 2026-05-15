package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        CheckBox cbGlutenFree = findViewById(R.id.cbGlutenFree);
        CheckBox cbLactoseFree = findViewById(R.id.cbLactoseFree);
        Button btnAddCart = findViewById(R.id.btnAddCart);

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Evaluamos las preferencias del usuario (podría guardarse en un objeto de pedido)
                boolean isGlutenFree = cbGlutenFree.isChecked();
                boolean isLactoseFree = cbLactoseFree.isChecked();

                // Proporcionamos retroalimentación inmediata sobre la acción
                Toast.makeText(ProductDetailActivity.this, "Plato añadido con éxito al carrito", Toast.LENGTH_SHORT).show();

                // Transición lógica hacia la vista del carrito
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}