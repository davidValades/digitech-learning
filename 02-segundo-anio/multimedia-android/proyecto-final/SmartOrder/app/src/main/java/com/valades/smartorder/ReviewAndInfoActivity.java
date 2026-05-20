package com.valades.smartorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewAndInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_and_info);

        Button btnSubmitReview = findViewById(R.id.btnSubmitReview);
        Button btnVisitarWeb = findViewById(R.id.btnVisitarWeb);

        // Funcionalidad extra: Enviar reseña y volver al inicio
        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReviewAndInfoActivity.this, "¡Gracias por tu valoración!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewAndInfoActivity.this, SplashActivity.class);
                startActivity(intent);
                finishAffinity(); // Cierra todas las activities previas para reiniciar la app
            }
        });

        // LÓGICA DEL BOTÓN URI (Requisito Rúbrica)
        btnVisitarWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Definimos la URL de nuestra Landing Page en Vercel
                String urlOficial = "https://smartorder-two.vercel.app/";

                // 2. Convertimos el texto en un objeto URI
                android.net.Uri paginaWeb = android.net.Uri.parse(urlOficial);

                // 3. Creamos un Intent implícito de tipo ACTION_VIEW
                Intent intentWeb = new Intent(Intent.ACTION_VIEW, paginaWeb);

                // 4. Lanzamos la petición al sistema operativo
                startActivity(intentWeb);
            }
        });
    }
}