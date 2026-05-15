package com.valades.smartorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button btnConfirmPayment = findViewById(R.id.btnConfirmPayment);
        RadioGroup rgPaymentMethods = findViewById(R.id.rgPaymentMethods);

        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar qué método está seleccionado (lógica preparatoria para el futuro)
                int selectedId = rgPaymentMethods.getCheckedRadioButtonId();

                // Uso 3/3 de la clase Toast obligatorio
                Toast.makeText(PaymentActivity.this, "Procesando pago seguro...", Toast.LENGTH_SHORT).show();

                // Navegación hacia la pantalla de éxito
                Intent intent = new Intent(PaymentActivity.this, ConfirmationActivity.class);
                startActivity(intent);
                finish(); // Destruimos esta vista para que no vuelva a pagar si pulsa atrás
            }
        });
    }
}