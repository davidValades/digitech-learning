package com.valades.smartorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Variable para capturar el correo (Asegúrate de que el ID en tu XML sea etEmail, o cámbialo aquí)
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        ImageButton btnSocialGoogle = findViewById(R.id.btnSocialGoogle);
        ImageButton btnSocialApple = findViewById(R.id.btnSocialApple);

        // Enlazamos el campo de email (si existe en tu vista)
        etEmail = findViewById(R.id.etEmail); // ¡Revisa este ID!

        // 1. Login Normal
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAlHome("Iniciando sesión...");
            }
        });

        // 2. Simulación Login con Google
        btnSocialGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Conectando con los servicios de Google...", Toast.LENGTH_SHORT).show();
                irAlHome("Verificando cuenta...");
            }
        });

        // 3. Simulación Login con Apple (FaceID simulado)
        btnSocialApple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Verificando FaceID de Apple...", Toast.LENGTH_SHORT).show();
                irAlHome("Autorizando...");
            }
        });
    }

    // Método que guarda sesión y viaja al HUB
    private void irAlHome(String mensajeBoton) {
        Button btnLogin = findViewById(R.id.btnLogin);

        // Efecto visual de que está cargando
        btnLogin.setText(mensajeBoton);
        btnLogin.setEnabled(false);
        btnLogin.setAlpha(0.7f);

        // Simulamos que el servidor tarda 1.5 segundos en responder
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                // --- 1. GUARDAR SESIÓN EN MEMORIA (SHAREDPREFERENCES) ---
                SharedPreferences prefs = getSharedPreferences("SmartOrderPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("IS_LOGGED_IN", true);

                // Extraemos el email si el campo no está vacío, sino ponemos uno por defecto
                String correoUsuario = "comensal";
                if (etEmail != null && !etEmail.getText().toString().trim().isEmpty()) {
                    correoUsuario = etEmail.getText().toString().trim();
                }
                editor.putString("USER_EMAIL", correoUsuario);
                editor.apply(); // Guarda de forma asíncrona

                // --- 2. REDIRIGIR AL HUB PRINCIPAL (AppHomeActivity) ---
                Intent intent = new Intent(LoginActivity.this, AppHomeActivity.class);
                // Estos flags destruyen las pantallas anteriores para que al darle a "Atrás" no vuelva al Login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}