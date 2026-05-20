package com.valades.smartorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Comprobamos la memoria interna
        SharedPreferences prefs = getSharedPreferences("SmartOrderPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("IS_LOGGED_IN", false);

        // Animación de tu logo (Efecto zoom de rebote UX que mencionabas)
        // Supongamos que tienes un ImageView con tu logo llamado ivLogo
    /*
    ivLogo.setScaleX(0f); ivLogo.setScaleY(0f);
    ivLogo.animate().scaleX(1f).scaleY(1f).setDuration(1200).setInterpolator(new android.view.animation.OvershootInterpolator()).start();
    */

        // Delay de 2 segundos para la demo del Splash y ruteo automático
        new android.os.Handler().postDelayed(() -> {
            if (isLoggedIn) {
                // Usuario ya autenticado anteriormente -> Al Hub Principal
                startActivity(new Intent(SplashActivity.this, AppHomeActivity.class));
            } else {
                // Primera vez o sesión cerrada -> Al Login obligatorio
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish(); // Cerramos el Splash para siempre
        }, 2000);
    }
}