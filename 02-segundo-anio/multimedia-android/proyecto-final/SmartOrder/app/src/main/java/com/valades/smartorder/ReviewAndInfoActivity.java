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
        Button btnVisitWebsite = findViewById(R.id.btnVisitWebsite);

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

        // REQUISITO CUMPLIDO: Uso de la clase URI e Intent implícito
        btnVisitWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.com"; // Puedes poner la web de tu restaurante o tu GitHub
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
    }
}