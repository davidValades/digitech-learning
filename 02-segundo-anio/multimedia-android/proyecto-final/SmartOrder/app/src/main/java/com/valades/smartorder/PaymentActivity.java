package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ImageView btnBack = findViewById(R.id.btnBack);
        RadioGroup rgPaymentMethods = findViewById(R.id.rgPaymentMethods);
        final Button btnConfirmPayment = findViewById(R.id.btnConfirmPayment);

        // Lógica para volver al carrito
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Lógica de confirmar el pago
        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Verificamos que haya seleccionado un método de pago (Requisito Rúbrica)
                int selectedId = rgPaymentMethods.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(PaymentActivity.this, "Por favor, selecciona un método de pago", Toast.LENGTH_SHORT).show();
                    return; // Cortamos la ejecución si no ha seleccionado nada
                }

                // 2. Animación de "Procesando pago..."
                btnConfirmPayment.setText("Procesando pago...");
                btnConfirmPayment.setEnabled(false); // Deshabilitamos para que no haga doble clic
                btnConfirmPayment.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#10B981"))); // Verde

                // 3. Simulamos un tiempo de carga y vaciamos el carrito
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Vaciamos la memoria del carrito
                        CartManager.vaciarCarrito();

                        // Viajamos a la pantalla de confirmación final
                        Intent intent = new Intent(PaymentActivity.this, ConfirmationActivity.class);
                        // Limpiamos el historial para que si le da atrás no vuelva a la pasarela de pago
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }, 1500); // Retraso de 1.5 segundos para darle realismo
            }
        });
    }
}